
FROM openjdk:17-alpine
ADD target/orderQueema-0.0.1-SNAPSHOT.jar queema.jar
ENTRYPOINT [ "java","-jar","queema.jar" ]
EXPOSE 8093

#12365motaha  -> parent user