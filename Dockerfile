FROM openjdk:11.0.5-jdk
MAINTAINER Marius Sarpe <sarpe36@gmail.com>
ADD ./target/intellst-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-jar", "/app/intellst-0.0.1-SNAPSHOT.jar"]
EXPOSE 8083