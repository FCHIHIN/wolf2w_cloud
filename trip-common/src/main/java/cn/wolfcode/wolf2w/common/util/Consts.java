package cn.wolfcode.wolf2w.common.util;

/**
 * 系统常量
 */
public class Consts {

    //验证码有效时间
    public static final int VERIFY_CODE_VAI_TIME = 5;  //单位分

    //token有效时间
    public static final int USER_INFO_TOKEN_VAI_TIME = 30;  //单位分




    public static final String VERIFY_CODE = "verify_code";

    public static String getSplit(){
        return ":";
    }


    //token_name
    public static final String TOKEN_NAME = "token";

    //请求客户端真实ip
    public static final String REAL_IP = "X-REAL-IP";

    //请求来源请求头name
    public static final String REQUEST_ORIGIN_KEY = "request_origin";

    //请求来源请求头--网关
    public static final String REQUEST_ORIGIN_GATEWAY = "gateway";
    //请求来源请求头--feign接口
    public static final String REQUEST_ORIGIN_FEIGN = "feign";


}
