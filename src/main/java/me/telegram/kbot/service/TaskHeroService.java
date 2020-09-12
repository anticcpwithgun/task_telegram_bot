package me.telegram.kbot.service;

import me.telegram.kbot.model.TaskHero;

import java.util.List;

/**
 * @author freealways
 * @date 2020/7/31 9:20
 */

public interface TaskHeroService {
    void save(TaskHero taskHero);

    /**
     * @param status 1 未结束 2 已结束
     */
    List<TaskHero> findByHeroIdAndStatus(String heroId,Integer status);

    /**
     *
     * @param type 1 领取者 2 执行者 3 已核验成功者 4 已核验失败者
     *             1 receive 2 action 3 check success 4 check fail
     */
    List<TaskHero> findByHeroIdAndStatusAndType(String heroId,Integer status,Integer type);
    List<TaskHero> findByTaskIdAndStatus(String taskId,Integer status);
    Integer updateStatusByTaskId(String taskId,Integer status);

    TaskHero findByTaskIdAndHeroId(String taskId,String heroId);
}
