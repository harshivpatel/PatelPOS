# PatelPOS – Point of Sale System

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

| Backend | Java, Spring Boot, Spring Data JPA |
| Database | MySQL |
| Build Tool | Maven |
| Authentication | JWT or Session-based authentication |
| Tools | Git, IntelliJ IDEA, Postman |

---

## Setup and Installation

### 1. Clone the Repository
```bash
git clone https://github.com/harshivpatel/PatelPOS.git
cd PatelPOS

