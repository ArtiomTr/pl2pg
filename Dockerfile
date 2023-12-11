FROM maven:3.9.5-eclipse-temurin-8-alpine

WORKDIR /home/worker

COPY pom.xml .

RUN mvn dependency:go-offline -B

copy ./src ./src/

RUN mvn
