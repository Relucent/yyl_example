package yyl.example.basic.nio.buffer;

import java.nio.IntBuffer;

/**
 * 位于java.nio.Buffer 是一个抽象类，内部包含一个内存块（数组），提供了一组比较有效的方法，用来进行写入和读取的交替访问。<br>
 * 有两个模式：读模式和写模式<br>
 * 重要的成员属性： capacity（容量）、position（读写位置）和limit（读写的限制）<br>
 * <br>
 * 重要的方法： <br>
 * allocate()：获取Buffer子类的实例对象<br>
 * put()：把对象写入缓冲区<br>
 * flip()：翻转方法，作用是将写模式翻转成读模式<br>
 * get()：从缓冲区读取数据<br>
 * mark()：将当前position的值保存起来放在mark属性中，让mark属性记住这个临时位置<br>
 * reset()：将mark的值恢复到position中<br>
 * clear()：在读模式下，将缓冲区切换为写模式 <br>
 * <br>
 * Buffer类的基本步骤如下：<br>
 * (1)使用创建子类实例对象的allocate()方法创建一个Buffer类的实例对象；<br>
 * (2)调用put()方法将数据写入缓冲区中；<br>
 * (3)写入完成后，在开始读取数据前调用Buffer.flip()方法，将缓冲区转换为读模式；<br>
 * (4)调用get()方法，可以从缓冲区中读取数据；<br>
 * (5)读取完成后，调用Buffer.clear()方法或Buffer.compact()方法，将缓冲区转换为写模式，可以继续写入。<br>
 */
public class BufferExample {

    public static void main(String[] args) {

        IntBuffer buffer = IntBuffer.allocate(10);
        print(buffer, "----------allocate----------");

        for (int i = 0; i < 5; i++) {
            buffer.put(i);
        }
        print(buffer, "----------put---------------");

        buffer.flip();
        print(buffer, "----------flip--------------");

        buffer.get();
        print(buffer, "----------get-1-------------");

        while (buffer.remaining() != 0) {
            buffer.get();
        }
        print(buffer, "----------get-all-------------");

        buffer.rewind();
        print(buffer, "----------rewind--------------");

        buffer.get();
        print(buffer, "----------get-1-------------");

        buffer.clear();
        print(buffer, "----------clear--------------");
    }

    private static void print(IntBuffer buffer, String title) {
        System.out.println(title);
        System.out.println("position=" + buffer.position());
        System.out.println("limit=" + buffer.limit());
        System.out.println("capacity=" + buffer.capacity());
        System.out.println();
    }
}
