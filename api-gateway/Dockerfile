# Stage 1: Final, slim image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the pre-built JAR file from the target directory
COPY target/*.jar app.jar

# Expose the port Cloud Run provides
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]