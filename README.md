# Task Management Backend API

A simple **Task Management REST API** built using **Spring Boot**, **PostgreSQL**, **JWT Authentication**, and **Flyway Migrations**.  
This project is developed as a **Backend Developer Assignment** to demonstrate backend fundamentals, clean architecture, and production-ready practices.

---

## Objective

Build a RESTful backend system that supports:
- User management
- Task management
- Pagination and filtering
- Validation and error handling
- JWT-based authentication
- Database migrations

---

## Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA (Hibernate)**
- **Spring Security**
- **JWT (JSON Web Token)**
- **PostgreSQL**
- **Flyway** (Database migrations)
- **Gradle**

---

## Features

###  User Management
- Create user
- List users with pagination
- Get user by ID

###  Task Management
- Create task
- Get task by ID
- Update task
- Delete task
- Update task status (PATCH)
- List tasks with pagination
- Filter tasks by status / priority / assigned user

###  Authentication
- JWT-based authentication
- All APIs secured except login
- Token passed via `Authorization: Bearer <token>`

### ️ Database
- Versioned database migrations using Flyway
- Hibernate runs in `validate` mode
- Schema changes are controlled and repeatable

---

##  Project Structure

```text
src/main/java
 ├── controller        # REST controllers
 ├── service           # Business logic
 ├── repository        # JPA repositories
 ├── entity            # JPA entities
 ├── dto               # Request/Response DTOs
 ├── exception         # Custom exceptions & handlers
 ├── security          # JWT filter & security config
 └── util              # Utility classes (JWT, etc.)

src/main/resources
 ├── db/migration      # Flyway SQL migrations
 └── application.properties

```

## Setup Instructions

Follow the steps below to run the application locally.

## Prerequisites
Make sure the following are installed:

1) Java 17 or higher
2) PostgreSQL
3) Gradle
4) Any IDE (IntelliJ / Eclipse / VS Code)



## Clone the Repository
1) git clone <repository-url>
2) cd task-management-api

##  Database Setup

1) Create a PostgreSQL database:
2) CREATE DATABASE taskdb;

## Application Configuration

Update src/main/resources/application.properties:

1) server.port=8080
2) Update the db Configuration
   spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
   spring.datasource.username=postgres
   spring.datasource.password=postgres

##  Run the Application

1) Using Gradle:

   i) ./gradlew bootRun

2) Or run the main class from your IDE:

   i) TaskApplication

## Database Migrations (Flyway)
Migration files are located at:
src/main/resources/db/migration (PATH)

## Authentication (JWT)
Login to Generate JWT Token:

Endpoint:

POST /api/auth/login


Request Body

{
"username": "admin",
"password": "admin123"
}


Response

{
"data": "<JWT_TOKEN>"
}


Credentials are hardcoded for assignment simplicity. (Hardcoded Values)

## Access Secured APIs

For all secured APIs, include the following header:

Authorization: Bearer <JWT_TOKEN>

Requests without a valid token will return 401 / 403.

## API Endpoints (USER API'S)

| Method | Endpoint                   | Description             |
| ------ |----------------------------| ----------------------- |
| POST   | `/api/users/create/user`   | Create user             |
| GET    | `/api/users/get/all/users` | List users (pagination) |
| GET    | `/api/users/get/{userId}`  | Get user by ID          |


## API Endpoints (TASK API'S)

| Method | Endpoint                                             | Description        |
| ------ |------------------------------------------------------| ------------------ |
| POST   | `/api/tasks/create/task`                             | Create task        |
| GET    | `/api/tasks/get/{taskId}`                            | Get task by ID     |
| PUT    | `/api/tasks/update/{taskId}`                         | Update task        |
| PATCH  | `/api/tasks/{taskId}/status`                         | Update task status |
| DELETE | `/api/tasks/delete/{taskId}`                         | Delete task        |
| GET    | `/api/tasks/get/filter/{status}/{priority}/{userId}` | Filter tasks       |


## Error Handling

All errors are handled using a Global Exception Handler.

HTTP Status	Description

400	Validation / bad request

401	Unauthorized

404	Resource not found

409	Duplicate resource

500	Internal server error

All responses follow a consistent response structure.