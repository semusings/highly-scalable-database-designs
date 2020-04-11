FROM openjdk:14-jdk-alpine
VOLUME /tmp
COPY target/employees-command-1.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","--enable-preview","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]