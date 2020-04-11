FROM openjdk:14-jdk-alpine
VOLUME /tmp
COPY target/employees-command.jar app.jar
ENTRYPOINT ["java","--enable-preview","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]