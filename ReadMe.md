# File -> New -> Module From Existing Sources -> select pom.xml
Exchange: fitness.exchange   is required to be added to the rabbit mq

keycloak ===  create 1) realm first and then  2) oath2-pkce-client


https://www.youtube.com/watch?v=_FdKTSFnWeg
Java Full Stack Spring Boot AI Microservice Project: AWS, Spring Boot, Spring Cloud, Keycloak, React
http://localhost:8888/activity-service/default
http://localhost:8888/user-service/default
http://localhost:8888/ai-service/default

eureka
http://localhost:8761/
https://www.composerize.com/

1) Start service discovery server.
eureka -   localhost:8761

2) userservice
start postgres db from userservice 
docker compose -f docker/compose.yml up
docker compose -f docker/compose.yml down

POST  http://localhost:81/apiv1/users/register
{
"email":"xyz@gmail.com",
"password":"password",
"firstName":"John",
"lastName":"Doe"
}
GET http://localhost:81/apiv1/users/1
GET http://localhost:81/apiv1/users/1/validate

3) activityservice
start mongo db from activity service
docker compose -f docker/compose.yml up
docker compose -f docker/compose.yml down

POST   localhost:82/apiv1/activities
{
"userId":3,
"type":"SWIMMING",
"duration":30,
"calories":300,
"startTime":"2025-06-12T12:11:33",
"additionalMetrics":{
"distance":30000,
"averageSpeed":11.4,
"maxHartRate":167
}
}

GET  localhost:82/apiv1/activities
Headers   key:X-User-ID    value:1

GET localhost:82/apiv1/activities/68485b0697b563208711c398


4) aiservice  currently uses the same mongo db as in activity service 
( should really use it's own db. Was not able to run to containers with the same port numbers )

GET   http://localhost:83/apiv1/recommendations/user/1
GET   http://localhost:83/apiv1/recommendations/activity/1

------------------------------------------------------------------
1 - PROBLEM  ( synchronous communication can slow system )
2 - EVENT DRIVEN MICROSERVICES
3 - RABBIT MQ

docker run --hostname rmq --name rabbit-server-cnr -p 8088:15672 -p 5672:5672 rabbitmq:3-management
##docker run -d --hostname rmq --name rabbit-server-cnr -p 8088:15672 -p 5672:5672
##rabbitmq:3-management
docker run -it --rm --name rabbitmq-cnr -p 5672:5672 -p 15672:15672 rabbitmq:4-management
docker network ls
docker network rm docker_default

1. add new exchange:fitness.exchange
rabbitmq.exchange.name=fitness.exchange

# RabitMQ server running
http://localhost:8088/
http://localhost:15672/
guest
guest

docker run -e SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/mydb -e API_KEY=your-key my-app
Dockerfile: Define environment variables in the Dockerfile using the ENV instruction:
dockerfile
ENV SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/mydb
ENV API_KEY=your-key
Running docker exec <container> env to list environment variables.

-------------------------------------------------------------------------
Option 1: IntelliJ Compound Run Configuration (Recommended for GUI users)

    Open IntelliJ.
    Go to Run > Edit Configurations...
    Click the "+" in the top-left to add a new configuration.
    Choose Compound.
    Give it a name, e.g., Start All Apps.
    In the "Run/Debug Configurations" section, click "+" and select your existing Spring Boot apps (you must have at least two defined).
    Click Apply and OK.

Now, when you run Start All Apps, IntelliJ will start all selected apps in parallel.
-------------------------------------------------------------------------------------
Example with @Retryable or manual retries for a REST call or DB connection.

Option 3: Use Docker Compose for Order and Health Checks
Docker Compose supports depends_on and health checks to manage order.

services:
app1:
build: ./app1
healthcheck:
test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 10s
retries: 5

app2:
build: ./app2
depends_on:
app1:
condition: service_healthy

-----------------------------------------------------------------
CTRL+SHIFT+F
-----------------------------------------------------------------
Option 1: Shell Script with Controlled Order
If you're using a script (e.g., start-all.sh), you can wait for each app to start before launching the next.

#!/bin/bash

# Start MongoDB container or external service, if any
docker-compose up -d mongo1 mongo2

# Start App 1 (e.g., service registry or config server)
echo "Starting App 1..."
cd path/to/app1
./mvnw spring-boot:run &
APP1_PID=$!

# Wait for App 1 to be ready (e.g., wait for port 8080)
while ! nc -z localhost 8080; do
echo "Waiting for App 1..."
sleep 2
done
echo "App 1 is up."

# Start App 2 (e.g., client app)
cd path/to/app2
./mvnw spring-boot:run &
APP2_PID=$!

wait $APP1_PID
wait $APP2_PID

    You can use curl, nc, or wait-for-it.sh to wait for a specific port or health endpoint.
=================================  docker
docker stop hungry_cerf

==========    keyclock   ==================
username: keycloak   password: keycloak
docker run -p 8181:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=keycloak -e KC_BOOTSTRAP_ADMIN_PASSWORD=keycloak quay.io/keycloak/keycloak:26.2.5 start-dev
docker run -p 8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.2.5 start-dev

name: <your project name>
services:
keycloak:
ports:
- 8080:8080
environment:
- KC_BOOTSTRAP_ADMIN_USERNAME=admin
- KC_BOOTSTRAP_ADMIN_PASSWORD=admin
image: quay.io/keycloak/keycloak:26.2.5
command: start-dev

==========================   volume for keycloak  =================
version: '3.8'

services:
keycloak:
image: quay.io/keycloak/keycloak:26.2.5
command: start-dev
ports:
- "8181:8080"
environment:
- KEYCLOAK_ADMIN=keycloak
- KEYCLOAK_ADMIN_PASSWORD=keycloak
volumes:
- ./gateway/docker/volumes/rabbitmq_data:/var/lib/rabbitmq


#- keycloak_data:/opt/keycloak/data
#volumes:
#keycloak_data: