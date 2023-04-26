# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

COPY pliki pliki
CMD ./mvn dependency:resolve
COPY target/ target
COPY src ./src


CMD java -jar -Dspring.profiles.active=prod target/SprawdzKompletacje-0.0.1-SNAPSHOT.jar