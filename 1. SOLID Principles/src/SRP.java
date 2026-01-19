// Invoice.java
class Invoice {
    private double amount;

    public Invoice(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

// Bad (multiple responsibilities in one class)
class InvoiceProcessor {
    public double calculateTotal(Invoice invoice) {
        return invoice.getAmount();
    }

    public void saveToDatabase(Invoice invoice) {
        System.out.println("Saving invoice with amount " + invoice.getAmount() + " to database");
    }

    public void printInvoice(Invoice invoice) {
        System.out.println("Printing invoice with amount " + invoice.getAmount());
    }
}

// Good (each class has one responsibility)

// InvoiceCalculator.java
class InvoiceCalculator {
    public double calculateTotal(Invoice invoice) {
        return invoice.getAmount();
    }
}

// InvoiceRepository.java
class InvoiceRepository {
    public void save(Invoice invoice) {
        System.out.println("Invoice saved with amount: " + invoice.getAmount());
    }
}

// InvoicePrinter.java
class InvoicePrinter {
    public void print(Invoice invoice) {
        System.out.println("Invoice amount: " + invoice.getAmount());
    }
}

// Demo class
public class SRP {

    public static void main(String[] args) {

        Invoice invoice = new Invoice(1000.0);

        // Using SRP-compliant classes
        InvoiceCalculator calculator = new InvoiceCalculator();
        InvoiceRepository repository = new InvoiceRepository();
        InvoicePrinter printer = new InvoicePrinter();

        double total = calculator.calculateTotal(invoice);
        System.out.println("Calculated total: " + total);

        repository.save(invoice);
        printer.print(invoice);
    }
}