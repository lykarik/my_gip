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

def playbook_push_hcs_artifacts (ansible_limit, inventory = '') {
    dir("") {
        ansiColor('xterm') {
            ansiblePlaybook(
             credentialsId: '',
             vaultCredentialsId: '',
             becomeUser: '',
             colorized: true,
             inventory: "",
             limit: "",
             playbook: '')
        }
    }
}

pipeline {
    agent {label 'master'}
    options {
        timestamps ()
        parallelsAlwaysFailFast()
    }
    parameters {
        booleanParam(name: 'test.gkhcontent.ru', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'nit.dom.test.gosuslugi.ru(>X<)', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'gkhcontent.ru', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'vtc.dom.test.gosuslugi.ru', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'mob.hcs.lanit.ru', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'Обновление idp.jks для сервиса idp', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'Обновление jira.keystore', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'mail.dom.test.gosuslugi.ru(>X<)', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'update certs mail.dom.test.gosuslugi.ru', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'sit0[1-2].dom.test.gosuslugi.ru', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'nt01.dom.test.gosuslugi.ru', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'kpak.dom.test.gosuslugi.ru', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'ft01.dom.test.gosuslugi.ru', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'sreda.vtc.dom.test.gosuslugi.ru', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'sreda.ft01.dom.test.gosuslugi.ru', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'ssp.dom.test.gosuslugi.ru', defaultValue: false, description: 'Toggle this value')
        booleanParam(name: 'vtc-esia & nt-esia', defaultValue: false, description: 'Toggle this value')
//        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
//        text(name: 'BIOGRAPHY', defaultValue: '', description: 'Enter some information about the person')
//        choice(name: 'CHOICE', choices: ['One', 'Two', 'Three'], description: 'Pick something')
//        password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'Enter a password')
    }
    environment {
        LOGIN = "admin"
        HCS_GIT_REFERENCE_ROOT = ''
        ANSIBLE_HOST_KEY_CHECKING = 'false'
        ANSIBLE_STRATEGY = 'free'
    }
    
    stages {
    //     stage('Git checkout on master') {
    //     agent {label 'master'}
    //         steps {
    //             dir("${WS_MASTER}") {
    //                 checkout([$class: 'GitSCM',
    //                         branches: [[name: ""]],
    //                         doGenerateSubmoduleConfigurations: false,
    //                         extensions: [
    //                             [$class: 'SubmoduleOption', shallow: true, depth: "1", parentCredentials: true],
    //                             [$class: 'GitLFSPull'],
    //                             [$class: 'CloneOption', reference: "", shallow: true, depth: "1"],
    //                             [$class: 'RelativeTargetDirectory', relativeTargetDir: ""],
    //                         ],
    //                         gitTool: 'Default',
    //                         submoduleCfg: [],
    //                         userRemoteConfigs: [[url: '', credentialsId: '']]
    //                     ])
    //             }
    //         }
    //     }

        stage('test.gkhcontent.ru') {
            when {
                expression { return params.test.gkhcontent.ru }
            }
            steps {
                echo ""
            }
        }
    }

    post {
        success {
            echo "0"
//            telegram("😀 SUCCESSFUL", true)
        }
        failure {
            echo "1"
//            telegram("😡 FAILED", true)
        }
        aborted {
            echo "11"
//            telegram("😡 ABORTED", true)
        }
    }
}
