FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/online-learning-0.0.1-SNAPSHOT.jar online-learning.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "online-learning.jar"]
