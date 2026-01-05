# Gym Management System

A complete, production-quality Desktop Application for Gym Management in Java.

## Features
- **Member Management**: Add, edit, delete, and search members using a friendly UI.
- **Coach Management**: Manage coaches and their specializations.
- **Membership Plans**: Create and manage subscription plans (Monthly, Quarterly, Yearly).
- **Subscriptions**: Link members to plans with automatic expiration calculation.
- **Payments**: Record and track payments linked to subscriptions.
- **Attendance**: Log member check-ins and view history.
- **Dashboard**: Real-time statistics on revenue, active users, and subscriptions.

## Tech Stack
- **Language**: Java Only (JDK 20 compatible).
- **UI**: Java Swing (chosen for robustness and zero-dependency configuration).
- **Architecture**: MVC (Model-View-Controller) + Repository Pattern + Service Layer.
- **Persistence**: Custom File-based Persistence (Java Object Serialization). No database installation required.

## Architecture Overview
The project is structured to separate concerns and ensure maintainability:
- **`model`**: POJOs representing the domain entities (`Member`, `Coach`, `Subscription`, etc.).
- **`repository`**: Data access layer. Uses a generic `FileRepository<T>` to manage persistence to `.dat` files in the `data/` directory.
- **`service`**: Business logic layer (`GymService`). orchestrates data flows, performs validations, and links entities.
- **`ui`**: Swing-based user interface. `MainFrame` hosts multiple `Panels` in a tabbed layout.

## Design Decisions
1.  **Zero-Dependency**: To meet the strict "No external services" and "Java Only" constraints, we avoided external libraries (like Gson/Jackson or JDBC drivers) and used Java's native `ObjectOutputStream` for robustness.
2.  **Swing vs JavaFX**: Swing was chosen as the UI framework to ensure the application runs out-of-the-box on standard JDK installations without requiring complex module path configurations or external SDK downloads.
3.  **Repository Pattern**: This abstraction allows the underlying storage (File-based) to be easily swapped for a Database (SQLite/H2) in the future without changing the Service or UI logic.

## How to Run
### Prerequisites
- Java JDK installed (version 8 or higher).

### Steps
1.  **Compile**:
    Open a terminal in the project root and run:
    ```bash
    javac -d bin -sourcepath src src/gym/Main.java
    ```

2.  **Run**:
    ```bash
    java -cp bin gym.Main
    ```

3.  **Usage**:
    - The application will launch with a Dashboard.
    - Navigate tabs to manage Members, Coaches, etc.
    - Data is automatically saved to the `data/` directory.

## Project Structure
```
src/gym/
  ├── Main.java           # Entry point
  ├── model/              # Entity classes
  ├── repository/         # Data access implementations
  ├── service/            # Business logic
  └── ui/                 # Swing panels and frames
```
