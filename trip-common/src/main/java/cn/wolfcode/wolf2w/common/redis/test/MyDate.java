package cn.wolfcode.wolf2w.common.redis.test;

import lombok.Getter;
import lombok.Setter;

/**
 * 枚举类最大特点
 * 1>枚举类构造器是私有的
 * 2>枚举类在定义完成之后，实例个数固定
 * 3>剩下操作基本跟普通类一样
 *
 */
@Getter
public enum  MyDate {
    //DATE1,DATE2;
    DATE1(1L, "date1"),DATE2(2L, "date2");
    @Setter
    private Long time;
    @Setter
    private String prefix;

    private MyDate(Long time, String prefix){
        this.time = time;
        this.prefix = prefix;
    }

    public String join(String value){
        return this.prefix + ":" +value;
    }
}
