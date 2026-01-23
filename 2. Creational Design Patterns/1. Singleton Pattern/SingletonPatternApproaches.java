// Eager Loading
// Instance created at class load time
// Thread-safe by default
class EagerSingleton {

    private static final EagerSingleton instance =
            new EagerSingleton();

    // Prevents external instantiation
    private EagerSingleton() {
    }

    // Global access point
    public static EagerSingleton getEagerInstance() {
        return instance;
    }
}


// Lazy Loading
// Instance created on first request
// NOT thread-safe
class LazySingleton {

    private static LazySingleton instance;

    private LazySingleton() {
    }

    // Race condition possible in multithreaded environment
    public static LazySingleton getLazyInstance() {
        if (instance == null)
            instance = new LazySingleton();

        return instance;
    }
}


// Synchronized Method
// Thread-safe
// Performance overhead due to method-level lock
class SyncSingleton {

    private static SyncSingleton instance;

    private SyncSingleton() {
    }

    public static synchronized SyncSingleton getInstance() {
        if (instance == null)
            instance = new SyncSingleton();

        return instance;
    }
}


// Double-Checked Locking
// Thread-safe
// Reduces synchronization overhead
class LockingSingleton {

    // volatile prevents instruction reordering
    private static volatile LockingSingleton instance;

    private LockingSingleton() {
    }

    public static LockingSingleton getInstance() {
        if (instance == null) {
            synchronized (LockingSingleton.class) {
                if (instance == null)
                    instance = new LockingSingleton();
            }
        }
        return instance;
    }
}


// Bill Pugh Singleton
// Lazy-loaded
// Thread-safe without synchronization
class BillPughSingleton {

    private BillPughSingleton() {
    }

    public static BillPughSingleton getInstance() {
        return Holder.INSTANCE;
    }

    // Loaded only when getInstance() is called
    private static class Holder {
        private static final BillPughSingleton INSTANCE =
                new BillPughSingleton();
    }
}


// Client / Test class
public class SingletonPatternApproaches {

    public static void main(String[] args) {

        EagerSingleton e1 = EagerSingleton.getEagerInstance();
        EagerSingleton e2 = EagerSingleton.getEagerInstance();

        LazySingleton l1 = LazySingleton.getLazyInstance();
        LazySingleton l2 = LazySingleton.getLazyInstance();

        SyncSingleton s1 = SyncSingleton.getInstance();
        SyncSingleton s2 = SyncSingleton.getInstance();

        LockingSingleton d1 = LockingSingleton.getInstance();
        LockingSingleton d2 = LockingSingleton.getInstance();

        BillPughSingleton b1 = BillPughSingleton.getInstance();
        BillPughSingleton b2 = BillPughSingleton.getInstance();

        // All pairs point to the same instance
        System.out.println(e1 == e2);
        System.out.println(l1 == l2);
        System.out.println(s1 == s2);
        System.out.println(d1 == d2);
        System.out.println(b1 == b2);
    }
}
