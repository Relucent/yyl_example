package yyl.example.demo.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.retry.RetryNTimes;

/**
 * Curator提供了三种Watcher(Cache)来监听节点的变化：<br>
 * Path Cache：监视一个路径下子节点的创建、删除、以及节点数据的更新<br>
 * Node Cache：监视一个节点的创建、更新、删除，并将节点的数据缓存在本地<br>
 * Tree Cache：Path Cache + Node Cache，监视路径下的创建、更新、删除事件，并缓存路径下所有孩子节点节点的数据<br>
 */
public class CuratorWatcherTest {

	public static final String ZK_ADDRESS = "localhost:2181";
	public static final String ZK_PATH = "/zktest";
	public static final String ZK_CHILDREN_PATH = "/zktest/hello";

	public static void main(String[] args) throws Exception {
		// 1.Connect to zk
		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));
		client.start();
		System.out.println("zk client start successfully!");

		// 2.Register watcher
		PathChildrenCache watcher = new PathChildrenCache(client, ZK_PATH, true /* if cache data */ );

		watcher.getListenable().addListener((client1, event) -> {
			ChildData data = event.getData();
			if (data == null) {
				System.out.println("No data in event[" + event + "]");
			} else {
				System.out.println("Receive event: " //
						+ "type=[" + event.getType() + "]" + ", "//
						+ "path=[" + data.getPath() + "]" + ", "//
						+ "data=[" + new String(data.getData()) + "]" + ", "//
						+ "stat=[" + data.getStat() + "]");
			}
		});
		watcher.start(StartMode.BUILD_INITIAL_CACHE);
		System.out.println("Register zk watcher successfully!");

		client.create().creatingParentsIfNeeded().forPath(ZK_CHILDREN_PATH, "hello".getBytes());
		client.setData().forPath(ZK_CHILDREN_PATH, "world".getBytes());
		client.delete().deletingChildrenIfNeeded().forPath(ZK_CHILDREN_PATH);

		watcher.close();
	}

}
