import java.time.LocalDate;

// INHERITANCE: Income extends the abstract Transaction class.
public class Income extends Transaction {
    private static final long serialVersionUID = 1L;

    public Income(int id, double amount, String category, String description, LocalDate date) {
        super(id, amount, category, description, date);
    }

    // POLYMORPHISM: Income provides its own getType() implementation.
    @Override
    public String getType() { return "INCOME"; }
}
