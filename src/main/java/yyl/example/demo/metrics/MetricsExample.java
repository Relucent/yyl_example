package yyl.example.demo.metrics;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;

/**
 * Metrics 是一款监控指标的度量类库，提供了许多工具帮助开发者来完成自定义的监控工作。<br>
 * Metrics提供了五个基本的度量类型：<br>
 * 1.Gauges（度量）<br>
 * 2.Counters（计数器）<br>
 * 3.Histograms（直方图数据）<br>
 * 4.Meters（TPS计算器）<br>
 * 5.Timers（计时器）<br>
 * MetricRegistry是中心容器，它是程序中所有度量的容器，所有新的度量工具都要注册到一个MetricRegistry实例中才可以使用，一般一个应用中 MetricRegistry 是一个单例。<br>
 */
public class MetricsExample {
    public static void main(String[] args) {
        MetricRegistry metrics = new MetricRegistry();
        Timer timer = metrics.timer("request");

        // Timer 是一个 Meter和Histogram的组合
        // 这个度量单位可以比较方便地统计请求的速率和处理时间。对于接口中调用的延迟等信息的统计就比较方便了
        // Meters会将最近1分钟，5分钟，15分钟的TPS（每秒处理的request数）给打印出来，还有所有时间的TPS
        ConsoleReporter.forRegistry(metrics).build().start(3, TimeUnit.SECONDS);

        // 运行一分钟
        for (int i = 0; i < 120; i++) {
            try (Context context = timer.time()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}
