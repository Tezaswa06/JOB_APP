# Job Application Microservices

This repository contains a microservices-based job application system built using Java, Spring Boot, and PostgreSQL. The application provides CRUD APIs for managing jobs, companies, and reviews, with secure role-based authentication (admin/user) using JWT. It leverages modern cloud-native technologies such as Docker, Kubernetes, Spring Cloud, and RabbitMQ to ensure scalability, fault tolerance, and efficient communication between services.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup and Installation](#setup-and-installation)
  - [Building the Application](#building-the-application)
  - [Running with Docker](#running-with-docker)
  - [Running with Docker Compose](#running-with-docker-compose)
  - [Configuring the Config Server](#configuring-the-config-server)
- [Usage](#usage)
- [Monitoring and Tracing](#monitoring-and-tracing)
- [Contributing](#contributing)
- [Screenshots](#screenshots)

## Overview
This project is a distributed microservices architecture designed to manage job listings, company details, and user reviews. Key components include a service registry, API gateway, configuration server, and asynchronous messaging, all containerized and orchestrated for production-like deployment. The system is built with scalability and reliability in mind, using tools like Zipkin for distributed tracing and Resilience4j for fault tolerance.

## Features
- CRUD operations for jobs, companies, and reviews.
- Role-based authentication (admin/user) using JWT for secure access control.
- Inter-service communication with RestTemplate, OpenFeign, and RabbitMQ.
- Service discovery using Eureka Server and API routing with Spring Cloud Gateway.
- Containerization with Docker and orchestration with Kubernetes/Minikube.
- Centralized configuration management with Spring Cloud Config Server.
- Distributed tracing with Zipkin and Micrometer for observability.
- Data persistence with JPA using H2 and PostgreSQL databases.
- CI/CD pipelines with Maven for automated builds and testing.
- Security compliance with OWASP practices and role-based access control.

## Technologies Used
- **Languages**: Java
- **Frameworks/API**: Spring Boot, RESTful
- **Miscellaneous**: RTOS, Git
- **Deployment**: Docker, Kubernetes
- **Tools**: Maven, Postman, PGAdmin, RabbitMQ, Zipkin, OpenFeign, Resilience4j
- **Databases**: PostgreSQL, H2

## Project Structure
The project is divided into multiple microservices, each containerized and orchestrated:
- **Job Service**: Manages job listings and related CRUD operations.
- **Company Service**: Handles company details and ratings.
- **Review Service**: Manages user reviews for companies and jobs.
- **Gateway Service**: Routes API requests using Spring Cloud Gateway.
- **Config Server**: Provides centralized configuration from a Git repository.
- **Service Registry**: Enables service discovery with Eureka Server.

## Prerequisites
- **Java Development Kit (JDK)**: Version 17 or later.
- **Maven**: For building and packaging the application.
- **Docker**: For containerization (latest version recommended).
- **Docker Compose**: For multi-container orchestration.
- **Minikube**: For local Kubernetes deployment (optional).
- **Git**: For version control and config server integration.
- **PostgreSQL Client**: For database management (e.g., PGAdmin).

## Setup and Installation

### Building the Application
1. Clone the repository:
   ```bash
   git clone https://github.com/Tezaswa06/job-app.git
   cd job-app
   ```

2. Build the Project Using Maven:
   ```bash
   ./mvnw clean package
   ```
   This creates a JAR file (e.g., companyMS-0.0.1-SNAPSHOT.jar) in the target folder.

3. Run the JAR File:
   ```bash
   java -jar ./target/companyMS-0.0.1-SNAPSHOT.jar
   ```

### Running with Docker

1. Build the Docker Image for the Spring Boot Application:
   ```bash
   ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=tezaswa06/job-app
   ```

2. Run the Application Container:
   ```bash
   docker run -p 8080:8080 tezaswa06/job-app
   ```

3. Start a PostgreSQL Container:
   ```bash
   docker run -d --name db -e POSTGRES_PASSWORD=Tezaswa@123 postgres
   ```

4. Start a PGAdmin Container:
   ```bash
   docker run -d --name pgadmin -e PGADMIN_DEFAULT_EMAIL=user@domain.com -e PGADMIN_DEFAULT_PASSWORD=Tezaswa@123 dpage/pgadmin4
   ```

**Note**: Ensure network connectivity by creating a custom network (see below) to allow ping db from pgadmin.

If connectivity fails (e.g., ping: bad address 'db'), remove existing containers with:
```bash
docker rm -f db pgadmin
```
Then recreate them with a custom network.

### Running with Docker Compose

1. Create a Custom Network:
   ```bash
   docker network create my-network
   ```

2. Use the Provided docker-compose.yml File:
   ```yaml
   version: '3.8'

   services:
     postgres:
       container_name: postgres_container
       image: postgres
       environment:
         POSTGRES_USER: tezaswa06
         POSTGRES_PASSWORD: Tezaswa@123
         PGDATA: /data/postgres
       volumes:
         - postgres:/data/postgres
       ports:
         - "5432:5432"
       networks:
         - postgres
       restart: unless-stopped

     pgadmin:
       container_name: pgadmin_container
       image: dpage/pgadmin4
       environment:
         PGADMIN_DEFAULT_EMAIL: ${PGADMIN_DEFAULT_EMAIL:-pgadmin4@pgadmin.org}
         PGADMIN_DEFAULT_PASSWORD: ${PGADMIN_DEFAULT_PASSWORD:-admin}
         PGADMIN_CONFIG_SERVER_MODE: 'False'
       volumes:
         - pgadmin:/var/lib/pgadmin
       ports:
         - "5050:80"
       networks:
         - postgres
       restart: unless-stopped

   networks:
     postgres:
       driver: bridge

   volumes:
     postgres:
     pgadmin:
   ```

3. Start the Services:
   ```bash
   docker-compose up -d
   ```

4. Check Container Logs:
   ```bash
   docker logs postgres_container
   docker logs pgadmin_container
   ```

### Configuring the Config Server
1. Set up a Git repository with your application.properties file.
2. Configure the Spring Cloud Config Server to fetch properties from the Git repository.
3. Update the Config Server URL in your microservices' bootstrap properties.

## Usage
- Access the API Gateway at: http://localhost:8080
- Use Postman or a similar tool to test CRUD endpoints for jobs, companies, and reviews.
- Log in with JWT-based credentials (admin/user roles) for secure access.
- Monitor database interactions via PGAdmin at: http://localhost:5050

## Monitoring and Tracing
**Zipkin**: Integrated with Micrometer to track requests from start to end.
- Access the Zipkin UI at: http://localhost:9411 (ensure Zipkin server is running).

**OpenFeign**: Used to reduce boilerplate code for inter-service calls.

Example:
```java
companyClient.getCompanyById(job.getCompanyId())
```
replaces:
```java
restTemplate.getForObject("http://company-ms:8082/api/companies/getCompanyById/1", Company.class)
```

**RabbitMQ**: Implements asynchronous messaging for updating company ratings.

## Contributing
1. Fork the repository.

2. Create a new branch:
   ```bash
   git checkout -b feature-branch
   ```

3. Make changes and commit:
   ```bash
   git commit -m "Description of changes"
   ```

4. Push to the branch:
   ```bash
   git push origin feature-branch
   ```

5. Submit a pull request for review.

## Screenshots

### DB

![image](https://github.com/user-attachments/assets/0f8d40c4-05e5-473a-a984-6a3ad1b34064)




![image](https://github.com/user-attachments/assets/96e5433e-2605-4410-a0b6-b5f522b583af)


### RestTemplate for inter service communication

![image](https://github.com/user-attachments/assets/56f055dc-288f-45e4-a9e7-d8c12e79b9aa)



### Zipkin for monitoring request


![image](https://github.com/user-attachments/assets/8dd51cbe-24db-46a7-bda8-2be1cb10c8df)


### API Gateway for better communication 

![image](https://github.com/user-attachments/assets/37d5eef9-370c-4747-98bc-1f5ff16182c1)



### After Creating gateway checking it in Zipkin

![image](https://github.com/user-attachments/assets/afebcda9-bfb4-4c29-8240-6539a7ec125e)

### Adding Resilience4j circuit breaker and rate limmiter for fault tolerance and testing

![image](https://github.com/user-attachments/assets/bbb06404-4c61-4b6f-9762-13022017fc0f)


### After adding rate limitter checking it with Apache maven Jmeter

![image](https://github.com/user-attachments/assets/e7459d86-e97a-4286-8bd0-f7a661bdfde2)



### Added RabbitMq for asynchronous communication between comany and review microservices

![image](https://github.com/user-attachments/assets/de2f8699-559e-47e4-93aa-9a2e55416d4c)


![image](https://github.com/user-attachments/assets/ba592dad-d8b4-44b4-8062-d4df1330cea0)


### Dockerized all the microservices running in my local system

![image](https://github.com/user-attachments/assets/d81eb2c7-d21e-4459-a13e-ba1659f74ea4)


![image](https://github.com/user-attachments/assets/206b3f70-0658-4684-a251-ce0e767b9221)












