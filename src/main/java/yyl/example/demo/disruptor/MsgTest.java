package yyl.example.demo.disruptor;

import java.util.concurrent.TimeUnit;

public class MsgTest {
	public static void main(String[] args) throws InterruptedException {
		MsgHelper msgHelper = new MsgHelper();
		msgHelper.start();
		for (int i = 0; i < 20; i++) {
			Msg msg = new Msg();
			msg.setId(i);
			msg.setValue("value-" + i);
			msgHelper.produce(msg);
		}
		TimeUnit.SECONDS.sleep(10);
		msgHelper.shutdown();
	}
}
