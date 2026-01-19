// Dependency Inversion Principle (DIP)
// High-level modules should not depend on low-level modules.
// Both should depend on abstractions.

// Without DIP (Tightly Coupled)

// Low-level module
class RecentlyAddedBad {
    public void getRecommendations() {
        System.out.println("Showing recently added content...");
    }
}

// High-level module directly depends on concrete class
class BadRecommendationEngine {

    private RecentlyAddedBad recommender = new RecentlyAddedBad();

    public void recommend() {
        recommender.getRecommendations();
    }
}

// With DIP (Loosely Coupled)

// Abstraction
interface RecommendationStrategy {
    void getRecommendations();
}

// Low-level modules depend on abstraction
class RecentlyAdded implements RecommendationStrategy {
    public void getRecommendations() {
        System.out.println("Showing recently added content...");
    }
}

class TrendingNow implements RecommendationStrategy {
    public void getRecommendations() {
        System.out.println("Showing trending content...");
    }
}

class GenreBased implements RecommendationStrategy {
    public void getRecommendations() {
        System.out.println("Showing content based on your favorite genres...");
    }
}

// High-level module depends on abstraction, not concrete classes
class RecommendationEngine {

    private final RecommendationStrategy strategy;

    public RecommendationEngine(RecommendationStrategy strategy) {
        this.strategy = strategy;
    }

    public void recommend() {
        strategy.getRecommendations();
    }
}

// Client / Demo
public class DIP {

    public static void main(String[] args) {

        RecommendationStrategy strategy = new TrendingNow();
        RecommendationEngine engine = new RecommendationEngine(strategy);
        engine.recommend();

        strategy = new GenreBased();
        engine = new RecommendationEngine(strategy);
        engine.recommend();

        strategy = new RecentlyAdded();
        engine = new RecommendationEngine(strategy);
        engine.recommend();
    }
}
