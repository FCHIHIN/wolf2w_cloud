package cn.wolfcode.wolf2w.data.job;


import cn.wolfcode.wolf2w.article.domain.StrategyCondition;
import cn.wolfcode.wolf2w.data.feign.IStrategyConditionFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 攻略条件列表定期任务-定时器
 */
@Component
public class StartegyConditionDataJob {


    @Autowired
    private IStrategyConditionFeignService strategyConditionFeignService;


    @Scheduled(cron = "0/5 * * * * ? ")
    public void doWork(){
        System.out.println("--------------------维护攻略条件列表--begin------------------------>");
        strategyConditionFeignService.conditionDataHandler(StrategyCondition.TYPE_THEME);      //主题
        strategyConditionFeignService.conditionDataHandler(StrategyCondition.TYPE_ABROAD);   //国外
        strategyConditionFeignService.conditionDataHandler(StrategyCondition.TYPE_CHINA);    //国内
        System.out.println("--------------------维护攻略条件列表--end------------------------>");
    }
}
