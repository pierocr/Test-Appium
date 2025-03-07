pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // ⬇️ Clona el repositorio desde GitHub en la rama main
                git branch: 'main', url: 'https://github.com/pierocr/Test-Appium.git'
            }
        }

        stage('Ejecutar Tests') {
            steps {
                // ⬇️ Ejecuta las pruebas con Gradle
                sh './gradlew clean test'
            }
        }
    }

    post {
        always {
            // ⬇️ Cambiar la ruta para asegurarnos de que Jenkins usa el JSON correcto de Cucumber
            cucumber buildStatus: 'SUCCESS', // Cambiar 'UNSTABLE' a 'SUCCESS' para evitar errores
                     fileIncludePattern: '**/cucumber.json',
                     jsonReportDirectory: 'build/allure-results', // Cambiado de 'build/reports' a 'build/allure-results'
                     sortingMethod: 'ALPHABETICAL'

            // ⬇️ Guarda las capturas de pantalla generadas en la ejecución
            archiveArtifacts artifacts: 'screenshots/*.png', allowEmptyArchive: true

            // ⬇️ Asegurar que el directorio de resultados de Allure existe antes de generar el reporte
            sh 'mkdir -p build/allure-results'

            // ⬇️ Genera y publica el reporte de Allure
            allure([
                includeProperties: false,
                results: [[path: 'build/allure-results']]
            ])
        }
    }
}
