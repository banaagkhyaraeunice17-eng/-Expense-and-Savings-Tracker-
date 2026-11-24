# Java Class Guide – Expense and Savings Tracker

This document explains the purpose of every Java class, shows key code examples, lists important methods, and explains class relationships. The full source code is in `src/`.

---

## 1. `Transaction.java` — Abstract Parent Class

**ROLE:** Defines the common information and behavior shared by income and expense transactions.

**DATA:** `id`, `amount`, `category`, `description`, and `date`.

**IMPORTANT CODE:**
```java
public abstract class Transaction implements Serializable {
    private final int id;
    private final double amount;
    private final String category;
    private final String description;
    private final LocalDate date;

    public Transaction(int id, double amount, String category,
                       String description, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }

    public abstract String getType();
}
```

**METHODS:** `getId()`, `getAmount()`, `getCategory()`, `getDescription()`, `getDate()`, and abstract `getType()`.

**OOP CONCEPT:** Abstraction and Encapsulation.

**RELATIONSHIP:**
- Parent class of `Income` and `Expense`.
- `FinanceTracker` stores both child types through the `Transaction` type.

---

## 2. `Income.java` — Income Child Class

**ROLE:** Represents money received by the user, such as allowance, salary, or other income.

**IMPORTANT CODE:**
```java
public class Income extends Transaction {
    public Income(int id, double amount, String category,
                  String description, LocalDate date) {
        super(id, amount, category, description, date);
    }

    @Override
    public String getType() {
        return "INCOME";
    }
}
```

**METHODS:** Constructor `Income(...)` creates an income record; `getType()` returns `"INCOME"`.

**OOP CONCEPT:** Inheritance and Polymorphism.

**RELATIONSHIP:** Child class of `Transaction`; created and managed by `FinanceTracker`.

---

## 3. `Expense.java` — Expense Child Class

**ROLE:** Represents money spent by the user, such as food, transportation, school supplies, or bills.

**IMPORTANT CODE:**
```java
public class Expense extends Transaction {
    public Expense(int id, double amount, String category,
                   String description, LocalDate date) {
        super(id, amount, category, description, date);
    }

    @Override
    public String getType() {
        return "EXPENSE";
    }
}
```

**METHODS:** Constructor `Expense(...)` creates an expense record; `getType()` returns `"EXPENSE"`.

**OOP CONCEPT:** Inheritance and Polymorphism.

**RELATIONSHIP:** Child class of `Transaction`; created and managed by `FinanceTracker`.

---

## 4. `SavingsGoal.java` — Savings Goal Class

**ROLE:** Stores a savings goal and tracks how much money has been saved toward its target.

**DATA:** `name`, `targetAmount`, and `savedAmount`.

**IMPORTANT CODE:**
```java
public class SavingsGoal implements Serializable {
    private final String name;
    private final double targetAmount;
    private double savedAmount;

    public double getRemainingAmount() {
        return Math.max(0, targetAmount - savedAmount);
    }

    public double getProgressPercentage() {
        return Math.min(100, (savedAmount / targetAmount) * 100);
    }

    public void addSavings(double amount) {
        savedAmount += amount;
    }
}
```

**METHODS:** `getName()`, `getTargetAmount()`, `getSavedAmount()`, `getRemainingAmount()`, `getProgressPercentage()`, and `addSavings(amount)`.

**OOP CONCEPT:** Encapsulation.

**RELATIONSHIP:** Created and managed by `FinanceTracker` when the user creates a goal or adds a savings deposit.

---

## 5. `FinanceTracker.java` — Service / Management Class

**ROLE:** Manages transaction records, savings goals, calculations, and saving/loading application data.

**DATA:** `transactions` (list of `Transaction` objects), `goals` (list of `SavingsGoal` objects), `nextId`, and `DATA_FILE`.

**IMPORTANT CODE:**
```java
public class FinanceTracker implements Serializable {
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private ArrayList<SavingsGoal> goals = new ArrayList<>();
    private int nextId = 1;

    public void addIncome(double amount, String category,
                          String description, LocalDate date) {
        transactions.add(
            new Income(nextId++, amount, category, description, date)
        );
        saveData();
    }

    public double getBalance() {
        return getTotalIncome()
             - getTotalExpenses()
             - getTotalSavingsDeposits();
    }
}
```

**MAIN METHODS:**
- `addIncome(...)` and `addExpense(...)` — record income and expenses.
- `getTotalIncome()` and `getTotalExpenses()` — calculate totals.
- `getTotalSavingsDeposits()` and `getBalance()` — calculate savings and available balance.
- `displaySummary()` — prints the financial summary.
- `displayTransactions()` — prints transaction history.
- `createSavingsGoal(...)` and `addSavings(...)` — manage savings goals.
- `displaySavingsGoals()` — prints goal progress.
- `saveData()` and `loadData()` — save and load records in `finance_data.dat`.

**OOP CONCEPT:** Encapsulation and Polymorphism. It manages child objects through the common `Transaction` parent type.

**RELATIONSHIP:** Used by `Main`; manages `Transaction`, `Income`, `Expense`, and `SavingsGoal` objects.

---

## 6. `Main.java` — Main / Entry-Point Class

**ROLE:** Starts the application, displays the console menu, receives and validates user input, and calls `FinanceTracker` methods.

**IMPORTANT CODE:**
```java
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final FinanceTracker tracker = new FinanceTracker();

    public static void main(String[] args) {
        tracker.loadData();

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Enter your choice: ", 1, 9);

            switch (choice) {
                case 1: addIncome(); break;
                case 2: addExpense(); break;
                case 3: tracker.displayTransactions(); break;
                case 4: tracker.displaySummary(); break;
                case 5: createSavingsGoal(); break;
                case 6: addSavings(); break;
                case 7: tracker.displaySavingsGoals(); break;
                case 8: tracker.saveData(); break;
                case 9:
                    tracker.saveData();
                    running = false;
                    break;
            }
        }
    }
}
```

**MAIN METHODS:** `main()`, `showMenu()`, `addIncome()`, `addExpense()`, `createSavingsGoal()`, `addSavings()`, `readText()`, `readInt()`, `readPositiveAmount()`, and `readDate()`.

**OOP CONCEPT:** Uses an object of `FinanceTracker` and calls its methods to perform application operations.

**RELATIONSHIP:** Entry point of the program; controls the menu and communicates with `FinanceTracker`.

---

## Class Relationship Overview

```text
                 Transaction (abstract)
                    /             \
                   /               \
                Income            Expense
                   \               /
                    \             /
                     FinanceTracker
                       /       \
                      /         \
               SavingsGoal     Main
```

- `Income` and `Expense` inherit from `Transaction`.
- `FinanceTracker` manages transaction objects and savings goals.
- `Main` uses `FinanceTracker` to run the application.

## Four OOP Principles Demonstrated

| OOP Principle | Where it appears |
|---|---|
| Encapsulation | Private fields and getter methods in `Transaction` and `SavingsGoal`; private lists in `FinanceTracker`. |
| Abstraction | `Transaction` is an abstract class and declares `getType()`. |
| Inheritance | `Income` and `Expense` extend `Transaction`. |
| Polymorphism | `FinanceTracker` stores objects as `Transaction` and calls the overridden `getType()` method. |
