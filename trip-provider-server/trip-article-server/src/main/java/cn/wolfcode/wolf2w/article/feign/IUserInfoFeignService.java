package cn.wolfcode.wolf2w.article.feign;

import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import cn.wolfcode.wolf2w.domain.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "trip-member-server")
public interface IUserInfoFeignService {

    @GetMapping("/userInfos/detail")
    JsonResult<UserInfo> detail(@RequestParam("id") Long id);
}
