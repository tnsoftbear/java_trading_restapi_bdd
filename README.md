# Trading Demo REST API

Welcome to the Trading Demo REST API! This application provides a RESTful API for managing users, securities, orders, and trades. It is built using Spring Boot framework.

## Features

- **Models**: User, Security, Order, Trade
- **Database**: H2 in-memory database. Access the H2 console at [http://localhost:8080/h2-ui](http://localhost:8080/h2-ui)
- **Testing**: Cucumber BDD test coverage
- **Reports**: Jacoco coverage report, Cluecumber report, and Cucumber HTML report

## System Overview

* **Functionality**: Trade Operations
* **Primary Objective**: Providing users with the ability to execute trade operations with securities.
* **Scenario**: Trades occur when there are both sell and buy orders for the same security.

### Scenario Steps

* **Precondition**: There is one security labeled "_WSB_" and two users named "_Diamond_" and "_Paper_" exist in the system.
* **Action Step**: User "_Diamond_" places a buy order for security "_WSB_" at a price of **101** and a quantity of **50**.
* **Action Step**: User "_Paper_" places a sell order for security "_WSB_" at a price of **100** and a quantity of **100**.
* **Result Verification**: A trade occurs with a price of **100** and a quantity of **50**.
* **Expected Outcome**: After the scenario execution, a successful trade operation will have taken place between users "_Diamond_" and "_Paper_" in the securities market. The trade price will be **100**, and the quantity traded will be **50** units.

## DB Schema overview

![20240217-db-schema.png](doc/i/20240217-db-schema.png)

DB Schema is defined in `src/main/resources/schema.sql`, and DB is preloaded with data from `src/main/resources/data.sql`.

**Models** are User, Security, Order, Trade.

* **Security**: Securities are represented as tradable entities, such as stocks, within the system.
* **User**: Users have the ability to place orders for buying or selling securities.
* **Order**: Orders are entities containing attributes such as ID, user, security, type, price, quantity, and fulfillment status. These orders represent intentions to buy or sell securities within the system.
* **Trade**: Trades are entities that are created when two or more orders are matched, resulting in an actual transaction taking place within the system.

## Getting Started

To run the REST API server locally, you can use Maven or Docker:

### Run REST API Server

```sh
# Using Maven
mvnw clean spring-boot:run

# Using Maven (alternative)
mvn clean package
java -jar ./target/trading_demo-0.0.1-SNAPSHOT.jar

# Using Docker
docker compose build
docker compose up
```

After starting the server, you can access the API endpoints at http://localhost:8080/api/v1.  
Read user list for API checking: `curl http://127.0.0.1:8080/api/v1/users` 

## Run BDD test

```sh
# Run tests via Maven
mvn test

# Or using Maven wrapper
mvnw test
```

### Test with reports

```sh
# Run tests with reports
mvn verify

# Or using Maven wrapper
mvnw verify

# Via docker
docker build -t trading_demo_bdd_test -f ./Dockerfile.runtest .
docker run trading_demo_bdd_test
# Enter docker container
docker run -it trading_demo_bdd_test bash
```

Observe HTML reports in

* `target/site/jacoco/index.html` Jacoco coverage report

![20240215-jacoco-report.png](doc/i/20240215-jacoco-report.png)

* `target/generated-report/index.html` Cluecumber report

![20240215-add-user-and-search-report.png](doc/i/20240215-add-user-and-search-report.png)
![20240215-all-steps-report.png](doc/i/20240215-all-steps-report.png)

* [trivago/cluecumber](https://github.com/trivago/cluecumber/)

* `target/cucumber-report/cucumber.html`

![20240216-cucumber-report.png](doc/i/20240216-cucumber-report.png)

## Manual requests

* post buy order and trade: POST: http://localhost:8080/api/v1/order/buy_and_trade

```json
{
  "price": 100,
  "quantity": 80,
  "securityName": "Apple",
  "userName": "user4"
}
```

* post sell order and trade: POST: http://localhost:8080/api/v1/order/sell_and_trade

```json
{
  "price": 150,
  "quantity": 40,
  "securityName": "Apple",
  "userName": "user3"
}
```
* list orders: GET: http://localhost:8080/api/v1/order

* list users: GET: http://localhost:8080/api/v1/users

`curl -X GET http://localhost:8080/api/v1/users`

* add user: POST: http://localhost:8080/api/v1/users/save

```json
{
    "username": "user5",
    "password": "pw5"
}
```

* list securities: GET: http://localhost:8080/api/v1/security
* find by name: GET: http://localhost:8080/api/v1/security/Apple
* add security: POST: http://localhost:8080/api/v1/security/save

```json
{
    "name": "sec-name"
}
```

* delete security: DELETE: http://localhost:8080/api/v1/security/delete/sec-name

## Contributing

We welcome contributions! If you have any ideas or improvements, feel free to open an issue or create a pull request.

Here's how you can contribute:

1. **Open an Issue**: If you find a bug, have a suggestion, or want to discuss something, open an issue on GitHub. Provide as much detail as possible to help us understand the problem or enhancement you're proposing.
2. **Create a Pull Request**: If you'd like to contribute code, fork the repository, create a new branch, and make your changes. Once you're ready, submit a pull request. Be sure to describe your changes in detail and reference any related issues.
3. **Review Pull Requests**: Help review open pull requests by testing changes, providing feedback, and approving or suggesting improvements.
4. **Spread the Word**: Share the project with others who might be interested. The more people who use and contribute to the project, the better it becomes!

Happy coding!
