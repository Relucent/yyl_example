package yyl.example.demo.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * 事件处理处理器
 */
public class MsgEventHandler implements EventHandler<MsgEvent> {

	@Override
	public void onEvent(MsgEvent event, long sequence, boolean endOfBatch) throws Exception {
		Msg msg = event.getMsg();
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + "->" + msg);
	}

}