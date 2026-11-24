# Expense and Savings Tracker

## 1. Project Title
**Expense and Savings Tracker**

## 2. Project Description
The Expense and Savings Tracker is a console-based Java application that helps users record income and expenses, monitor their available balance, and manage personal savings goals. It uses a menu-driven interface and stores records locally so they can be loaded when the program is reopened.

## 3. Problem Being Addressed
People may find it difficult to keep track of where their money comes from and where it goes. Without organized records, users may overspend or lose track of their financial goals. This project provides a simple way to record and review personal finances.

## 4. Project Objectives
- Record income with amount, source/category, description, and date.
- Record expenses and prevent spending beyond the available balance.
- Display transaction history and financial summaries.
- Create savings goals and record deposits toward those goals.
- Display savings progress and remaining target amounts.
- Save and load records using a local data file.
- Demonstrate the four fundamental OOP principles.

## 5. Target Users
- Students who want to monitor their allowance and expenses.
- Individuals who want a simple personal budgeting tool.
- Beginners learning Java and Object-Oriented Programming.

## 6. Main Features
1. Add income.
2. Add expenses with available-balance validation.
3. View transaction history.
4. View total income, expenses, savings deposits, and remaining balance.
5. Create savings goals.
6. Add deposits to savings goals.
7. View savings goal progress.
8. Save and load records locally.
9. Validate menu choices, positive amounts, required text, and dates.

## 7. Technologies Used
- Java
- Java standard libraries: `java.util`, `java.io`, and `java.time`
- Scanner for console input
- Java object serialization for local file storage
- Git and GitHub for version control and project hosting

## 8. Team Members and Roles
| Team Member | Role / Responsibility |
|---|---|
| Aquino, Roche Aira M. | Project Leader / Documentation |
| Banaag, Khyara Eunice M. | Java Programmer |
| Roxas, Angela Marie O. | Java Programmer |
| Gagalac, Alexander C. | OOP Design and Testing |
| Tamaray, Jhanine A. | Quality Assurance / Presentation |

## Source Code Classes
All Java source files are located in `src/`. The project uses the default package, so keep these files together and do not add package declarations unless you update the compile instructions too.

| File / Class | Purpose |
|---|---|
| `Main.java` — `Main` | Entry point. Displays the menu, receives user input, validates entries, and calls the tracker. |
| `FinanceTracker.java` — `FinanceTracker` | Manages transactions and savings goals, calculates totals and balance, and saves/loads data. |
| `Transaction.java` — `Transaction` | Abstract parent class containing shared transaction fields, getters, and `getType()`. |
| `Income.java` — `Income` | Child class representing income transactions. |
| `Expense.java` — `Expense` | Child class representing expense transactions. |
| `SavingsGoal.java` — `SavingsGoal` | Stores a savings target, saved amount, remaining amount, and progress. |

### Class Relationship
```text
             Transaction (abstract)
                /          \
               /            \
           Income          Expense

Main ---> FinanceTracker ---> SavingsGoal
                 |
                 +---- manages Transaction objects
```


### Detailed Class Documentation

For each class's role, data, important Java code, methods, OOP concept, and relationships, see [`docs/CLASS-GUIDE.md`](docs/CLASS-GUIDE.md).

## Object-Oriented Programming (OOP) Principles

This project demonstrates the four main principles of Object-Oriented Programming:

### Encapsulation

Encapsulation is implemented by declaring the class attributes as `private`. This protects the data from direct access outside the class. Public getter methods are used to access the values.

```java
private final double amount;
private final String category;

public double getAmount() {
    return amount;
}

public String getCategory() {
    return category;
}
```

### Abstraction

Abstraction is implemented through the abstract `Transaction` class. It provides a common structure for income and expense transactions while requiring subclasses to implement the `getType()` method.

```java
static abstract class Transaction implements Serializable {

    public abstract String getType();
}
```

### Inheritance

