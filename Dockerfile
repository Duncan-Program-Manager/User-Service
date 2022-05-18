FROM openjdk:11
ADD target/User-API-0.0.1-SNAPSHOT.jar userapi-docker.jar
ENTRYPOINT ["java","-jar","userapi-docker.jar"]