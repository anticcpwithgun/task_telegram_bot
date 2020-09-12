package me.telegram.kbot.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author freealways
 * @date 2020/7/28 20:40
 */
@Data
@Entity
public class Hero {
    @Id
    private String heroId;
    private String userName;
    private String name;
    private Integer integral;
    /**
     * 权限等级 0最低，9最高 -1 封禁
     */
    private Integer level;
    /**
     * 任务领取次数
     */
    private Integer taskReceiveTimes;
    /**
     * 任务执行次数
     */
    private Integer taskActionTimes;
    /**
     * 任务成功次数
     */
    private Integer actionSuccessTimes;
    /**
     * 任务失败次数
     */
    private Integer actionFailTimes;
    /**
     * 任务核验成功次数
     */
    private Integer checkSuccessTimes;
    /**
     * 任务核验失败次数
     */
    private Integer checkFailTimes;

}
