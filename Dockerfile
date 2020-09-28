FROM openjdk:11.0.5-jdk
MAINTAINER Marius Sarpe <sarpe36@gmail.com>
COPY ./target/classes/lib/libopencv_java450.so /run
ADD ./target/intellst-0.0.1-SNAPSHOT.jar /run/intellst-0.0.1-SNAPSHOT.jar
ENTRYPOINT  ["java", "-Djava.library.path=/run", "-jar", "/run/intellst-0.0.1-SNAPSHOT.jar", "-D exec.mainClass=com.recognition.intellst.IntellStApplication"]
EXPOSE 8084
