FROM openjdk:17.0.1-jdk-slim
VOLUME /tmp
COPY build/libs/guide-1.0.jar Guide.jar
ENTRYPOINT ["java","-jar","Guide.jar"]