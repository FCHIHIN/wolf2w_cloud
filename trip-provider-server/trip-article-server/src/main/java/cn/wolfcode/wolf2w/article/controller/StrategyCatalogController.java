package cn.wolfcode.wolf2w.article.controller;

import cn.wolfcode.wolf2w.article.domain.StrategyCatalog;
import cn.wolfcode.wolf2w.article.query.StrategyCatalogQuery;
import cn.wolfcode.wolf2w.article.service.IStrategyCatalogService;
import cn.wolfcode.wolf2w.article.vo.CatalogVO;
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
* 攻略分类控制层
*/
@RestController
@RequestMapping("strategyCatalogs")
public class StrategyCatalogController {

    @Autowired
    private IStrategyCatalogService strategyCatalogService;

    @GetMapping("/query")
    public JsonResult query(StrategyCatalogQuery qo){
        IPage<StrategyCatalog> page = strategyCatalogService.queryPage(qo);
        return  JsonResult.success(page);
    }

    @GetMapping("/list")
    public JsonResult list(){
        return  JsonResult.success(strategyCatalogService.list());
    }

    @GetMapping("/detail")
    public JsonResult detail(Long id){
        return  JsonResult.success(strategyCatalogService.getById(id));
    }

    @PostMapping("/save")
    public JsonResult save(StrategyCatalog strategyCatalog){
        strategyCatalogService.save(strategyCatalog);
        return  JsonResult.success();
    }

    @PostMapping("/update")
    public JsonResult update(StrategyCatalog strategyCatalog){
        strategyCatalogService.updateById(strategyCatalog);
        return  JsonResult.success();
    }
    @PostMapping("/delete/{id}")
    public JsonResult delete(@PathVariable Long id){
        strategyCatalogService.removeById(id);
        return  JsonResult.success();
    }

    @GetMapping("/groups")
    public JsonResult queryCatalogGroup(Long id){
        List<CatalogVO> list = strategyCatalogService.queryCatalogGroup();
        return JsonResult.success(list);
    }

}
