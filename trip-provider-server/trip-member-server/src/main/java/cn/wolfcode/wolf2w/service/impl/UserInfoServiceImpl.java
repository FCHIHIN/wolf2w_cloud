package cn.wolfcode.wolf2w.service.impl;

import cn.wolfcode.wolf2w.common.exception.LogicException;
import cn.wolfcode.wolf2w.common.redis.service.RedisService;
import cn.wolfcode.wolf2w.common.redis.util.RedisKeys;
import cn.wolfcode.wolf2w.common.util.AssertUtil;
import cn.wolfcode.wolf2w.common.util.Consts;
import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.mapper.UserInfoMapper;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements IUserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public boolean checkPhone(String phoneNum) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",phoneNum);
        UserInfo userInfo = userInfoMapper.selectOne(wrapper);
        return userInfo !=null;

    }



    @Override
    public void sendVerifyCode(String phone) {
//        1. 构造验证码
        String code = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4);
//        2. 拼接短信
        StringBuffer stringBuffer = new StringBuffer(80);
        stringBuffer.append("您的注册验证码是:").append(code).append(",请在")
                .append(Consts.VERIFY_CODE_VAI_TIME).append("之内使用");
        // 模拟发送短信
        System.out.println(stringBuffer.toString());
//        3. 缓存验证码

//            使用一个工具类：RestTemplate
        RestTemplate template = new RestTemplate();
        String ret = template.getForObject("https://way.jd.com/chuangxin/cxchangsms?mobile="+phone+"&content=【创信】亲，包包已插上幸福的翅膀奔向您！单号："+code+"登录手机客户端随时关注包包行程哟！&appkey=49eed8e99cd9b3fdbaf8474cff47d2c6" ,String.class);

        System.err.println(ret);
        if(!ret.contains("Success")){
            throw new LogicException("短信发送失败");
        }
//        4.唯一,可读,灵活,时效--方便后续管理
        //缓存验证码
        //参数1：key，参数2：value，参数3：有效时间， 参数4：时间单位
        String key = RedisKeys.VERIFY_CODE.join(phone);  //唯一，可读，灵活，时效
        redisService.setCacheObject(key, code, RedisKeys.VERIFY_CODE.getTime(), TimeUnit.SECONDS);
    }

    @Override
    public void regist(String nickname, String password, String rpassword, String phone, String verifyCode) {
        // 判空参数
        AssertUtil.hasText(nickname,"昵称不能为空");
        AssertUtil.hasText(password,"密码不能为空");
        AssertUtil.hasText(rpassword,"确认不能为空");
        AssertUtil.hasText(phone,"手机号码不能为空");
        AssertUtil.hasText(verifyCode,"验证码不能为空");
        // 手机号格式
        AssertUtil.isPhoneNum(phone,"手机号码格式不正确");
        // 密码一致
        AssertUtil.isEquals(password,rpassword,"密码不一致");
        // 手机号码是否唯一
        if (this.checkPhone(phone)) {
            throw new LogicException("手机号码已注册");
        }
        //判断验证码是否一致
        String key =  RedisKeys.VERIFY_CODE.join(phone);
        String code = redisService.getCacheObject(key);
        if(!verifyCode.equalsIgnoreCase(code)){
            throw new LogicException("验证码失效或者不正确");
        }

        //实现注册
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname(nickname);
        userInfo.setPhone(phone);
        userInfo.setPassword(password); //假装已经加密
        userInfo.setHeadImgUrl("/images/default.jpg");

        //重要属性自己控制
        userInfo.setState(UserInfo.STATE_NORMAL);

        super.save(userInfo);
    }


}
