package yyl.example.basic.jdbc.lock;

/**
 * 基于MySql行锁的分布式锁测试 <br>
 * 如果行锁正确，lock和unlock 应该是成对输出的<br>
 */
public class Test {

	public static void main(String[] args) {
		new Master("Master_1").start();
		new Master("Master_2").start();
		new Master("Master_3").start();
	}

	private static class Master extends Thread {
		private String name;
		private MyLock lock;

		private Master(String name) {
			//如果项目中使用MyLockFactory应该做成单例模式，此处主要为了模拟多个机器不同Factory的情况
			MyLockFactory factory = new MyLockFactory(DataSourceUtil.getDataSource());
			//根据ID获得锁(锁ID是一个长度不超过255的字符串)
			lock = factory.getLock("lock-1");
			this.name = name;
		}

		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				try {
					lock.lock();
					System.out.print(name + "(" + i + ")lock+1 	");

					//下面代码块是为了测试锁的可重入性
					{
						try {
							lock.lock();
							System.out.print(name + "(" + i + ")lock+2 	");
						} finally {
							System.out.print(name + "(" + i + ")unlock-2	");
							lock.unlock();
						}
					}

				} finally {
					System.out.println(name + "(" + i + ")unlock-1");
					lock.unlock();
				}
			}
		}
	}

}
