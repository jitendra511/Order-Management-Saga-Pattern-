
# Order Management Saga Pattern

A distributed Order Management System built using Spring Boot Microservices, implementing the Saga Pattern for handling distributed transactions with Apache Kafka.

## Tech Stack

- Java
- Spring Boot
- Microservices
- MySQL
- Apache Kafka
- Docker & Docker Compose
- JWT Authentication

## Architecture
5 Microservices:
- User Service
- Order Service
- Payment Service
- Stock Service
- Delivery Service

Key Concepts:
- Event-driven communication using Kafka
- Saga Pattern for distributed transaction management

## Docker Setup

Run the entire system with a single command:

docker-compose up -d

## Docker Images

- jitendra511/user-service:v1
- jitendra511/order-service:v1
- jitendra511/payment-service:v1
- jitendra511/stock-service:v1
- jitendra511/delivery-service:v1

## Project Structure

```
project-root/
│
├── user-service/
├── order-service/
├── payment-service/
├── stock-service/
├── delivery-service/
│
├── docker-compose.yml
├── mysql-init/
│   └── init.sql
│
└── README.md
```

## Features

- Microservices Architecture
- JWT Authentication (Role-based: Admin/User)
- Saga Pattern Implementation
- Event-driven communication using Kafka
- Distributed transaction management

## Saga Flow
- User creates order
- Order service publishes event
- Payment service processes payment
- Stock service updates inventory
- Delivery service handles shipment


## API Endpoints
### 1. Signup
- Endpoint: POST http://localhost:8086/auth/signup
- Description: Register a new user
- ![Signup]()

### 2. Signin
- Endpoint: POST http://localhost:8086/auth/signin
- Description: Log in the user.
- ![Signin](https://github.com/jitendra511/Order-Management-Saga-Pattern-/blob/main/Images/signin.png?raw=true)

### 3. Get all user (only admin can access)
- Endpoint: GET http://localhost:8086/tm/getAllUser
- Description: Retrieves all User.
- ![getAllUser](https://github.com/jitendra511/Order-Management-Saga-Pattern-/blob/main/Images/getAllUser.png)

### 4. Add balance
- Endpoint: POST http://localhost:8086/tm/addBalance
- Description: Add balance to the logged user.
- ![add balance](https://github.com/jitendra511/Order-Management-Saga-Pattern-/blob/main/Images/addBalance.png)

###5. Get balance
- Endpoint: GET http://localhost:8086/tm/getBalance
- Description: get Balance of logged user.
- ![getBalance](https://github.com/jitendra511/Order-Management-Saga-Pattern-/blob/main/Images/getBalance.png)
  
### 6. Update the balance 
- Endpoint: PUT http://localhost:8086/tm/updateBalance
- Description: update the balance of logged user.
- ![updateBalance](https://github.com/jitendra511/Order-Management-Saga-Pattern-/blob/main/Images/updateBalance.png)

&nbsp;7. Create Order 
- Endpoint: POST http://localhost:8081/order/createOrder
- Description: user is creating the order.
