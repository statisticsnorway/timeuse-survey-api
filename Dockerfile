FROM maven:3.8.6-amazoncorretto-17 as build

COPY ./build/libs/timeuse-survey-api.jar /usr/share/timeuse/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/usr/share/timeuse/timeuse-survey-api.jar"]


