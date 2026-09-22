# Build Sirius Web from the working tree and package the runtime image, tests skipped.
# Same steps as .github/workflows/build.yml. USERNAME and PASSWORD: a GitHub login and a
# token with read:packages, for the dependencies hosted on GitHub Packages.

FROM node:24 AS frontend
ARG PASSWORD
WORKDIR /src
COPY . .
RUN echo "//npm.pkg.github.com/:_authToken=${PASSWORD}" >> .npmrc
RUN npm ci
RUN npm run build

FROM maven:3.9-eclipse-temurin-21 AS backend
ARG USERNAME
ARG PASSWORD
WORKDIR /src
COPY . .
COPY --from=frontend /src/packages/sirius-web/frontend/sirius-web/dist packages/sirius-web/backend/sirius-web-frontend/src/main/resources/static
RUN mvn -B clean package -DskipTests -f packages/pom.xml --settings settings.xml -pl sirius-web/backend/sirius-web -am

# Runtime, same as packages/sirius-web/backend/sirius-web/Dockerfile
FROM eclipse-temurin:21-jre
RUN useradd sirius-web
COPY --from=backend /src/packages/sirius-web/backend/sirius-web/target/sirius-web-*[^sources].jar ./sirius-web.jar
EXPOSE 8080
USER sirius-web
ENTRYPOINT ["java", "-jar", "/sirius-web.jar"]
