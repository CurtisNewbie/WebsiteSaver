FROM nginx:latest

LABEL "author"="yongj.zhuang"
LABEL "description"="Nginx frontend for website saver"
LABEL "created"="09/02/2021"

# copy nginx config file
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

# copy hosted files
COPY ./static/ /usr/share/nginx/html/

EXPOSE 80
