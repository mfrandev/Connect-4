# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy

COPY Game/ Game/
COPY AI/ AI/
COPY Network/ Network/

RUN ["javac", "Game/Main.java"]
ENTRYPOINT ["java", "Game/Main"]