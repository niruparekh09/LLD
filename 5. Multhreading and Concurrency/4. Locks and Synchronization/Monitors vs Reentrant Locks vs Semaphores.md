# Monitors vs. Reentrant Locks vs. Semaphores

## 1. Comparison

| Feature                      | Monitor (`synchronized`)                  | ReentrantLock                           | Semaphore                      |
|------------------------------|-------------------------------------------|-----------------------------------------|--------------------------------|
| Main purpose                 | Protect shared state                      | Protect shared state with extra control | Limit concurrent access        |
| Concurrent access            | One thread per monitor                    | One thread per lock                     | Up to N threads                |
| Acquisition                  | Automatic                                 | `lock()`                                | `acquire()`                    |
| Release                      | Automatic when exiting synchronized block | Manual: `unlock()`                      | Manual: `release()`            |
| Timed acquisition            | No built-in timeout                       | `tryLock(timeout, unit)`                | `tryAcquire(timeout, unit)`    |
| Can acquire without waiting? | No                                        | `tryLock()`                             | `tryAcquire()`                 |
| Ownership                    | Current thread owns monitor               | Current thread owns lock                | Permits are not thread-owned   |
| Reentrant?                   | Yes                                       | Yes                                     | No reentrant behavior built in |

## 2. Simple examples

### A. Monitor - `synchronized`

**Use case:** Protect a shared stock price from concurrent updates.

```java
class Stock {
    private double price = 100;

    public synchronized void updatePrice(double newPrice) {
        price = newPrice;
    }
}
```

* Only one thread at a time can execute a synchronized method on the same object.
* Java automatically acquires and releases the monitor.

### B. ReentrantLock

**Use case:** Protect a shared stock price, but allow more control over acquiring the lock.

```java
import java.util.concurrent.locks.ReentrantLock;

class Stock {
    private double price = 100;
    private final ReentrantLock lock = new ReentrantLock();

    public void updatePrice(double newPrice) {
        lock.lock();
        try {
            price = newPrice;
        } finally {
            lock.unlock();
        }
    }
}
```

* Like `synchronized`, only one thread at a time can hold the lock.
* Provides extra features such as `tryLock()` and interruptible acquisition.
* You must manually release it in `finally`.

### C. Semaphore

**Use case:** Allow at most two threads to fetch stock data simultaneously.

```java
import java.util.concurrent.Semaphore;

class StockAPI {
    private final Semaphore permits = new Semaphore(2);

    public void fetchPrice() throws InterruptedException {
        permits.acquire();
        try {
            System.out.println("Fetching stock price...");
        } finally {
            permits.release();
        }
    }
}
```

* Up to two threads can fetch data at once.
* Additional threads wait until a permit is released.
* A semaphore can allow multiple concurrent users rather than just one.

## 3. Remember the difference

* **Monitor:** One person at a time; Java manages the lock automatically.
* **ReentrantLock:** One person at a time, with extra controls and manual unlocking.
* **Semaphore:** A limited number of entry permits; for example, two people at a time.

**Interview tip:** Use a monitor or `ReentrantLock` to protect shared mutable state. Use a semaphore to limit concurrent
access to a resource, such as API calls or database connections.
