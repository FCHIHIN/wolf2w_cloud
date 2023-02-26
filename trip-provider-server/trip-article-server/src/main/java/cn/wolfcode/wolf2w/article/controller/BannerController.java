package cn.wolfcode.wolf2w.article.controller;

import cn.wolfcode.wolf2w.article.domain.Banner;
import cn.wolfcode.wolf2w.article.query.BannerQuery;
import cn.wolfcode.wolf2w.article.service.IBannerService;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* Banner控制层
*/
@RestController
@RequestMapping("banners")
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    @GetMapping("/query")
    public JsonResult query(BannerQuery qo){
        IPage<Banner> page = bannerService.queryPage(qo);
        return  JsonResult.success(page);
    }

    @GetMapping("/list")
    public JsonResult list(){
        return  JsonResult.success(bannerService.list());
    }

    @GetMapping("/detail")
    public JsonResult detail(Long id){
        return  JsonResult.success(bannerService.getById(id));
    }

    @PostMapping("/save")
    public JsonResult save(Banner banner){
        bannerService.save(banner);
        return  JsonResult.success();
    }

    @PostMapping("/update")
    public JsonResult update(Banner banner){
        bannerService.updateById(banner);
        return  JsonResult.success();
    }
    @PostMapping("/delete/{id}")
    public JsonResult delete(@PathVariable Long id){
        bannerService.removeById(id);
        return  JsonResult.success();
    }


    @GetMapping("/travel")
    public JsonResult<Banner> travel(){
        List<Banner> list = bannerService.queryByType(Banner.TYPE_TRAVEL);
        return  JsonResult.success(list);
    }


    @GetMapping("/strategy")
    public JsonResult<Banner> strategy(){
        List<Banner> list = bannerService.queryByType(Banner.TYPE_STRATEGY);
        return  JsonResult.success(list);
    }
}
