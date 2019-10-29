
FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG JAVA_OPTS
#ENV JAVA_OPTS=$JAVA_OPTS
#ADD TicketService.jar ./
#EXPOSE 8080
#ENTRYPOINT exec java $JAVA_OPTS -jar TicketService.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar spring-server-generated.jar

#FROM java:8-jdk-alpine

RUN mkdir -p /TicketClient
COPY ./spring-server/target/TicketService.jar /TicketClient
WORKDIR /TicketClient
RUN sh -c 'touch TicketService.jar'
ENTRYPOINT ["java","-jar","TicketService.jar"]
