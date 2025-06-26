FROM openjdk:21-jdk-slim
ADD ./target/eureka-0.0.1-SNAPSHOT.jar eureka.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eureka.jar"]