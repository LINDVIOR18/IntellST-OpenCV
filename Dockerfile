FROM ubuntu:18.04
MAINTAINER Marius Sarpe <sarpe36@gmail.com>

RUN apt-get -y update &&\
    apt-get -y upgrade &&\
    apt-get install -y git &&\
    apt-get install -y ffmpeg &&\
    apt-get install -y default-jre &&\
    apt-get install -y default-jdk

RUN git clone https://github.com/LINDVIOR18/OpenCV-4.5.0-FOR-JAVA.git
COPY ./target/intellst-0.0.1-SNAPSHOT.jar /run
ENTRYPOINT  ["java", "-Djava.library.path=/OpenCV-4.5.0-FOR-JAVA/", "-jar", "/run/intellst-0.0.1-SNAPSHOT.jar", "-D exec.mainClass=com.recognition.intellst.IntellStApplication"]
EXPOSE 8084
