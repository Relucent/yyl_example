package yyl.example.basic.thread;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            CompletableFuture.runAsync(() -> {
                System.out.println(Thread.currentThread() + ":run");
                sheep(1000);
                System.out.println(Thread.currentThread() + ":complete");
            });
        }
        sheep(2000);
    }

    private static void sheep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
