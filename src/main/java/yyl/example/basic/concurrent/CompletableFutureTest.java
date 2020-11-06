package yyl.example.basic.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 在Java8中，CompletableFuture提供了非常强大的Future的扩展功能，可以帮助我们简化异步编程的复杂性，可以通过回调的方式处理计算结果。
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread() {
            public void run() {
                try {
                    for (int i = 5; i > 0; i--) {
                        Thread.sleep(1000);
                        System.out.println(i);
                    }
                    future.complete("ok");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();
        System.out.println(future.get());
    }
}
