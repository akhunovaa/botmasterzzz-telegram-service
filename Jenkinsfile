pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Checkout'
            }
        }

            stage('Build') {
                steps {
                    echo 'Clean Build'
                    sh 'mvn clean compile package'
                }
            }

            stage('Test') {
                steps {
                    echo 'Testing'
                    sh 'mvn test'
                }
            }


            stage('Package') {
                steps {
                    echo 'Packaging'
                    sh 'mvn clean package -DskipTests'
                }
            }

            stage('Build Docker Image') {
                steps {
                    echo 'Build Docker Image'
                    sh 'docker build --no-cache -t leon4uk/botmasterzzz-telegram:1.0.0 .'
                }
            }

            stage('Push Docker image') {
                steps {
                    echo 'Push Docker image'
                    withCredentials([string(credentialsId: 'dockerHubPwd', variable: 'dockerHubPwd')]) {
                        sh "docker login -u leon4uk -p ${dockerHubPwd}"
                    }
                    sh 'docker push leon4uk/botmasterzzz-telegram:1.0.0'
                    sh 'docker rmi leon4uk/botmasterzzz-telegram:1.0.0'
                }
            }

            stage('Connect to helper node') {
                steps{
                    sshagent(credentials : ['second_node']) {
                        sh 'ssh -o StrictHostKeyChecking=no root@5.189.146.63 uptime'
                        sh 'ssh -v root@5.189.146.63'
                    }
                }
            }



            stage('Deploy') {
                steps {
                    echo '## Deploy locally ##'
                    withCredentials([string(credentialsId: 'dockerHubPwd', variable: 'dockerHubPwd')]) {
                        sh "docker login -u leon4uk -p ${dockerHubPwd}"
                    }
                    sh "docker container ls -a -f name=botmasterzzz-telegram -q | xargs --no-run-if-empty docker container stop"
                    sh 'docker container ls -a -f name=botmasterzzz-telegram -q | xargs -r docker container rm'
                    sh 'docker run -v /home/repository:/home/repository -v /etc/localtime:/etc/localtime --name botmasterzzz-telegram -d --net=botmasterzzznetwork -p 0.0.0.0:8064:8064 leon4uk/botmasterzzz-telegram:1.0.0'
                }
            }
        }
}
