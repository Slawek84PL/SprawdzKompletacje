# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY . ./
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#COPY frontend frontend
#COPY pliki pliki
#CMD ./mvn dependency:resolve
#
#COPY src ./src
EXPOSE $PORT:$PORT
CMD ["./mvnw", "spring-boot:run", "-Dserver.port=$PORT"]