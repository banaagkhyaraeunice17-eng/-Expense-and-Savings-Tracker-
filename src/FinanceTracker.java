import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

// Handles financial records, calculations, savings goals, and local file storage.
public class FinanceTracker implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DATA_FILE = "finance_data.dat";
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private ArrayList<SavingsGoal> goals = new ArrayList<>();
    private int nextId = 1;

    public ArrayList<SavingsGoal> getSavingsGoals() { return goals; }

    public void addIncome(double amount, String category, String description, LocalDate date) {
        transactions.add(new Income(nextId++, amount, category, description, date));
        System.out.println("Income recorded successfully!");
        saveData();
    }

    public void addExpense(double amount, String category, String description, LocalDate date) {
        if (amount > getBalance()) {
            System.out.printf("Not enough available balance. Your balance is PHP %.2f%n", getBalance());
            return;
        }
        transactions.add(new Expense(nextId++, amount, category, description, date));
        System.out.println("Expense recorded successfully!");
        saveData();
    }

    public double getTotalIncome() {
        double total = 0;
        for (Transaction transaction : transactions)
            if (transaction instanceof Income) total += transaction.getAmount();
        return total;
    }

    public double getTotalExpenses() {
        double total = 0;
        for (Transaction transaction : transactions)
            if (transaction instanceof Expense) total += transaction.getAmount();
        return total;
    }

    public double getTotalSavingsDeposits() {
        double total = 0;
        for (SavingsGoal goal : goals) total += goal.getSavedAmount();
        return total;
    }

    public double getBalance() {
        return getTotalIncome() - getTotalExpenses() - getTotalSavingsDeposits();
    }

    public void displaySummary() {
        System.out.println("\n========== FINANCIAL SUMMARY ==========");
        System.out.printf("Total Income:          PHP %.2f%n", getTotalIncome());
        System.out.printf("Total Expenses:        PHP %.2f%n", getTotalExpenses());
        System.out.printf("Savings Deposits:      PHP %.2f%n", getTotalSavingsDeposits());
        System.out.printf("Remaining Balance:     PHP %.2f%n", getBalance());
        System.out.println("=======================================");
    }

    public void displayTransactions() {
        System.out.println("\n============== TRANSACTION HISTORY ==============");
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded yet.");
            return;
        }
        System.out.printf("%-5s %-10s %-12s %-15s %-20s %12s%n",
                "ID", "TYPE", "DATE", "CATEGORY", "DESCRIPTION", "AMOUNT");
        System.out.println("--------------------------------------------------------------------------");
        // POLYMORPHISM: calls the correct getType() for Income or Expense.
        for (Transaction transaction : transactions) {
            System.out.printf("%-5d %-10s %-12s %-15s %-20s PHP %8.2f%n",
                    transaction.getId(), transaction.getType(), transaction.getDate(),
                    shorten(transaction.getCategory(), 14),
                    shorten(transaction.getDescription(), 19), transaction.getAmount());
        }
    }

    public void createSavingsGoal(String name, double targetAmount) {
        goals.add(new SavingsGoal(name, targetAmount));
        System.out.println("Savings goal created successfully!");
        saveData();
    }

    public void addSavings(int goalIndex, double amount) {
        if (goalIndex < 0 || goalIndex >= goals.size()) {
            System.out.println("Invalid savings goal.");
            return;
        }
        if (amount > getBalance()) {
            System.out.printf("Insufficient balance. Available: PHP %.2f%n", getBalance());
            return;
        }
        SavingsGoal goal = goals.get(goalIndex);
        if (amount > goal.getRemainingAmount()) {
            System.out.printf("That amount is more than the goal's remaining target (PHP %.2f).%n",
                    goal.getRemainingAmount());
            return;
        }
        goal.addSavings(amount);
        System.out.println("Savings deposit added successfully!");
        saveData();
    }

    public void displaySavingsGoals() {
        System.out.println("\n================ SAVINGS GOALS ================");
        if (goals.isEmpty()) {
            System.out.println("No savings goals created yet.");
            return;
        }
        for (int i = 0; i < goals.size(); i++) {
            SavingsGoal goal = goals.get(i);
            System.out.printf("%d. %s%n", i + 1, goal.getName());
            System.out.printf("   Target:    PHP %.2f%n", goal.getTargetAmount());
            System.out.printf("   Saved:     PHP %.2f%n", goal.getSavedAmount());
            System.out.printf("   Remaining: PHP %.2f%n", goal.getRemainingAmount());
            System.out.printf("   Progress:  %.1f%%%n", goal.getProgressPercentage());
            if (goal.getProgressPercentage() >= 100) System.out.println("   Status:    COMPLETED!");
        }
    }

    public void saveData() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            output.writeObject(this);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("No previous data found. Starting a new tracker.");
            return;
        }
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
            Object saved = input.readObject();
            if (saved instanceof FinanceTracker) {
                FinanceTracker loaded = (FinanceTracker) saved;
                transactions = loaded.transactions;
                goals = loaded.goals;
                nextId = loaded.nextId;
                System.out.println("Previous records loaded successfully.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load saved data: " + e.getMessage());
            System.out.println("Starting with an empty tracker.");
        }
    }

    private String shorten(String text, int maxLength) {
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}
