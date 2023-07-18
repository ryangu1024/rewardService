# Assignment: Compute Reward Service

This service allows user to input transaction history of customers and extract reward point for any customer per month and total.


# API

I created two REST APIs for this service:

## getReward

Endpoint: /reward/getReward

This GET API allows users to obtain reward of a customer aggregated on a monthly basis and the total reward within a specified time range.

It accepts three parameter:

* 'userID', the name of customer.

* 'startMonth', the start month of the time range for the statistics. If not provided, the service will use the recent X months as the time range, X is the value of 'months' parameter.(format: "yyyy-MM")

* 'months', the number of month of the time range. The default value is 3.

### Example Request

curl -X GET "http://localhost:8080/reward/getReward?userID=Bill&startMonth=2023-05&months=3"

### Example Response

{"total":140,"2023-05":50,"2023-06":0,"2023-07":90}

## addTransaction

Endpoint: /reward/addTransaction

This POST API allows users to input a transaction to the database.

It accepts three parameter:

* 'userID', the name of customer.

* 'price', the amount of dollar the customer spent on this transaction.

* 'date', the date of transaction. The default value is current date. (format: "yyyy-MM-DD")

### Example Request

curl http://localhost:8080/reward/addTransaction -d "userID=Bill&price=100&date=2023-05-16"

# Implementation

Controller, Service and Repository classes are created for presentation layer, service layer and DAO with Spring Boot.

This service uses Hibernate as ORM and utilizes the H2 database for data storage and retrieval.

JPA Criteria Queries API is used to create SQL queries.

# Build

To build the service, run this command in terminal:

'mvn clean compile'

# Unit test

Test cases are created for controller, service and utility method.

# built-in data

Some transaction data is added for test:

| PRICE   | REWARD   | TIMESTAMP   | ID   | USERID   |
|---------|----------|-------------|------|----------|
| 20.0    | 0        | 2023-07-01  | 1    | Bill     |
| 120.0   | 90       | 2023-07-01  | 2    | Bill     |
| 100.0   | 50       | 2023-05-01  | 3    | Bill     |
| 150.0   | 150      | 2023-02-01  | 4    | Bill     |
| 100.0   | 50       | 2022-07-01  | 5    | Alice    |
| 60.0    | 10       | 2023-07-01  | 6    | Alice    |

