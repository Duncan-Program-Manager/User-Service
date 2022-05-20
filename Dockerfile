FROM openjdk:11 as rabbitmq
EXPOSE 8083 3306
ADD target/User-API-0.0.1-SNAPSHOT.jar userapi-docker.jar
ENTRYPOINT ["java","-jar","userapi-docker.jar"]