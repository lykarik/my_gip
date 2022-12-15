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

def playbook_push_hcs_artifacts (ansible_limit, inventory = 'inventories/voshod/test') {
    dir("${WS_MASTER}/hcs_infra_ansible.git") {
        ansiColor('xterm') {
            ansiblePlaybook(
             credentialsId: 'CredID_SSH_Jenkins_as_jboss_into_hosts',
             vaultCredentialsId: 'CredID_HCS_INFRA_ANSIBLE_Vault_Pass',
             becomeUser: 'jboss',
             colorized: true,
             inventory: "${inventory}",
             limit: "${ansible_limit}",
             playbook: 'playbooks/jboss_timer_voshod_restart.yml')
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
//        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
//        text(name: 'BIOGRAPHY', defaultValue: '', description: 'Enter some information about the person')
        booleanParam(name: 'test.gkhcontent.ru:', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'nit.dom.test.gosuslugi.ru(>X<)', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'gkhcontent.ru', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'vtc.dom.test.gosuslugi.ru', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'mob.hcs.lanit.ru', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'Обновление idp.jks для сервиса idp', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'Обновление jira.keystore', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'mail.dom.test.gosuslugi.ru(>X<)', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'update certs mail.dom.test.gosuslugi.ru', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'sit0[1-2].dom.test.gosuslugi.ru', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'nt01.dom.test.gosuslugi.ru', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'kpak.dom.test.gosuslugi.ru', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'ft01.dom.test.gosuslugi.ru', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'sreda.vtc.dom.test.gosuslugi.ru', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'sreda.ft01.dom.test.gosuslugi.ru', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'ssp.dom.test.gosuslugi.ru', defaultValue: true, description: 'Toggle this value')
        booleanParam(name: 'vtc-esia & nt-esia', defaultValue: true, description: 'Toggle this value')

        

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
//            telegram("😀 SUCCESSFUL", true)
        }
        failure {
//            telegram("😡 FAILED", true)
        }
        aborted {
//            telegram("😡 ABORTED", true)
        }
    }
}
