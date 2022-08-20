package com.passfailerror

pipeline {
    agent { label 'test' }
    stages {
        stage('First stage') {
            steps {
                echo 'First'
                sh 'mvn --version'
            }
        }
        stage('Second stage') {
            steps {
                echo 'Second'
                sh 'java -version'
            }
        }
    }
}