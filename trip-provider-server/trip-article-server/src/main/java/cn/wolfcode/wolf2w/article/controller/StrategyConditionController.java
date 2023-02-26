package cn.wolfcode.wolf2w.article.controller;

import cn.wolfcode.wolf2w.article.domain.StrategyCondition;
import cn.wolfcode.wolf2w.article.query.StrategyConditionQuery;
import cn.wolfcode.wolf2w.article.service.IStrategyConditionService;
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
* 攻略条件统计表控制层
*/
@RestController
@RequestMapping("strategyConditions")
public class StrategyConditionController {

    @Autowired
    private IStrategyConditionService strategyConditionService;

    @GetMapping("/query")
    public JsonResult query(StrategyConditionQuery qo){
        IPage<StrategyCondition> page = strategyConditionService.queryPage(qo);
        return  JsonResult.success(page);
    }

    @GetMapping("/list")
    public JsonResult list(){
        return  JsonResult.success(strategyConditionService.list());
    }

    @GetMapping("/detail")
    public JsonResult detail(Long id){
        return  JsonResult.success(strategyConditionService.getById(id));
    }

    @PostMapping("/save")
    public JsonResult save(StrategyCondition strategyCondition){
        strategyConditionService.save(strategyCondition);
        return  JsonResult.success();
    }

    @PostMapping("/update")
    public JsonResult update(StrategyCondition strategyCondition){
        strategyConditionService.updateById(strategyCondition);
        return  JsonResult.success();
    }
    @PostMapping("/delete/{id}")
    public JsonResult delete(@PathVariable Long id){
        strategyConditionService.removeById(id);
        return  JsonResult.success();
    }

    @GetMapping("/condition")
    public JsonResult condition(Integer type){
        List<StrategyCondition> list = strategyConditionService.queryCondition(type);
        return  JsonResult.success(list);
    }

    @PostMapping("/conditionDataHandler")
    public JsonResult conditionDataHandler(int type){
        strategyConditionService.conditionDataHandler(type);
        return  JsonResult.success();
    }

}
