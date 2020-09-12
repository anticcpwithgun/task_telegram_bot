package me.telegram.kbot.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.telegram.kbot.model.Task;
import me.telegram.kbot.model.TaskHero;
import me.telegram.kbot.repository.TaskRepository;
import me.telegram.kbot.service.TaskHeroService;
import me.telegram.kbot.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author freealways
 * @date 2020/7/31 9:48
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskHeroService taskHeroService;

    @Override
    public void save(Task task) {
        if (task.getTaskId() == null) {
            String uuid = UUID.randomUUID().toString();
            task.setTaskId(uuid);
            task.setCreateDate(new Date());
        } else {
            task.setUpdateDate(new Date());
        }
        taskRepository.save(task);
    }

    @Override
    public Task findByTaskId(String taskId) {
        return taskRepository.findByTaskId(taskId);
    }

    @Override
    public List<Task> listByHeroIdAndStatus(String heroId, Integer status) {
        List<TaskHero> taskHeroList = taskHeroService.findByHeroIdAndStatus(heroId,status);
        List<String> taskIdList = taskHeroList.stream().map(TaskHero::getTaskId).collect(Collectors.toList());
        return taskRepository.findByTaskIdIn(taskIdList);
    }


    @Override
    public Page<Task> listByLevelAndPage(Integer page, Integer size,Integer level) {
        PageRequest pageRequest = PageRequest.of(page, size);
        pageRequest.getSort().and(Sort.by(Sort.Order.asc("createDate")));
        List<Integer> statusList = new ArrayList<>();
        statusList.add(2);
        return taskRepository.findByLevelAndStatusIn(level,statusList, pageRequest);
    }

    @Override
    public String receiveTask(String taskId, String heroId) {
        Task task = taskRepository.findByTaskId(taskId);
        if (task.getStatus() != 2) {
            return "任务已停止招募";
        }
        TaskHero taskHero = taskHeroService.findByTaskIdAndHeroId(taskId,heroId);
        if (taskHero != null) {
            return "无需重复领取";
        }
        List<TaskHero> taskHeroList = taskHeroService.findByHeroIdAndStatus(heroId,1);
        if (!taskHeroList.isEmpty()) {
            return "请先完成已领取任务";
        }
        taskHero = new TaskHero();
        String uuid = UUID.randomUUID().toString();
        taskHero.setId(uuid);
        taskHero.setTaskId(taskId);
        taskHero.setHeroId(heroId);
        taskHero.setType(1);
        taskHero.setStatus(1);

        taskHeroService.save(taskHero);
        return "领取成功";
    }
}
