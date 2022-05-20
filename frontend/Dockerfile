FROM node:16-alpine as builder
WORKDIR '/app'
COPY ./package.json ./
RUN npm install @angular/cli && npm install
COPY . .
RUN npm run build

FROM nginx:1.21.4-alpine
EXPOSE 4200
COPY nginx-client.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/dist/pizza-app /usr/share/nginx/html