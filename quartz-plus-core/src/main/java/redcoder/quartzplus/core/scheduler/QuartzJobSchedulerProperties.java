package redcoder.quartzplus.core.scheduler;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * quartz任务调度管理平台的配置属性
 *
 * @author redcoder
 * @since 1.4
 */
@ConfigurationProperties(prefix = "quartz-job-scheduler.registry")
public class QuartzJobSchedulerProperties {

    /**
     * 注册实例信息的地址
     */
    private String registerUrl;

    /**
     * 解除注册实例信息的地址
     */
    private String unregisterUrl;

    /**
     * 任务执行记录上报地址
     */
    private String reportUrl;;

    public String getRegisterUrl() {
        return registerUrl;
    }

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }

    public String getUnregisterUrl() {
        return unregisterUrl;
    }

    public void setUnregisterUrl(String unregisterUrl) {
        this.unregisterUrl = unregisterUrl;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public QuartzJobSchedulerProperties setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
        return this;
    }
}
