package examples;// Synchronized methods are used to prevent concurrent access to shared resources.
// Only one thread can execute a synchronized method at a time. That ensures that
// the shared resource is not accessed concurrently.

public class SynchronizedExample
{
    private int counter = 0;

    public synchronized void increment() {
        counter++;
    }

    public synchronized int getCounter() {
        return counter;
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedExample example = new SynchronizedExample();

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
