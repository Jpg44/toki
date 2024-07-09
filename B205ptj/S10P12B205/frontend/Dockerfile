

FROM node:20-alpine AS install

LABEL project_name="toki-front"
MAINTAINER "mudokja@gmail.com"

WORKDIR /usr/src/modules
COPY ./package*.json ./
RUN npm install

FROM node:20-alpine as build

COPY --from=install /usr/src/modules /usr/src/build
WORKDIR /usr/src/build
COPY ./ ./

RUN npm run build


FROM nginx:alpine

RUN rm -rf /usr/share/nginx/html/* /etc/nginx/conf.d/default.conf
COPY --from=build /usr/src/build/dist /usr/shard/nginx/html

ARG DEFAULT_PORT=80
ARG DEFAULT_BACKEND_BASEURL=http://localhost:8081

ENV PORT ${DEFAULT_PORT}
ENV BACKEND_BASEURL ${DEFAULT_BACKEND_BASEURL}

EXPOSE $80
COPY ./nginx/nginx.conf /etc/nginx/nginx.conf


ENTRYPOINT ["nginx", "-g", "daemon off;"]