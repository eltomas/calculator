#!/bin/bash
echo "Running smoke test... $0, $1, $2"
whoami
CALCULATOR_PORT=$(sshpass -p $1 ssh -o StrictHostKeychecking=no eltomas@$2 "docker-compose port calculator 8080 | cut -d: -f2")
echo "Host: $2:$CALCULATOR_PORT"
./gradlew smokeTest -Dcalculator.url=http://$2:$CALCULATOR_PORT