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
                    sh "ssh -i /var/jenkins_home/.ssh/id_rsa root@5.189.146.63"
                    sh 'docker build --no-cache -t leon4uk/botmasterzzz-telegram:1.0.0 .'
                }
            }

            stage('Push Docker image') {
                steps {
                    echo 'Push Docker image'
                    withCredentials([string(credentialsId: 'dockerHubPwd', variable: 'dockerHubPwd')]) {
                        sh "docker login -u leon4uk -p ${dockerHubPwd}"
                    }
                    sh "ssh -i /var/jenkins_home/.ssh/id_rsa root@5.189.146.63"
                    sh 'docker push leon4uk/botmasterzzz-telegram:1.0.0'
                    sh 'docker rmi leon4uk/botmasterzzz-telegram:1.0.0'
                }
            }

            stage('Deploy') {
                steps {
                    echo '## Deploy remote ##'
                    sh "ssh -i /var/jenkins_home/.ssh/id_rsa root@5.189.146.63"
                    sh "docker container ls -a -f name=botmasterzzz-telegram -q | xargs --no-run-if-empty docker container stop"
                    sh 'docker container ls -a -f name=botmasterzzz-telegram -q | xargs -r docker container rm'
                    sh 'docker run -v /home/repository:/home/repository -v /etc/localtime:/etc/localtime --name botmasterzzz-telegram -d -p 0.0.0.0:8064:8064 --restart always leon4uk/botmasterzzz-telegram:1.0.0'
                }
            }
        }
}
