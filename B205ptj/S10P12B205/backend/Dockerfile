FROM azul/zulu-openjdk-alpine:17
MAINTAINER "mudokja@gmail.com"

ARG DEFAULT_PORT=8081
ARG JAR_FILE=build/libs/*.jar
ARG DEFAULT_BACKEND_BASEURL=http://localhost

COPY ${JAR_FILE} backend-app.jar

ENV PORT ${DEFAULT_PORT}
ENV BACKEND_BASEURL ${DEFAULT_BACKEND_BASEURL}

EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "/backend-app.jar"]