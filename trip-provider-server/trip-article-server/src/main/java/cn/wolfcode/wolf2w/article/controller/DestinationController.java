package cn.wolfcode.wolf2w.article.controller;

import cn.wolfcode.wolf2w.article.domain.*;
import cn.wolfcode.wolf2w.article.query.DestinationQuery;
import cn.wolfcode.wolf2w.article.query.RegionQuery;
import cn.wolfcode.wolf2w.article.service.IDestinationService;
import cn.wolfcode.wolf2w.article.service.IStrategyCatalogService;
import cn.wolfcode.wolf2w.article.service.IStrategyService;
import cn.wolfcode.wolf2w.article.service.ITravelService;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/destinations")
public class DestinationController {
    @Autowired
    private IDestinationService destinationService;
    @Autowired
    private IStrategyCatalogService strategyCatalogService;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private ITravelService travelService;

    @GetMapping("/list")
    public JsonResult<Destination> list() {
        return JsonResult.success(destinationService.list());
    }

    @GetMapping("/query")
    public JsonResult<Page<Destination>> query(DestinationQuery qo) {
        Page<Destination> page = destinationService.queryPage(qo);
        return JsonResult.success(page);
    }

    @GetMapping("/toasts")
    public JsonResult<List<Destination>> toasts(Long destId) {
        return JsonResult.success(destinationService.toasts(destId));
    }

    // 攻略概况
    @GetMapping("/catalogs")
    public JsonResult catalogs(Long destId) {
        List<StrategyCatalog> list = strategyCatalogService.queryByDestId(destId);
        return JsonResult.success(list);
    }

    @GetMapping("/strategies/viewnnumTop3")
    public JsonResult<List<Strategy>> strategyViewnnumTop3(Long destId){
        List<Strategy> list = strategyService.queryViewnumTop3(destId);
        return JsonResult.success(list);
    }

    @GetMapping("/travels/viewnnumTop3")
    public JsonResult<List<Travel>> travelViewnnumTop3(Long destId){
        List<Travel> list = travelService.queryViewnumTop3(destId);
        return JsonResult.success(list);
    }

    @GetMapping("/queryByName")
    public JsonResult queryByName(String name){
        return JsonResult.success(destinationService.queryByName(name));
    }

    @GetMapping("/detail")
    public JsonResult detail(Long id){

        return JsonResult.success(destinationService.detail(id));
    }
}
