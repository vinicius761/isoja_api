FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

RUN mkdir -p /app/uploads && chmod -R 777 /app/uploads

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw

# Copia o código diretamente
COPY src ./src

EXPOSE 8080

# Executa compilando em tempo real
CMD ["./mvnw", "spring-boot:run"]