Inheritance is implemented when the `Income` and `Expense` classes extend the `Transaction` class. They inherit its common attributes and methods.

```java
static class Income extends Transaction {

    @Override
    public String getType() {
        return "INCOME";
    }
}

static class Expense extends Transaction {

    @Override
    public String getType() {
        return "EXPENSE";
    }
}
```

### Polymorphism

Polymorphism is demonstrated when the parent class reference `Transaction` is used to access different transaction objects. The overridden `getType()` method returns the appropriate type depending on the actual object.

```java
for (Transaction transaction : transactions) {
    System.out.println(transaction.getType());
}
```

## 10. Instructions for Running the Program
### Requirements
- Install a Java Development Kit (JDK).
- Use a terminal/command prompt or a Java IDE such as NetBeans, IntelliJ IDEA, or VS Code.

### Run using Command Prompt / Terminal
1. Clone or download this repository.
2. Open a terminal in the project folder.
3. Compile all Java files:

   ```bash
   javac -d out src/*.java
   ```

4. Run the program:

   ```bash
   java -cp out Main
   ```

### Run using an IDE
1. Open the project folder in your Java IDE.
2. Include all six `.java` files inside `src/` in the project.
3. Run the `Main` class.

### Data File
The application creates `finance_data.dat` in the program's working directory. Keep this file if you want to retain saved records. The `.dat` file is excluded from Git by `.gitignore` because it may contain personal financial information.

## 11. Sample Output

### Main Menu
Displays the main menu of the Expense and Savings Tracker, where users can choose the different features of the system.

![Main Menu](screenshots/Main%20Menu.png)

### Add Income
Allows users to record their income by entering the amount and its source.

![Add Income](screenshots/Add%20Income.png)

### Add Expense
Allows users to record their expenses by entering the amount and expense category.

![Add Expense](screenshots/Add%20Expense.png)

### Transaction History
Shows the list of recorded income and expenses, allowing users to review their financial transactions.

![Transaction History](screenshots/Transaction%20History.png)

### Financial Summary
Displays a summary of the user's total income, expenses, and remaining balance.

![Financial Summary](screenshots/Financial%20Summary.png)

### Saving Goals
Allows users to set savings targets, track their progress, and monitor their current savings.

![Saving Goals 1](screenshots/Saving%20Goals.png)

![Saving Goals 2](screenshots/Screenshot%202026-11-23%20170029.png)

![Saving Goals 3](screenshots/Screenshot%202026-11-23%20170046.png)

## UML Class Diagram

The UML Class Diagram provides a visual representation of the system's structure, showing the different classes, their attributes, methods, and relationships. It illustrates how the classes interact with one another in the Expense and Savings Tracker.

![UML Diagram](docs/Expense%20and%20Savings%20Tracker%20UML%20Diagram.png)

## 12. Known Limitations
- Console-based; no graphical user interface.
- Data is stored locally in a serialized `.dat` file rather than a database.
- Intended for a single user on one computer.
- No user accounts or multi-device synchronization.
- No transaction editing, deletion, or advanced reporting yet.

## 13. Future Improvements
- Add transaction editing and deletion.
- Add search and filtering by date or category.
- Generate monthly and yearly reports.
- Export records to CSV or PDF.
- Use a database for data management.
- Add secure user accounts.
- Add budgets and spending alerts by category.

## 14. Project Contributors
This project was developed as an academic exercise in Java programming and Object-Oriented Programming.

**Contributors:**
- Aquino, Roche Aira M.
- Banaag, Khyara Eunice M.
- Gagalac, Alexander C.
- Roxas, Angela Marie O.
- Tamaray, Jhanine A.

# Future Enhancements
The system can be enhanced in the future by adding user login and registration, database integration, monthly and yearly reports, data visualization, budget management, transaction editing and deletion, savings goal reminders, and a graphical user interface (GUI) to make managing personal finances easier and more convenient.
