#!/usr/bin/env groovy

/* set basic job configurations */
properties([
        buildDiscarder(logRotator(numToKeepStr: '50', artifactDaysToKeepStr: '10')), 
        disableConcurrentBuilds(), 
        pipelineTriggers([snapshotDependencies()])
    ])

node('jdk11') {
    try{
        stage('Prepare'){        
            checkout scm
        }
        
        stage('Build'){
            try{
                mvn '-U clean install'
            }finally{
                junit '**/target/*-reports/TEST-*.xml'
                jacoco execPattern: '**/target/jacoco*.exec'    
            }
        }
        
        stage('Static Analysis'){
            try{
                withSonarQubeEnv('Sonarqube') {
                    def model = readMavenPom(file: 'pom.xml')
                    mvn "-DskipTests \
                            -Dsonar.projectKey=${model.getGroupId()}:${model.getArtifactId()}:${BRANCH_NAME.replace('/',"-")} \
                            -Dsonar.projectName=\"${model.getName()} ($BRANCH_NAME)\" \
                            pmd:cpd pmd:pmd sonar:sonar"
                }
            }finally{
                recordIssues (enabledForFailure: true, 
                    tools: [
                        cpd(pattern: '**/target/cpd.xml'), 
                        pmdParser(pattern: '**/target/pmd.xml'),
                        taskScanner(includePattern: '**/*.java',excludePattern: '**/target/**', highTags: 'FIXME', normalTags: 'PENDING', lowTags: 'TODO')
                    ])
            }
        }
        
        stage("Quality Gate"){
            timeout(time: 15, unit: 'MINUTES') {
                def qg = waitForQualityGate()
                if (qg.status == 'ERROR') {
                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                }else if (qg.status == 'WARN') {
                    currentBuild.result = 'UNSTABLE'
                }
            }
        }

//        stage('Deploy'){
//            retry(3) {
//                mvn "-DskipTests deploy"
//            }
//        }
        
        currentBuild.result = 'SUCCESS'
    }catch(ex){
        if(null == currentBuild.result){
            currentBuild.result = 'FAILED'
        }
        throw ex
    }finally{
        stage("Inform"){
            recordIssues enabledForFailure: true, tools: [
                mavenConsole(),
                java(),
                javaDoc()
            ]        
            emailext (recipientProviders: [culprits()], 
                subject: "Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' ${currentBuild.result}",
                body: """
                <p>${currentBuild.result}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>\n\
            """,
                attachLog: true
            )
        }
    }
}

def mvn(String goals){
    withMaven(
        jdk: 'jdk11',
        maven: 'default', 
        mavenSettingsConfig: '1cefb286-dc79-4719-8d17-b27e2f75a22d'
    ) {
        sh "mvn $goals"
    }
}
