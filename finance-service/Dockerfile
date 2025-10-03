# ===================================================================
# Dockerfile for the FinTrack Finance Service
# ===================================================================
# This Dockerfile uses a multi-stage build for a smaller, more secure final image.

# --- Build Stage ---
# Use a specific Maven version with a JDK to build the application JAR.
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
# Copy the pom.xml file first to leverage Docker's layer caching.
# This way, dependencies are only re-downloaded if the pom.xml changes.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code and build the application.
COPY src ./src
RUN mvn clean package -DskipTests

# --- Run Stage ---
# Use a minimal Java runtime image for the final container.
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copy the built JAR file from the 'build' stage.
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application will run on.
# The actual port will be set by the PORT environment variable in Cloud Run.
EXPOSE 8082

# Command to run the application when the container starts.
ENTRYPOINT ["java", "-jar", "app.jar"]
