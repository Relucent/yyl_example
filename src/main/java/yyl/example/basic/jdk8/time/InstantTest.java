package yyl.example.basic.jdk8.time;

import java.time.Instant;

/**
 * Instant 类在Java 8 新引入的时间日期API中表示时间线中的一个特定时刻。<br>
 * Instant类 定义为从起点开始的的偏移量，起点是格林威治时间（GMT）1970-01-01:00:00<br>
 * 时间从起点开始向前移动，每天为86,400秒。<br>
 * (实际上Instant 取代原有的 Date 类)<br>
 */
public class InstantTest {
	public static void main(String[] args) {

		Instant instant = Instant.now();
		System.out.println("Instant->" + instant);

		//访问秒
		System.out.println("EpochSecond->" + instant.getEpochSecond());

		//访问纳秒
		System.out.println("Nano->" + instant.getNano());

	}

}
