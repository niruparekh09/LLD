public class Retry {
    public static void main(String[] args) {
        System.out.println(test());
    }

    private static String test() {
        int maxAttempts = 3;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                return callInventoryService();
            } catch (TransientServiceException e) {
                if (attempt == maxAttempts) {
                    throw e;
                }

                // A real implementation should wait with backoff.
            }
        }

        throw new IllegalStateException("Unreachable");
    }

    private static String callInventoryService() {
        return "Inventory Service";
    }
}
