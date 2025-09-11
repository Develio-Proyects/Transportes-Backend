# Backend Transportes

## Variables de ambiente

SPRING_PROFILES_ACTIVE=  
FRONT_URL=  
API_URL=  
JWT_SECRET_KEY=  
DB_PROD_URL=  
DB_PROD_PASSWORD=  
MP_ACCESS_TOKEN=  
MP_WEBHOOK_KEY=  
JAVA_TOOL_OPTIONS=-Duser.timezone=America/Argentina/Buenos_Aires

## Dockerización

Comandos para generar la imagen que utiliza el server
- docker login
- ./gradlew bootJar
- docker build -t tobiasriccone/backend-transportes:latest .
- docker push tobiasriccone/backend-transportes:latest