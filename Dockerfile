FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY . /app
RUN chmod +x mvnw
RUN ./mvnw package
CMD ["java", "-jar", "target/personal-collection-management.jar"]