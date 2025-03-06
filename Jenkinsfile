pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/pierocr/Test-Appium.git'
            }
        }

        stage('Ejecutar Tests') {
            steps {
                sh './gradlew test'
            }
        }
    }

    post {
        always {
            cucumber buildStatus: 'UNSTABLE',
                     fileIncludePattern: '**/reports/cucumber.json',
                     jsonReportDirectory: 'build/reports',
                     sortingMethod: 'ALPHABETICAL'
        }
    }
}
