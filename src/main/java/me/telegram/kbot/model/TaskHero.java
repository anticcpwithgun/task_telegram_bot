package me.telegram.kbot.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author freealways
 * @date 2020/7/30 20:44
 */
@Data
@Entity
public class TaskHero {
    @Id
    private String id;
    private String taskId;
    private String heroId;
    /**
     * 1 领取者 2 执行者 3 已核验成功者 4 已核验失败者
     * 1 receive 2 action 3 check success 4 check fail
     */
    private Integer type;
    /**
     * 1 未结束 2 已结束
     */
    private Integer status;
}
