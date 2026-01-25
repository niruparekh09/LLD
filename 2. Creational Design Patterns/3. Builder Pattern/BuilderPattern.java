import java.util.Arrays;
import java.util.List;

class BurgerMeal {

    // Required fields (immutable)
    private final String bunType;
    private final String patty;

    // Optional fields (immutable)
    private final boolean hasCheese;
    private final List<String> toppings;
    private final String side;
    private final String drink;

    // Private constructor accepts only builder
    private BurgerMeal(BurgerBuilder builder) {
        this.bunType = builder.bunType;
        this.patty = builder.patty;
        this.hasCheese = builder.hasCheese;
        this.toppings = builder.toppings;
        this.side = builder.side;
        this.drink = builder.drink;
    }

    // Builder class handles construction logic
    public static class BurgerBuilder {

        // Required fields
        private final String bunType;
        private final String patty;

        // Optional fields with defaults
        private boolean hasCheese;
        private List<String> toppings;
        private String side;
        private String drink;

        // Enforces required fields
        public BurgerBuilder(String bunType, String patty) {
            this.bunType = bunType;
            this.patty = patty;
        }

        // Fluent setter
        public BurgerBuilder withCheese(boolean hasCheese) {
            this.hasCheese = hasCheese;
            return this;
        }

        public BurgerBuilder withToppings(List<String> toppings) {
            this.toppings = toppings;
            return this;
        }

        public BurgerBuilder withSide(String side) {
            this.side = side;
            return this;
        }

        public BurgerBuilder withDrink(String drink) {
            this.drink = drink;
            return this;
        }

        // Creates the final immutable object
        public BurgerMeal build() {
            return new BurgerMeal(this);
        }
    }
}

public class BuilderPattern {
    public static void main(String[] args) {

        // Only required fields
        BurgerMeal plainBurger =
                new BurgerMeal.BurgerBuilder("wheat", "veg")
                        .build();

        // Required + one optional
        BurgerMeal burgerWithCheese =
                new BurgerMeal.BurgerBuilder("wheat", "veg")
                        .withCheese(true)
                        .build();

        // Fully configured object
        List<String> toppings =
                Arrays.asList("lettuce", "onion", "jalapeno");

        BurgerMeal loadedBurger =
                new BurgerMeal.BurgerBuilder("multigrain", "chicken")
                        .withCheese(true)
                        .withToppings(toppings)
                        .withSide("fries")
                        .withDrink("coke")
                        .build();

        // Step-by-step construction (no chaining)
        BurgerMeal.BurgerBuilder builder =
                new BurgerMeal.BurgerBuilder("wheat", "veg");

        builder.withCheese(true);
        builder.withSide("fries");

        BurgerMeal burgerWithCheeseAndSides =
                builder.build();
    }
}
