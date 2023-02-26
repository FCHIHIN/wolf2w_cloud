package cn.wolfcode.wolf2w.article.query;


import cn.wolfcode.wolf2w.common.web.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

/**
* 游记查询参数封装对象
*/
@Setter
@Getter
public class TravelQuery extends  QueryObject{
    private Long destId;
    private int dayType;
    private int travelTimeType;
    private int consumeType;
    private String orderBy;
}
