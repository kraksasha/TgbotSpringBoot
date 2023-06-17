FROM openjdk:17

WORKDIR /app

ARG JAR_FILE=target/TgbotSpringBoot-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} TgbotSpringBoot-0.0.1-SNAPSHOT.jar
COPY Manifest.mf /app/Manifest.mf

CMD ["java", "-jar", "TgbotSpringBoot-0.0.1-SNAPSHOT.jar", "-m","Manifest.mf"]