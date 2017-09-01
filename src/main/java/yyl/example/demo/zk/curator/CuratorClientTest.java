package yyl.example.demo.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

/**
 * Curator是Netflix公司开源的一个Zookeeper客户端，与Zookeeper提供的原生客户端相比，Curator的抽象层次更高，简化了Zookeeper客户端的开发量
 *
 * curator-recipes (管理者菜谱) 包含功能:<br>
 * • 锁：包括共享锁、共享可重入锁、读写锁等。<br>
 * • 选举：Leader选举算法。<br>
 * • Barrier：阻止分布式计算直至某个条件被满足的“栅栏”，可以看做JDK Concurrent包中Barrier的分布式实现。<br>
 * • 缓存：前面提到过的三种Cache及监听机制。<br>
 * • 持久化节点：连接或Session终止后仍然在Zookeeper中存在的节点。<br>
 * • 队列：分布式队列、分布式优先级队列等。<br>
 *
 * ZK常用的命令<br>
 * • create：创建路径节点<br>
 * • ls：查看路径下的所有节点<br>
 * • get：获得节点上的值<br>
 * • set：修改节点上的值<br>
 * • delete：删除节点<br>
 */
public class CuratorClientTest {

	//ZK连接地址
	public static final String ZK_ADDRESS = "localhost:2181";
	//测试节点路径
	public static final String ZK_PATH = "/zktest";

	public static void main(String[] args) throws Exception {
		
		// 1.Connect to zk
		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));
		client.start();
		System.out.println("zk client start successfully!");

		// 2.Client API test
		// 2.1 Create node
		String data1 = "hello";
		print("create", ZK_PATH, data1);
		client.create().creatingParentsIfNeeded().forPath(ZK_PATH, data1.getBytes());

		// 2.2 Get node and data
		print("ls", "/");
		print(client.getChildren().forPath("/"));
		print("get", ZK_PATH);
		print(client.getData().forPath(ZK_PATH));

		// 2.3 Modify data
		String data2 = "world";
		print("set", ZK_PATH, data2);
		client.setData().forPath(ZK_PATH, data2.getBytes());
		print("get", ZK_PATH);
		print(client.getData().forPath(ZK_PATH));

		// 2.4 Remove node
		print("delete", ZK_PATH);
		client.delete().forPath(ZK_PATH);
		print("ls", "/");
		print(client.getChildren().forPath("/"));
		
		client.close();
	}

	private static void print(String... cmds) {
		StringBuilder text = new StringBuilder("$ ");
		for (String cmd : cmds) {
			text.append(cmd).append(" ");
		}
		System.out.println(text.toString());
	}

	private static void print(Object result) {
		System.out.println(result instanceof byte[] ? new String((byte[]) result) : result);
	}

}
