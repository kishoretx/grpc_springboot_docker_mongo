# Use the official OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app


# Copy the JAR file into the container
COPY target/grpc_springboot_docker_mongo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that Spring Boot application runs on
EXPOSE 9090

# Command to run the application
CMD ["java", "-jar", "app.jar"]
