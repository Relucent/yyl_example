package yyl.example.basic.jdk8.time;

import java.time.Duration;
import java.time.Instant;

public class DurationTest {

	public static void main(String[] args) {
		System.out.println(Duration.parse("PT12S"));
		System.out.println(Duration.ofSeconds(12L));
		System.out.println(Duration.parse("P1D"));
		System.out.println(Duration.ofDays(1L));

		Instant instant1 = Instant.now();
		Instant instant2 = instant1.plusSeconds(30);

		//Duration java.time.Duration.between (Temporal startInclusive, Temporal endExclusive)
		System.out.println(Duration.between(instant1, instant2).getSeconds());
	}
}
