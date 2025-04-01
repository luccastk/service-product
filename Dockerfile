FROM maven:3.8.3-openjdk-17 AS builder

LABEL authors="Japa2k"

WORKDIR /app
COPY . .
RUN mvn clean package -f pom.xml -DskipTests

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY --from=builder /app/target/products-0.0.1-SNAPSHOT.jar /app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

ENTRYPOINT ["java", "-jar", "/app.jar"]
