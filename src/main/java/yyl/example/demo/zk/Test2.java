package yyl.example.demo.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;


// 观察者
public class Test2 extends Thread {
  public static void main(String[] args) {
    ZooKeeper zk = null;
    try {
      zk = new ZooKeeper("localhost:2181", 1000, new Watcher() {
        // 监控所有被触发的事件
        public void process(WatchedEvent event) {
          System.out.println("已经触发了" + event.getType() + "事件！");
        }
      });

      printChildren("/", zk);

    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (zk != null) {
          zk.close();
        }
      } catch (Exception e1) {}
    }
  }

  private static void printChildren(String path, ZooKeeper zk) throws KeeperException, InterruptedException {
    for (String name : zk.getChildren(path, true)) {
      String childpath = path + (path.length() == 1 ? "" : "/") + name;
      System.out.println(childpath);
      printChildren(childpath, zk);
    }
  }

}
