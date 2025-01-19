
# Order Management Saga Pattern

This is a Role based(Admin,User) Order Management System designed to help organize their order with ease and efficiency as I used saga design pattern. It is a backend micro-service architecture, leveraging the robust Spring Boot framework for the backend, and the dependable MySQL database for data storage.



## Features

- Microservices Architecture - Composed of five independent services: User, Order, Payment, Stock, and Delivery.

- User Authentication - Users can register as admin or normal user and log in to create the order securely. I used JWT Authentication which ensures that only authorized users can perform operations.

- Saga Design Pattern - Implements the Saga pattern for distributed transaction management, ensuring data consistency across microservices.

-  Event-driven communication - I used kafka for communication between microservices.



## Tech Stack

**Skills:** Java, Spring Boot (Microservices), MySQL, Apache Kafka(for event-driven communication).

**Server:** Apache Tomcat (application server), Apache Kafka(event streaming platform)


## Project Setup
   - ## Backend
        - In Eclipse or similar IDE import the "backend" from this repo with option "import existing maven project".
        - Build the maven project to install all the required dependencies.
        - To setup database for every micro-services, install MySQL.
          - Make database for user service micro-service which named as tmsuser and update below details in file application.properties inside /resources folder.
            - spring.datasource.url=jdbc:mysql://localhost:3306/tmsuser
            - spring.datasource.username=root
            - spring.datasource.password=root
          - Make database for order service micro-service which named as tmorder and update below details in file application.properties inside /resources folder.
            - spring.datasource.url=jdbc:mysql://localhost:3306/tmorder
            - spring.datasource.username=root
            - spring.datasource.password=root
          - Make database for payment service micro-service which named as tmpayment and update below details in file application.properties inside /resources folder.
            - spring.datasource.url=jdbc:mysql://localhost:3306/tmpayment
            - spring.datasource.username=root
            - spring.datasource.password=root
          - Make database for stock service micro-service which named as tmstock and update below details in file application.properties inside /resources folder.
            - spring.datasource.url=jdbc:mysql://localhost:3306/tmstock
            - spring.datasource.username=root
            - spring.datasource.password=root
          - Make database for delivery service micro-service which named as tmdelivery and update below details in file application.properties inside /resources folder.
            - spring.datasource.url=jdbc:mysql://localhost:3306/tmdelivery
            - spring.datasource.username=root
            - spring.datasource.password=root

        - ## Kafka configuration
             Step 1: Download Apache Kafka  
             1.Go to the official Kafka downloadpage: https://kafka.apache.org/downloads.

             Step 2: Extract Kafka  
             1.Extract the downloaded .tgz file using tools like 7-Zip or WinRAR.   
             2. Move the extracted folder to your desired location, e.g., C:\kafka.

             Step 3: Configure Zookeeper and Kafka  
             Kafka requires Zookeeper to manage the cluster. Kafka distributions typically include Zookeeper binaries.  
             1.Open a terminal or command prompt.   
             2.Navigate to the Kafka directory: cd C:\kafka  
             
             Step 4: Start Zookeeper  
             1.Open a new command prompt.  
             2.Navigate to the Kafka directory and run: .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties.  
             3.Ensure Zookeeper is running on port 2181 (default).

            Step 5: Start Kafka Broker
             1.Open another command prompt.   
             2.Navigate to the Kafka directory and run: .\bin\windows\kafka-server-start.bat .\config\server.properties.  
             3.Ensure Kafka is running on port 9092 (default).



        - Run the all Spring Boot application using your IDE.
  

## API Endpoints
&nbsp;1. Signup
- Endpoint: POST http://localhost:8086/auth/signup
- Description: Register a new user

&nbsp;2. Signin
- Endpoint: POST http://localhost:8086/auth/signin
- Description: Log in the user.
- ![image alt](https://github.com/jitendra511/Order-Management-Saga-Pattern-/blob/main/Images/signin.png?raw=true)

&nbsp;3. Get all user (only admin can access)
- Endpoint: GET http://localhost:8086/tm/getAllUser
- Description: Retrieves all User.

&nbsp;4. Add balance
- Endpoint: POST http://localhost:8086/tm/addBalance
- Description: Add balance to the logged user.

&nbsp;5. Get balance
- Endpoint: GET http://localhost:8086/tm/getBalance
- Description: get Balance of logged user.

&nbsp;6. Update the balance 
- Endpoint: PUT http://localhost:8086/tm/updateBalance
- Description: update the balance of logged user.

&nbsp;7. Create Order 
- Endpoint: POST http://localhost:8081/order/createOrder
- Description: user is creating the order.
