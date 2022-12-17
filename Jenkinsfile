def telegram(prefix, showDuration) {
    def duration = showDuration ? "\nDuration: ${currentBuild.durationString.split(' and ')[0]}" : ""
    def standName = "${STAND_NAME}".toUpperCase()
    sh """
       tags=\$(echo "#plo #$standName #${params.dockerImageTag}" | tr '.-' '_')
       header="$prefix deploy $standName <a href='${RUN_DISPLAY_URL}'>${SERVICE_NAME}:${params.dockerImageTag}</a>"
       message="${currentBuild.getBuildCauses()[0].shortDescription} $duration"
       echo "Do TG notification ${header} ${message} ${tags}"
       """
}

def playbook_init () {
    dir("") {
        ansiColor('xterm') {
            ansiblePlaybook(
             credentialsId: '',
             vaultCredentialsId: 'CredID_HCS_INFRA_ANSIBLE_Vault_Pass',
             becomeUser: "${ANSIBLE_BECOME_USER}",
             colorized: true,
             limit: 'vtc-gkhcontent',
             inventory: 'inventories/voshod/test',
             playbook: 'ansible-common/playbooks/pki-letsencrypt-nginx.yml')
        }
    }
}

pipeline {
    agent {label 'master'}
    options {
        timestamps ()
    }
    parameters {
        booleanParam(name: 'GKHCONTENT',                defaultValue: false, description: 'gkhcontent.ru')
        booleanParam(name: 'TEST_GKHCONTENT',           defaultValue: true, description: 'test.gkhcontent.ru')

        booleanParam(name: 'VTC',                       defaultValue: false, description: 'vtc.dom.test.gosuslugi.ru')
        booleanParam(name: 'SIT',                       defaultValue: false, description: 'sit0[1-2].dom.test.gosuslugi.ru')
        booleanParam(name: 'SSP',                       defaultValue: false, description: 'ssp.dom.test.gosuslugi.ru')
        booleanParam(name: 'NT01',                      defaultValue: false, description: 'nt01.dom.test.gosuslugi.ru')
        booleanParam(name: 'KPAK',                      defaultValue: false, description: 'kpak.dom.test.gosuslugi.ru')
        booleanParam(name: 'FT01',                      defaultValue: false, description: 'ft01.dom.test.gosuslugi.ru')
        booleanParam(name: 'SREDA_VTC',                 defaultValue: false, description: 'sreda.vtc.dom.test.gosuslugi.ru')
        booleanParam(name: 'SREDA_FT01',                defaultValue: false, description: 'sreda.ft01.dom.test.gosuslugi.ru')

        booleanParam(name: 'UPDATE_CERTS_MAIL',         defaultValue: false, description: 'update certs mail.dom.test.gosuslugi.ru')

        booleanParam(name: 'MOB_HCS_LANIT',             defaultValue: false, description: 'mob.hcs.lanit.ru')
        booleanParam(name: 'ESIA_VTC',                  defaultValue: false, description: 'vtc-esia')
        booleanParam(name: 'ESIA_NT',                   defaultValue: false, description: 'nt-esia')

        booleanParam(name: 'IDP_JKS_UPDATE',            defaultValue: false, description: 'Updating idp.jks for idp service')
        booleanParam(name: 'JIRA_KEYSTORE_UPDATE',      defaultValue: false, description: 'Updating jira.keystore')

        string(name: 'ANSIBLE_BECOME_USER',             defaultValue: '', description: 'Enter username for Ansible BECOME')

        booleanParam(name: 'NIT',                       defaultValue: false, description: 'nit.dom.test.gosuslugi.ru(deleted)')
        booleanParam(name: 'MAIL',                      defaultValue: false, description: 'mail.dom.test.gosuslugi.ru(deleted)')

    }
    environment {
        PWD_VAR = "${WORKSPACE}"
    }

    stages {

        stage('Git checkout') {
            steps {
                git branch: 'update_certs_try', 
                    changelog: false, 
                    credentialsId: 'jenkins-master-git-key', 
                    url: 'git@github.com:lykarik/my_gip.git'
            }
        }

        stage('Another checkout') {
            steps {
                dir("${WORKSPACE}") {
                    checkout([$class: 'GitSCM',

                                userRemoteConfigs: [[url: 'git@github.com:lykarik/my_gip.git', credentialsId: 'jenkins-master-git-key']],
                                branches: [[name: "main", "update_certs_try"]],
                                doGenerateSubmoduleConfigurations: false,
                                gitTool: 'Default',
                                submoduleCfg: [],
                                
                                extensions: [
                                    [$class: 'SubmoduleOption', 
                                        shallow: true, 
                                        depth: "1", 
                                        parentCredentials: true],
                                    [$class: 'GitLFSPull'],
                                    [$class: 'CloneOption', 
                                        reference: "/var/lib/jenkins/workspace/_git", 
                                        shallow: true, 
                                        depth: "1"],
                                    [$class: 'RelativeTargetDirectory', 
                                    relativeTargetDir: "my_gip_${BUILD_DISPLAY_NAME}_${GIT_BRANCH}"]
                                ]

                            ])
                }
            }
        }

        stage('test.gkhcontent.ru') {
            when {
                expression { return params.TEST_GKHCONTENT }
            }
            steps {
                echo "test.gkhcontent.ru"
                echo "${ANSIBLE_BECOME_USER}"
                echo "${PWD_VAR}"
                sh "pwd"
                sh "ls -la"
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


//        text(name: 'BIOGRAPHY', defaultValue: '', description: 'Enter some information about the person')
//        choice(name: 'CHOICE', choices: ['One', 'Two', 'Three'], description: 'Pick something')
//        password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'Enter a password')
