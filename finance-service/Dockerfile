# ===================================================================
# Dockerfile for the FinTrack Finance Service
# ===================================================================
# This is a single-stage build that packages the pre-built JAR file.
# It aligns with the project's Cloud Build pipeline configuration.

# Use a minimal Java runtime image for the final container.
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the pre-built JAR file from the target directory into the container.
# This assumes the JAR has been built by a previous step (like in cloudbuild.yaml).
COPY target/*.jar app.jar

# Expose the port the application will run on.
# Cloud Run will set the PORT environment variable.
EXPOSE 8082

# Command to run the application when the container starts.
ENTRYPOINT ["java", "-jar", "app.jar"]

