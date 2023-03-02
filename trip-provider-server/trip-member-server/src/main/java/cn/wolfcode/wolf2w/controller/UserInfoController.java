package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.common.redis.service.RedisService;
import cn.wolfcode.wolf2w.common.redis.util.RedisKeys;
import cn.wolfcode.wolf2w.common.security.annotation.RequireLogin;
import cn.wolfcode.wolf2w.common.security.annotation.UserParam;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import com.alibaba.fastjson2.JSON;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/userInfos")
public class UserInfoController {
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private RedisService redisService;

    @RequireLogin
    @GetMapping("/get/{id}")
    public UserInfo findByid(@PathVariable("id") Long id) {
        return userInfoService.getById(id);
    }

    @GetMapping("/checkPhone")
    public JsonResult checkPhone(String phone) {
        boolean ret = userInfoService.checkPhone(phone);
        return JsonResult.success(ret);
    }

    @GetMapping("/detail")
    public JsonResult<UserInfo> detail(Long userId ) {
        return JsonResult.success(userInfoService.getById(userId));
    }

    @GetMapping("/sendVerifyCode")
    public JsonResult sendVerifyCode(String phone) {
        userInfoService.sendVerifyCode(phone);
        return JsonResult.success();
    }

    @PostMapping("/regist")
    public JsonResult regist(String nickname, String password, String rpassword, String phone, String verifyCode) {
        userInfoService.regist(nickname, password, rpassword, phone, verifyCode);
        return JsonResult.success();
    }

    @RequireLogin
    @PostMapping("/login")
    public JsonResult<Map<String, Object>> login(String username, String password) {
        Map<String, Object> map = userInfoService.login(username, password);
        return JsonResult.success(map);
    }

    @RequireLogin
    @GetMapping("/currentUser")
    public JsonResult currentUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfo user = userInfoService.queryByToken(token);
        return JsonResult.success(user);
    }

    @GetMapping("/list")
    public JsonResult list() {
        return JsonResult.success(userInfoService.list());
    }

    @GetMapping("/queryCity")
    public JsonResult<List<UserInfo>> queryCity(String city) {
        return JsonResult.success(userInfoService.queryCity(city));
    }

}
