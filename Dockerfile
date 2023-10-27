FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/BlogWebsite-0.0.1-SNAPSHOT.jar /BlogWebsite-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/BlogWebsite-0.0.1-SNAPSHOT.jar"]