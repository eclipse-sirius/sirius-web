#!/bin/sh
java -Dsirius.web.graphql.websocket.allowed.origins="*" \
     -jar backend/sirius-web-sample-application/target/sirius-web-sample-application-*-SNAPSHOT.jar \
     --spring.datasource.url=jdbc:postgresql://localhost:5433/sirius-web-db \
     --spring.datasource.username=dbuser \
     --spring.datasource.password=dbpwd \
     --spring.liquibase.change-log=classpath:db/changelog/sirius-web.db.changelog.xml
