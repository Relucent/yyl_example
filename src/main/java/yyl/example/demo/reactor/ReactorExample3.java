package yyl.example.demo.reactor;

import reactor.core.publisher.Flux;

public class ReactorExample3 {
    public static void main(String[] args) throws InterruptedException {
        Flux<Integer> flux1 = Flux.just(1, 3, 5, 7, 9);
        Flux<Integer> flux2 = Flux.just(2, 4, 6, 8, 10);
        Flux<Integer> flux3 = flux1.mergeWith(flux2);
        flux3.subscribe(System.out::println);
    }
}
