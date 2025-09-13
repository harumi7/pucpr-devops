# Use a Maven base image to build your application
FROM maven:3.8.3-openjdk-11 AS build

# Set the working directory
WORKDIR /app

# Copy the project files
COPY . .

# Run the Maven build command
RUN mvn clean package shade:shade

# Use a lighter base image for the final runtime
FROM openjdk:11-jre-slim

# Set the working directory to where the JAR will be located
# This is crucial for the CMD command to find the JAR file
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run the application
CMD ["java", "-jar", "app.jar"]
