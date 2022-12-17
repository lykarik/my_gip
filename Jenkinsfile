node ("node02"){

    stage ('Get source code') {
        git branch: "${branch}" url: 'https://github.com/lykarik/my_gip.git'
    }

    stage ('Preparation') {
      dir('maven_app') {
        env.APP_VER = sh( script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true).trim()
        sh "echo ${env.APP_VER}"
      }
    }

    stage ('MaveN test'){
      dir('maven_app') {
        sh 'mvn test'
      }
    }

    stage ('Build') {
      dir ('maven_app'){
        sh 'mvn clean install'
      }
    }

    stage ('Deploy to server') {
      dir ('maven_app') {
        sh 'echo "Here should be deploy"'
      }
    }
}
