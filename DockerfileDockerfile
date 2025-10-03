# Imagem base do OpenJDK 11 (leve)
FROM openjdk:11-jdk-slim

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR da aplicação
COPY target/controle-estoque-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta que a aplicação vai usar
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java","-jar","app.jar"]
