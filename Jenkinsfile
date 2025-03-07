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
                     fileIncludePattern: 'build/reports/*.json',
                     jsonReportDirectory: 'build/reports',
                     sortingMethod: 'ALPHABETICAL'

            archiveArtifacts artifacts: 'screenshots/*.png', allowEmptyArchive: true

            // ⬇️ Aseguramos que el directorio de resultados de Allure existe
            sh 'mkdir -p build/allure-results'

            // ⬇️ Publica el reporte de Allure
            allure([
                includeProperties: false,
                jdk: '',
                results: [[path: 'build/allure-results']]
            ])
        }
    }
}
