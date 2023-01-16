FROM maven:3.8.7-eclipse-temurin-19-focal

WORKDIR /workers
COPY . .

RUN mvn clean install

CMD mvn spring-boot:run