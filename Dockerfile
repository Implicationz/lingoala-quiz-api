# Stage 1: Build the app using Maven
FROM maven:3.9.7-eclipse-temurin-22-alpine AS builder

# Create and set working directory
WORKDIR /app

# Copy the entire source code
COPY . .

# Build the JAR (skip tests for faster CI builds)
RUN mvn clean package -DskipTests

# Stage 2: Run the built app with JDK
FROM openjdk:21-jdk

# Set working directory
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
