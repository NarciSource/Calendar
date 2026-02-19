# Git 설정
if [[ -f .gitmessage ]]; then
    echo "Configuring Git commit template..."
    git config commit.template .gitmessage
fi

# Node.js 의존성 설치
if [[ -f package.json ]]; then
    echo "Installing Node.js dependencies..."
    npm install
fi

# Python 가상환경 설정 및 의존성 설치
if [[ -f requirements.txt ]]; then
    echo "Creating Python virtual environment..."
    python -m venv venv
    source ./venv/bin/activate
    echo "Installing Python dependencies..."
    pip install -r requirements.txt
fi

# Spring Maven 의존성 설치
if [[ -f "pom.xml" ]]; then
    echo "Installing Maven dependencies..."
    mvn clean install
fi

# Spring Gradle 의존성 설치
if [[ -f "build.gradle" || -f "build.gradle.kts" ]]; then
    echo "Installing Gradle dependencies..."
    ./gradlew clean build
fi

echo "Setup complete!"
