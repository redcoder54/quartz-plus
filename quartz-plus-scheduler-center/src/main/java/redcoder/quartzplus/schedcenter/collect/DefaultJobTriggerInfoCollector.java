package redcoder.quartzplus.schedcenter.collect;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redcoder.quartzplus.common.utils.HttpTemplate;
import redcoder.quartzplus.core.core.dto.QuartzJobTriggerInfo;
import redcoder.quartzplus.schedcenter.dto.ApiResult;
import redcoder.quartzplus.schedcenter.entity.QuartzSchedulerJobTriggerInfo;
import redcoder.quartzplus.schedcenter.repository.InstanceRepository;
import redcoder.quartzplus.schedcenter.repository.JobTriggerInfoRepository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static redcoder.quartzplus.schedcenter.constant.QuartzApiConstants.JOB_TRIGGER_INFO_LIST;


/**
 * 负责采集job和trigger信息的采集器，对于集群模式的quartz实例，随机选择其中一个实例，进行信息采集。
 *
 * @author redcoder54
 */
@Slf4j
@Component
public class DefaultJobTriggerInfoCollector implements JobTriggerInfoCollector {

    @Resource
    private JobTriggerInfoRepository jobTriggerInfoRepository;
    @Resource
    private InstanceRepository instanceRepository;
    @Resource
    private CollectingErrorHandlerChain errorHandlerChain;

    @Override
    public void collect() {
        Set<String> handledScheduler = new HashSet<>();
        instanceRepository.findAll().forEach(instance -> {
            String schedName = instance.getSchedName();
            if (!handledScheduler.contains(schedName)) {
                try {
                    doCollect(instance.getSchedName(), instance.getInstanceHost(), instance.getInstancePort());
                    handledScheduler.add(schedName);
                } catch (Exception e) {
                    errorHandlerChain.handle(instance, e);
                }
            }
        });
    }

    @Override
    public void collect(String schedName, String host, int port) {
        try {
            doCollect(schedName, host, port);
        } catch (Exception e) {
            log.error("collecting error", e);
        }
    }

    private void doCollect(String schedName, String host, int port) {
        List<QuartzJobTriggerInfo> jobTriggerInfos = getJobTriggerInfos(host, port);
        clearThenInsert(schedName, jobTriggerInfos);
    }

    private List<QuartzJobTriggerInfo> getJobTriggerInfos(String host, int port) {
        String url = "http://" + host + ":" + port + JOB_TRIGGER_INFO_LIST;
        ApiResult<List<QuartzJobTriggerInfo>> result = HttpTemplate.doGet(url, new TypeReference<ApiResult<List<QuartzJobTriggerInfo>>>() {
        });
        if (result.getStatus() != 0) {
            log.error("获取QuartzJobTriggerInfo失败，原因：{}", result.getMessage());
            return Collections.emptyList();
        }
        return result.getData();
    }

    /**
     * 删除指定的schedName数据，然后再插入
     *
     * @param jobTriggerInfos job和trigger数据
     */
    private void clearThenInsert(String schedName, List<QuartzJobTriggerInfo> jobTriggerInfos) {
        if (jobTriggerInfos.isEmpty()) {
            return;
        }
        // delete
        jobTriggerInfoRepository.deleteBySchedName(schedName);

        // insert
        for (QuartzJobTriggerInfo dto : jobTriggerInfos) {
            QuartzSchedulerJobTriggerInfo info = QuartzSchedulerJobTriggerInfo.valueOf(dto);
            jobTriggerInfoRepository.save(info);
        }
    }
}
