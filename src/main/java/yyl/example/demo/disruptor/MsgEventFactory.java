package yyl.example.demo.disruptor;

import com.lmax.disruptor.EventFactory;

public class MsgEventFactory implements EventFactory<MsgEvent> {
	public MsgEvent newInstance() {
		return new MsgEvent();
	}
};