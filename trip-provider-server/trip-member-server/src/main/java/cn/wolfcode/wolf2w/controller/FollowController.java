package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.common.security.annotation.UserParam;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/follows")
public class FollowController {

    @Autowired
    private IUserInfoService userInfoService;

    @GetMapping("/detail")
    public JsonResult detail(Long userId) {
        return JsonResult.success(userInfoService.getById(userId));
    }

    @PostMapping("/statisDataInit")
    public JsonResult statisDataInit() {
        userInfoService.statisDataInit();
        return JsonResult.success();
    }

    @PostMapping("/follow")
    public JsonResult follow(Long userId, @UserParam UserInfo userInfo) {
        Map<String, Object> map = userInfoService.follow(userId, userInfo.getId());
        return JsonResult.success(map);
    }

    @GetMapping("/getdata")
    public JsonResult getdata(Long userId,@UserParam UserInfo userInfo) {
        Map<String, Object> map = userInfoService.getdata(userId, userInfo.getId());
        return JsonResult.success(map);
    }

    @GetMapping("/list")
    public JsonResult follows(Long userId,@UserParam UserInfo userInfo) {
        List<UserInfo> list = userInfoService.follows(userId, userInfo.getId());
        return JsonResult.success(list);
    }
}
