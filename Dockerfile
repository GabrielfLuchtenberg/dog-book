FROM openjdk:8-alpine

COPY target/uberjar/dog-book.jar /dog-book/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/dog-book/app.jar"]
