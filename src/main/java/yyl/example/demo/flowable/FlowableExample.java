package yyl.example.demo.flowable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

/**
 * Flowable是一个使用Java编写的轻量级业务流程引擎。<br>
 * Flowable流程引擎可用于部署BPMN 2.0流程定义（用于定义流程的行业XML标准）， 创建这些流程定义的流程实例，进行查询，访问运行中或历史的流程实例与相关数据。<br>
 */
public class FlowableExample {

	private final static Scanner SCANNER = new Scanner(System.in);

	public static void main(String[] args) {

		System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");

		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("yyl/example/demo/flowable/flowable.cfg.xml");

		// configuration.setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=1000");
		// configuration.setJdbcDriver("org.h2.Driver");
		// configuration.setJdbcUsername("sa");
		// configuration.setJdbcPassword("");
		// configuration.setDatabaseSchemaUpdate("true");
		// configuration.setAsyncExecutorActivate(false);
		// configuration.setMailServerHost("localhost");
		// configuration.setMailServerPort(25);

		// 创建流程引擎
		ProcessEngine processEngine = configuration.buildProcessEngine();

		// 创建了一个新的部署
		RepositoryService repositoryService = processEngine.getRepositoryService();

		Deployment deployment = repositoryService.createDeployment()//
				.addClasspathResource("yyl/example/demo/flowable/holiday-request.bpmn20.xml")//
				.deploy();

		// 部署流程定义
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()//
				.deploymentId(deployment.getId())//
				.singleResult();//

		System.out.println("Found process definition : " + processDefinition.getName());

		// 参数传递
		Map<String, Object> variables = new LinkedHashMap<String, Object>();
		System.out.println("Enter employee:");
		variables.put("employee", SCANNER.nextLine());
		System.out.println("Enter holidays:");
		variables.put("holidays", SCANNER.nextLine());

		RuntimeService runtimeService = processEngine.getRuntimeService();

		// 通过RuntimeService启动流程实例
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);

		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
		System.out.println("You have " + tasks.size() + " tasks:");
		for (int i = 0; i < tasks.size(); i++) {
			System.out.println((i + 1) + ") " + tasks.get(i).getName());
		}

		// 获取特定的流程实例变量
		System.out.println("Which task would you like to complete?");
		int taskIndex = Integer.valueOf(SCANNER.nextLine());
		Task task = tasks.get(taskIndex - 1);
		Map<String, Object> processVariables = taskService.getVariables(task.getId());
		System.out.println(processVariables);

		// 完成任务，传递排他网关条件
		boolean approved = SCANNER.nextLine().toLowerCase().equals("y");
		variables = new LinkedHashMap<String, Object>();
		variables.put("approved", approved);
		taskService.complete(task.getId(), variables);

		// 查询完成的活动，按结束时间排序
		HistoryService historyService = processEngine.getHistoryService();
		List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()//
				.processInstanceId(processInstance.getId())//
				.finished()//
				.orderByHistoricActivityInstanceEndTime()//
				.asc()//
				.list();
		for (HistoricActivityInstance activity : activities) {
			System.out.println(activity.getActivityId() + " took " + activity.getDurationInMillis() + " milliseconds");
		}
	}
}
