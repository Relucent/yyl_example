package yyl.example.basic.jdk8.lambda;

/**
 * Lambda初步了解
 */
public class LambdaDemo {

    public static void main(String[] args) {

        HelloFunction func1 = new HelloFunction() {
            @Override
            public void apply(String s) {
                System.out.println(s);
            }
        };


        HelloFunction func2 = (s) -> {
            System.out.println(s);
        };

        HelloFunction func3 = (s) -> System.out.println(s);


        HelloFunction func4 = s -> System.out.println(s);

        HelloFunction func5 = System.out::print;

        apply(func1);
        apply(func2);
        apply(func3);
        apply(func4);
        apply(func5);
    }


    private static void apply(HelloFunction function) {
        function.apply("hello");
    }


    @FunctionalInterface
    private static interface HelloFunction {
        void apply(String value);
    }
}
