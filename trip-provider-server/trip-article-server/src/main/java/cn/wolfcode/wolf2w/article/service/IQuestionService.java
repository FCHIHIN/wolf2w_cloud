package cn.wolfcode.wolf2w.article.service;

import cn.wolfcode.wolf2w.article.domain.Question;
import cn.wolfcode.wolf2w.article.query.QuestionQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 问题服务接口
 */
public interface IQuestionService extends IService<Question>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<Question> queryPage(QuestionQuery qo);

    Question getAnswer(Long id);
}
