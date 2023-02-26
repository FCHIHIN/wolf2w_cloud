package cn.wolfcode.wolf2w.common.redis.util;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * VO对象转换工具类
 */
public class VOConvertUtil {

    /**
     * 将对象装换成Map集合
     * 转换规则是object 对象内省机制
     * @param vo  要转换对象
     * @param fields 要转换的字段
     * @return map集合
     */
    public static Map<String, Object> object2Map(Object vo, String... fields){
        HashMap<String, Object> map  = new HashMap<>();
        try {
            //1、通过突破口（核心类Introspector）获取javaBean描述对象BeanInfo
            BeanInfo beanInfo = Introspector.getBeanInfo(vo.getClass(), Object.class);
            //2、获取javaBean中的属性描述器
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                String name = pd.getName();
                for (String field : fields) {
                    if(name.equals(field)){
                        Method readMethod = pd.getReadMethod();
                        Object value = readMethod.invoke(vo);
                        if(value != null){
                            map.put(name, value);
                        }
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
