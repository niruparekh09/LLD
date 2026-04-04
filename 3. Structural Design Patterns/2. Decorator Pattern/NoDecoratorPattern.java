// BAD DESIGN: No Decorator Pattern Used
// Each possible combination of pizza requires a separate class.
// This leads to class explosion and poor scalability.

// Base pizza class
class PlainPizza_ {
    // Represents a simple pizza without toppings
}

// Adding cheese requires a new subclass
class CheesePizza_ extends PlainPizza_ {
    // Now this pizza always has cheese
}

// Adding olives requires another subclass
class OlivePizza_ extends PlainPizza_ {
    // Now this pizza always has olives
}

// Adding stuffed crust requires another subclass
class StuffedPizza_ extends PlainPizza_ {
    // Now this pizza always has stuffed crust
}

// Want cheese + stuffed? Create another class
class CheeseStuffedPizza_ extends CheesePizza_ {
    // Combination baked into class hierarchy
}

// Want cheese + olive? Another class
class CheeseOlivePizza_ extends CheesePizza_ {
    // Hardcoded combination
}

// Want cheese + olive + stuffed?
// Yet another subclass
class CheeseOliveStuffedPizza_ extends CheeseOlivePizza_ {
    // Class hierarchy keeps growing
}

public class NoDecoratorPattern {
    public static void main(String[] args) {

        // Base pizza
        PlainPizza_ plainPizza = new PlainPizza_();

        // Single toppings require separate classes
        CheesePizza_ cheesePizza = new CheesePizza_();
        OlivePizza_ olivePizza = new OlivePizza_();
        StuffedPizza_ stuffedPizza = new StuffedPizza_();

        // Every combination requires a new class
        CheeseStuffedPizza_ cheeseStuffedPizza = new CheeseStuffedPizza_();
        CheeseOlivePizza_ cheeseOlivePizza = new CheeseOlivePizza_();

        // As combinations increase,
        // number of classes grows exponentially (2^n problem)
        CheeseOliveStuffedPizza_ cheeseOliveStuffedPizza =
                new CheeseOliveStuffedPizza_();
    }
}