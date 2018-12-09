pipeline {
    environment {
        registry = 'tjarmuz/calculator'
        registryCredential = 'dockerhub'
        dockerImage = ''
        ansibleSudoCredential = 'ansible_sudo'
    }
    agent any
    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage("Compile") {
            steps {
                sh "./gradlew compileJava"
            }
        }
        stage("Unit test") {
            steps {
                sh "./gradlew test"
            }
        }
        stage("Code coverage") {
            steps {
                sh "./gradlew jacocoTestReport"
                publishHTML (target: [
                    reportDir: 'build/reports/jacoco/test/html',
                    reportFiles: 'index.html',
                    reportName: "JaCoCo Report"
                ])
                sh "./gradlew jacocoTestCoverageVerification"
            }
        }
        stage("Static code analysis") {
            steps {
                sh "./gradlew checkstyleMain"
                publishHTML (target: [
                    reportDir: 'build/reports/checkstyle/',
                    reportFiles: 'main.html',
                    reportName: "Checkstyle Report"
                ])
            }
        }
        stage("Package") {
            steps {
                sh "./gradlew build"
            }
        }
        stage("Docker build") {
            steps {
                /*sh "docker build -t tjarmuz/calculator ."*/
                script {
                    dockerImage = docker.build(registry + ':${BUILD_TIMESTAMP}')
                }
            }
        }
        stage("Docker push") {
            steps {
                /*sh "docker push tjarmuz/calculator"*/
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage("Deploy to staging") {
            steps {
                /*sh "docker run -d --rm -p 8765:8080 --name calculator tjarmuz/calculator"*/
                /*sh "docker-compose up -d"*/
                /*sh "ansible-playbook playbook.yml -i inventory/staging"*/
                ansiblePlaybook(credentialsId: ansibleSudoCredential, inventory: 'inventory/staging', playbook: 'playbook.yml', become: 'true', becomeUser: 'eltomas')
                /*ansiblePlaybook('playbook.yml') {
                    inventoryPath('inventory/staging')
                    credentialsId(ansibleSudoCredential)
                    become(true)
                    becomeUser("eltomas")
                }*/
                sleep 20
            }
        }
        stage("Acceptance test") {
            steps {
                sleep 10
                sh "sh ./acceptance_test.sh 192.168.43.4"
                /*sh "docker-compose -f docker-compose.yml -f acceptance/docker-compose-acceptance.yml build test"
                sh "docker-compose -f docker-compose.yml -f acceptance/docker-compose-acceptance.yml -p acceptance up -d"
                sh 'test $(docker wait acceptance_test_1) -eq 0'*/
            }
        }
        // Performance test stages
        stage("Release") {
            steps {
                /*sh "ansible-playbook playbook.yml -i inventory/production"*/
                /*ansiblePlaybook(credentialsId: ansibleSudoCredential, inventory: 'inventory/production', playbook: 'playbook.yml')*/
                ansiblePlaybook('playbook.yml') {
                    inventoryPath('inventory/production')
                    credentialsId(ansibleSudoCredential)
                    become(true)
                    becomeUser("eltomas")
                }
                sleep 20
            }
        }
        stage("Smoke test") {
            steps {
                sh "./smoke_test.sh 192.168.43.118"
            }
        }
    }
    /*post {
        always {
            *//*sh "docker stop calculator"*//*
            sh "docker-compose down"
            *//*sh "docker-compose -f docker-compose.yml-f acceptance/docker-compose-acceptance.yml-p acceptance down"*//*
        }
    }*/
}