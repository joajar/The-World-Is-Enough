FROM openjdk:8
ADD target/joajar-postgre.jar joajar-postgre.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "joajar-postgre.jar"]
