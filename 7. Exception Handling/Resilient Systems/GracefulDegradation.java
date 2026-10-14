
import java.util.List;

public class GracefulDegradation {

    private final ProductService productService = new ProductService();
    private final RecommendationService recommendationService =
            new RecommendationService();

    public static void main(String[] args) {
        GracefulDegradation app = new GracefulDegradation();

        ProductPage page = app.getProductPage(101L);

        System.out.println("Product: " + page.getProduct().getName());
        System.out.println("Recommendations: " + page.getRecommendations());
    }

    public ProductPage getProductPage(long productId) {
        Product product = productService.getProduct(productId);

        List<Product> recommendations;
        try {
            recommendations =
                    recommendationService.getRecommendations(productId);
        } catch (RecommendationServiceException e) {
            recommendations = List.of(); // Fallback
        }

        return new ProductPage(product, recommendations);
    }

    // Product entity
    static class Product {
        private final long id;
        private final String name;

        public Product(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Response model
    static class ProductPage {
        private final Product product;
        private final List<Product> recommendations;

        public ProductPage(Product product, List<Product> recommendations) {
            this.product = product;
            this.recommendations = recommendations;
        }

        public Product getProduct() {
            return product;
        }

        public List<Product> getRecommendations() {
            return recommendations;
        }
    }

    // Primary service
    static class ProductService {
        public Product getProduct(long productId) {
            return new Product(productId, "Laptop");
        }
    }

    // Optional service that may fail
    static class RecommendationService {
        public List<Product> getRecommendations(long productId) {
            // Simulate recommendation service failure
            throw new RecommendationServiceException(
                    "Recommendation service unavailable");
        }
    }

    // Custom exception
    static class RecommendationServiceException extends RuntimeException {
        public RecommendationServiceException(String message) {
            super(message);
        }
    }
}
