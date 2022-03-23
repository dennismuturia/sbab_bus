FROM openjdk:11
RUN export trafik_key=79af0cbbdff04beda7ea774ecaa51343
EXPOSE 8082
COPY /build/libs/sbab_bus_test-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]