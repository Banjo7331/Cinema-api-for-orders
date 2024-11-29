FROM openjdk:17

ARG JAR_FILE=target/Cinema-api-for-orders-0.0.1-SNAPSHOT.jar

WORKDIR /app

COPY ${JAR_FILE} app.jar

COPY ./src/main/resources/application.properties application.properties

COPY .env .env

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.config.location=/app/application.properties", "-jar", "app.jar"]

#FROM openjdk:17-jdk
#
#ARG JAR_FILE=target/Cinema-api-for-orders-0.0.1-SNAPSHOT.jar
## Set the working directory
#WORKDIR /app
## Copy the Spring Boot application JAR
#COPY ${JAR_FILE} app.jar
#
## Expose the application port
#EXPOSE 8080
#
## Run the application
#ENTRYPOINT ["java", "-jar", "app.jar"]