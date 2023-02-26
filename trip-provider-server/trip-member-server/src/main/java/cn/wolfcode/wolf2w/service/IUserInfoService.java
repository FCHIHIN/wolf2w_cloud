package cn.wolfcode.wolf2w.service;

import cn.wolfcode.wolf2w.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface IUserInfoService extends IService<UserInfo> {

    boolean checkPhone(String phoneNum);

    void sendVerifyCode(String phone);


    void regist(String nickname, String password, String rpassword, String phone, String verifyCode);

    Map<String, Object> login(String username, String password);

    UserInfo queryByToken(String token);

    List<UserInfo> queryCity(String city);
}

