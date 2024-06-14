# Use a imagem oficial do Maven, versão 3.8.1 com JDK 17
FROM maven:3.8.1-openjdk-17 as build

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie o pom.xml e o diretório src para o diretório de trabalho
COPY pom.xml .
COPY src ./src

# Construa o projeto
RUN mvn package -DskipTests

# Use a imagem oficial do OpenJDK 17 para rodar o aplicativo
FROM openjdk:17-jdk-slim

# Copie o jar do estágio de construção
COPY --from=build /app/target/*.jar appv1.jar

# Exponha a porta 8080 para o Spring Boot
EXPOSE 8080

# Defina o comando para iniciar o aplicativo
ENTRYPOINT ["java","-jar","/appv1.jar"]