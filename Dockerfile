FROM openjdk:8-jdk-alpine
EXPOSE 8080
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
ADD target/app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]