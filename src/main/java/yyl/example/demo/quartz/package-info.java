/**
 * Quartz, 是一个企业级调度工作的框架，帮助Java应用程序到调度工作/任务在指定的日期和时间运行。<br>
 * <a target="_blank" href="http://www.quartz-scheduler.org/">官方网站</a><br>
 */
package yyl.example.demo.quartz;

//| #运行环境
//| Quartz 可以运行嵌入在另一个独立式应用程序
//| Quartz 可以在应用程序服务器(或servlet容器)内被实例化，并且参与XA事务
//| Quartz 可以作为一个独立的程序运行(其自己的Java虚拟机内)，可以通过RMI使用
//| Quartz 可以被实例化，作为独立的项目集群(负载平衡和故障转移功能)，用于作业的执行
//|
//| #作业调度
//| 作业被安排在一个给定的触发时运行。触发器可以使用以下指令的接近任何组合来创建：
//| 在一天中的某个时间（到毫秒）
//| 在一周的某几天
//| 在每月的某一天
//| 在一年中的某些日期
//| 不在注册的日历中列出的特定日期（如商业节假日除外）
//| 重复特定次数
//| 重复进行，直到一个特定的时间/日期
//| 无限重复
//| 重复的延迟时间间隔
//| 
//| #作业执行
//| 作业可以实现简单的作业接口，为作业执行工作的任何Java类。
//| Job类的实例可以通过Quartz被实例化，或者通过应用程序框架。
//| 当触发时，调度通知实现JobListener和TriggerListener接口零个或多个Java对象（监听器可以是简单的Java对象，或EJB，JMS或发布者等）。这些监听器在作业已经执行之后通知。
//| 由于作业完成后返回JobCompletionCode，它通知的成功或失败的调度。JobCompletionCode还可以指示的基础上，成功的话就采取行动调度/失败的代码 - 如立即重新执行作业。
//| 
//| #作业持久性
//| Quartz的设计包括可被实现以提供的作业存储各种机制一个作业存储接口
//| 通过使用包含的JDBCJobStore，所有的作业和触发器配置为“非挥发性”都存储在通过JDBC关系数据库。
//| 通过使用包含的RAMJobStore，所有的作业和触发器存储在RAM，因此不计划执行仍然存在 - 但这是无需使用外部数据库的优势。
//| 
//| #事务
//| 可以参与JTA事务，通过使用JobStoreCMT（JDBCJobStore的子类）。
//| Quartz可以管理JTA事务（开始并提交它们）周围作业的执行，从而使作业执行的工作自动将JTA事务中发生。
//| 
//| #集群
//| 故障切换
//| 负载均衡
//| Quartz的内置的群集功能，通过JDBCJobStore（如上所述）依靠数据库持久
//| Terracotta扩展Quartz提供集群功能，而不需要一个支持数据库
//| 
//| #监听器和插件
//| 应用程序可以捕捉事件的调度监控或通过实现一个或多个监听器接口控制工作/触发行为。
//| 插件机制，可以用来添加功能，Quartz让作业执行过程中或工作负载和触发定义的历史不受限在一个文件中。
//| 附带了一些“工厂建有”插件和监听器。
//|
//|
//| #CronExpression表达式
//| 完整格式为： [秒] [分] [小时] [日] [月] [周] [年]
//| 序号	|说明	|是否必填	|允许填写的值			|允许的通配符    
//| 1	|秒	|是		|0-59				|, - * /    
//| 2	|分	|是		|0-59				|, - * /    
//| 3	|时	|是		|0-23				|, - * /    
//| 4	|日	|是		|1-31				|, - * ? / L W    
//| 5	|月	|是		|1-12 or JAN-DEC	|, - * /    
//| 6	|周	|是		|1-7 or SUN-SAT		|, - * ? / L #    
//| 7	|年	|否		|empty 或 1970-2099	|, - * /
//| 
//| 通配符说明:
//| * 表示所有值，例如在分的字段上设置 "*",表示每一分钟都会触发。
//| ? 表示不指定值
//| - 表示区间  
//| , 表示指定多个值，例如在周字段上设置 "MON,WED,FRI" 表示周一，周三和周五
//| / 表示递增触发，例如在秒字段上设置'10/5' 所示每分钟第10秒开始，每隔5秒触发一次
//| # 表示序号(表示每月的第几个周几) 例如在周字段上设置"6#3"表示在每月的第三个周六.
//|   如果在"L"前加上数字，则表示该数据的最后一个，W 表示离指定日期的最近那个工作日
//| 
//| #常用示例
//| 0 0 12 * * ?    每天12点触发    
//| 0 15 10 ? * *    每天10点15分触发    
//| 0 15 10 * * ?    每天10点15分触发    
//| 0 15 10 * * ? *    每天10点15分触发    
//| 0 15 10 * * ? 2005    2005年每天10点15分触发    
//| 0 * 14 * * ?    每天下午的 2点到2点59分每分触发    
//| 0 0/5 14 * * ?    每天下午的 2点到2点59分(整点开始，每隔5分触发)    
//| 0 0/5 14,18 * * ?    每天下午的 2点到2点59分、18点到18点59分(整点开始，每隔5分触发)    
//| 0 0-5 14 * * ?    每天下午的 2点到2点05分每分触发    
//| 0 10,44 14 ? 3 WED    3月分每周三下午的 2点10分和2点44分触发    
//| 0 15 10 ? * MON-FRI    从周一到周五每天上午的10点15分触发    
//| 0 15 10 15 * ?    每月15号上午10点15分触发    
//| 0 15 10 L * ?    每月最后一天的10点15分触发    
//| 0 15 10 ? * 6L    每月最后一周的星期五的10点15分触发    
//| 0 15 10 ? * 6L 2002-2005    从2002年到2005年每月最后一周的星期五的10点15分触发    
//| 0 15 10 ? * 6#3    每月的第三周的星期五开始触发    
//| 0 0 12 1/5 * ?    每月的第一个中午开始每隔5天触发一次    
//| 0 11 11 11 11 ?    每年的11月11号 11点11分触发    