#start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

#Information around who maintains the image
MAINTAINER eazybytes.com

#Add the application;s jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar account-0.0.1-SNAPSHOT.jar

#command to execute the application
ENTRYPOINT ["java", "-jar", "account-0.0.1-SNAPSHOT.jar"]