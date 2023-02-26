package cn.wolfcode.wolf2w.article.controller;

import cn.wolfcode.wolf2w.article.domain.Strategy;
import cn.wolfcode.wolf2w.article.domain.StrategyRank;
import cn.wolfcode.wolf2w.article.query.StrategyRankQuery;
import cn.wolfcode.wolf2w.article.service.IStrategyRankService;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
* 攻略统计表控制层
*/
@RestController
@RequestMapping("strategyRanks")
public class StrategyRankController {

    @Autowired
    private IStrategyRankService strategyRankService;

    @GetMapping("/query")
    public JsonResult query(StrategyRankQuery qo){
        IPage<StrategyRank> page = strategyRankService.queryPage(qo);
        return  JsonResult.success(page);
    }

    @GetMapping("/list")
    public JsonResult list(){
        return  JsonResult.success(strategyRankService.list());
    }

    @GetMapping("/detail")
    public JsonResult detail(Long id){
        return  JsonResult.success(strategyRankService.getById(id));
    }

    @PostMapping("/save")
    public JsonResult save(StrategyRank strategyRank){
        strategyRankService.save(strategyRank);
        return  JsonResult.success();
    }

    @PostMapping("/update")
    public JsonResult update(StrategyRank strategyRank){
        strategyRankService.updateById(strategyRank);
        return  JsonResult.success();
    }
    @PostMapping("/delete/{id}")
    public JsonResult delete(@PathVariable Long id){
        strategyRankService.removeById(id);
        return  JsonResult.success();
    }

    @GetMapping("/rank")
    public JsonResult rank(Integer type){
        List<StrategyRank> list = strategyRankService.queryRank(type);
        return  JsonResult.success(list);
    }

    @PostMapping("/rankDataHandler")
    public JsonResult rankDataHandler(int type){

        strategyRankService.rankDataHandler(type);

        return  JsonResult.success();
    }
}
