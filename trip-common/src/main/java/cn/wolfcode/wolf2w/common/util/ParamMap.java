package cn.wolfcode.wolf2w.common.util;

import java.util.HashMap;

public class ParamMap extends HashMap<String, Object> {
    @Override
    public ParamMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }
    public static ParamMap newInstance(){
        return new ParamMap();
    }
}
