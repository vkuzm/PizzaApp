FROM maven:3.8.4-jdk-11-slim
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

ENTRYPOINT ["java","-jar","/home/app/target/admin-service.jar"]