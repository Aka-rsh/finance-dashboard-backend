 # 💰 Finance Dashboard Backend API

A secure and scalable RESTful API built with **Spring Boot 3** for managing personal finances.  
This application enables users to track income and expenses with **JWT-based authentication** and **role-based access control (RBAC)**.

---

## 🚀 Key Features

- 🔐 **JWT Authentication**
  - Secure, stateless authentication using JSON Web Tokens

- 👥 **Role-Based Access Control (RBAC)**
  - Supports `ADMIN` and `USER` roles
  - Protects sensitive endpoints

- 💸 **Financial Management**
  - Full CRUD operations for income & expense records

- 🗑️ **Soft Delete**
  - Records are marked as deleted instead of permanently removed

- ⚡ **In-Memory Database**
  - Uses H2 database for fast development and testing

---

## 🛠️ Tech Stack

| Category      | Technology |
|--------------|-----------|
| Language      | Java 21 |
| Framework     | Spring Boot 3.2.4 |
| Security      | Spring Security + JWT (JJWT) |
| Database      | H2 (In-Memory) |
| Build Tool    | Maven |

---

## 📂 Project Structure
src/main/java/com/finance/dashboard/
├── controller/ # REST Controllers (API endpoints)
├── entity/ # Database Models (User, FinancialRecord)
├── repository/ # JPA Repositories (Data Access Layer)
├── security/ # JWT, Filters, Security Config
└── service/ # Business Logic

---

## 🔐 API Endpoints

### 🧑‍💻 Authentication APIs

| Method | Endpoint | Description | Access |
|--------|---------|------------|--------|
| POST | `/api/users/register` | Register a new user | Public |
| POST | `/api/auth/login` | Login and receive JWT | Public |

---

### 💰 Financial Records APIs

| Method | Endpoint | Description | Access |
|--------|---------|------------|--------|
| POST | `/api/records/add` | Add a new financial record | Authenticated |
| GET | `/api/records/active` | Get all active records | Authenticated |
| DELETE | `/api/records/delete/{id}` | Delete a record | Admin Only |

---

## 🗄️ H2 Database Console

You can access the in-memory database using:

http://localhost:8080/h2-console

### Configuration:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: *(leave blank)*

---

## 🔑 Authentication Flow

1. Register a new user: 
2. Login to get JWT token:
3. Use the token in headers for protected APIs:

---


## ⚙️ Getting Started

### 📌 Prerequisites

- Java 21
- Maven

---

### ▶️ Run the Application

```bash
mvn spring-boot:run     or to write access h2 and authentication flow details also in readme files 
