/*
 =========== Component Interface ============
 Defines common contract for both base objects
 and decorators.
*/
interface Pizza {
    String getDescription();
    double getCost();
}

/*
 ============= Concrete Component: Plain Pizza ==============
 Basic object that can be decorated.
*/
class PlainPizza implements Pizza {
    @Override
    public String getDescription() {
        return "Plain Pizza";
    }

    @Override
    public double getCost() {
        return 15.00;
    }
}

/*
 ============= Concrete Component: Margaretta Pizza ==============
 Another base object that can be decorated.
*/
class MargarettaPizza implements Pizza {

    @Override
    public String getDescription() {
        return "Margaretta Pizza";
    }

    @Override
    public double getCost() {
        return 20.00;
    }
}

/*
 ======================== Abstract Decorator ===========================
 Implements Pizza and holds a reference to a Pizza object.

 Why abstract?
 - Provides common wrapping structure.
 - Prevents direct instantiation (only concrete decorators should be used).
 - Ensures all decorators follow same structure.
*/
abstract class PizzaDecorator implements Pizza {
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;  // wrapping the original object
    }
}

/*
 ============ Concrete Decorator: Adds Extra Cheese ================

 Why constructor with Pizza parameter?
 - Allows wrapping any Pizza object (base or already decorated).
 - Enables dynamic layering of behaviors.
*/
class ExtraCheese extends PizzaDecorator {

    public ExtraCheese(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + " + extra cheese";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 5.00;
    }
}

/*
 Concrete Decorator: Adds Olives
*/
class Olives extends PizzaDecorator {

    public Olives(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + " + olives";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 3.00;
    }
}

/*
 Concrete Decorator: Adds BBQ Sauce
*/
class BBQ extends PizzaDecorator {

    public BBQ(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + " + BBQ Sauce";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 2.00;
    }
}

public class DecoratorPattern {
    public static void main(String[] args) {

        // Base Pizza
        Pizza pizza1 = new PlainPizza();
        System.out.println(pizza1.getDescription() + " -> $" + pizza1.getCost());

        System.out.println("------");

        // Plain Pizza + Extra Cheese
        Pizza pizza2 = new ExtraCheese(new PlainPizza());
        System.out.println(pizza2.getDescription() + " -> $" + pizza2.getCost());

        System.out.println("------");

        // Margaretta + Olives + BBQ + Extra Cheese
        Pizza pizza3 = new ExtraCheese(
                new BBQ(
                        new Olives(
                                new MargarettaPizza()
                        )
                )
        );

        System.out.println(pizza3.getDescription() + " -> $" + pizza3.getCost());
    }
}