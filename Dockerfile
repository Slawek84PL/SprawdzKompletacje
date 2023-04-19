# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

#COPY . ./
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
#COPY frontend frontend
COPY pliki pliki
CMD ./mvn dependency:resolve
COPY target/ target
#COPY src ./src
#EXPOSE 8080:8080
CMD ["./mvnw", "spring-boot:run"]
#CMD ["java -jar app\target\SprawdzKompletacje-0.0.1-SNAPSHOT.jar"]