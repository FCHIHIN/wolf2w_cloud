package cn.wolfcode.wolf2w.article.controller;

import cn.wolfcode.wolf2w.article.domain.Question;
import cn.wolfcode.wolf2w.article.query.QuestionQuery;
import cn.wolfcode.wolf2w.article.service.IQuestionService;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* 问题控制层
*/
@RestController
@RequestMapping("questions")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    @GetMapping("/query")
    public JsonResult query(QuestionQuery qo){
        IPage<Question> page = questionService.queryPage(qo);
        return  JsonResult.success(page);
    }

    @GetMapping("/list")
    public JsonResult list(){
        return  JsonResult.success(questionService.list());
    }

    @GetMapping("/detail")
    public JsonResult detail(Long id){
        return  JsonResult.success(questionService.getById(id));
    }

    @PostMapping("/save")
    public JsonResult save(Question question){
        questionService.save(question);
        return  JsonResult.success();
    }

    @PostMapping("/update")
    public JsonResult update(Question question){
        questionService.updateById(question);
        return  JsonResult.success();
    }
    @PostMapping("/delete/{id}")
    public JsonResult delete(@PathVariable Long id){
        questionService.removeById(id);
        return  JsonResult.success();
    }

    @GetMapping("/get")
    public JsonResult getAnswer(Long id){
        Question question = questionService.getAnswer(id);
        return  JsonResult.success(question);
    }

}
