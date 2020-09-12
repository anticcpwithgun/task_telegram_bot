package me.telegram.kbot.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.telegram.kbot.model.TaskHero;
import me.telegram.kbot.repository.TaskHeroRepository;
import me.telegram.kbot.service.TaskHeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author freealways
 * @date 2020/7/31 9:33
 */
@Service
@Slf4j
public class TaskHeroServiceImpl implements TaskHeroService {
    @Autowired
    private TaskHeroRepository taskHeroRepository;

    @Override
    public void save(TaskHero taskHero) {
        taskHeroRepository.save(taskHero);
    }

    @Override
    public List<TaskHero> findByHeroIdAndStatus(String heroId, Integer status) {
        return taskHeroRepository.findByHeroIdAndStatus(heroId,status);
    }

    @Override
    public List<TaskHero> findByHeroIdAndStatusAndType(String heroId, Integer status, Integer type) {
        return taskHeroRepository.findByHeroIdAndStatusAndType(heroId,status,type);
    }

    @Override
    public List<TaskHero> findByTaskIdAndStatus(String taskId, Integer status) {
        return taskHeroRepository.findByTaskIdAndStatus(taskId,status);
    }

    @Override
    public Integer updateStatusByTaskId(String taskId, Integer status) {
        return taskHeroRepository.updateStatusByTaskId(taskId,status);
    }

    @Override
    public TaskHero findByTaskIdAndHeroId(String taskId, String heroId) {
        return taskHeroRepository.findByTaskIdAndHeroId(taskId,heroId);
    }
}
