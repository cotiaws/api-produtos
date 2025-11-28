FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . /app
RUN ./mvnw -B clean package -DskipTests
EXPOSE 8082
CMD ["java", "-jar", "target/api-produtos-0.0.1-SNAPSHOT.jar"]