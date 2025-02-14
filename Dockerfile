# Usa a versão do Java correta
FROM openjdk:23


# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

COPY target/avonManager-0.0.1-SNAPSHOT.jar app.jar
# Exponha a porta que o Spring Boot estará ouvindo
EXPOSE 8080

# Comando para executar o JAR, com a classe principal AvonManagerApplication
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]