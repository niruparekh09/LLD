import java.util.List;

// Represents a customizable Burger Meal
class BadBurgerMeal {

    // Required fields
    private String bun;
    private String patty;

    // Optional fields
    private String sides;
    private List<String> toppings;
    private boolean cheese;

    // Single constructor handling all combinations
    // Forces callers to pass nulls and defaults
    // Hard to read and maintain
    public BadBurgerMeal(String bun, String patty, String sides,
                         List<String> toppings, boolean cheese) {
        this.bun = bun;
        this.patty = patty;
        this.sides = sides;
        this.toppings = toppings;
        this.cheese = cheese;
    }
}

public class BadBuilderPattern {
    public static void main(String[] args) {

        // Nulls + false reduce readability
        // Order of parameters is error-prone
        BadBurgerMeal badBurgerMeal =
                new BadBurgerMeal("wheat", "veg", null, null, false);
    }
}
