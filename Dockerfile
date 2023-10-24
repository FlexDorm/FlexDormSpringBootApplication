FROM maven:3.8.4-openjdk-17 AS build
COPY . .
RUN mvn clean package

FROM openjdk:17.0.2-slim-buster
COPY --from=build /target/FlexDormSpringBootApplication-0.0.1-SNAPSHOT.jar FlexDormSpringBootApplication-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "FlexDormSpringBootApplication-0.0.1-SNAPSHOT.jar"]
