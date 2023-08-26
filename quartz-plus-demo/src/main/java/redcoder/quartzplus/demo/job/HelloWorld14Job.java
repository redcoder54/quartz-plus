package redcoder.quartzplus.demo.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import redcoder.quartzplus.core.annotation.QuartzJob;
import redcoder.quartzplus.core.annotation.QuartzTrigger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@QuartzJob(description = "在控制台打印hello world 14, 每隔1分钟执行一次")
@QuartzTrigger(cron = "0 0/1 * * * ?")
public class HelloWorld14Job implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("hello world 14, current time: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
