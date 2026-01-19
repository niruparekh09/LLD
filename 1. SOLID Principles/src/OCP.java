// Open/Closed Principle (OCP) Example
// Open for extension, closed for modification

// Bad Example — Violates OCP
// Every new customer type requires modifying this class
class DiscountCalculatorBad {

    public double calculateDiscount(String customerType, double amount) {

        if ("REGULAR".equals(customerType)) {
            return amount * 0.10;
        } else if ("PREMIUM".equals(customerType)) {
            return amount * 0.20;
        }

        // Adding GOLD / PLATINUM will require changing this method
        return 0;
    }
}

// Good Example — Follows OCP

// Step 1: Abstraction
// Defines a contract for discount calculation
interface DiscountStrategy {
    double calculateDiscount(double amount);
}

// Step 2: Concrete implementations
// New discount types are added by creating new classes
class RegularCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.10;
    }
}

class PremiumCustomerDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.20;
    }
}

// Step 3: High-level module depends on abstraction
// This class does NOT change when new discounts are added
class DiscountCalculator {

    private final DiscountStrategy discountStrategy;

    public DiscountCalculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculate(double amount) {
        return discountStrategy.calculateDiscount(amount);
    }
}

// Client / Demo class
public class OCP {

    public static void main(String[] args) {

        // Using Regular discount
        DiscountCalculator regularCalculator =
                new DiscountCalculator(new RegularCustomerDiscount());
        System.out.println("Regular Discount: " + regularCalculator.calculate(1000));

        // Using Premium discount
        DiscountCalculator premiumCalculator =
                new DiscountCalculator(new PremiumCustomerDiscount());
        System.out.println("Premium Discount: " + premiumCalculator.calculate(1000));
    }
}
