# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY frontend frontend
COPY pliki pliki
CMD ./mvn dependency:resolve

COPY src ./src
COPY target ./target

CMD ["./mvnw", "spring-boot:run", "-Pproduction"]
RUN ["java -jar SprawdzKompletacje-0.0.1-SNAPSHOT.jar"]
