FROM ubuntu  as starter
RUN apt-get update
RUN apt-get install openssh-client -y git
RUN useradd -ms /bin/bash rashed
USER rashed
RUN mkdir -p -m 0600 ~/.ssh && ssh-keyscan github.com >> ~/.ssh/known_hosts
RUN --mount=type=ssh git clone git@github.com:rashed-08/config-service.git


FROM maven:3.8.4-jdk-11 AS build
COPY --from=starter config-service ~
COPY src /usr/app/src
COPY pom.xml /usr/app
RUN mvn -f /usr/app/pom.xml clean install

FROM openjdk:11
ENV APP_ROOT /app
RUN mkdir ${APP_ROOT}
WORKDIR ${APP_ROOT}

COPY --from=build /usr/app/target/ ${APP_ROOT}/

ENTRYPOINT ["java","-jar","config-service.jar"]
