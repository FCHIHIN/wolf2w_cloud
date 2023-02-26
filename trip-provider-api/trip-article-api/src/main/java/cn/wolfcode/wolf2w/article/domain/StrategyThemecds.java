package cn.wolfcode.wolf2w.article.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@TableName("strategy_themecds")
public class StrategyThemecds {
    @TableId(type= IdType.AUTO)
    private Long id;

    private Long refid; //主题id
    private String name;//主题名字
    private Integer count; //主题下攻略个数
    private String destids;//攻略对应的目的地id
    private String destnames;//攻略对应的目的地名字

}
