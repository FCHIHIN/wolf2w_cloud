package cn.wolfcode.wolf2w.article.controller;

import cn.wolfcode.wolf2w.article.domain.StrategyTheme;
import cn.wolfcode.wolf2w.article.query.StrategyThemeQuery;
import cn.wolfcode.wolf2w.article.service.IStrategyThemeService;
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
* 攻略主题控制层
*/
@RestController
@RequestMapping("strategyThemes")
public class StrategyThemeController {

    @Autowired
    private IStrategyThemeService strategyThemeService;

    @GetMapping("/query")
    public JsonResult query(StrategyThemeQuery qo){
        IPage<StrategyTheme> page = strategyThemeService.queryPage(qo);
        return  JsonResult.success(page);
    }

    @GetMapping("/list")
    public JsonResult list(){
        return  JsonResult.success(strategyThemeService.list());
    }

    @GetMapping("/detail")
    public JsonResult detail(Long id){
        return  JsonResult.success(strategyThemeService.getById(id));
    }

    @PostMapping("/save")
    public JsonResult save(StrategyTheme strategyTheme){
        strategyThemeService.save(strategyTheme);
        return  JsonResult.success();
    }

    @PostMapping("/update")
    public JsonResult update(StrategyTheme strategyTheme){
        strategyThemeService.updateById(strategyTheme);
        return  JsonResult.success();
    }
    @PostMapping("/delete/{id}")
    public JsonResult delete(@PathVariable Long id){
        strategyThemeService.removeById(id);
        return  JsonResult.success();
    }

}
