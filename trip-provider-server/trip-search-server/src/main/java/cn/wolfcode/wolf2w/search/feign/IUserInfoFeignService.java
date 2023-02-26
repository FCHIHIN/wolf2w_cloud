package cn.wolfcode.wolf2w.search.feign;

import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import cn.wolfcode.wolf2w.domain.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="trip-member-server")
public interface IUserInfoFeignService {

    @GetMapping("/userInfos/list")
    JsonResult<List<UserInfo>> list();
    @GetMapping("/userInfos/queryCity")
    JsonResult<List<UserInfo>> queryCity(@RequestParam("city") String city);
    @GetMapping("/userInfos/detail")
    JsonResult<UserInfo> detail(@RequestParam("id") Long id);
}
