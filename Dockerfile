FROM openjdk:17-jdk-alpine

LABEL authors="AyselS"

EXPOSE 5500

COPY target/MoneyTransferService-0.0.1-SNAPSHOT.jar moneytransferapp.jar

CMD ["java", "-jar", "moneytransferapp.jar"]