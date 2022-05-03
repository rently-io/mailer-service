FROM openjdk:17
ADD target/mailerservice.jar mailerservice.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","/mailerservice.jar"]