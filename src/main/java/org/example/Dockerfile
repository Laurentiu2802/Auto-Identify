FROM gradle:8.3-jdk17
WORKDIR /opt/app
COPY ./build/libs/autoidentify-1.0-SNAPSHOT.jar ./
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar autoidentify-1.0-SNAPSHOT.jar"]