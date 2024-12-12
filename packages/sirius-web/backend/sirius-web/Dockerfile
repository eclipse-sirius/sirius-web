FROM eclipse-temurin:17-jre
RUN useradd sirius-web
COPY target/sirius-web-*[^sources].jar ./sirius-web.jar
EXPOSE 8080
USER sirius-web
ENTRYPOINT ["java", "-jar", "/sirius-web.jar"]