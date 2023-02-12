package cn.wolfcode.wolf2w.service.impl;

import cn.wolfcode.wolf2w.common.exception.LogicException;
import cn.wolfcode.wolf2w.common.redis.service.RedisService;
import cn.wolfcode.wolf2w.common.redis.util.RedisKeys;
import cn.wolfcode.wolf2w.common.util.AssertUtil;
import cn.wolfcode.wolf2w.common.util.Consts;
import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.mapper.UserInfoMapper;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.imageio.plugins.tiff.TIFFField;
import java.util.HashMap;
import java.util.Map;
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

    @Override
    public Map<String, Object> login(String username, String password) {
        // 判空参数
        AssertUtil.hasText(username,"账号能为空");
        AssertUtil.hasText(password,"密码不能为空");
        // 判断账号不存在
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",username);
        UserInfo user1 = userInfoMapper.selectOne(wrapper);
        if(user1 == null) {
            throw new LogicException("账号不存在");
        }

        // 去数据库查user
        QueryWrapper<UserInfo> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("phone",username);
        wrapper1.eq("password",password);
        UserInfo user = userInfoMapper.selectOne(wrapper1);
        // 判断用户状态

        // 5分钟1判断
        if (redisService.hasKey("freezetime")){
            UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("phone",username);
            updateWrapper.set("state",UserInfo.STATE_NORMAL);
            userInfoMapper.update(null,wrapper);
        }

        // 当用户登录失败次数超过5次，暂时冻结5分钟，5分钟之后可以重新登录
        if (user == null) {
            Long times = redisService.incrementCacheObjectValue("username");
            if (times == 5) {
                UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("phone",username);
                updateWrapper.set("state",UserInfo.STATE_DISABLE);
                userInfoMapper.update(null,wrapper);
                // 存5分钟冻结时间
                redisService.setCacheObject("freezetime",1,RedisKeys.VERIFY_CODE.getTime(),TimeUnit.SECONDS);
                throw new LogicException("用户登录失败次数超过5次，暂时冻结5分钟，5分钟之后可以重新登录");
                //当用户又登录失败5次(10次), 永久冻结
            } else if (times == 10) {
                UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("phone",username);
                updateWrapper.set("state",UserInfo.STATE_DISABLE);
                userInfoMapper.update(null,wrapper);
                redisService.deleteObject("username");
                throw new LogicException("账号永久冻结");
            }
            else {
                throw new LogicException("账号或密码错误");
            }
        }




        // 拼接key
        String token = UUID.randomUUID().toString().replaceAll("-","");
        String key = RedisKeys.USER_LOGIN_TOKEN.join(token);
        // value(用Json)
        String value = JSON.toJSONString(user);
        // 缓存redis
        redisService.setCacheObject(key,value,RedisKeys.USER_LOGIN_TOKEN.getTime(),TimeUnit.SECONDS);
        HashMap<String, Object> map = new HashMap<>();
        map.put("token",token);
        map.put("user",user);
        return map;
    }

    @Override
    public UserInfo queryByToken(String token) {
        // 判空
        AssertUtil.hasText(token,"参数异常");
        // 拼接key
        String key = RedisKeys.USER_LOGIN_TOKEN.join(token);
        // 看看有没有key
        if (redisService.hasKey(key)) {
            // 拿出user
            String strUser = redisService.getCacheObject(key);
            UserInfo user = JSON.parseObject(strUser,UserInfo.class);
            // 重置时间
            redisService.expire(key,RedisKeys.USER_LOGIN_TOKEN.getTime(),TimeUnit.SECONDS);
            return user;
        }
        return null;

    }


}
