package cn.wolfcode.wolf2w.common.util;

import cn.wolfcode.wolf2w.common.exception.LogicException;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * 参数断言工具类--后续需要自己控制抛出异常
 */
public class AssertUtil {

    /**
     * 判断指定text是否为null/""
     * @param text
     * @param message
     */
    public static void hasText(@Nullable String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new LogicException(message);
        }
    }

    /**
     * 比较v1， v2是否一致
     * @param v1
     * @param v2
     * @param msg
     */
    public static void isEquals(String v1, String v2, String msg) {

        if(v1 == null || v2 == null){
            throw new RuntimeException("参数不能为null");
        }

        if(!v1.equals(v2)){
            throw new LogicException(msg);
        }


    }

    /**
     * 手机号码格式
     * @param phoneNum
     * @param msg
     */
    public static void isPhoneNum(String phoneNum,String msg) {
        if (phoneNum == null) {
            throw new RuntimeException("参数不能为null");
        }
        if (!PhoneUtil.isMobileNumber(phoneNum)) {
            throw new LogicException(msg);
        }
    }

}
