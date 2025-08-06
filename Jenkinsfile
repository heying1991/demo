pipeline {
   agent any
    tools {
            maven 'maven-3.9.11'
        }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }

//     stages {
//         stage('Checkout') {
//             steps {
//                 git branch: 'main', url: 'https://github.com/your-repo/your-app.git'
//             }
//         }
//
//         stage('Build Docker Image') {
//             steps {
//                 script {
//                     docker.build("${DOCKER_REGISTRY}/${APP_NAME}:${TAG}")
//                 }
//             }
//         }
//
//         stage('Push Docker Image') {
//             steps {
//                 script {
//                     docker.withRegistry('https://your-registry', 'dockerhub-credentials') {
//                         docker.image("${DOCKER_REGISTRY}/${APP_NAME}:${TAG}").push()
//                     }
//                 }
//             }
//         }
//
//         stage('Deploy to Kubernetes') {
//             steps {
//                 script {
//                     // 设置kubectl配置
//                     withEnv(["KUBECONFIG=${KUBE_CONFIG}"]) {
//                         // 更新K8s部署文件中的镜像标签
//                         sh "sed -i 's|IMAGE_TAG|${TAG}|g' k8s/deployment.yaml"
//
//                         // 应用K8s配置
//                         sh "kubectl apply -f k8s/"
//
//                         // 检查部署状态
//                         sh "kubectl rollout status deployment/${APP_NAME}"
//                     }
//                 }
//             }
//         }
//     }
//
//     post {
//         success {
//             slackSend(color: 'good', message: "Deployment succeeded: ${APP_NAME}:${TAG}")
//         }
//         failure {
//             slackSend(color: 'danger', message: "Deployment failed: ${APP_NAME}:${TAG}")
//         }
//     }
}