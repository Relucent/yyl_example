package yyl.example.demo.disruptor;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.YieldingWaitStrategy;

public class MsgHelper {

	private static final int BUFFER_SIZE = 256;//ringBuffer的容量，必须是2的N次方
	private RingBuffer<MsgEvent> ringBuffer;
	private SequenceBarrier sequenceBarrier;
	private MsgEventHandler handler;
	private BatchEventProcessor<MsgEvent> batchEventProcessor;
	private boolean inited = false;

	public MsgHelper() {
		//创建RingBuffer
		ringBuffer = RingBuffer.createMultiProducer(new MsgEventFactory(), BUFFER_SIZE, new YieldingWaitStrategy());
		//获取生产者的位置信息
		sequenceBarrier = ringBuffer.newBarrier();
		//消费者
		handler = new MsgEventHandler();
		//事件处理器，监控指定ringBuffer,有数据时通知指定handler进行处理
		batchEventProcessor = new BatchEventProcessor<MsgEvent>(ringBuffer, sequenceBarrier, handler);
		//传入消费者线程的序号
		ringBuffer.addGatingSequences(batchEventProcessor.getSequence());
	}

	/**
	 * 启动消费者线程，实际上调用了AudioDataEventHandler中的onEvent方法进行处理
	 */
	public void start() {
		Thread thread = new Thread(batchEventProcessor);
		thread.start();
		inited = true;
	}

	/**
	 * 停止
	 */
	public void shutdown() {
		if (!inited) {
			throw new RuntimeException("EncodeHelper还没有初始化！");
		} else {
			batchEventProcessor.halt();
		}
	}

	private void doProduce(Msg Msg) {
		//获取下一个序号
		long sequence = ringBuffer.next();
		//写入数据
		ringBuffer.get(sequence).setMsg(Msg);
		//通知消费者该资源可以消费了
		ringBuffer.publish(sequence);
	}

	/**
	 * 生产者压入生产数据
	 * @param data
	 */
	public void produce(Msg Msg) {
		if (!inited) {
			throw new RuntimeException("EncodeHelper还没有初始化！");
		} else {
			doProduce(Msg);
		}
	}
}
