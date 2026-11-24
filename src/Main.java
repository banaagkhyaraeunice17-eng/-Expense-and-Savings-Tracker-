import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

// Main class: program entry point, menu, and user input.
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final FinanceTracker tracker = new FinanceTracker();

    public static void main(String[] args) {
        tracker.loadData();
        System.out.println("========================================");
        System.out.println("       EXPENSE AND SAVINGS TRACKER");
        System.out.println("========================================");
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
                case 8: tracker.saveData(); System.out.println("Data saved successfully."); break;
                case 9: tracker.saveData(); System.out.println("Your records have been saved. Goodbye!"); running = false; break;
                default: System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n--------------- MAIN MENU ---------------");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View Transaction History");
        System.out.println("4. View Financial Summary");
        System.out.println("5. Create Savings Goal");
        System.out.println("6. Add Savings");
        System.out.println("7. View Savings Goals");
        System.out.println("8. Save Data");
        System.out.println("9. Save and Exit");
        System.out.println("-----------------------------------------");
    }

    private static void addIncome() {
        System.out.println("\n--- ADD INCOME ---");
        double amount = readPositiveAmount("Amount: ");
        String category = readText("Source/Category: ");
        String description = readText("Description: ");
        LocalDate date = readDate("Date (YYYY-MM-DD): ");
        tracker.addIncome(amount, category, description, date);
    }

    private static void addExpense() {
        System.out.println("\n--- ADD EXPENSE ---");
        System.out.printf("Available balance: PHP %.2f%n", tracker.getBalance());
        double amount = readPositiveAmount("Amount: ");
        String category = readText("Category: ");
        String description = readText("Description: ");
        LocalDate date = readDate("Date (YYYY-MM-DD): ");
        tracker.addExpense(amount, category, description, date);
    }

    private static void createSavingsGoal() {
        System.out.println("\n--- CREATE SAVINGS GOAL ---");
        String name = readText("Goal name: ");
        double target = readPositiveAmount("Target amount: ");
        tracker.createSavingsGoal(name, target);
    }

    private static void addSavings() {
        System.out.println("\n--- ADD SAVINGS ---");
        if (tracker.getSavingsGoals().isEmpty()) {
            System.out.println("You have no savings goals. Create one first.");
            return;
        }
        tracker.displaySavingsGoals();
        int goalNumber = readInt("Select goal number: ", 1, tracker.getSavingsGoals().size());
        double amount = readPositiveAmount("Deposit amount: ");
        tracker.addSavings(goalNumber - 1, amount);
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("Please enter a whole number from " + min + " to " + max + ".");
        }
    }

    private static double readPositiveAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double amount = Double.parseDouble(scanner.nextLine().trim());
                if (Double.isFinite(amount) && amount > 0) return amount;
            } catch (NumberFormatException ignored) { }
            System.out.println("Please enter a valid amount greater than zero.");
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid date. Use YYYY-MM-DD (example: 2026-11-23).");
            }
        }
    }
}
