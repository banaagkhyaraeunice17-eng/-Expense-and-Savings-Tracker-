import java.io.Serializable;
import java.time.LocalDate;

// ABSTRACTION: common structure for all financial transactions.
public abstract class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int id;
    private final double amount;
    private final String category;
    private final String description;
    private final LocalDate date;

    public Transaction(int id, double amount, String category, String description, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    // ENCAPSULATION: private fields are accessed through getters.
    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public abstract String getType();
}
