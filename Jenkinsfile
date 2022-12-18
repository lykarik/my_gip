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
			steps {
				dir('maven_app02') {
					echo "Here will be deploy"
				}
			}
		}
	}
}
