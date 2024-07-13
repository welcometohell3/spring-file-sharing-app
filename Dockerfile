FROM openjdk:17 as build
COPY target/FileSharing-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]
