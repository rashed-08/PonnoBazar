FROM maven:3.8.4-jdk-11 AS build
COPY src /usr/app/src
COPY pom.xml /usr/app
RUN cd /usr/app/ && mvn clean install

FROM openjdk:11
ENV APP_ROOT /app
RUN mkdir ${APP_ROOT}
WORKDIR ${APP_ROOT}

COPY --from=build /usr/app/target/ ${APP_ROOT}/

ENTRYPOINT ["java","-jar","eureka-server.jar"]
