package yyl.example.demo.zk.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;

/**
 * 分布式编程时，会遇到多个应用同时访问某一资源的问题，这时候需要某种机制去协调它们。<br>
 * Zookeeper中有一种节点叫做顺序节点，假如我们在/lock/目录下创建节3个点，ZooKeeper集群会按照提起创建的顺序来创建节点，节点分别为/lock/0000000001、/lock/0000000002、/lock/0000000003。
 * ZooKeeper中还有一种名为临时节点的节点，临时节点由某个客户端创建，当客户端与ZooKeeper集群断开连接时临时节点会自动删除<br>
 * Curator 使用这种机制创建分布式锁 <br>
 * 每次加锁时生成顺序节点(如[_c_685692a1-531a-41db-9586-db478bac79ac-lock-0000000039])<br>
 * 当最小的目录与当前线程记录目录相同获得锁， 释放锁时删除节点<br>
 */
public class CuratorDistributedLockTest {

	private static final String ZK_ADDRESS = "localhost:2181";
	private static final String ZK_LOCK_PATH = "/zktest/lock0";

	/**
	 * 下面的程序会启动几个线程去争夺锁，拿到锁的线程会占用5秒
	 */
	public static void main(String[] args) throws InterruptedException {
		// 1.Connect to zk
		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));
		client.start();

		System.out.println(client.getState());

		System.out.println("zk client start successfully!");

		InterProcessMutex lock = new InterProcessMutex(client, ZK_LOCK_PATH);

		for (int i = 0; i < 3; i++) {
			new Thread(() -> {
				doWithLock(client, lock);
			}, "Thread-" + i).start();
		}

	}

	private static void doWithLock(CuratorFramework client, InterProcessMutex lock) {
		try {
			String name = Thread.currentThread().getName();
			if (lock.acquire(10 * 1000, TimeUnit.SECONDS)) {

				System.out.println(name + " hold lock");

				System.out.println(client.getChildren().forPath(ZK_LOCK_PATH));

				Thread.sleep(5000L);
				System.out.println(name + " release lock");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				lock.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
