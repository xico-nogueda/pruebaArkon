FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/pruebaArkon-0.0.1.jar
COPY ${JAR_FILE} app_arkon.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_arkon.jar"]