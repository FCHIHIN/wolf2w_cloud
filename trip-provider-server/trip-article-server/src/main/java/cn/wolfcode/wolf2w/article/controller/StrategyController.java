package cn.wolfcode.wolf2w.article.controller;

import cn.wolfcode.wolf2w.article.domain.Strategy;
import cn.wolfcode.wolf2w.article.query.StrategyQuery;
import cn.wolfcode.wolf2w.article.service.IStrategyService;
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
* 攻略控制层
*/
@RestController
@RequestMapping("strategys")
public class StrategyController {

    @Autowired
    private IStrategyService strategyService;

    @GetMapping("/query")
    public JsonResult query(StrategyQuery qo){
        IPage<Strategy> page = strategyService.queryPage(qo);
        return  JsonResult.success(page);
    }

    @GetMapping("/list")
    public JsonResult list(){
        return  JsonResult.success(strategyService.list());
    }

    @GetMapping("/detail")
    public JsonResult detail(Long id){
        return  JsonResult.success(strategyService.getById(id));
    }

    @PostMapping("/save")
    public JsonResult save(Strategy strategy){
        strategyService.save(strategy);
        return  JsonResult.success();
    }

    @PostMapping("/update")
    public JsonResult update(Strategy strategy){
        strategyService.updateById(strategy);
        return  JsonResult.success();
    }
    @PostMapping("/delete/{id}")
    public JsonResult delete(@PathVariable Long id){
        strategyService.removeById(id);
        return  JsonResult.success();
    }

}
