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
}
