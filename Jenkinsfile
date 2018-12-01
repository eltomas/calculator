pipeline {
    environment {
        registry = 'tjarmuz/calculator'
        registryCredential = 'dockerhub'
        dockerImage = ''
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
                script {
                    dockerImage = docker.build registry
                }
            }
        }
        stage("Docker push") {
            steps {
                script {
                    docker.withRegistry( ‘’, registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage("Deploy to staging") {
            steps {
                sh "docker run -d --rm -p 8765:8080 --name calculator tjarmuz/calculator"
            }
        }
        stage("Acceptance test") {
            steps {
                sleep 30
                sh "sh ./acceptance_test.sh"
            }
        }
    }
    post {
        always {
            sh "docker stop calculator"
        }
    }
}