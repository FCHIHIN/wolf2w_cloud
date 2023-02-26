package cn.wolfcode.wolf2w.article.controller;

import cn.wolfcode.wolf2w.article.domain.Travel;
import cn.wolfcode.wolf2w.article.domain.TravelContent;
import cn.wolfcode.wolf2w.article.feign.IUserInfoFeignService;
import cn.wolfcode.wolf2w.article.mapper.TravelContentMapper;
import cn.wolfcode.wolf2w.article.query.TravelQuery;
import cn.wolfcode.wolf2w.article.service.ITravelService;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import cn.wolfcode.wolf2w.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* 游记控制层
*/
@RestController
@RequestMapping("travels")
public class TravelController {

    @Autowired
    private ITravelService travelService;
    @Autowired
    private IUserInfoFeignService userInfoFeignService;

    @GetMapping("/query")
    public JsonResult query(TravelQuery qo){
        IPage<Travel> page = travelService.queryPage(qo);
        return  JsonResult.success(page);
    }

    @GetMapping("/list")
    public JsonResult list(){
        return  JsonResult.success(travelService.list());
    }

    @GetMapping("/detail")
    public JsonResult detail(Long id){
        Travel travel = travelService.getById(id);
        TravelContent travelContent = travelService.getContent(id);
        travel.setContent(travelContent);
        JsonResult<UserInfo> result = userInfoFeignService.detail(travel.getAuthorId());
        if (result.getCode()==JsonResult.CODE_SUCCESS) {
            travel.setAuthor(result.getData());
        }
        return  JsonResult.success(travel);
    }

    @PostMapping("/save")
    public JsonResult save(Travel travel){
        travelService.save(travel);
        return  JsonResult.success();
    }

    @PostMapping("/update")
    public JsonResult update(Travel travel){
        travelService.updateById(travel);
        return  JsonResult.success();
    }
    @PostMapping("/delete/{id}")
    public JsonResult delete(@PathVariable Long id){
        travelService.removeById(id);
        return  JsonResult.success();
    }

    @GetMapping("/content")
    public JsonResult content(Long id) {
        return JsonResult.success(travelService.getContent(id));
    }

    @PostMapping("/audit")
    public JsonResult audit(Long id, int state){
        travelService.audit(id, state);
        return  JsonResult.success();
    }
    @GetMapping("/queryByDestId")
    public JsonResult queryByDestId(Long destId) {
        return JsonResult.success(travelService.queryByDestId(destId));
    }

}
