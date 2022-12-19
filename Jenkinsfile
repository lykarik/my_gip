parameters {
        booleanParam(name: 'DEPLOY', defaultValue: false, description: 'Need deploy?') }

pipeline {
    agent {label 'maven'}
    options {
        timestamps ()
    }
	stages {
		stage('Git checkout for Jenkinsfile') {
			steps {
				git branch: 'main', 
					changelog: false, 
					credentialsId: 'jenkins-slave01-git', 
					url: 'git@github.com:lykarik/my_gip.git'
			}	
		}
	
		stage ('Prepare') {
			steps {
				dir('maven_app02') {
					sh "mvn --version"
				}
			}
		}
	
		stage ('Maven test') {
			steps {
				dir('maven_app02') {
					sh "mvn test"
				}
			}
		}
	
		stage ('Build') {
			steps {
				dir('maven_app02') {
					sh "mvn clean install"
				}
			}
		}
	
		stage ('Deploy') {
			when {
                expression { return params.DEPLOY } }
			steps {
				dir('maven_app02') {
						deploy adapters: [tomcat9(credentialsId: 'tomcat-creds', path: "${WORKSPACE}", url: 'http://192.168.98.239:8080/')], contextPath: null, war: '**/*.war'
					}
				}
			}
		}
	}
}

