# syntax=docker/dockerfile:1

# Both targets use the same runtime configuration.
FROM eclipse-temurin:21-jre AS runtime
RUN useradd sirius-web
EXPOSE 8080
USER sirius-web
ENTRYPOINT ["java", "-jar", "/sirius-web.jar"]

# Build from source using a GitHub login and a token with read:packages.
FROM node:24 AS frontend
WORKDIR /src
COPY . .
RUN printf '\n//npm.pkg.github.com/:_authToken=${NODE_AUTH_TOKEN}\n' >> .npmrc
RUN --mount=type=secret,id=github_token,env=NODE_AUTH_TOKEN,required=true npm ci
RUN npm run build

FROM maven:3.9-eclipse-temurin-21 AS backend
ARG USERNAME
WORKDIR /src
COPY . .
COPY --from=frontend /src/packages/sirius-web/frontend/sirius-web/dist packages/sirius-web/backend/sirius-web-frontend/src/main/resources/static
RUN --mount=type=secret,id=github_token,env=PASSWORD,required=true mvn -B clean package -DskipTests -f packages/pom.xml --settings settings.xml -pl sirius-web/backend/sirius-web -am

FROM runtime AS source
COPY --from=backend /src/packages/sirius-web/backend/sirius-web/target/sirius-web-*[^sources].jar /sirius-web.jar

# CI selects this target with BuildKit, skipping the source build stages.
FROM runtime AS prebuilt
COPY packages/sirius-web/backend/sirius-web/target/sirius-web-*[^sources].jar /sirius-web.jar
