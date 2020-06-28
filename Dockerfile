FROM maven:latest AS builder
WORKDIR /bigdata/
COPY . .
RUN mvn clean package

FROM openjdk:8-jdk-alpine
COPY --from=builder /bigdata/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


