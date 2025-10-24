@echo off
echo Compilando backend con Gradle...
call gradlew.bat bootJar

echo Construyendo imagen Docker...
docker build -t tobiasriccone/backend-transportes:latest .

echo Pusheando imagen a Docker Hub...
docker push tobiasriccone/backend-transportes:latest

pause