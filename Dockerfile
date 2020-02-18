FROM openjdk:8-alpine

# Required for starting application up.
RUN apk update && apk add bash

CMD ["java", "-Dspring.data.mongodb.uri=mongodb://springboot-mongo:27017/boliche","-Djava.security.egd=file:/dev/./urandom","-jar","./target/teste-boliche-1.0-SNAPSHOT.jar"]
