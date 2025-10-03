# Stage 1: Build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /workspace/app

# Copia apenas os arquivos necessários
COPY pom.xml .
COPY src src

# Se você tiver o mvnw e .mvn, pode usar o Maven Wrapper
# Caso contrário, use o Maven do container
# Copiando mvnw e dando permissão
COPY mvnw .
RUN chmod +x ./mvnw

# Build do projeto (skip tests para acelerar)
RUN ./mvnw install -DskipTests || mvn install -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia o jar gerado no build
COPY --from=build /workspace/app/target/*.jar app.jar

# Expõe porta padrão do Spring Boot
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
