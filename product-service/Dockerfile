FROM openjdk:11

ENV APP_ROOT /app
RUN mkdir ${APP_ROOT}
WORKDIR ${APP_ROOT}

COPY target/*.jar ${APP_ROOT}/product_service.jar

ENTRYPOINT ["java","-jar","product_service.jar"]