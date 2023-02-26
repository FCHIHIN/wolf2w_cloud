package cn.wolfcode.wolf2w.data.listener;

import cn.wolfcode.wolf2w.data.feign.IStrategyFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
public class RedisDataInitListener implements ApplicationListener<ContextStartedEvent> {
    //容器调用refresh方法执行下面逻辑
    @Autowired
    private IStrategyFeignService strategyFeignService;

    //spring容器初始化成功后直接调用
    @Override
    public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
        System.out.println("---------------攻略统计数hash缓存到redis--begin-------------------------");
        //1:查询攻略表得到所有攻略数据
        //2:遍历所有攻略， 将攻略统计数据缓存到redis中
        strategyFeignService.statisDataInit();
        System.out.println("---------------攻略统计数hash缓存到redis--end-------------------------");
    }
}
