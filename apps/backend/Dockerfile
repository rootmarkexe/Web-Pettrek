From eclipse-temurin:23-jdk-jammy as builder

WORKDIR backend/

COPY src .
COPY pom.xml .

RUN mvn dependency:go-offline -B
RUN mvn clean package -DskipTests

FROM eclipse-temurin:23-jre-jammy

WORKDIR backend

COPY --from=builder /backend/target/*.jar backend.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend.jar"]
