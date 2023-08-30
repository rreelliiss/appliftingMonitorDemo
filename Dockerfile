FROM eclipse-temurin:17-jdk-alpine

COPY target/*.jar application.jar
CMD java -jar application.jar


