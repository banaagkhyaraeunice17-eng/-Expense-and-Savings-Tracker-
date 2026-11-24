import java.time.LocalDate;

// INHERITANCE: Expense extends the abstract Transaction class.
public class Expense extends Transaction {
    private static final long serialVersionUID = 1L;

    public Expense(int id, double amount, String category, String description, LocalDate date) {
        super(id, amount, category, description, date);
    }

    // POLYMORPHISM: Expense provides its own getType() implementation.
    @Override
    public String getType() { return "EXPENSE"; }
}
