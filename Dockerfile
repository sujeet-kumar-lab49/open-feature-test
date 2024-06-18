FROM amazoncorretto:21
ADD target/feature-0.0.1-SNAPSHOT.jar feature-test.jar
ENTRYPOINT ["java", "-jar", "/feature-test.jar"]