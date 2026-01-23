// Enum-based Singleton
// Recommended way to implement Singleton in Java
public class EnumSingleton {

    public static void main(String[] args) {

        // Both references point to the same instance
        Singleton s1 = Singleton.INSTANCE;
        Singleton s2 = Singleton.INSTANCE;

        s1.doSomething();

        // Proves single instance
        System.out.println(s1 == s2); // true
    }

    // Enum itself is the Singleton
    enum Singleton {
        INSTANCE;

        // Example method
        public void doSomething() {
            System.out.println("Enum Singleton instance working");
        }
    }
}