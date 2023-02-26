package cn.wolfcode.wolf2w.data.feign;

import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("trip-article-server")
public interface IStrategyFeignService {
    // 调用远程初始化
    @PostMapping("/strategies/statisDataInit")
    JsonResult statisDataInit();

    //调用远程article服务--攻略数据持久化方法
    @PostMapping("/strategies/statisDataPersistence")
    JsonResult statisDataPersistence();
}

