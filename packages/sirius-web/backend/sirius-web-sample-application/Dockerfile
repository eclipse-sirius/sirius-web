FROM eclipse-temurin:17-jre
COPY target/sirius-web-sample-application-2024.5.1.jar ./app.jar
EXPOSE 8080
RUN adduser --disabled-password myuser
USER myuser
ENTRYPOINT ["java","-jar","/app.jar"]
