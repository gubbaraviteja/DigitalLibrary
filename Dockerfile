FROM openjdk:8-jdk-alpine
LABEL maintainer="gubbaraviteja@gmail.com"
VOLUME /tmp
EXPOSE 9000
ARG JAR_FILE=target/libraryApi-0.0.1.jar
COPY ${JAR_FILE} digital-library-api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/digital-library-api.jar"]