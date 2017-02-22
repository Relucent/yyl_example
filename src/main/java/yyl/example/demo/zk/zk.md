#Zookeeper

官网：https://zookeeper.apache.org/  
下载：http://apache.fayea.com/zookeeper/zookeeper-3.4.9/zookeeper-3.4.9.tar.gz

##1. 概述  
ZooKeeper是Hadoop的正式子项目，它是一个针对大型分布式系统的可靠协调系统，提供的功能包括：配置维护、名字服务、分布式同步、组服务等。ZooKeeper的目标就是封装好复杂易出错的关键服务，将简单易用的接口和性能高效、功能稳定的系统提供给用户。

##2. 安装&配置  

### Windows 环境安装

1. 把下载的zookeeper的文件解压到指定目录，例如

	D:\zookeeper

2. 修改配置文件  

在 conf 目录下增加一个 zoo.cfg 配置文件，内容如下：

	# The number of milliseconds of each tick  心跳间隔 毫秒每次
	tickTime=2000

	# The number of ticks that the initial
	# synchronization phase can take
	initLimit=10

	# The number of ticks that can pass between
	# sending a request and getting anacknowledgement
	syncLimit=5

	# the directory where the snapshot isstored.  //镜像数据位置
	dataDir=D:/zookeeper/data/0

	#日志位置
	dataLogDir=D:/zookeeper/logs/0

	# the port at which the clients willconnect  客户端连接的端口
	clientPort=2181

	#server.1=localhost:2887:3887


注：可以参考 zoo_sample.cfg 文件内容


3. 启动ZK服务 

进入到bin目录，并且启动zkServer.cmd  

	cd bin  
	zkServer.cmd  

这个脚本中会启动一个Java进程，启动后jps可以看到QuorumPeerMain的进程

	jps

4. 测试连接

进入bin目录，启动客户端运行查看一下  

	zkCli.cmd -server 127.0.0.1:2181

5. 单机伪集群

如果想实现单机器的伪集群，可以修改 zkServer.cmd 文件，增加在里面加入

	set ZOOCFG=..\conf\zoo1.cfg

另存为  zkServer-1.cmd，这样启动 zkServer-1.cmd 时候，读取的配置文件是 zoo1.cfg  
单机配置伪集群时候要注意数据路径，日志目录，端口不要重复  
另外还需要在每个dataDir目录添加一个 myid 文件，内容就为对应的zoo.cfg里server.后数字