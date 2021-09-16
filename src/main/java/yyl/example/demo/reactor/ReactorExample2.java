package yyl.example.demo.reactor;

import reactor.core.publisher.Mono;

public class ReactorExample2 {
    public static void main(String[] args) {
        // 命令式编程
        String name = "Reactor";
        String capitalName = name.toLowerCase();
        String greeting = "hello " + capitalName;
        System.out.println(greeting);

        // 函数式、反应式代码
        Mono.just("Reactor")//
                .map(String::toLowerCase)//
                .map(x -> "hello " + x)//
                .subscribe(System.out::println);
    }
}
