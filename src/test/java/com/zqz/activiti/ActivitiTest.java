package com.zqz.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * @Author: zqz
 * @CreateTime: 2024/06/24
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationStart.class)
public class ActivitiTest {

    /**
     * 部署流程定义
     */
    @Test
    public void testDeployment() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/evection.bpmn20.xml")
                .addClasspathResource("bpmn/evection.png")
                .name("出差申请单")
                .deploy();
        log.info("流程部署ID:{}", deployment.getId());
        log.info("流程部署名称:{}", deployment.getName());
    }


    /**
     * 启动流程实例
     */
    @Test
    public void testStartProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("evection");
        log.info("流程定义ID:{}", processInstance.getProcessDefinitionId());
        log.info("流程实例ID:{}", processInstance.getId());
        log.info("当前活动ID:{}", processInstance.getActivityId());
    }

    /**
     * 查询当前个人待执行的任务
     */
    @Test
    public void testFindPersonalTaskList() {
        String assignee = "张三丰";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("evection")
                .taskAssignee(assignee)
                .list();

        for (Task task : list) {
            log.info("流程实例id:{}", task.getProcessInstanceId());
            log.info("任务id:{}", task.getId());
            log.info("任务负责人:{}", task.getAssignee());
            log.info("任务名称:{}", task.getName());
        }
    }


    /**
     * 完成任务
     */
    @Test
    public void completTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("evection")
                .taskAssignee("张三丰")
                .singleResult();
        taskService.complete(task.getId());
    }
}
