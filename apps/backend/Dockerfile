FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /backend/

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy

WORKDIR /backend


RUN apt-get update && \
    apt-get install -y haveged && \
    update-rc.d haveged defaults && \
    rm -rf /var/lib/apt/lists/*

COPY --from=builder /backend/target/*.jar backend.jar

EXPOSE 8082
CMD ["sh", "-c", "service haveged start && java ${JAVA_OPTS} -jar backend.jar"]
