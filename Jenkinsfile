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
        booleanParam(name: 'test_gkhcontent', defaultValue: false, description: 'test.gkhcontent.ru')
        booleanParam(name: 'nit', defaultValue: false, description: 'nit.dom.test.gosuslugi.ru(>X<)')
        booleanParam(name: 'gkhcontent', defaultValue: false, description: 'gkhcontent.ru')
        booleanParam(name: 'vtc', defaultValue: false, description: 'vtc.dom.test.gosuslugi.ru')
        booleanParam(name: 'mob_hcs', defaultValue: false, description: 'mob.hcs.lanit.ru')
        booleanParam(name: 'idp_jks_update', defaultValue: false, description: 'Обновление idp.jks для сервиса idp')
        booleanParam(name: 'jira_keystore_update', defaultValue: false, description: 'Обновление jira.keystore')
        booleanParam(name: 'mail', defaultValue: false, description: 'mail.dom.test.gosuslugi.ru(>X<)')
        booleanParam(name: 'update_certs_mail', defaultValue: false, description: 'update certs mail.dom.test.gosuslugi.ru')
        booleanParam(name: 'sit', defaultValue: false, description: 'sit0[1-2].dom.test.gosuslugi.ru')
        booleanParam(name: 'nt01', defaultValue: false, description: 'nt01.dom.test.gosuslugi.ru')
        booleanParam(name: 'kpak', defaultValue: false, description: 'kpak.dom.test.gosuslugi.ru')
        booleanParam(name: 'ft01', defaultValue: false, description: 'ft01.dom.test.gosuslugi.ru')
        booleanParam(name: 'sreda_vtc', defaultValue: false, description: 'sreda.vtc.dom.test.gosuslugi.ru')
        booleanParam(name: 'sreda_ft01', defaultValue: false, description: 'sreda.ft01.dom.test.gosuslugi.ru')
        booleanParam(name: 'ssp', defaultValue: false, description: 'ssp.dom.test.gosuslugi.ru')
        booleanParam(name: 'vtc-esia_nt-esia', defaultValue: false, description: 'vtc-esia & nt-esia')
        
        string(name: 'ANSIBLE_BECOME_USER', defaultValue: '', description: 'Enter username for Ansible BECOME')
//        text(name: 'BIOGRAPHY', defaultValue: '', description: 'Enter some information about the person')
//        choice(name: 'CHOICE', choices: ['One', 'Two', 'Three'], description: 'Pick something')
//        password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'Enter a password')
    }
    environment {
        PWD_VAR = "${WORKSPACE}"
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
                expression { return params.test_gkhcontent }
            }
            steps {
                echo "test.gkhcontent.ru"
                echo "${PWD_VAR}"
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
