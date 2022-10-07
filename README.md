# Online Store for digital games

This project is designed for high scalability and availability
- microservices
- reactive
- quarkus
- hibernate
- maven
- kubernetes
- rest

At the moment it's a really basic backend with some tests. You can make new orders, add games, categories, etc.

# Backlog
- CQRS
- logging
- drafts
- currency support
- ci/cd
- NoSQL


[postman collection](https://www.getpostman.com/collections/932c083dff295a5d7698)

# DB
run `kubectl apply -f configmap.yaml` in the root of the project to add settings for db (each service uses this data)
alternatively you can edit /src/main/resources/application.properties in each of the services
you can use different DB with each service, or a load balancer
# Starting in kubernetes
run `mvn clean package -Dquarkus.kubernetes.deploy=true` in:
- /customer-service
- /game-service
- /order-service

# Starting in dev mode
run `mvn quarkus:dev` in:
- /customer-service
- /game-service
- /order-service