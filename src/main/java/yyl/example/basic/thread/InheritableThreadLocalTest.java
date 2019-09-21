package yyl.example.basic.thread;

/**
 * ThreadLocal设计之初就是为了绑定当前线程，如果希望当前线程的ThreadLocal能够被子线程使用，则需要使用InheritableThreadLocal。
 */
public class InheritableThreadLocalTest {

    public static void main(String[] args) {
        ThreadLocal<String> local1 = new ThreadLocal<>();
        ThreadLocal<String> local2 = new InheritableThreadLocal<>();

        local1.set("value");
        local2.set("value");

        new ChildrenThread("Thread-1", local1).start();
        new ChildrenThread("Thread-2", local2).start();
    }

    private static class ChildrenThread extends Thread {
        private final ThreadLocal<String> local;

        ChildrenThread(String name, ThreadLocal<String> local) {
            super(name);
            this.local = local;
        }

        @Override
        public void run() {
            System.out.println(getName() + "->" + local.get());
        }

    }

}
