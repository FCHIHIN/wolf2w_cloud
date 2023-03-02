package cn.wolfcode.wolf2w.data.feign;

import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="trip-member-server")
public interface IMemberFeignService {

    // 调用远程初始化
    @PostMapping("/follows/statisDataInit")
    JsonResult statisDataInit();
}
