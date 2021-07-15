package yyl.example.demo.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorExample1 {
    public static void main(String[] args) {
        // 流中的元素依次是：0 1 2 3 4 5
        Flux<Integer> flux = Flux.generate(() -> 0, (state, sink) -> {
            sink.next(state);
            if (state == 5) {
                sink.complete();
            }
            return state + 1;
        });
        flux.flatMap(x -> {
            System.out.println("Step1=" + x);
            return Mono.just(x + 1);
        }).subscribe(x -> System.out.println("Result=" + x + "\n"));
    }
}
