package cn.wolfcode.wolf2w.common.redis.test;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MyDate2 {
    public static final MyDate2 DATE1 = new MyDate2(1L, "date1");
    public static final MyDate2 DATE2 = new MyDate2(2L, "date2");
    @Setter
    private Long time;
    @Setter
    private String prefix;

    private MyDate2(Long time, String prefix){
        this.time = time;
        this.prefix = prefix;
    }

    public String join(String value){
        return this.prefix + ":" +value;
    }
}
