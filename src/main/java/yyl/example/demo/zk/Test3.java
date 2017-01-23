package yyl.example.demo.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;


// 观察者
public class Test3 extends Thread {
  public static void main(String[] args) {
    ZooKeeper zk = null;
    try {
      zk = new ZooKeeper("localhost:2181", 1000, null);

      zk.exists("/test", new Watcher() {
        @Override
        public void process(WatchedEvent e) {
          System.out.println(e);
        }
      });

      Thread.sleep(60 * 1000);

    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (zk != null) {
          zk.close();
        }
      } catch (Exception e1) {}
    }
  }


}
