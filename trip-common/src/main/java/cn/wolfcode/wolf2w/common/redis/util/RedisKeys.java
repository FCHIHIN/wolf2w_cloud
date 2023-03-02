package cn.wolfcode.wolf2w.common.redis.util;

import cn.wolfcode.wolf2w.common.util.Consts;
import lombok.Getter;

/**
 * Rediskey 的管理类
 * 约定：redis 每个key 都跟枚举类实例一一对应
 *
 */
@Getter
public enum RedisKeys {
    // 关注数统计
    USER_FOLLOW_HASH("user_follow_hash",-1L),
    // 用户关注标记key
    USER_FOLLOW("user_follow",-1L),
    //攻略点赞标记key  实例对象
    STRATEGY_THUMB("strategy_thumb", -1L),  //约定暂时没有设置有效时间
    //用户攻略收藏列表存 key  实例对象
    USER_STRATEGY_FAVOR("user_strategy_favor", -1L),  //约定暂时没有设置有效时间
    //攻略统计缓存 key  实例对象
    STRATEGY_STATIS_HASH("strategy_statis_hash", -1L),  //约定暂时没有设置有效时间
    //用户登录的缓存 key  实例对象
    USER_LOGIN_TOKEN("user_login_token", Consts.USER_INFO_TOKEN_VAI_TIME * 60L),
    //注册短信验证码缓存 key  实例对象
    VERIFY_CODE("verify_code", Consts.VERIFY_CODE_VAI_TIME * 60L);

    private Long time;      //redis key 的有效时间，单位s
    private String prefix;  //redis key 前缀

    private RedisKeys(String prefix, Long time){
        this.time = time;
        this.prefix = prefix;
    }
    //拼接出真实key
    public String join(String... values){
        StringBuilder sb = new StringBuilder(80);
        sb.append(this.prefix);

        for (String value : values) {
            sb.append(":").append(value);
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        //key --> verify_code:137000000000:xxx:yyy:zzz
        String key = RedisKeys.VERIFY_CODE.join("13700000000", "xxx", "yyy", "zzz");
        System.out.println(key);

    }
}
