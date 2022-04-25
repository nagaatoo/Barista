FROM openjdk:17-oracle

COPY ./build/libs/barista-0.0.1-SNAPSHOT.jar ./app/barista.jar
EXPOSE 8551
ENTRYPOINT ["java", "-jar", "./app/barista.jar"]
