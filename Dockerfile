FROM openjdk:8-jdk-alpine
MAINTAINER Haridas Parekh

VOLUME /tmp
EXPOSE 8080
ADD build/libs/note-service-0.0.1-SNAPSHOT.jar note-service.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/note-service.jar"]