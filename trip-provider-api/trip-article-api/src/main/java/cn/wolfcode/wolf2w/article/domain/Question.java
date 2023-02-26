package cn.wolfcode.wolf2w.article.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 攻略
 */
@Setter
@Getter
@TableName("question")
public class Question {
    @TableId(type= IdType.AUTO)
    private Long id;


    private Long destId;  //关联的目的地
    private String destName;
    private String title;  //标题
    private String summary;  //内容摘要
    private Integer sharenum; //分享数
    private Integer answernum;//回答数
    private Integer viewnum;  //点击数
    private Date createTime;  //创建时间



    @TableField(exist = false)
    private AnswerContent content; //内容
}
