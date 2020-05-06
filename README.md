# FilteringPhoneSubscriptionApi
A basic Dockerized Springboot Maven application, with a single REST API endpoint


**Documentation for FilteringPhoneSubscriptionApi:**

1. Download FilteringPhoneSubscriptionApi.zip file.


2. I used h2 in-memory database, in order to have access to database, just use the link: "http://localhost:8080/h2-console" 
NOTE: (JDBC URL:jdbc:h2:mem:testdb) (User Name: sa) (Password: )(There is no need to password)


3. In order to build and run the application using maven:
``` 
mvn package
java -jar target/assignment.api-0.0.1-SNAPSHOT.jar
``` 

4.In order to using docker:

docker image has been built, you can pull it and run it through below commands:
``` 
docker pull saeidehdocker/telenorapp
docker run -p 8080:8080 saeidehdocker/telenorapp 
``` 

5. In order to make a request and verify the behaviour either by postman or browser you can use:

http://localhost:8080/loadData --> to Load data

http://localhost:8080/product --> to make a query

In postman:
GET /product ==> to get all products by any combination of params
