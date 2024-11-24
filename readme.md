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

## Для запуска


```shell
$ mvn clean package -DskipTests -Pbuild-image
```

```shell
$ mvn spring-boot:run
```

