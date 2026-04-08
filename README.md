
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

##  Docker Setup

### Prerequisites

Make sure the following are installed:

- Docker  
- Docker Compose  

---

### 🚀 Steps to Run the Project

1. Clone the repository:
git clone https://github.com/jitendra511/Order-Management-Saga-Pattern-.git

2. cd your-repo

3. Start Docker Desktop (ensure Docker is running)

4. Run the application:

 - docker-compose up -d

5. Stop the Application

 - docker-compose down

### ⚠️ Notes

- Ensure required ports are free (e.g., 8080, 8081, etc.)
- Kafka & MySQL containers will start automatically via Docker Compose


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
  <p align="left">
    <img src="Images/Signup.png" width="800"/>
  </p>

### 2. Signin
- Endpoint: POST http://localhost:8086/auth/signin
- Description: Log in the user.
  <p align="left">
    <img src="" width="800"/>
  </p>

### 3. Get all user (only admin can access)
- Endpoint: GET http://localhost:8086/tm/getAllUser
- Description: Retrieves all User.
  <p align="left">
    <img src="Images/getAllUser.png" width="800"/>
  </p>
  
### 4. Add balance
- Endpoint: POST http://localhost:8086/tm/addBalance
- Description: Add balance to the logged user.
  <p align="left">
    <img src="Images/Add_Balance.png" width="800"/>
  </p>

### 5. Get balance
- Endpoint: GET http://localhost:8086/tm/getBalance
- Description: get Balance of logged user.
  <p align="left">
    <img src="Images/Get_Balance.png" width="800"/>
  </p>
  
### 6. Update the balance 
- Endpoint: PUT http://localhost:8086/tm/updateBalance
- Description: update the balance of logged user.
  <p align="left">
    <img src="Images/updateBalance.png" width="800"/>
  </p>
  
### 7. Create Order 
- Endpoint: POST http://localhost:8081/order/createOrder
- Description: user is creating the order.
  <p align="left">
    <img src="Images/Create_Order.png" width="800"/>
  </p>

### 8. Get My Orders
- Endpoint: GET http://localhost:8081/order/getMyOrders
- Description: get all past orders of logged user.
  <p align="left">
    <img src="Images/Get_My_Orders.png" width="800"/>
  </p>

### 9. Add Item To Stock (only admin can access)
- Endpoint: POST http://localhost:8083/stock/addItem
- Description: Add item to stock.
  <p align="left">
    <img src="Images/Add_Item.png" width="800"/>
  </p>

### 10. Get All Item Of Stock
- Endpoint: GET http://localhost:8083/stock/getAllItem
- Description: get all Item od stock
  <p align="left">
    <img src="Images/Get_All_Item.png" width="800"/>
  </p>

##  Future Improvements

- Implement centralized API Gateway (Spring Cloud Gateway)
- Add Service Discovery using Eureka
- Add monitoring using Prometheus & Grafana
- Implement retry & circuit breaker (Resilience4j)

  
