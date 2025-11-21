# Imagem base oficial do OpenJDK 17
FROM openjdk:17-jdk-slim

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR gerado pelo Maven para dentro do container
COPY target/nova-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão do Spring Boot (geralmente 8080)
EXPOSE 8080

# Comando para executar o aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]
