FROM maven:3.9.6 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM maven:3.9.6
WORKDIR /app
COPY --from=build /app/target/trading_demo-0.0.1-SNAPSHOT.jar /app/demo.jar
ENTRYPOINT ["java", "-jar", "/app/demo.jar"]
