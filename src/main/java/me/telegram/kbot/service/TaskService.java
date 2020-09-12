package me.telegram.kbot.service;

import me.telegram.kbot.model.Task;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author freealways
 * @date 2020/7/30 21:00
 */

public interface TaskService {
    void save(Task task);
    Task findByTaskId(String taskId);

    /**
     *
     * @param status 1 未完成 2 已完成
     */
    List<Task> listByHeroIdAndStatus(String heroId, Integer status);
    Page<Task> listByLevelAndPage(Integer page, Integer size,Integer level);
    String receiveTask(String taskId,String heroId);
}
