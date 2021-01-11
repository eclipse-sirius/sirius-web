FROM openjdk:15-jdk-alpine
COPY target/sirius-web-test-sample-application-*.jar ./app.jar
EXPOSE 8080
RUN adduser -D myuser
USER myuser
CMD [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]