# PonnoBazar
**PonnoBazar** is basic microservice practice project. 
## PonnoBazar Architecture ##
![PonnoBazarNew](https://user-images.githubusercontent.com/41707235/170825877-c66efb29-d7d0-48a1-aed4-01be5e60c014.PNG)_

## Prerequisite ##
* Java 11
* Maven
* Docker
* Docker-compose

## How to build ##

`git clone git@github.com:rashed-08/PonnoBazar.git`

`cd PonnoBazar`

`cd eureka-service`

`mvn clean install`

`cd gateway-service`

`mvn clean install`

`cd inventory-service`

`mvn clean install`

`cd order-service`

`mvn clean install`

`cd product-service`

`mvn clean install`

`docker-compose up -d --build`

`cd elk`

`docker-compose up -d --build`