package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/userInfos")
public class UserInfoController {
    @Autowired
    private IUserInfoService userInfoService;

    @GetMapping("/get/{id}")
    public UserInfo findByid(@PathVariable("id") Long id) {
        return userInfoService.getById(id);
    }

    @GetMapping("/checkPhone")
    public JsonResult checkPhone(String phone) {
        boolean ret = userInfoService.checkPhone(phone);
        return JsonResult.success(ret);
    }

    @GetMapping("/sendVerifyCode")
    public JsonResult sendVerifyCode(String phone) {
        userInfoService.sendVerifyCode(phone);
        return JsonResult.success();
    }

    @PostMapping("/regist")
    public JsonResult regist(String nickname,String password,String rpassword,String phone,String verifyCode) {
        userInfoService.regist(nickname,password,rpassword,phone,verifyCode);
        return JsonResult.success();
    }
}
