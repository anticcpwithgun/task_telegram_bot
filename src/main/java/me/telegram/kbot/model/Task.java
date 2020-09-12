package me.telegram.kbot.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author freealways
 * @date 2020/7/30 17:31
 */
@Data
@Entity
public class Task {
    @Id
    private String taskId;
    /**
     * 任务来源 HeroId
     */
    private String fromHeroId;
    /**
     * 任务来源 HeroName
     */
    private String fromHeroName;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 备注
     */
    private String remark;
    /**
     * 任务等级 0最低，9最高
     */
    private Integer level;
    /**
     * 奖励
     */
    private String reward;
    /**
     * 执行积分值
     */
    private Integer actionIntegral;
    /**
     * 核验积分值
     */
    private Integer checkIntegral;

    /**
     * 0 已创建 1 已发起 2 招募中 3 执行中
     * 4 执行结束 5 核实中 6 任务成功 7 任务流产
     */
    private Integer status;
    /**
     * 需要接受的人数
     */
    private Integer needReceiveNumber;
    /**
     * 需要执行的人数
     */
    private Integer needActionNumber;
    /**
     * 需要的核验人数
     */
    private Integer needCheckNumber;

    /**
     * 完成者
     */
    private String finishHeroId;
    /**
     * 完成时间
     */
    private Date finishDate;
    /**
     * 核验时间
     */
    private Date checkedDate;

    private Date createDate;

    private Date updateDate;
}
