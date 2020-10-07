FROM ubuntu:18.04
MAINTAINER Marius Sarpe <sarpe36@gmail.com>
RUN apt-get -y update
RUN apt-get -y upgrade
RUN apt-get install -y ffmpeg
RUN apt-get install -y openjdk-11-jre openjdk-11-jdk
COPY ./target/classes/lib/libopencv_java450.so /run
COPY ./target/intellst-0.0.1-SNAPSHOT.jar /run
ENTRYPOINT  ["java", "-Djava.library.path=/run", "-jar", "/run/intellst-0.0.1-SNAPSHOT.jar", "-D exec.mainClass=com.recognition.intellst.IntellStApplication"]
EXPOSE 8084
