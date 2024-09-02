# Usar a imagem oficial do Gradle com OpenJDK 17 como base
FROM gradle:8.0.2-jdk17 AS build

# Definir o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar arquivos de configuração do Gradle
COPY build.gradle.kts settings.gradle.kts ./

# Copiar o código-fonte do aplicativo
COPY src ./src

# Executar o comando Gradle para compilar e empacotar a aplicação
RUN gradle clean bootJar -x test

# Usar uma imagem menor do OpenJDK 22 para a execução
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar o JAR gerado pelo build
COPY --from=build /app/build/libs/*.jar app.jar

# Expor a porta usada pelo Spring Boot (por padrão, 8080)
EXPOSE 8080

# Definir o comando de inicialização
CMD ["java", "-jar", "app.jar"]
