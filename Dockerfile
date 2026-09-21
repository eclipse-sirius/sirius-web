# Build Sirius Web from the working tree and package the runtime image, tests skipped.
# The dependencies hosted on GitHub Packages are built from their sources.

FROM node:24 AS frontend
WORKDIR /src
COPY . .

# @ObeoNetwork/react-trello and @ObeoNetwork/gantt-task-react
RUN mkdir -p /deps/react-trello /deps/gantt-task-react
RUN curl -fsSL https://github.com/ObeoNetwork/react-trello/archive/refs/tags/v2.4.11.tar.gz | tar -xz -C /deps/react-trello --strip-components=1
RUN curl -fsSL https://github.com/ObeoNetwork/gantt-task-react/archive/refs/tags/v0.6.6.tar.gz | tar -xz -C /deps/gantt-task-react --strip-components=1
RUN cd /deps/react-trello && npm install && npm pack --pack-destination /deps
RUN cd /deps/gantt-task-react && npm install && npm pack --pack-destination /deps

# The lockfile names GitHub Packages as the source of the two forks: point it at the tarballs
RUN <<EOF
node -e '
  const fs = require("fs");
  const lock = JSON.parse(fs.readFileSync("package-lock.json"));

  const trello = lock.packages["node_modules/@ObeoNetwork/react-trello"];
  trello.resolved = "file:/deps/ObeoNetwork-react-trello-2.4.11.tgz";
  delete trello.integrity;

  const gantt = lock.packages["node_modules/@ObeoNetwork/gantt-task-react"];
  gantt.resolved = "file:/deps/ObeoNetwork-gantt-task-react-0.6.6.tgz";
  delete gantt.integrity;

  fs.writeFileSync("package-lock.json", JSON.stringify(lock, null, 2));
'
EOF
# The gantt tarball carries a prepare script, approved as package.json does for other packages
RUN npm pkg set "allowScripts[@ObeoNetwork/gantt-task-react@file:/deps/ObeoNetwork-gantt-task-react-0.6.6.tgz]=true" --json

# Frontend
RUN npm ci
RUN npm run build

# fr.obeo.dsl.designer.sample.flow 1.0.11-SNAPSHOT, a Tycho 1.7 build which needs JDK 11
FROM maven:3.9-eclipse-temurin-11 AS flow
RUN mkdir -p /deps/flow
RUN curl -fsSL https://github.com/ObeoNetwork/Flow-Designer/archive/refs/heads/ocp.tar.gz | tar -xz -C /deps/flow --strip-components=1
RUN mvn -B install -f /deps/flow/pom.xml -pl plugins/fr.obeo.dsl.designer.sample.flow,plugins/fr.obeo.dsl.designer.sample.flow.edit -am

FROM maven:3.9-eclipse-temurin-21 AS backend
WORKDIR /src
COPY . .
COPY --from=frontend /src/packages/sirius-web/frontend/sirius-web/dist packages/sirius-web/backend/sirius-web-frontend/src/main/resources/static
COPY --from=flow /root/.m2/repository/fr/obeo /root/.m2/repository/fr/obeo

# org.eclipse.sirius.emfjson 2.5.4-SNAPSHOT
RUN mkdir -p /deps/emfjson
RUN curl -fsSL https://github.com/eclipse-sirius/sirius-emf-json/archive/refs/tags/v2.5.4.tar.gz | tar -xz -C /deps/emfjson --strip-components=1
RUN mvn -B install -DskipTests -f /deps/emfjson/pom.xml

# Backend
RUN mvn -B clean package -DskipTests -f packages/pom.xml -pl sirius-web/backend/sirius-web -am

# Runtime, same as packages/sirius-web/backend/sirius-web/Dockerfile
FROM eclipse-temurin:21-jre
RUN useradd sirius-web
COPY --from=backend /src/packages/sirius-web/backend/sirius-web/target/sirius-web-*[^sources].jar ./sirius-web.jar
EXPOSE 8080
USER sirius-web
ENTRYPOINT ["java", "-jar", "/sirius-web.jar"]
