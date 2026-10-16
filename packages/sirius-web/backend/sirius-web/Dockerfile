FROM eclipse-temurin:21-jre AS runtime
RUN useradd sirius-web
EXPOSE 8080
USER sirius-web
ENTRYPOINT ["java", "-jar", "/sirius-web.jar"]

FROM node:24 AS frontend
WORKDIR /src
COPY . .
RUN printf '\n//npm.pkg.github.com/:_authToken=${NODE_AUTH_TOKEN}\n' >> .npmrc
RUN --mount=type=secret,id=github_token,env=NODE_AUTH_TOKEN,required=true \
    --mount=type=cache,target=/root/.npm \
    npm ci
RUN npm run build

FROM maven:3.9-eclipse-temurin-21 AS backend
ARG USERNAME
WORKDIR /src
COPY . .
COPY --from=frontend /src/packages/sirius-web/frontend/sirius-web/dist packages/sirius-web/backend/sirius-web-frontend/src/main/resources/static
RUN --mount=type=secret,id=github_token,env=PASSWORD,required=true \
    --mount=type=cache,target=/root/.m2 \
    mvn -B clean package -DskipTests -f packages/pom.xml --settings settings.xml -pl sirius-web/backend/sirius-web -am

FROM runtime AS source
COPY --from=backend /src/packages/sirius-web/backend/sirius-web/target/sirius-web-*[^sources].jar /sirius-web.jar

FROM runtime AS prebuilt
COPY packages/sirius-web/backend/sirius-web/target/sirius-web-*[^sources].jar /sirius-web.jar
