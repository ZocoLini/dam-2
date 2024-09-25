package examples;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// Channels are awesome for communication between threads. They are used to
// exchange data between threads in a producer-consumer pattern. A thread can
// put data into a channel, and another thread can take data from the channel.
// Channels are thread-safe and can be used to implement a producer-consumer
// pattern without the need for explicit synchronization.

public class ChannelExample
{
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> channel = new LinkedBlockingQueue<>();

        Thread producer = new Thread(() -> {
            try {
                channel.put("Msg 1");
                Thread.sleep(1000);
                channel.put("Msg 2");
                Thread.sleep(1000);
                channel.put("FIN");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                String message;
                while (!(message = channel.take()).equals("FIN")) {
                    System.out.println("Consume: " + message);
                }
                System.out.println("Consume: All processed.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}
