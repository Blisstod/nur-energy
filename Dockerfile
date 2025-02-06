FROM gradle:7.5-jdk17 AS build
WORKDIR /app

# Copy Gradle configuration and wrapper files first (for caching)
COPY gradlew ./
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Ensure Gradle wrapper is executable, then download dependencies
RUN chmod +x gradlew
RUN ./gradlew --version

# Now copy the rest of the project
COPY . .

# Build the Spring Boot JAR (adjust the task if necessary)
RUN ./gradlew clean bootJar --no-daemon

# ---- Stage 2: Create the minimal runtime image ----
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port (optional)
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
