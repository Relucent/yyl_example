package yyl.example.basic.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 只具有弱引用的对象拥有短暂的生命周期。<br>
 * 在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。<br>
 * 不过，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象。<br>
 * 弱引用可以和一个引用队列（ReferenceQueue）联合使用，如果弱引用所引用的对象被垃圾回收，Java虚拟机就会把这个弱引用加入到与之关联的引用队列中。
 */
public class WeakReferenceQueueTest {

    public static void main(String[] args) throws InterruptedException {

        String value = new String("object");

        ReferenceQueue<String> queue = new ReferenceQueue<>();

        WeakReference<String> reference = new WeakReference<String>(value, queue);

        System.out.println(reference.get());

        value = null;
        System.gc();
        Thread.sleep(1000);// 等待垃圾回收

        System.out.println(reference.get());
        System.out.println(queue.poll() == reference);
    }
}
