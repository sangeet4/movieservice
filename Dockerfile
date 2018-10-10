FROM openjdk:10-jre
ADD ./target/movieservice-0.0.1-SNAPSHOT.jar /usr/app/movieservice-0.0.1-SNAPSHOT.jar
WORKDIR usr/app
ENTRYPOINT ["java","-jar", "movieservice-0.0.1-SNAPSHOT.jar"]