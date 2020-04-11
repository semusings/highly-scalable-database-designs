# Stage 0, "build-stage", based on Node.js, to build and compile the frontend
#FROM node:13 as build-stage
#WORKDIR /app
#COPY ./ /app/
#RUN yarn
#RUN npm run links && npm run build

# Stage 1, based on Nginx, to have only the compiled app, ready for production with Nginx
FROM nginx:1.17
COPY --from=build-stage /app/dist/out/ /usr/share/nginx/html
# Copy the default nginx.conf provided by build-stage
COPY --from=build-stage /app/nginx.conf /etc/nginx/conf.d/default.conf