package cn.wolfcode.wolf2w.article.controller;

import cn.wolfcode.wolf2w.article.domain.Strategy;
import cn.wolfcode.wolf2w.article.domain.StrategyContent;
import cn.wolfcode.wolf2w.article.mapper.StrategyContentMapper;
import cn.wolfcode.wolf2w.article.query.StrategyQuery;
import cn.wolfcode.wolf2w.article.service.IStrategyService;
import cn.wolfcode.wolf2w.article.util.UploadUtil;
import cn.wolfcode.wolf2w.article.vo.ThemeVO;
import cn.wolfcode.wolf2w.common.exception.LogicException;
import cn.wolfcode.wolf2w.common.security.annotation.RequireLogin;
import cn.wolfcode.wolf2w.common.security.annotation.UserParam;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import cn.wolfcode.wolf2w.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 攻略控制层
*/
@RestController
@RequestMapping("strategies")
public class StrategyController {

    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private StrategyContentMapper strategyContentMapper;

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
        StrategyContent strategyContent = strategyContentMapper.selectById(id);
        Strategy strategy = strategyService.getById(id);
        strategy.setContent(strategyContent);
        return  JsonResult.success(strategy);
    }
    @PostMapping("/save")
    public JsonResult save(Strategy strategy){
        strategyService.saveOrUpdate(strategy);
        return  JsonResult.success();
    }

    @PostMapping("/update")
    public JsonResult update(Strategy strategy){
        strategyService.saveOrUpdate(strategy);
        return  JsonResult.success();
    }
    @PostMapping("/delete/{id}")
    public JsonResult delete(@PathVariable Long id){
        strategyService.removeById(id);
        return  JsonResult.success();
    }

    @PostMapping("/uploadImg")
    public Map<String, Object> uploadImg(MultipartFile upload){
        Map<String,Object> map = new HashMap<>();
        if (upload == null || upload.getSize() == 0) {
            throw new LogicException("非法图片");
        }
            try {
                String path = UploadUtil.uploadAli(upload);
                map.put("uploaded",1);
                map.put("url",path);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("uploaded",0);
                Map<String,Object> errormap = new HashMap<>();
                errormap.put("message","上传失败");
                map.put("error",errormap);
            }

        return map;
    }


    @GetMapping("/content")
    public JsonResult<StrategyContent> content(Long id){
        StrategyContent content = strategyService.getConent(id);
        return  JsonResult.success(content);
    }

    @GetMapping("/themeCds")
    public JsonResult<List<ThemeVO>> themeCds() {
        List<ThemeVO> list = strategyService.queryThemeCds();
        return JsonResult.success(list);
    }

    @PostMapping("/veiwnumIncr")
    public JsonResult<Map<String,Object>> veiwnumIncr(Long sid) {
        Map<String,Object> map = strategyService.veiwnumIncr(sid);
        return JsonResult.success(map);
    }

    @PostMapping("/replynumIncr")
    public JsonResult<Map<String,Object>> replynumIncr(Long sid) {
        Map<String,Object> map = strategyService.replynumIncr(sid);
        return JsonResult.success(map);
    }

    @RequireLogin
    @PostMapping("/favornumIncr")
    public JsonResult<Map<String,Object>> favornumIncr(Long sid,@UserParam UserInfo userInfo) {
        Map<String,Object> map = strategyService.favornumIncr(sid,userInfo.getId());
        return JsonResult.success(map);
    }

    @GetMapping("/isUserFavor")
    public JsonResult<Boolean> isUserFavor(Long sid,Long uid) {
        boolean flag = strategyService.isUserFavor(sid,uid);
        return JsonResult.success(flag);
    }

    @RequireLogin
    @PostMapping("/thumbnumIncr")
    public JsonResult<Map<String,Object>> thumbnumIncr(Long sid,@UserParam UserInfo userInfo) {
        Map<String,Object> map = strategyService.thumbnumIncr(sid,userInfo.getId());
        return JsonResult.success(map);
    }

    @PostMapping("/statisDataInit")
    public JsonResult statisDataInit() {
        strategyService.statisDataInit();
        return JsonResult.success();
    }

    //持久化
    @PostMapping("/statisDataPersistence")
    public JsonResult statisDataPersistence(){
        strategyService.statisDataPersistence();
        return  JsonResult.success();
    }

    @GetMapping("/queryByDestId")
    public JsonResult queryByDestId(Long destId) {
        List<Strategy> list = strategyService.queryByDestId(destId);
        return JsonResult.success(list);
    }
}
