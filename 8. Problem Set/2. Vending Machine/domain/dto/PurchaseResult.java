package domain.dto;

import domain.Denomination;
import domain.Product;

import java.util.Map;

public class PurchaseResult {
    private final boolean successful;
    private final String message;
    private final Product product;
    private final Map<Denomination, Integer> change;
    private final Map<Denomination, Integer> refund;

    private PurchaseResult(
            boolean successful,
            String message,
            Product product,
            Map<Denomination, Integer> change,
            Map<Denomination, Integer> refund
    ) {
        this.successful = successful;
        this.message = message;
        this.product = product;
        this.change = Map.copyOf(change);
        this.refund = Map.copyOf(refund);
    }

    public static PurchaseResult success(
            Product product,
            Map<Denomination, Integer> change
    ) {
        return new PurchaseResult(
                true, "Purchase successful", product, change, Map.of()
        );
    }

    public static PurchaseResult failure(
            String message,
            Map<Denomination, Integer> refund
    ) {
        return new PurchaseResult(
                false, message, null, Map.of(), refund
        );
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getMessage() {
        return message;
    }

    public Product getProduct() {
        return product;
    }

    public Map<Denomination, Integer> getChange() {
        return change;
    }

    public Map<Denomination, Integer> getRefund() {
        return refund;
    }

    @Override
    public String toString() {
        return "PurchaseResult{" +
                "successful=" + successful +
                ", message='" + message + '\'' +
                ", product=" + product +
                ", change=" + change +
                ", refund=" + refund +
                '}';
    }
}