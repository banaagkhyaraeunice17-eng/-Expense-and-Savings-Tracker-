import java.io.Serializable;

// Represents one savings target.
public class SavingsGoal implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final double targetAmount;
    private double savedAmount;

    public SavingsGoal(String name, double targetAmount) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.savedAmount = 0;
    }

    public String getName() { return name; }
    public double getTargetAmount() { return targetAmount; }
    public double getSavedAmount() { return savedAmount; }
    public double getRemainingAmount() { return Math.max(0, targetAmount - savedAmount); }
    public double getProgressPercentage() { return Math.min(100, savedAmount / targetAmount * 100); }
    public void addSavings(double amount) { savedAmount += amount; }
}
