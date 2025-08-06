pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    tools {
            maven 'maven-3.9.11'
        }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Deliver') {
            steps {
                sh './jenkins/scripts/deliver.sh'
            }
        }
    }
}