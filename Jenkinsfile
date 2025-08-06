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
    }
      stage('Deploy to K8s') {
                steps {
                    sh """
                        kubectl apply -f deploy/deployment.yaml
                        kubectl rollout status deployment/springboot-app
                    """
                }
            }
    }
}