package cn.wolfcode.wolf2w.service;

import cn.wolfcode.wolf2w.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserInfoService extends IService<UserInfo> {

    boolean checkPhone(String phoneNum);

    void sendVerifyCode(String phone);


    void regist(String nickname, String password, String rpassword, String phone, String verifyCode);
}

