# Dogcue Assessment

CRU Rest API for customers data


## Install

First of all, clone or download this repo. 

Once this is done, ensure Docker daemon is started. Then, build the entire project from parent.

To start all services, from parent folder type on command line:
```sh
docker-compose up -d
```

If any of the microservices exits, rerun exited ones by typing:
```sh
docker-compose start
```

Microservices can be found on ports 8080 and 8081. You can interact with API by using Swagger interface, found at http://localhost:8080/swagger-ui.html or http://localhost:8081/swagger-ui.html.


## Docs

Documentation can be found on code or via SwaggerUI. Beside that, you can find here basic documentation too.

### Customer Entity

Represents the customer information saved into database. It has following attributes:

* id - _autogenerated, unique_
* name - _required_
* email - _required, unique_
* phone - _unique_
* address

### Microservices

This project exposes 2 microservices:

1. This service is used to ***create*** or ***update*** customers. Exposes POST method to create a customer. Exposes PUT method to update a customer.
2. This service is used to ***retrieve*** customer data. Exposes GET method requiring customer ***id***. Additionaly Exposes GET method to retrieve all customers (just testing purposes).

### Database

I used In-Memory H2 Database.

Database is shared between microservices. To acomplish this, customerPost microservice also starts database and exposes it on localhost:9090.

### Versions

* Java jdk 11
* Gradle v6.8.1
* Docker v20.10.10


---
Made by Mario Ferrero Fernández
