FROM alpine-with-jdk11:latest as jdk
WORKDIR /app
COPY ./target/*.jar /app

LABEL "author"="yongj.zhuang"
LABEL "description"="Backend for websiteSaver"

ENTRYPOINT ["java", "-jar", "websiteSaver-0.0.1.jar"] 

