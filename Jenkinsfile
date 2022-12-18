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

def playbook_init (ansible_limit, inventory, playbook) {
    dir("${WORKSPACE}") {
        ansiColor('xterm') {
            ansiblePlaybook(
             credentialsId: 'ansible-lol-creds',
             //vaultCredentialsId: 'CredID_HCS_INFRA_ANSIBLE_Vault_Pass',
             becomeUser: "${ANSIBLE_BECOME_USER}",
             colorized: true,
             limit: "${ansible_limit}",
             inventory: "${inventory}",
             playbook: "${playbook}")
        }
    }
}

def playbook_pki_letsencrypt_nginx (ansible_limit, inventory) {
    dir("${WORKSPACE}") {
        ansiColor('xterm') {
            ansiblePlaybook(
             credentialsId: '',
             //vaultCredentialsId: 'CredID_HCS_INFRA_ANSIBLE_Vault_Pass',
             becomeUser: "${ANSIBLE_BECOME_USER}",
             colorized: true,
             limit: "${ansible_limit}",
             inventory: "${inventory}",
             playbook: 'ansible-common/playbooks/pki-letsencrypt-nginx.yml')
        }
    }
}

def playbook_nginx_ng_rsa (ansible_limit, inventory) {
    dir("${WORKSPACE}") {
        ansiColor('xterm') {
            ansiblePlaybook(
             credentialsId: '',
             //vaultCredentialsId: 'CredID_HCS_INFRA_ANSIBLE_Vault_Pass',
             becomeUser: "${ANSIBLE_BECOME_USER}",
             colorized: true,
             limit: "${ansible_limit}",
             inventory: "${inventory}",
             playbook: 'playbooks/Nginx-ng-rsa.yml')
        }
    }
}

def playbook_nginx_ng (ansible_limit, inventory) {
    dir("${WORKSPACE}") {
        ansiColor('xterm') {
            ansiblePlaybook(
             credentialsId: '',
             //vaultCredentialsId: 'CredID_HCS_INFRA_ANSIBLE_Vault_Pass',
             becomeUser: "${ANSIBLE_BECOME_USER}",
             colorized: true,
             limit: "${ansible_limit}",
             inventory: "${inventory}",
             playbook: 'playbooks/Nginx-ng.yml')
        }
    }
}

def playbook_update_jks (ansible_limit, inventory) {
    dir("${WORKSPACE}") {
        ansiColor('xterm') {
            ansiblePlaybook(
             credentialsId: '',
             //vaultCredentialsId: 'CredID_HCS_INFRA_ANSIBLE_Vault_Pass',
             becomeUser: "${ANSIBLE_BECOME_USER}",
             colorized: true,
             limit: "${ansible_limit}",
             inventory: "${inventory}",
             playbook: 'playbooks/update_jks.yml')
        }
    }
}

def playbook_hcs_keys_install (ansible_limit, inventory) {
    dir("${WORKSPACE}") {
        ansiColor('xterm') {
            ansiblePlaybook(
             credentialsId: '',
             //vaultCredentialsId: 'CredID_HCS_INFRA_ANSIBLE_Vault_Pass',
             becomeUser: "${ANSIBLE_BECOME_USER}",
             colorized: true,
             limit: "${ansible_limit}",
             inventory: "${inventory}",
             playbook: 'hcs-keys-install.yml')
        }
    }
}

