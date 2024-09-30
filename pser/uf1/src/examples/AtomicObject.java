package examples;

import java.util.concurrent.atomic.AtomicInteger;

// Atomic classes provide a way to perform atomic operations on shared variables.
// They are used to prevent concurrent access to shared resources.
// Only one thread can execute an atomic operation at a time. That ensures that
// the shared resource is not accessed concurrently.

public class AtomicObject
{
    private final AtomicInteger counter = new AtomicInteger(0);

    public void increment() {
        counter.addAndGet(1);
    }

    public int getCounter() {
        return counter.get();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicObject example = new AtomicObject();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Contador final: " + example.getCounter());
    }
}
