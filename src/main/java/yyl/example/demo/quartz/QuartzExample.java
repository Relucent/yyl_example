package yyl.example.demo.quartz;

import java.time.LocalDateTime;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 1. Job 工作，定义要运行什么 <br>
 * 2. Trigger 触发器，定义什么时候运行<br>
 * 3. Scheduler 调度类，将“工作”和“触发器”链接到一起，并执行 <br>
 */
public class QuartzExample {

	public static void main(String[] args) throws Exception {

		// JobDetail job = new JobDetail();
		// job.setName("jobName");
		// job.setJobClass(HelloJob.class);
		JobDetail job = JobBuilder.newJob(HelloJob.class)//
				.withIdentity("jobName", "groupName")//
				.build();

		//CronTrigger trigger = new CronTrigger();
		//trigger.setName("triggerName");
		//trigger.setCronExpression("0/5 * * * * ?");

		Trigger trigger = TriggerBuilder.newTrigger()//
				.withIdentity("triggerName", "group")//
				.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))//
				.build();

		//
		StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();

		//默认从quartz.properties文件读取配置
		//stdSchedulerFactory.initialize();

		//使用Properties设置配置信息
		Properties props = new Properties();
		props.put("org.quartz.scheduler.instanceName", "MyScheduler");
		props.put("org.quartz.threadPool.threadCount", "3");
		props.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
		stdSchedulerFactory.initialize(props);

		Scheduler scheduler = stdSchedulerFactory.getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);

		//运行30秒关闭
		Thread.sleep(30 * 1000);
		scheduler.shutdown();
	}

	public static class HelloJob implements Job {
		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
			System.out.println(LocalDateTime.now() + " | hello quartz!");
		}
	}
}