pipeline {
    agent {label 'master'}
    options {
        timestamps ()
    }
    parameters {
        booleanParam(name: 'GKHCONTENT',                defaultValue: false, description: 'gkhcontent.ru')                              //1
        booleanParam(name: 'TEST_GKHCONTENT',           defaultValue: true, description: 'test.gkhcontent.ru')                         //2
                                                                                                                                        //
        booleanParam(name: 'VTC',                       defaultValue: false, description: 'vtc.dom.test.gosuslugi.ru')                  //3
        booleanParam(name: 'SIT',                       defaultValue: false, description: 'sit0[1-2].dom.test.gosuslugi.ru')            //4
        booleanParam(name: 'SSP',                       defaultValue: false, description: 'ssp.dom.test.gosuslugi.ru')                  //5
        booleanParam(name: 'NT01',                      defaultValue: false, description: 'nt01.dom.test.gosuslugi.ru')                 //6
        booleanParam(name: 'KPAK',                      defaultValue: false, description: 'kpak.dom.test.gosuslugi.ru')                 //7
        booleanParam(name: 'FT01',                      defaultValue: false, description: 'ft01.dom.test.gosuslugi.ru')                 //8
        booleanParam(name: 'SREDA_VTC',                 defaultValue: false, description: 'sreda.vtc.dom.test.gosuslugi.ru')            //9
        booleanParam(name: 'SREDA_FT01',                defaultValue: false, description: 'sreda.ft01.dom.test.gosuslugi.ru')           //10
                                                                                                                                        //
        booleanParam(name: 'UPDATE_CERTS_MAIL',         defaultValue: false, description: 'Update certs mail.dom.test.gosuslugi.ru')    //11
                                                                                                                                        //
        booleanParam(name: 'MOB_HCS_LANIT',             defaultValue: false, description: 'mob.hcs.lanit.ru')                           //12
        booleanParam(name: 'ESIA_VTC',                  defaultValue: false, description: 'vtc-esia')                                   //13
        booleanParam(name: 'ESIA_NT',                   defaultValue: false, description: 'nt-esia')                                    //14

        booleanParam(name: 'IDP_JKS_UPDATE',            defaultValue: false, description: 'Updating idp.jks for idp service')
        booleanParam(name: 'JIRA_KEYSTORE_UPDATE',      defaultValue: false, description: 'Updating jira.keystore')

        string(name: 'ANSIBLE_BECOME_USER',             defaultValue: '', description: 'Enter username for Ansible BECOME')             

        booleanParam(name: 'NIT',                       defaultValue: false, description: 'nit.dom.test.gosuslugi.ru(deleted)')
        booleanParam(name: 'NIT_API',                   defaultValue: false, description: 'api.nit.dom.test.gosuslugi.ru(deleted)')
        booleanParam(name: 'MAIL',                      defaultValue: false, description: 'mail.dom.test.gosuslugi.ru(deleted)')

    }
    environment {
        PWD_VAR = "${WORKSPACE}"
        PROJECT = 'my_gip'
    }

    stages {
        stage('Git checkout for Jenkinsfile') {
            steps {
                git branch: 'update_certs_try', 
                    changelog: false, 
                    credentialsId: 'jenkins-master-git-key', 
                    url: 'git@github.com:lykarik/my_gip.git'
            }
        }

        stage('Git checkout main branch') {
            steps {
                dir("${WORKSPACE}") {
                    checkout([$class: 'GitSCM',

                                userRemoteConfigs: [[url: "git@github.com:lykarik/${PROJECT}.git", credentialsId: 'jenkins-master-git-key']],
                                branches: [[name: "main"]],
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
                                    relativeTargetDir: "${PROJECT}_${BUILD_DISPLAY_NAME}_${params.Branch}"]
                                ]

                    ])
                }
            }
        }

        stage('gkhcontent.ru') {
            when {
                expression { return params.GKHCONTENT } }
            environment {
                ANSIBLE_LIMIT ='gkhcontent'
                INVENTORY = 'inventories/voshod/ppak' }
            steps {
                playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT, INVENTORY) }
        }

        stage('test.gkhcontent.ru') {
            when {
                expression { return params.TEST_GKHCONTENT } }
            environment {
                ANSIBLE_LIMIT ='some-group'
                INVENTORY = 'inventories/main'
                PLAYBOOK = 'playbooks/what_play.yml' }
            steps {
                playbook_init (ANSIBLE_LIMIT, INVENTORY, PLAYBOOK) }
        }

        stage('vtc.dom.test.gosuslugi.ru') {
            when {
                expression { return params.VTC } }
            environment {
                ANSIBLE_LIMIT ='idp01'
                INVENTORY = 'inventories/voshod/test' }
            steps {
                playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT, INVENTORY) }
        }

        stage('sit0[1-2].dom.test.gosuslugi.ru') {
            when {
                expression { return params.SIT } }
            environment {
                ANSIBLE_LIMIT_PKI ='sit01:sit01u:sit02u'
                ANSIBLE_LIMIT_RSA ='sit-gs'
                INVENTORY = 'inventories/voshod/test' }
            steps {
                playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT_PKI, INVENTORY)
                playbook_nginx_ng_rsa (ANSIBLE_LIMIT_RSA, INVENTORY) }
        }

        stage('ssp.dom.test.gosuslugi.ru') {
            when {
                expression { return params.SSP } }
            environment {
                ANSIBLE_LIMIT ='ssp-http-my'
                INVENTORY = 'inventories/voshod/test' }
            steps {
                playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT, INVENTORY) }
        }

        stage('nt01.dom.test.gosuslugi.ru') {
            when {
                expression { return params.NT01 } }
            environment {
                ANSIBLE_LIMIT_MY ='nt-http-my'
                ANSIBLE_LIMIT_LK ='nt-http-lk'
                INVENTORY = 'inventories/voshod/test' }
            steps {
                playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT_MY, INVENTORY)
                playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT_LK, INVENTORY) }
        }

        stage('kpak.dom.test.gosuslugi.ru') {
            when {
                expression { return params.KPAK } }
            environment {
                ANSIBLE_LIMIT ='kpak-http-my:kpak-http-api'
                INVENTORY = 'inventories/voshod/test' }
            steps {
                playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT, INVENTORY) }
        }

        stage('ft01.dom.test.gosuslugi.ru') {
            when {
                expression { return params.FT01 } }
            environment {
                ANSIBLE_LIMIT ='ft-http-my'
                INVENTORY = 'inventories/voshod/test' }
            steps {
                playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT, INVENTORY) }
        }

        stage('sreda.vtc.dom.test.gosuslugi.ru-sreda.ft01.dom.test.gosuslugi.ru') {
            when {
                expression { return params.SREDA_VTC } }
            environment {
                ANSIBLE_LIMIT ='tgs-http'
                INVENTORY = 'inventories/voshod/test' }
            steps {
                playbook_pki_letsencrypt_nginx (ansible_limit, inventory) }
        }

        //stage('sreda.ft01.dom.test.gosuslugi.ru') {
        //    when {
        //        expression { return params.SREDA_FT01 }
        //    }
        //    steps {
        //        playbook_pki_letsencrypt_nginx (ansible_limit, inventory)
        //    }
        //}

        stage('Update certs mail.dom.test.gosuslugi.ru') {
            when {
                expression { return params.UPDATE_CERTS_MAIL }
            }
            steps {
                echo "plug"
            }
        }

        stage('mob.hcs.lanit.ru') {
            when {
                expression { return params.MOB_HCS_LANIT }
            }
            steps {
                playbook_pki_letsencrypt_nginx (ansible_limit, inventory)
            }
        }

        stage('vtc-esia') {
            when {
                expression { return params.ESIA_VTC }
            }
            steps {
                echo "plug"
            }
        }

        stage('nt-esia') {
            when {
                expression { return params.ESIA_NT }
            }
            steps {
                echo "plug"
            }
        }

        // stage('nit.dom.test.gosuslugi.ru(DELETED)') {
        //     when {
        //         expression { return params.NIT } }
        //     environment {
        //         ANSIBLE_LIMIT ='nit-http-my'
        //         INVENTORY = 'inventories/voshod/test' }
        //     steps {
        //         playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT, INVENTORY) }
        // }

        // stage('api.nit.dom.test.gosuslugi.ru(DELETED)') {
        //     when {
        //         expression { return params.NIT_API } }
        //     environment {
        //         ANSIBLE_LIMIT ='nit-http-api'
        //         INVENTORY = 'inventories/voshod/test' }
        //     steps {
        //         playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT, INVENTORY) }
        // }

        // stage('mail.dom.test.gosuslugi.ru(NOT NEED UPDATE)') {
        //     when {
        //         expression { return params.MAIL } }
        //     environment {
        //         ANSIBLE_LIMIT ='vtc-mail'
        //         INVENTORY = 'inventories/voshod/test' }
        //     steps {
        //         playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT, INVENTORY) }
        // }

        stage('plug stage') {
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
