#!/bin/bash
echo "Running acceptance test... $0, $1, $2"
whoami
CALCULATOR_PORT=$(sshpass -p $1 ssh -o StrictHostKeychecking=no eltomas@$2 "docker-compose port calculator 8080 | cut -d: -f2")
echo "Host: $2:$CALCULATOR_PORT"
#test $(curl localhost:$CALCULATOR_PORT/sum?a=1\&b=2) -eq 3
./gradlew acceptanceTest -Dcalculator.url=http://$2:$CALCULATOR_PORT
