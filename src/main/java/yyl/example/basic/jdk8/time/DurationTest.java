package yyl.example.basic.jdk8.time;

import java.time.Duration;

public class DurationTest {

    public static void main(String[] args) {
        System.out.println(Duration.parse("PT12S"));
        System.out.println(Duration.ofSeconds(12L));
        System.out.println(Duration.parse("P1D"));
        System.out.println(Duration.ofDays(1L));
    }
}
