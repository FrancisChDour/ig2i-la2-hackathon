FROM openjdk:15-slim
RUN groupadd spring && useradd -g spring spring
USER spring:spring
ARG JAR_FILE=./ig2i-lady-diary-backend/target/*.jar
RUN echo ${JAR_FILE}
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=h2","-jar","/app.jar"]
