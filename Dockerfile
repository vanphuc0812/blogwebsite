FROM eclipse-temurin:17
WORKDIR /app
COPY target/BlogWebsite-0.0.1-SNAPSHOT.jar /BlogWebsite-0.0.1-SNAPSHOT.jar
COPY images .
ENTRYPOINT ["java","-jar","/BlogWebsite-0.0.1-SNAPSHOT.jar"]