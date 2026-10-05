# MyMarket

A comprehensive desktop application built in Java using Java Swing for the graphical user interface and JUnit for unit testing. The system supports multi-role user management (Administrators and Customers), real-time inventory tracking, shopping cart operations, and local file-based data persistence for products, categories, and user accounts.

---

## 📋 Table of Contents
- [Overview](#-overview)
- [Key Features](#-key-features)
  - [Customer Capabilities](#-customer-capabilities)
  - [Administrator Capabilities](#%EF%B8%8F-administrator-capabilities)
  - [Data Persistence & Management](#-data-persistence--storage)
- [System Architecture](#-system-architecture)
  - [Package & Layer Structure](#package--layer-structure)
  - [Core Components](#core-components)
- [Project Directory Structure](#-project-directory-structure)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Compilation & Execution (CLI)](#compilation--execution-cli)
  - [Running in IntelliJ IDEA](#running-in-intellij-idea)
- [Default Demo Credentials](#-default-demo-credentials)
- [Testing & Quality Assurance](#-testing--quality-assurance)
- [Technologies & Libraries](#%EF%B8%8F-technologies--libraries)

---

## 📌 Overview

**MyMarket** is a modular desktop supermarket management and e-commerce application implemented in Java. It separates responsibilities cleanly into a presentation layer built with **Java Swing**, a business logic and controller layer (**`SupermarketAPI`** and associated managers), and a dedicated file I/O layer for local text-based data persistence.

The system is designed with educational clarity and robust Object-Oriented principles, featuring role-based access control, input validation, dynamic stock recalculations during cart operations, and an automated **JUnit 4** test suite.

---

## ✨ Key Features

### 👤 Customer Capabilities
- **Account Registration & Authentication**: New customers can sign up by providing their username, password, first name, and last name.
- **Product Catalog Browsing**: Displays available products in an intuitive table featuring product title, description, category, subcategory, unit price, and current stock.
- **Hierarchical Search & Filters**: Search products by keyword/title and filter dynamically by Category and Subcategory. Subcategories automatically populate based on the selected category.
- **Shopping Cart Management**:
  - Add items to cart with desired quantities.
  - Automatically verifies stock availability before adding.
  - Temporarily reserves inventory while items reside in the cart.
  - Real-time subtotal calculation and cart balance preview.
  - Modify quantities or remove items (stock returns immediately to inventory).
- **Checkout Process**:
  - Validates cart contents.
  - Converts cart items into an active `Order`.
  - Automatically decrements and persists inventory changes to disk.
- **Order History**: Track customer purchases and view order logs (`OrderDetailsWindow`).

### ⚙️ Administrator Capabilities
- **Full Inventory Oversight**: Complete visibility of all products, stock levels, and measurement types (Pieces vs Kilograms).
- **Product Addition**: Dialog (`AdminAddProductWindow`) to create new products with title, description, category, subcategory, price, quantity, and measurement unit.
- **Product Editing**: In-place modification (`AdminEditProductWindow`) of product metadata, pricing, and stock levels.
- **Product Deletion**: Instant deletion of products with immediate UI table and persistent file updates.
- **Search & Catalog Querying**: Fast multi-criteria search by title, category, and subcategory across the inventory.

### 💾 Data Persistence & Storage
All system records are persisted without requiring external database servers, using human-readable flat files located in `src/resources/`:
- **`users.txt`**: User credentials, role (`ADMIN` or `CUSTOMER`), and full names.
  ```text
  username;password;ROLE;firstName;lastName
  ```
- **`products.txt`**: Detailed product records including title, description, hierarchical category, price (with euro formatting), and quantity unit (`kg` or `τεμάχια`).
- **`categories_subcategories.txt`**: Categories mapped to their respective subcategories using the delimiter format:
  ```text
  Category (Subcategory1@Subcategory2@Subcategory3)
  ```

---

## 🏛️ System Architecture

The application adopts a **Model-View-Controller (MVC)** inspired architecture:

```
┌────────────────────────────────────────────────────────┐
│                   GUI Layer (Swing)                    │
│  LoginPage, CustomerMain, AdminMain, CartWindow, etc.  │
└───────────────────────────┬────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────┐
│             Facade / Controller Layer                  │
│                    SupermarketAPI                      │
└──────┬────────────────────┬────────────────────┬───────┘
       │                    │                    │
       ▼                    ▼                    ▼
┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│ UserManager  │     │ProductManager│     │CategoryManage│
└──────┬───────┘     └──────┬───────┘     └──────┬───────┘
       │                    │                    │
       ▼                    ▼                    ▼
┌────────────────────────────────────────────────────────┐
│              Domain Models & Business Logic            │
│  User, Role, Product, QuantityType, Cart, CartItem,    │
│  CustomerManager, Order                                │
└───────────────────────────┬────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────┐
│                Persistence Layer                       │
│                   FileManager                          │
└────────────────────────────────────────────────────────┘
```

### Core Components
- **`SupermarketAPI`**: The central controller / API facade connecting GUI frames with backend services.
- **`ProductManager`**: Handles product collections, additions, updates, removals, and multi-criteria queries.
- **`UserManager`**: Manages credential validation, registration, and user lookup.
- **`CustomerManager`**: Oversees customer-specific actions, including order history records.
- **`CategoryManager`**: Loads and maps categories and their associated subcategories.
- **`FileManager`**: Reads and writes entities to disk (`loadProducts`, `saveProducts`, `loadUsers`, `saveUsers`, `loadCategories`).
- **`Cart` & `CartItem`**: Encapsulates cart logic, quantity tracking, subtotal pricing, and stock validation.
- **`Order`**: Represents a completed order with unique ID, timestamp, and purchased item line records.

---

## 📂 Project Directory Structure

```text
mymarket-main/
├── README.md                                # Project documentation
├── MyMarket.iml                             # IntelliJ IDEA module configuration
├── lib/                                     # External libraries & dependencies
│   ├── junit-4.13.1.jar                     # JUnit 4 testing framework
│   └── hamcrest-core-1.3.jar                # Hamcrest matchers
├── src/                                     # Production source code
│   ├── Main.java                            # Application entry point
│   ├── api/                                 # Business logic, models & controllers
│   │   ├── Cart.java                        # Shopping cart operations & checkout
│   │   ├── CartItem.java                    # Cart item line model
│   │   ├── CategoryManager.java             # Category and subcategory repository
│   │   ├── CustomerManager.java             # Customer orders & history management
│   │   ├── FileManager.java                 # File I/O persistence handler
│   │   ├── Order.java                       # Order model with timestamp & items
│   │   ├── Product.java                     # Product entity
│   │   ├── ProductManager.java              # Product inventory management & search
│   │   ├── QuantityType.java                # Unit enum (PIECES, KILOGRAMS)
│   │   ├── Role.java                        # Authorization role enum (ADMIN, CUSTOMER)
│   │   ├── SupermarketAPI.java              # Central facade controller
│   │   └── User.java                        # User entity
│   ├── gui/                                 # Java Swing user interface components
│   │   ├── AdminAddProductWindow.java       # Product creation dialog
│   │   ├── AdminEditProductWindow.java      # Product modification dialog
│   │   ├── AdminMain.java                   # Administrator dashboard
│   │   ├── CartItemWindow.java              # Add-to-cart quantity selection dialog
│   │   ├── CartWindow.java                  # Shopping cart view & checkout dialog
│   │   ├── CustomerMain.java                # Customer storefront & search interface
│   │   ├── CustomerManagerWindow.java       # Customer order history viewer
│   │   ├── LoginPage.java                   # Login and authentication window
│   │   ├── OrderDetailsWindow.java          # Order receipt view
│   │   └── RegisterWindow.java              # Customer registration window
│   └── resources/                           # Persistent flat-file storage
│       ├── categories_subcategories.txt     # Categories and subcategories definition
│       ├── products.txt                     # Product catalog data
│       └── users.txt                        # User accounts data
└── test/                                    # Automated JUnit 4 unit tests
    └── api/
        ├── CartItemTest.java                # Unit tests for CartItem
        ├── CartTest.java                    # Unit tests for Cart calculations & stock
        ├── CustomerManagerTest.java         # Unit tests for CustomerManager
        ├── OrderTest.java                   # Unit tests for Order
        ├── ProductManagerTest.java          # Unit tests for ProductManager queries
        ├── UserManagerTest.java             # Unit tests for UserManager authentication
        ├── products_test.txt                # Mock products data for test cases
        └── users_test.txt                   # Mock users data for test cases
```

---

## 🚀 Getting Started

### Prerequisites
- **Java Development Kit (JDK)**: Version 8 or higher (OpenJDK 11, 17, or 21 recommended).
- **Git** (optional, for cloning the repository).

Verify your Java installation:
```bash
java -version
javac -version
```

### Compilation & Execution (CLI)

#### 1. Compile the Source Files
From the project root directory:
```bash
javac -d out -cp "lib/*:src" src/Main.java src/api/*.java src/gui/*.java
```
*(On Windows Command Prompt, use semicolons: `javac -d out -cp "lib/*;src" src/Main.java src/api/*.java src/gui/*.java`)*

#### 2. Run the Application
Run the `Main` entry point from the project root (so relative paths to `src/resources/` resolve properly):
```bash
java -cp "out:src" Main
```
*(On Windows Command Prompt: `java -cp "out;src" Main`)*

### Running in IntelliJ IDEA
1. Open IntelliJ IDEA and choose **File > Open**, then select the project folder (`mymarket-main`).
2. Verify that the Project SDK is configured under **File > Project Structure > Project** (set to Java 8+).
3. Ensure `lib/junit-4.13.1.jar` and `lib/hamcrest-core-1.3.jar` are added as module libraries.
4. Locate [src/Main.java](src/Main.java), right-click and select **Run 'Main.main()'**.

---

## 👥 Default Demo Credentials

Pre-configured accounts available in `src/resources/users.txt`:

| Role | Username | Password | Notes |
| :--- | :--- | :--- | :--- |
| **Administrator** | `admin1` | `password1` | Full administrative privileges (inventory management) |
| **Administrator** | `admin2` | `password2` | Secondary administrator account |
| **Customer** | `user1` | `password1` | Customer account (Γιάννης Παπαδόπουλος) |
| **Customer** | `user2` | `password2` | Customer account (Κώστας Παπαγιάννης) |

> 💡 **Tip:** You can also register a brand new customer account directly from the **Εγγραφή** button on the Login screen.

---

## 🧪 Testing & Quality Assurance

The project includes unit tests using **JUnit 4** covering core business logic, cart computations, inventory deduction, order creation, and user management.

To compile and run all unit tests from the command line:

```bash
# Compile tests and source files
javac -cp "lib/*:src" -d out src/api/*.java src/gui/*.java src/Main.java test/api/*.java

# Run JUnit 4 Test Suite
java -cp "out:lib/*:." org.junit.runner.JUnitCore \
  api.CartTest \
  api.CartItemTest \
  api.CustomerManagerTest \
  api.OrderTest \
  api.ProductManagerTest \
  api.UserManagerTest
```

*(On Windows, replace `:` with `;` in the classpath).*

---

## 🛠️ Technologies & Libraries

- **Language**: Java (SE 8+)
- **GUI Framework**: Java Swing (`JFrame`, `JDialog`, `JTable`, `GridBagLayout`, `BorderLayout`)
- **Testing**: [JUnit 4.13.1](https://junit.org/junit4/) & [Hamcrest 1.3](http://hamcrest.org/)
- **Data Format**: Flat text-based persistence (`.txt`)
- **IDE Support**: IntelliJ IDEA (`.iml` configuration included)