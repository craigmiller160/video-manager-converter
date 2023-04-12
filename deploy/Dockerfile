FROM openjdk:19-jdk-alpine

RUN mkdir -p /opt/videos
RUN mkdir -p /opt/video-manager/homeDir
COPY build/video-manager-converter-*.jar /video-manager-converter.jar

ENTRYPOINT ["java", "-jar", "/video-manager-converter.jar"]