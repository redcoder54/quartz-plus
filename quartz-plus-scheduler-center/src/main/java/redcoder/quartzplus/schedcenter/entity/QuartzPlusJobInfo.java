package redcoder.quartzplus.schedcenter.entity;

import lombok.Data;
import redcoder.quartzplus.core.core.dto.QuartzJobInfo;
import org.springframework.beans.BeanUtils;
import redcoder.quartzplus.schedcenter.entity.key.QuartzPlusJobInfoKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "QuartzPlusJobInfo")
@Table(name = "`quartz_plus_job_info`")
@IdClass(QuartzPlusJobInfoKey.class)
@Data
public class QuartzPlusJobInfo implements Serializable {
    /**
     * the name of scheduler
     */
    @Id
    @Column(name = "`sched_name`")
    private String schedName;

    /**
     * job名称
     */
    @Id
    @Column(name = "`job_name`")
    private String jobName;

    /**
     * job所在组名称
     */
    @Id
    @Column(name = "`job_group`")
    private String jobGroup;

    /**
     * 与job相关联的触发器名称
     */
    @Column(name = "`trigger_name`")
    private String triggerName;

    /**
     * 与job相关联的触发器所在组名称
     */
    @Column(name = "`trigger_group`")
    private String triggerGroup;

    /**
     * job的描述信息
     */
    @Column(name = "`job_desc`")
    private String jobDesc;

    /**
     * 触发器的描述信息
     */
    @Column(name = "`trigger_desc`")
    private String triggerDesc;

    /**
     * job的上一次执行时间
     */
    @Column(name = "`prev_fire_time`")
    private Date prevFireTime;

    /**
     * job的下一次执行时间
     */
    @Column(name = "`next_fire_time`")
    private Date nextFireTime;

    /**
     * 触发器的状态
     */
    @Column(name = "`trigger_state`")
    private String triggerState;

    /**
     * 触发器的状态描述
     */
    @Column(name = "`trigger_state_desc`")
    private String triggerStateDesc;

    /**
     * cron表达式
     */
    @Column(name = "`cron`")
    private String cron;

    @Column(name = "`create_time`")
    private Date createTime;

    @Column(name = "`update_time`")
    private Date updateTime;

    public static QuartzPlusJobInfo valueOf(QuartzJobInfo origin) {
        QuartzPlusJobInfo info = new QuartzPlusJobInfo();
        // 属性赋值
        BeanUtils.copyProperties(origin, info);
        if (origin.getPrevFireTime() > 0) {
            info.setPrevFireTime(new Date(origin.getPrevFireTime()));
        }
        if (origin.getNextFireTime() > 0) {
            info.setNextFireTime(new Date(origin.getNextFireTime()));
        }
        info.setCreateTime(new Date());
        info.setUpdateTime(new Date());
        return info;
    }
}