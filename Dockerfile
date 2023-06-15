FROM openjdk:11
WORKDIR /app
COPY build/libs/chat-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "app.jar"]
