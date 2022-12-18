/*
- поменять креды/репозиторий/ветки
- проверить вызов extraVars
- удалить ли шаги с неиспользуемыми хостами..?
- проверить корректность рабочего пути
- оповещения в ТГ
*/

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
        // checkboxes for gkhcontent.ru stands
        booleanParam(name: 'GKHCONTENT',                defaultValue: false, description: 'gkhcontent.ru')
        booleanParam(name: 'TEST_GKHCONTENT',           defaultValue: true, description: 'test.gkhcontent.ru')
        // checkboxes for dom.test.gosuslugi.ru stands
        booleanParam(name: 'VTC',                       defaultValue: false, description: 'vtc.dom.test.gosuslugi.ru')
        booleanParam(name: 'SIT',                       defaultValue: false, description: 'sit0[1-2].dom.test.gosuslugi.ru')
        booleanParam(name: 'SSP',                       defaultValue: false, description: 'ssp.dom.test.gosuslugi.ru')
        booleanParam(name: 'NT01',                      defaultValue: false, description: 'nt01.dom.test.gosuslugi.ru')
        booleanParam(name: 'KPAK',                      defaultValue: false, description: 'kpak.dom.test.gosuslugi.ru')
        booleanParam(name: 'FT01',                      defaultValue: false, description: 'ft01.dom.test.gosuslugi.ru')
        booleanParam(name: 'SREDA_VTC',                 defaultValue: false, description: 'sreda.vtc.dom.test.gosuslugi.ru')
        booleanParam(name: 'SREDA_FT01',                defaultValue: false, description: 'sreda.ft01.dom.test.gosuslugi.ru')
        booleanParam(name: 'UPDATE_CERTS_MAIL',         defaultValue: false, description: 'Update certs mail.dom.test.gosuslugi.ru')
        // checkboxes for hcs.lanit.ru stands
        booleanParam(name: 'MOB_HCS_LANIT',             defaultValue: false, description: 'mob.hcs.lanit.ru')
        // checkboxes for ESIA stands
        booleanParam(name: 'ESIA_VTC',                  defaultValue: false, description: 'vtc-esia')
        booleanParam(name: 'ESIA_NT',                   defaultValue: false, description: 'nt-esia')

        booleanParam(name: 'IDP_JKS_UPDATE',            defaultValue: false, description: 'Updating idp.jks for idp service')
        booleanParam(name: 'JIRA_KEYSTORE_UPDATE',      defaultValue: false, description: 'Updating jira.keystore')

        string(name: 'ANSIBLE_BECOME_USER',             defaultValue: '', description: 'Enter username for Ansible BECOME')

        // deprecated
        booleanParam(name: 'NIT',                       defaultValue: false, description: 'nit.dom.test.gosuslugi.ru(deleted)')
        booleanParam(name: 'NIT_API',                   defaultValue: false, description: 'api.nit.dom.test.gosuslugi.ru(deleted)')
        booleanParam(name: 'MAIL',                      defaultValue: false, description: 'mail.dom.test.gosuslugi.ru(deleted)')

    }
    environment {
        PWD_VAR = "${WORKSPACE}"
        PROJECT = 'my_gip'
        REPO = 'git@github.com:lykarik'
    }

    stages {
        stage('Git checkout for Jenkinsfile') {
            steps {
                git branch: 'update_certs_try', 
                    changelog: false, 
                    credentialsId: 'jenkins-master-git-key', 
                    url: "${REPO}/${PROJECT}.git"
            }
        }
        stage('Git checkout main branch') {
            steps {
                dir("${WORKSPACE}") {
                    checkout([$class: 'GitSCM',

                                userRemoteConfigs: [[url: "${REPO}/${PROJECT}.git", credentialsId: 'jenkins-master-git-key']],
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
        stage('plug stage') {
            when {
                expression { return params.TEST_GKHCONTENT }
            }
            steps {
                ansiColor('xterm') {
                    step(
                        [$class: 'AnsibleAdHocCommandBuilder',
                          ansibleName: 'Copy certs to ESIA',
                          disableHostKeyChecking: true,
                          colorizedOutput: true,
                          inventory: [$class: 'InventoryPath', path: "./inventories/main/hosts"],
                          hostPattern: 'jenkins-slave01:jenkins-slave02',
                          module: 'copy',
                          command: 'src=../_git/take_me.please dest=~/ owner=root group=wheel mode=644',
                          //becomeUser: "${ANSIBLE_BECOME_USER}",
                          //module: 'copy',
                          //command: 'src=../files/pki_files/certs/rsa_vtc.dom.test.gosuslugi.ru-letsencrypt.crt dest=/etc/pki/common/certs/ owner=root group=nginx mode=644',
                          credentialsId: 'ansible-lol-creds'
                        ]);
                    echo "test.gkhcontent.ru"
                    echo "${ANSIBLE_BECOME_USER}"
                    echo "${PWD_VAR}"
                    sh "pwd"
                    sh "ls -la"
                }
            }
        }

// stages for gkhcontent.ru stands
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
//
// stage for update vtc.dom.test.gosuslugi.ru stands
        stage('vtc.dom.test.gosuslugi.ru') {
            when {
                expression { return params.VTC } }
            environment {
                ANSIBLE_LIMIT_01 ='vtc-gs:putils02'
                ANSIBLE_LIMIT_02 ='voshod-test-single'
                INVENTORY = 'inventories/voshod/test' }
            steps {
                dir("${WORKSPACE}") {
                    ansiColor('xterm') {
                        ansiblePlaybook(
                            credentialsId: '',
                            //vaultCredentialsId: 'CredID_HCS_INFRA_ANSIBLE_Vault_Pass',
                            becomeUser: "${ANSIBLE_BECOME_USER}",
                            colorized: true,
                            limit: 'idp01',
                            inventory: 'inventories/voshod/test',
                            playbook: 'ansible-common/playbooks/pki-letsencrypt-nginx.yml',
                            extraVars: [
                                nginx_domain_name: "dom.test.gosuslugi.ru",
                                nginx_server_name: "vtc.{{nginx_domain_name}}",
                                pki: "{{pki_rsa_only_vtc_dom_test_idp}}",
                            ])
                    }
                }
                playbook_nginx_ng_rsa (ANSIBLE_LIMIT_01, INVENTORY)     // update hosts vtc.dom.test.gosuslugi.ru
                playbook_nginx_ng_rsa (ANSIBLE_LIMIT_02, INVENTORY)     // update hosts vtc.dom.test.gosuslugi.ru
            }
        }
//
// stage for update mob.hcs.lanit.ru stands
        stage('mob.hcs.lanit.ru') {
            when {
                expression { return params.MOB_HCS_LANIT } }
            environment {
                ANSIBLE_LIMIT ='izolda'
                INVENTORY = 'inventories/voshod/test' }
            steps {
                dir("${WORKSPACE}") {
                    ansiColor('xterm') {
                        ansiblePlaybook(
                            credentialsId: '',
                            //vaultCredentialsId: 'CredID_HCS_INFRA_ANSIBLE_Vault_Pass',
                            becomeUser: "${ANSIBLE_BECOME_USER}",
                            colorized: true,
                            limit: 'idp01',
                            inventory: 'inventories/voshod/test',
                            playbook: 'ansible-common/playbooks/pki-letsencrypt-nginx.yml',
                            extraVars: [
                                nginx_domain_name: "hcs.lanit.ru",
                                nginx_server_name: "mob.{{nginx_domain_name}}",
                                pki: "{{pki_rsa_only_mob_hcs}}",
                            ])
                    }
                }
                playbook_nginx_ng (ANSIBLE_LIMIT, INVENTORY)
            }
        }
// stages for ESIA stands
        stage('vtc-esia') {
            when {
                expression { return params.TEST_GKHCONTENT }
            }
            steps {
                ansiColor('xterm') {
                    step(
                        [$class: 'AnsibleAdHocCommandBuilder',
                          ansibleName: 'Copy certs to VTC-ESIA',
                          disableHostKeyChecking: true,
                          colorizedOutput: true,
                          inventory: [$class: 'InventoryPath', path: "./inventories/main/hosts"],
                          hostPattern: 'jenkins-slave01:jenkins-slave02',
                          module: 'copy',
                          command: 'src=../_git/take_me.please dest=~/ owner=root group=wheel mode=644',
                          //becomeUser: "${ANSIBLE_BECOME_USER}",
                          //module: 'copy',
                          //command: 'src=../files/pki_files/certs/rsa_vtc.dom.test.gosuslugi.ru-letsencrypt.crt dest=/etc/pki/common/certs/ owner=root group=nginx mode=644',
                          credentialsId: 'ansible-lol-creds'
                        ]);
                }
            }
        }
        stage('nt-esia') {
            when {
                expression { return params.TEST_GKHCONTENT }
            }
            steps {
                ansiColor('xterm') {
                    step(
                        [$class: 'AnsibleAdHocCommandBuilder',
                          ansibleName: 'Copy certs to NT-ESIA',
                          disableHostKeyChecking: true,
                          colorizedOutput: true,
                          inventory: [$class: 'InventoryPath', path: "./inventories/main/hosts"],
                          hostPattern: 'jenkins-slave01:jenkins-slave02',
                          module: 'copy',
                          command: 'src=../_git/take_me.please dest=~/ owner=root group=wheel mode=644',
                          //becomeUser: "${ANSIBLE_BECOME_USER}",
                          //module: 'copy',
                          //command: 'src=../files/pki_files/certs/rsa_vtc.dom.test.gosuslugi.ru-letsencrypt.crt dest=/etc/pki/common/certs/ owner=root group=nginx mode=644',
                          credentialsId: 'ansible-lol-creds'
                        ]);
                }
            }
        }
//
// stages for dom.test.gosuslugi.ru stands
        stage('Update certs mail.dom.test.gosuslugi.ru') {
            when {
                expression { return params.UPDATE_CERTS_MAIL } }
            environment {
                ANSIBLE_LIMIT ='vtc-mail'
                INVENTORY = 'inventories/voshod/test'
                PLAYBOOK = 'ansible-common/playbooks/postfix.yml' }
            steps {
                playbook_init (ANSIBLE_LIMIT, INVENTORY, PLAYBOOK) }
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
                playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT, INVENTORY) }
        }
//

/* 
        stage('nit.dom.test.gosuslugi.ru(DELETED)') {
            when {
                expression { return params.NIT } }
            environment {
                ANSIBLE_LIMIT ='nit-http-my'
                INVENTORY = 'inventories/voshod/test' }
            steps {
                playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT, INVENTORY) }
        }

        stage('api.nit.dom.test.gosuslugi.ru(DELETED)') {
            when {
                expression { return params.NIT_API } }
            environment {
                ANSIBLE_LIMIT ='nit-http-api'
                INVENTORY = 'inventories/voshod/test' }
            steps {
                playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT, INVENTORY) }
        }

        stage('mail.dom.test.gosuslugi.ru(NOT NEED UPDATE)') {
            when {
                expression { return params.MAIL } }
            environment {
                ANSIBLE_LIMIT ='vtc-mail'
                INVENTORY = 'inventories/voshod/test' }
            steps {
                playbook_pki_letsencrypt_nginx (ANSIBLE_LIMIT, INVENTORY) }
        }
*/
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
