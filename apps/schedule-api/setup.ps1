# Git 설정
if (Test-Path .gitmessage) {
    Write-Host "Configuring Git commit template..."
    git config commit.template .gitmessage
}

# Node.js 의존성 설치
if (Test-Path package.json) {
    Write-Host "Installing Node.js dependencies..."
    npm install
}

# Python 가상환경 설정 및 의존성 설치
if (Test-Path requirements.txt) {
    Write-Host "Creating Python virtual environment..."
    python -m venv venv
    & ./venv/Scripts/Activate.ps1
    Write-Host "Installing Python dependencies..."
    pip install -r requirements.txt
}

# Spring Maven 의존성 설치
if (Test-Path "pom.xml") {
    Write-Host "Installing Maven dependencies..."
    mvn clean install
}

# Spring Gradle 의존성 설치
if ((Test-Path "build.gradle") -or (Test-Path "build.gradle.kts")) {
    Write-Host "Installing Gradle dependencies..."
    .\gradlew clean build
}

Write-Host "Setup complete!"
