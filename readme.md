# Создан микросервис Kafka + Spring


## Описание
Имеется три микросервиса: \
`order-service` - отправляет `Order` событие в топик Kafka и управляет процессом распределенной транзакции \
`payment-service` -  выполняет локальную транзакцию по счету клиента на основе цены `Order` \
`stock-service` - выполняет локальную транзакцию на основе количества товаров в `Order`


![image](https://raw.githubusercontent.com/piomin/sample-spring-kafka-microservices/master/arch.png)

## Принцип работы
(1) `order-service` отправляет новый `Order` -> `status == NEW` \
(2) `payment-service` и `stock-service` получают `Order` и обрабатывают действие, выполняя локальную транзакцию \
(3) `payment-service` и `stock-service` отправляют ответ `Order` -> `status == ACCEPT` или `status == REJECT` \
(4) `order-service` обрабатывают входящий поток от `payment-service` и `stock-service`, и помещает их в `Order` с новым статусом -> `status == CONFIRMATION` или `status == ROLLBACK` или `status == REJECTED` \
(5) `payment-service` и `stock-service` получают `Order` с окончательным статусом "commit" или "rollback"

## Running on Docker locally
You can easily run all the apps on Docker with Spring Boot support for
(a) Testcontainers
(b) Docker Compose

(a) For Testcontainers
Go to the `order-service` directory and execute:
```shell
$ mvn clean spring-boot:test-run
```

Then go to the `payment-service` directory and execute:
```shell
$ mvn clean spring-boot:test-run
```

Finally go to the `stock-service` directory and execute:
```shell
$ mvn clean spring-boot:test-run
```

You will have three apps running with a single shared Kafka running on Testcontainers.

(b) For Docker Compose
First build the whole project and images with the following command:
```shell
$ mvn clean package -DskipTests -Pbuild-image
```

Then, you can go to the one of available directories: `order-service`, `payment-service` or `stock-service` and execute:
```shell
$ mvn spring-boot:run
```

You start your app and have Kafka and two other containers started with Docker Compose.
