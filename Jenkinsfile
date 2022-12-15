pipeline {
    agent {label 'master'}
    options {
        timestamps ()
        parallelsAlwaysFailFast()
    }
    parameters {
        choice (name: 'STAND_NAME', choices: '0000000\n1111111\n2222222\n33333', description: 'variants')
//        string (name: 'STAND_NAME_PARA',defaultValue: 'fjboss01',description: 'STAND_NAME')
        string (name: 'JOB_NAME',defaultValue: 'undefine',description: 'JOB_NAME')
        string (name: 'JOB_CRON', defaultValue: '0 0/30 * * * ?',description: 'JOB_CRON')
        string (name: 'JOB_STATUS', defaultValue: 'A',description: 'JOB_STATUS')
    }
    environment {
        LOGIN = "admin"
        HCS_GIT_REFERENCE_ROOT = ''
        ANSIBLE_HOST_KEY_CHECKING = 'false'
        ANSIBLE_STRATEGY = 'free'
    }
    stages {
        stage('Select workspace on master') {
            agent {label 'master'}
            steps {
                script {
                    env.WS_MASTER = WORKSPACE
                }
            }
        }
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
    }
}
