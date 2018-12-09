#!/bin/bash
echo "Running acceptance test..."
CALCULATOR_PORT=$(ssh -o StrictHostKeychecking=no eltomas@$@ "docker-compose port calculator 8080 | cut -d: -f2")
echo "Host: $@:$CALCULATOR_PORT"
#test $(curl localhost:$CALCULATOR_PORT/sum?a=1\&b=2) -eq 3
./gradlew acceptanceTest -Dcalculator.url=http://$@:$CALCULATOR_PORT
