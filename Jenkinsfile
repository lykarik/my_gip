pipeline {
    agent {label 'maven'}
    options {
        timestamps ()
    }
	parameters {
        booleanParam(name: 'DEPLOY', defaultValue: false, description: 'Need deploy?') }
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
					withCredentials([usernamePassword(credentialsId: 'tomcat-creds', passwordVariable: 'PASSWORD', usernameVariable: 'USER')]) {
						sh "scp -o StrictHostKeyChecking=no /home/jenkins/workspace/maven_app/maven_app02/target/java-hello-world.war ${USER}@192.168.98.239:/opt/tomcat/latest/webapps"
					}
				}
			}
		}
	}
}
