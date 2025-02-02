# Usando a imagem oficial do Maven para compilar a aplicação
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos do projeto para dentro do container
COPY pom.xml .
COPY src ./src

# Faz o build da aplicação
RUN mvn clean package -DskipTests

# Usando a imagem oficial do OpenJDK 21 para rodar a aplicação
FROM eclipse-temurin:21-jdk

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR gerado pelo estágio de build
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Comando de execução da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
