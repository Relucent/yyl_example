package yyl.example.demo.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;


// 观察者
public class Test4 extends Thread {
  public static void main(String[] args) {
    ZooKeeper zk = null;
    try {
      zk = new ZooKeeper("localhost:2181", 1000, null);

      zk.create("/test", "demo".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

      Thread.sleep(10 * 1000);

      zk.delete("/test", -1);

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
