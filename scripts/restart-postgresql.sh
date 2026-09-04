#!/bin/sh
docker kill sirius-web-postgres
docker run -p 5433:5432 --name sirius-web-postgres -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=dbpwd -e POSTGRES_DB=sirius-web-db -d postgres