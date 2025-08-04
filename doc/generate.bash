#!/bin/bash

set -x
set -e
# version : 0.1.0

SCRIPT=$(realpath "$0")
SCRIPTPATH=$(dirname "$SCRIPT")

# Generate HTML / PDF
echo "Antora build"


docker build ${SCRIPTPATH} -t sirius-web_doc_generator:local

docker run -v $SCRIPTPATH/..:/usr/app:z --security-opt label=disable sirius-web_doc_generator:local
