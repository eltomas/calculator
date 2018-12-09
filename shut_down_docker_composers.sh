#!/bin/bash
echo "Shuting down server... $0, $1, $2"
whoami
sshpass -p $1 ssh -o StrictHostKeychecking=no eltomas@$2 "docker-compose down"