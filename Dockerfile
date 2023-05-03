# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY target/ target

CMD java -jar -Dspring.profiles.active=prod -Dserver.port=$PORT target/SprawdzKompletacje-0.0.1-SNAPSHOT.jar