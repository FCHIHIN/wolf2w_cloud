package cn.wolfcode.wolf2w.common.redis.test;

public class EnumTest {

    public static void main(String[] args) {
        MyDate md = MyDate.DATE1;
        md.setPrefix("xxx");
        System.out.println(md.getPrefix());
        System.out.println(md.join("xxxx"));
        System.out.println("----------------");
        MyDate2 clMd = MyDate2.DATE1;
        clMd.setPrefix("xxx");
        System.out.println(clMd.getPrefix());
        System.out.println(clMd.join("xxxx"));
    }
}
