FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY mvnw .
COPY .mvn/ .mvn/
COPY pom.xml .

RUN chmod +x mvnw \
 && ./mvnw dependency:go-offline -B

COPY src/ src/
RUN ./mvnw clean package -DskipTests -B

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY Wallet_MJVBC32FWKXL3WN2 /app/Wallet_MJVBC32FWKXL3WN2

ENV TNS_ADMIN=app/Wallet_MJVBC32FWKXL3WN2

COPY --from=build /app/target/ms_solicitudes_dyc-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT [ "java", "-jar", "app.jar" ]