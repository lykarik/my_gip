def telegram(prefix, showDuration) {
    def duration = showDuration ? "\nDuration: ${currentBuild.durationString.split(' and ')[0]}" : ""
    def standName = "${STAND_NAME}".toUpperCase()
    sh """
       tags=\$(echo "#plo #$standName #${params.dockerImageTag}" | tr '.-' '_')
       header="$prefix deploy $standName <a href='${RUN_DISPLAY_URL}'>${SERVICE_NAME}:${params.dockerImageTag}</a>"
       message="${currentBuild.getBuildCauses()[0].shortDescription} $duration"
       /usr/local/bin/telegram_jenkins_deploys_mecroservices.sh "\${header}" "\${message}" "\${tags}"
       """
}


pipeline {
    agent {label 'master'}
    options {
        timestamps ()
        parallelsAlwaysFailFast()
    }
    parameters {
        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
        text(name: 'BIOGRAPHY', defaultValue: '', description: 'Enter some information about the person')
        booleanParam(name: 'TOGGLE', defaultValue: true, description: 'Toggle this value')
        choice(name: 'CHOICE', choices: ['One', 'Two', 'Three'], description: 'Pick something')
        password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'Enter a password')
    }
    environment {
        LOGIN = "admin"
        HCS_GIT_REFERENCE_ROOT = ''
        ANSIBLE_HOST_KEY_CHECKING = 'false'
        ANSIBLE_STRATEGY = 'free'
    }
    stages {
        stage('Git checkout on master') {
        agent {label 'master'}
            steps {
                dir("${WS_MASTER}") {
                    checkout([$class: 'GitSCM',
                            branches: [[name: ""]],
                            doGenerateSubmoduleConfigurations: false,
                            extensions: [
                                [$class: 'SubmoduleOption', shallow: true, depth: "1", parentCredentials: true],
                                [$class: 'GitLFSPull'],
                                [$class: 'CloneOption', reference: "", shallow: true, depth: "1"],
                                [$class: 'RelativeTargetDirectory', relativeTargetDir: ""],
                            ],
                            gitTool: 'Default',
                            submoduleCfg: [],
                            userRemoteConfigs: [[url: '', credentialsId: '']]
                        ])
                }
            }
        }
    
        stage('Clean') {
            when {
                expression { return params.clean }
            }
            steps {
                script { 
                    clean(env.STAND_NAME)
                }
            }
        }
    }    
    post {
        success {
            telegram("😀 SUCCESSFUL", true)
        }
        failure {
            telegram("😡 FAILED", true)
        }
        aborted {
            telegram("😡 ABORTED", true)
        }
    }
}
