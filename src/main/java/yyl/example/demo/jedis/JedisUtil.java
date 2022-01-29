//package yyl.example.demo.jedis;
//
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//public class JedisUtil {
//
//    /**
//     * 创建连接池（使用默认配置）
//     * @param host 地址
//     * @param port 端口
//     * @return 连接池
//     */
//    public static JedisPool getJedisPool(String host, int port) {
//        JedisPoolConfig config = new JedisPoolConfig();
//        // 重要参数
//        config.setMaxTotal(8);// 最大连接，默认8
//        config.setMaxIdle(8);// 允许最大空闲连接数，默认8
//        config.setMinIdle(0);// 保证的最小空闲连接，默认0
//        config.setBlockWhenExhausted(true);// 当资源池用尽后，调用者是否要等待
//        config.setMaxWaitMillis(12 * 1000L);// 当资源池连接用尽后，调用者的最大等待时间（单位为毫秒），默认-1（表示永不超时）
//        config.setTestOnBorrow(true);// 向资源池借用连接时是否做连接有效性检测（ping），检测到的无效连接将会被移除；默认false
//        config.setTestOnReturn(true);// 向资源池归还连接时是否做连接有效性检测（ping），检测到无效连接将会被移除
//        config.setJmxEnabled(true);// 是否开启JMX监控
//        // 空闲对象检测由下列四个参数组合完成
//        config.setTestWhileIdle(true); // 是否开启空闲资源检测，默认false
//        config.setTimeBetweenEvictionRunsMillis(30 * 1000);// 空闲资源的检测周期（单位为毫秒），默认-1（不检测）
//        config.setMinEvictableIdleTimeMillis(60 * 1000L);// 资源池中资源的最小空闲时间（单位为毫秒），达到此值后空闲资源将被移除；默认30分钟
//        config.setNumTestsPerEvictionRun(-1);// 做空闲资源检测时，每次检测资源的个数，默认3；设置为 -1，就是对所有连接做空闲监测。
//        return new JedisPool(config, host, port, 8000);
//    }
//}
