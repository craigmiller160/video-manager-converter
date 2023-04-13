FROM openjdk:19-jdk-alpine

RUN mkdir -p /opt/videos
RUN mkdir -p /opt/video-manager/homeDir
COPY build/video-manager-converter-*.jar /video-manager-converter.jar
COPY ./ffmpeg /tmp/jave/ffmpeg-amd64-3.3.1
RUN chmod 777 /tmp/jave/ffmpeg-amd64-3.3.1

#RUN apk update
#RUN apk add ffmpeg

ENTRYPOINT ["java", "-jar", "/video-manager-converter.jar"]