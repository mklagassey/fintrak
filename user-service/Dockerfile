# Stage 1: Build the application using Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the POM file first to leverage Docker layer caching
COPY pom.xml .

# Copy the rest of the user-service source code
COPY src ./src

# Build the application JAR
RUN mvn clean install -DskipTests

# Stage 2: Create the final, slim image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the built JAR from the 'build' stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]