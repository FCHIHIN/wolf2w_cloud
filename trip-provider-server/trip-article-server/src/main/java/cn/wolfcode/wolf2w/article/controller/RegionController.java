package cn.wolfcode.wolf2w.article.controller;

import cn.wolfcode.wolf2w.article.domain.Destination;
import cn.wolfcode.wolf2w.article.domain.Region;
import cn.wolfcode.wolf2w.article.query.RegionQuery;
import cn.wolfcode.wolf2w.article.service.IDestinationService;
import cn.wolfcode.wolf2w.article.service.IRegionService;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regions")
public class RegionController {
    @Autowired
    private IRegionService regionService;
    @Autowired
    private IDestinationService destinationService;

    @GetMapping("/query")
    public JsonResult<Page<Region>> query(RegionQuery qo) {
        Page<Region> page = regionService.queryPage(qo);
        return JsonResult.success(page);
    }

    @PostMapping("/save")
    public JsonResult<Region> save(Region region) {
        regionService.save(region);
        return JsonResult.success();
    }

    @PostMapping("/update")
    public JsonResult<Region> update(Region region) {
        regionService.updateById(region);
        return JsonResult.success();
    }

    @GetMapping("/detail")
    public JsonResult<Region> detail(Long id) {
        return JsonResult.success( regionService.getById(id));

    }

    @PostMapping("/delete/{id}")
    public JsonResult<Region> delete(@PathVariable("id") Long id) {
        regionService.deleteById(id);
        return JsonResult.success();
    }

    @GetMapping("/{id}/destination")
    public JsonResult<List<Destination>> viewDestination(@PathVariable("id") Long rid) {
        List<Destination> list = destinationService.viewDestination(rid);
        return JsonResult.success(list);
    }

    @GetMapping("/hot")
    public JsonResult<List<Region>> hot() {

        return JsonResult.success(regionService.hot());
    }

    @GetMapping("/destination")
    public JsonResult<List<Destination>> queryByRidSon(Long rid) {

        return JsonResult.success(destinationService.queryByRidSon(rid));
    }
}
