package cn.wolfcode.wolf2w.article.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@TableName("answer_content")
public class AnswerContent {
    private Long id;
    private String content;
}
