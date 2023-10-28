pipeline {
    agent any
    //agent { label 'jenkins-agent' }
    
    environment {
    	//uri = '518637836680.dkr.ecr.eu-west-2.amazonaws.com/ashkumarkdocker/docker-e2e-automation'
    	//registryCredential = '518637836680'
    	dockerImage = ''
    }

    options {
        skipDefaultCheckout(true)
    }
    
    stages {
        stage('Clean WS & checkout SCM') {
            steps {
                // Clean before build
                cleanWs()
                sh 'rm -rf $WORKSPACE'
                deleteDir()
                // We need to explicitly checkout from SCM here
                checkout scm
                echo "Building ${env.JOB_NAME}..."
            }
        }
        stage('Build Image') {
            steps {
                script {
                        dockerImage = docker.build("ashkumarkdocker/docker-e2e-automation")
                }
            }
        }
        stage('API Automation') {
        	agent {
                docker {
                    image 'ashkumarkdocker/docker-e2e-automation'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
            	sh 'mvn test -Dcucumber.filter.tags="@API"'
            }
        }
        /*stage('Push Image') {
            steps {
                script {     
                   docker.withRegistry("https://" + uri, "ecr:eu-west-2:" + registryCredential) {
                         dockerImage.push()
				   }				       
				}
             }
        }*/
    }

    post {
        success {
            // publish html
            publishHTML target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'target/Reports/',
                    reportFiles: 'index.html',
                    reportName: 'E2E Tests Report'
            ]
        }

        always {
            cleanWs()
            deleteDir()
        }
    }

}