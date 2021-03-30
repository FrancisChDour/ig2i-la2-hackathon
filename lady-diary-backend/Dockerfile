FROM openjdk:15-slim
RUN groupadd spring && useradd -g spring spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=h2","-jar","/app.jar"]