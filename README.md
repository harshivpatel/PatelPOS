# Point of Sale System

PatelPOS is a Java Spring Boot-based Point of Sale (POS) application designed for small retail environments.  
It enables staff to manage sales efficiently and managers to oversee orders, products, and transactions.

---

## Features

- Secure user authentication with role-based access (Staff and Manager)
- Staff role:
  - Select from predefined products
  - Place and record orders via Cash or Card
- Manager role:
  - Add or update products
  - View and edit past orders
  - Retrieve complete order history
- Clean RESTful API structure for easy future frontend integration
- Order and OrderItem relationship with persistent storage in MySQL
- Logging and exception handling integrated throughout the backend

---

## Tech Stack

| Layer | Technologies |
|------|-------------|
| Backend | Java, Spring Boot, Spring Data JPA |
| Database | MySQL |
| Build Tool | Maven |
| Authentication | JWT or Session-based |
| Tools | Git, IntelliJ IDEA, Postman |

---

## Setup and Installation

### Prerequisites

Make sure you have the following installed:

- **Java JDK 17+** (recommended for Spring Boot)
- **Maven** (or use the Maven wrapper `mvnw`)
- **MySQL Server**
- **Git**
- (Optional) **Postman** for API testing

Check versions:

```bash
java -version
mvn -v
mysql --version
