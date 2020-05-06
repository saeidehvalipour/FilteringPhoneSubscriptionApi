FROM openjdk:8-jdk-alpine
EXPOSE 8080
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
ADD target/assignment.api-0.0.1-SNAPSHOT.jar telenorapp.jar
ENTRYPOINT ["java","-jar","telenorapp.jar"]