package cn.wolfcode.wolf2w.service.impl;

import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.mapper.UserInfoMapper;
import cn.wolfcode.wolf2w.service.IUserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements IUserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public boolean checkPhone(String phoneNum) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",phoneNum);
        UserInfo userInfo = userInfoMapper.selectOne(wrapper);
        return userInfo !=null;

    }
}
