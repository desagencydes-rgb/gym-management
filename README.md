# Gymbo: Enterprise Facility ERP

![Java](https://img.shields.io/badge/Java-JDK%2020-red?style=for-the-badge&logo=java)
![Architecture](https://img.shields.io/badge/Architecture-MVC%20%2B%20Repository-blue?style=for-the-badge&logo=oracle)
![Status](https://img.shields.io/badge/Deployment-Zero%20Dependency-green?style=for-the-badge)

**Gymbo** is a monolithic Enterprise Resource Planning (ERP) system designed for facility management. Built strictly with **Core Java**, it demonstrates a clean **Model-View-Controller (MVC)** architecture without relying on external frameworks, ensuring 100% stability and zero configuration overhead.

> **Use Case:** Designed for rapid on-premise deployment in environments where database servers cannot be installed.

## 🏗 Software Architecture

The system is engineered to strictly separate business logic from UI concerns, following the **Repository Pattern** to abstract data persistence.

| Layer | Component | Responsibility |
| :--- | :--- | :--- |
| **Presentation** | `ui` (Swing) | Event-driven interface using `MainFrame` and modular `Panels`. |
| **Business Logic** | `service` (GymService) | Transaction management, validation, and entity orchestration. |
| **Data Access** | `repository` (FileRepository) | Generic CRUD implementation utilizing **Java Object Serialization**. |
| **Domain** | `model` (POJOs) | Strongly typed entities (`Member`, `Subscription`) with encapsulation. |

## 🚀 Key Modules

### 1. Membership Lifecycle
* Full CRUD capabilities for Member and Coach entities.
* **Automatic Expiration Logic:** Background service calculates subscription validity based on plan duration (Monthly/Yearly).

### 2. Financial Tracking
* **Ledger System:** Records incoming payments and links them to specific subscription instances.
* **Revenue Dashboard:** Visualizes real-time metrics (Active Users vs. Expired, Monthly Revenue).

### 3. "Zero-Dependency" Persistence
* Implemented a custom **File-based Persistence Layer** that serializes object graphs to `.dat` files.
* **Design Decision:** This eliminates the need for SQL server installation (MySQL/PostgreSQL), allowing the ERP to run directly from a USB drive or restricted terminal.

## 🛠 Tech Stack

* **Core:** Java 20 (Backward compatible to Java 8)
* **GUI:** Java Swing (JFC) - Chosen for native OS look-and-feel and robustness.
* **Design Patterns:** MVC, Repository, Singleton, Factory.
* **Storage:** Native Object Serialization (NoSQL-style object storage).

## 🔧 Deployment

### Prerequisites
* Java JDK 8+ installed.

### Build & Run
Since the project uses zero dependencies, it requires no Maven/Gradle configuration.

```bash
# 1. Compile Source
javac -d bin -sourcepath src src/gym/Main.java

# 2. Launch Application
java -cp bin gym.Main
```

## 📂 Project Structure

```bash
src/gym/
├── model/           # Domain Entities (The "Truth")
├── repository/      # Data Persistence (The "Store")
├── service/         # Business Logic (The "Brain")
├── ui/              # User Interface (The "Face")
└── Main.java        # Application Bootstrapper
```

## 📜 License
Proprietary Software. Developed by **D.E.S Agency R&D**. Technical demonstration of "Pure Java" architecture.
