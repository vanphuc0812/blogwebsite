FROM maven:3.8.4-openjdk-17 as BUILD
COPY . .
RUN mvn clean package -DskipTests

WORKDIR /app
COPY target/BlogWebsite-0.0.1-SNAPSHOT.jar /BlogWebsite-0.0.1-SNAPSHOT.jar
COPY images .
ENTRYPOINT ["java","-jar","/BlogWebsite-0.0.1-SNAPSHOT.jar"]
