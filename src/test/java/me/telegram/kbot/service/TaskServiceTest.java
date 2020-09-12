package me.telegram.kbot.service;
import me.telegram.kbot.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.UUID;

/**
 * @author freealways
 * @date 2020/8/1 15:36
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TaskServiceTest {
    @Autowired
    private TaskService taskService;
    @Test
    public void save(){
        Task task = new Task();
        task.setTaskId(UUID.randomUUID().toString());
        task.setFromHeroId("232131");
        task.setFromHeroName("testName");
        task.setTitle("测试任务1");
        task.setContent("测试任务1内容");
        task.setRemark("测试任务1备注");
        task.setLevel(0);
        task.setActionIntegral(10);
        task.setCheckIntegral(5);
        task.setStatus(2);
        task.setNeedReceiveNumber(15);
        task.setNeedActionNumber(5);
        task.setNeedCheckNumber(10);
        task.setCreateDate(new Date());
        task.setUpdateDate(new Date());
    }
}