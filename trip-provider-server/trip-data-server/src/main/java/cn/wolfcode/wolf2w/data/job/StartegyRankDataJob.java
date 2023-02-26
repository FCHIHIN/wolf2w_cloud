package cn.wolfcode.wolf2w.data.job;


import cn.wolfcode.wolf2w.article.domain.StrategyRank;
import cn.wolfcode.wolf2w.data.feign.IStrategyRankFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 攻略推荐排行定期任务-定时器
 */
@Component
public class StartegyRankDataJob {

    @Autowired
    private IStrategyRankFeignService strategyRankFeignService;

    /**
     *
     * cron表达式详解
     * 　　Cron表达式是一个字符串，字符串以5或6个空格隔开，分为6或7个域，每一个域代表一个含义，Cron有如下两种语法格式：
     *
     * 　　（1） Seconds Minutes Hours DayofMonth Month DayofWeek Year
     *            秒      分     时      几号       月     周几     年
     *
     * 　　（2）Seconds Minutes Hours DayofMonth Month DayofWeek   --- spring支持这种
     *            秒      分     时      几号       月     周几
     *
     *            0       0      2       1         *       ?       *   表示在每月的1日的凌晨2点调整任务
     *            0       15     10      ?         *       MON-FRI      表示周一到周五每天上午10:15执行作业
     *
     */
    //任务调度注解
    //cron： 定制任务计划(啥时候执行定时任务的时间)
    @Scheduled(cron = "0/5 * * * * ? ")
    public void doWork(){
        System.out.println("--------------------维护攻略推荐排行--begin------------------------>");
        strategyRankFeignService.rankDataHandler(StrategyRank.TYPE_HOT);      //热门
        strategyRankFeignService.rankDataHandler(StrategyRank.TYPE_ABROAD);   //国外
        strategyRankFeignService.rankDataHandler(StrategyRank.TYPE_CHINA);    //国内
        System.out.println("--------------------维护攻略推荐排行--end------------------------>");
    }
}
