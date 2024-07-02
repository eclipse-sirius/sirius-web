FROM eclipse-temurin:17-jre
COPY target/sirius-web-*.jar ./
RUN find ./ -name '*.jar' ! -name '*-sources.jar' -execdir mv {} sirius-web.jar \;
EXPOSE 8080
RUN adduser --disabled-password myuser
USER myuser
ENTRYPOINT ["java", "-jar", "/sirius-web.jar"]
