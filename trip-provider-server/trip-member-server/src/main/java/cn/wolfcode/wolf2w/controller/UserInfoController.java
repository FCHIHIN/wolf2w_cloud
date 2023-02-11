package cn.wolfcode.wolf2w.controller;

import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/userInfos")
public class UserInfoController {
    @Autowired
    private IUserInfoService userInfoService;

    @ResponseBody
    @GetMapping("/get/{id}")
    public UserInfo findByid(@PathVariable("id") Long id) {
        return userInfoService.getById(id);
    }

    @ResponseBody
    @GetMapping("/checkPhone")
    public JsonResult checkPhone(String phone) {
        boolean ret = userInfoService.checkPhone(phone);
        return JsonResult.success(ret);
    }


}
