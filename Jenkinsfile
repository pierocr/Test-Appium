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
                sh './gradlew clean test'
            }
        }
    }

    post {
        always {
            cucumber buildStatus: 'UNSTABLE',
                     fileIncludePattern: '**/cucumber.json',
                     jsonReportDirectory: 'build/reports',
                     sortingMethod: 'ALPHABETICAL'

            // Archiva las capturas generadas durante la ejecución
            archiveArtifacts artifacts: 'screenshots/*.png', allowEmptyArchive: true

            // Genera el reporte Allure
                    allure([
                        includeProperties: false,
                        results: [[path: 'build/allure-results']]
                    ])
        }
    }
}