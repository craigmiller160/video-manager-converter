package io.craigmiller160.videomanagerconverter.service

import io.craigmiller160.videomanagerconverter.domain.entity.FileToConvert
import org.slf4j.LoggerFactory
import ws.schild.jave.Encoder
import ws.schild.jave.MultimediaObject
import ws.schild.jave.encode.AudioAttributes
import ws.schild.jave.encode.EncodingAttributes
import ws.schild.jave.encode.VideoAttributes
import ws.schild.jave.encode.enums.X264_PROFILE
import ws.schild.jave.info.MultimediaInfo
import ws.schild.jave.progress.EncoderProgressListener
import java.nio.file.Paths
import java.util.concurrent.atomic.AtomicLong
import kotlin.io.path.deleteIfExists
import kotlin.io.path.exists

class FileConverterImpl(
    private val rootDir: String,
    private val file: FileToConvert
) : FileConverter {
    private val encoder = Encoder()
    private val log = LoggerFactory.getLogger(javaClass)
    override fun convert(): Result<Unit> =
        runCatching {
            val targetFile = Paths.get(rootDir, file.targetFile)
            if (targetFile.exists()) {
                log.error("Target file exists, aborting conversion. File: {}", targetFile)
                return@runCatching
            }

            val sourceFile = Paths.get(rootDir, file.sourceFile)
            val mediaSource = sourceFile.toFile().let { MultimediaObject(it) }
            val (audio, video) = mediaSource.let { getAudioVideoAttributes(it.info) }

            val attrs =
                EncodingAttributes().apply {
                    //      setInputFormat("matroska,webm")
                    setOutputFormat("mp4")
                    setAudioAttributes(audio)
                    setVideoAttributes(video)
                }
            encoder.encode(mediaSource, targetFile.toFile(), attrs, FileConverterListener(file.sourceFile, file.targetFile))
            if (!sourceFile.deleteIfExists()) {
                log.error("Unable to delete source file: {}", sourceFile)
            }
        }
}

private class FileConverterListener(
    private val sourceFile: String,
    private val targetFile: String
) : EncoderProgressListener {
    private val log = LoggerFactory.getLogger(javaClass)
    private val counter = AtomicLong(0)
    override fun sourceInfo(info: MultimediaInfo) {}
    override fun progress(permil: Int) {
        if (counter.incrementAndGet() % 20 == 0L) {
            log.debug("Conversion in progress. Source: {}, Target: {}", sourceFile, targetFile)
        }
    }
    override fun message(message: String) {
        log.warn("Encoder warning: {}", message)
    }
}

private fun getAudioVideoAttributes(
    info: MultimediaInfo
): Pair<AudioAttributes, VideoAttributes> {
    val audio =
        AudioAttributes().apply {
            val codec = getAudioCodec(info)
            setCodec(codec.codecString)
            if (info.audio.bitRate > 0) {
                setBitRate(info.audio.bitRate)
            }
            setChannels(info.audio.channels)
            setSamplingRate(info.audio.samplingRate)
        }

    val video =
        VideoAttributes().apply {
            val codec = getVideoCodec(info)
            setCodec(codec.codecString)
            if (VideoCodec.H264 == codec) {
                setX264Profile(X264_PROFILE.BASELINE)
            }
            if (info.video.bitRate > 0) {
                setBitRate(info.video.bitRate)
            }
            setFrameRate(info.video.frameRate.toInt())
            setSize(info.video.size)
        }

    return audio to video
}

private fun getAudioCodec(info: MultimediaInfo): AudioCodec =
    AudioCodec.values().find { codec -> codec.regex.matches(info.audio.decoder) }
        ?: throw UnsupportedOperationException("Unsupported audio codec: ${info.audio.decoder}")

private fun getVideoCodec(info: MultimediaInfo): VideoCodec =
    VideoCodec.values().find { codec -> codec.regex.matches(info.video.decoder) }
        ?: throw UnsupportedOperationException("Unsupported video codec: ${info.video.decoder}")

private enum class AudioCodec(val codecString: String, val regex: Regex) {
    AAC("aac", Regex("^.*aac.*$"))
}

private enum class VideoCodec(val codecString: String, val regex: Regex) {
    H265("hevc", Regex("^.*hevc.*$")),
    H264("h264", Regex("^$"))
}