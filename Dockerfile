FROM eclipse-temurin:17
LABEL authors="mchlm"
ARG JAR_FILE=target/*.jar
COPY ./target/D387_sample_code-0.0.2-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]