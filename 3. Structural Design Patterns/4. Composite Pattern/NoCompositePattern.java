import java.util.ArrayList;
import java.util.List;

class Product_ {
    private String name;
    private double price;

    public Product_(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void display(String indent) {
        System.out.println(indent + "Product: " + name + " - ₹" + price);
    }
}

// Represents a bundle of products
class ProductBundle_ {
    private String bundleName;
    private List<Product_> products = new ArrayList<>();

    public ProductBundle_(String bundleName) {
        this.bundleName = bundleName;
    }

    public void addProduct(Product_ product) {
        products.add(product);
    }

    public double getPrice() {
        double total = 0;
        for (Product_ product : products) {
            total += product.getPrice();
        }
        return total;
    }

    public void display(String indent) {
        System.out.println(indent + "Bundle: " + bundleName);
        for (Product_ product : products) {
            product.display(indent + "  ");
        }
    }
}

public class NoCompositePattern {
    public static void main(String[] args) {
        // Individual Items
        Product_ book = new Product_("Book", 500);
        Product_ headphones = new Product_("Headphones", 1500);
        Product_ charger = new Product_("Charger", 800);
        Product_ pen = new Product_("Pen", 20);
        Product_ notebook = new Product_("Notebook", 60);

        // Bundle: Iphone Combo
        ProductBundle_ iphoneCombo = new ProductBundle_("iPhone Combo Pack");
        iphoneCombo.addProduct(headphones);
        iphoneCombo.addProduct(charger);

        // Bundle: School Kit
        ProductBundle_ schoolKit = new ProductBundle_("School Kit");
        schoolKit.addProduct(pen);
        schoolKit.addProduct(notebook);

        // Add to cart logic
        List<Object> cart = new ArrayList<>();
        cart.add(book);
        cart.add(iphoneCombo);
        cart.add(schoolKit);

        // Display Cart
        double total = 0;
        System.out.println("Cart Details:\n");

        for (Object item : cart) {
            if (item instanceof Product_) {
                ((Product_) item).display("  ");
                total += ((Product_) item).getPrice();
            } else if (item instanceof ProductBundle_) {
                ((ProductBundle_) item).display("  ");
                total += ((ProductBundle_) item).getPrice();
            }
        }

        System.out.println("\nTotal Price: ₹" + total);
    }
}