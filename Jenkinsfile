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
			environment {
        		APP_VER = "sh( script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true).trim()" }
			steps {
				dir('maven_app02') {
					//env.APP_VER = sh( script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true).trim()
					sh "echo ${env.APP_VER}"
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
			steps {
				dir('maven_app02') {
					echo "Here will be deploy"
				}
			}
		}
	}
}
