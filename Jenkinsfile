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
