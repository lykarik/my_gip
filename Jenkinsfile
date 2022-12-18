pipeline {
    agent {label 'maven'}
    options {
        timestamps ()
    }
    stage('Git checkout for Jenkinsfile') {
        steps {
            git branch: 'main', 
                changelog: false, 
                credentialsId: 'jenkins-master-git-key', 
                url: 'git@github.com:lykarik/my_gip.git'
        }	
    }

    stage ('Preparation') {
      dir('maven_app02') {
        env.APP_VER = sh( script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true).trim()
        sh "echo ${env.APP_VER}"
      }
    }

    stage ('MaveN test'){
      dir('maven_app02') {
        sh 'mvn test'
      }
    }

    stage ('Build') {
      dir ('maven_app02'){
        sh 'mvn clean install'
      }
    }

    stage ('Deploy to server') {
      dir ('maven_app02') {
        sh 'echo "Here should be deploy"'
      }
    }
}
