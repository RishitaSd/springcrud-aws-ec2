# Use a Maven base image to build the application
FROM maven:3.8.1-openjdk-17-slim AS build

# Set the working directory in the build container
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy the source code into the container
COPY src ./src

# Build the application
RUN mvn clean package

# Use a smaller OpenJDK image to run the app
FROM openjdk:17-jdk-slim

# Set the working directory for the runtime container
WORKDIR /app

# Copy the JAR file from the build container to the runtime container
COPY --from=build /app/target/springcruddemo-0.1.jar /app/springcruddemo-0.1.jar

# Expose the port the application will run on
EXPOSE 8090

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/springcruddemo-0.1.jar"]

