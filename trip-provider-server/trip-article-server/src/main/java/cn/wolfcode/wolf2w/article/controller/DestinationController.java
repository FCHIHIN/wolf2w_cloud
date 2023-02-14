package cn.wolfcode.wolf2w.article.controller;

import cn.wolfcode.wolf2w.article.domain.Destination;
import cn.wolfcode.wolf2w.article.domain.Region;
import cn.wolfcode.wolf2w.article.query.DestinationQuery;
import cn.wolfcode.wolf2w.article.query.RegionQuery;
import cn.wolfcode.wolf2w.article.service.IDestinationService;
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

}
