```
src
├── main
│   ├── java
│   │   └── org
│   │       └── example
│   │           └── trading_demo
│   │               ├── TradingDemoApplication.java
│   │               ├── controller
│   │               │   ├── SecurityController.java
│   │               │   ├── StoredOrderController.java
│   │               │   └── UserController.java
│   │               ├── model
│   │               │   ├── CustomerOrder.java
│   │               │   ├── Security.java
│   │               │   ├── Trade.java
│   │               │   ├── User.java
│   │               │   └── stored_order
│   │               │       ├── StoredOrder.java
│   │               │       └── Type.java
│   │               ├── repository
│   │               │   ├── SecurityRepository.java
│   │               │   ├── StoredOrderRepository.java
│   │               │   ├── TradeRepository.java
│   │               │   └── user
│   │               │       ├── H2UserRepository.java
│   │               │       └── InMemoryUserRepository.java
│   │               └── service
│   │                   ├── OrderService.java
│   │                   ├── TradeService.java
│   │                   └── user
│   │                       ├── UserService.java
│   │                       └── impl
│   │                           ├── H2UserServiceImpl.java
│   │                           └── InMemoryUserServiceImpl.java
│   └── resources
│       ├── application.properties
│       ├── data.sql
│       ├── schema.sql
│       ├── static
│       └── templates
└── test
    ├── java
    │   └── org
    │       └── example
    │           └── trading_demo
    │               └── test
    │                   ├── RunCucumberTests.java
    │                   ├── common
    │                   │   ├── CucumberConfiguration.java
    │                   │   └── StepHooks.java
    │                   ├── security_feature
    │                   │   └── SecurityStepsTests.java
    │                   ├── trade_feature
    │                   │   └── TradeStepsTests.java
    │                   └── user_feature
    │                       └── UserStepsTests.java
    └── resources
        └── features
            ├── security
            │   └── security.feature
            ├── trade
            │   └── trade.feature
            └── user
                └── user.feature
```