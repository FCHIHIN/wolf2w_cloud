package cn.wolfcode.wolf2w.article.service.impl;

import cn.wolfcode.wolf2w.article.domain.AnswerContent;
import cn.wolfcode.wolf2w.article.domain.Question;
import cn.wolfcode.wolf2w.article.mapper.AnswerContentMapper;
import cn.wolfcode.wolf2w.article.mapper.QuestionMapper;
import cn.wolfcode.wolf2w.article.query.QuestionQuery;
import cn.wolfcode.wolf2w.article.service.IQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 问题服务接口实现
*/
@Service
@Transactional
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper,Question> implements IQuestionService  {

    @Autowired
    private AnswerContentMapper answerContentMapper;
    @Override
    public IPage<Question> queryPage(QuestionQuery qo) {
        IPage<Question> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Question> wrapper = Wrappers.<Question>query();
        return super.page(page, wrapper);
    }

    @Override
    public Question getAnswer(Long id) {
        AnswerContent content = answerContentMapper.selectById(id);
        Question question = super.getById(id);
        question.setContent(content);
        return question;
    }
}
