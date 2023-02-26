package cn.wolfcode.wolf2w.comment.controller;

import cn.wolfcode.wolf2w.comment.domain.StrategyComment;
import cn.wolfcode.wolf2w.comment.domain.TravelComment;
import cn.wolfcode.wolf2w.comment.query.StrategyCommentQuery;
import cn.wolfcode.wolf2w.comment.service.IStrategyCommentService;
import cn.wolfcode.wolf2w.comment.service.ITravelCommentService;
import cn.wolfcode.wolf2w.common.redis.service.RedisService;
import cn.wolfcode.wolf2w.common.redis.util.RedisKeys;
import cn.wolfcode.wolf2w.common.security.annotation.RequireLogin;
import cn.wolfcode.wolf2w.common.security.annotation.UserParam;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import cn.wolfcode.wolf2w.domain.UserInfo;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/travelComments")
public class TravelCommentController {

    @Autowired
    private ITravelCommentService travelCommentService;
    @Autowired
    private RedisService redisService;

    /**
     * 接口中涉及获取当前登录用户采用方式：
     *     @GetMapping("/info")
     *     public JsonResult info(HttpServletRequest request) {
     *         String token = request.getHeader("token");
     *         String userStr = redisService.getCacheObject(RedisKeys.USER_LOGIN_TOKEN.join(token));
     *         UserInfo userInfo = JSON.parseObject(userStr, UserInfo.class);
     *         return JsonResult.success(userInfo);
     *     }
     * 如果仅仅是1 2 接口到还好，一旦多了，此时存在代码重复的问题，此时怎么办？--抽--怎么抽?
     *
     * 我希望： 愿景：能不能将上面获取当前登录用户对象方式转换成下面操作
     *     @GetMapping("/info")
     *     public JsonResult info(UserInfo userInfo) {
     *         return JsonResult.success(userInfo);
     *     }
     *
     * 上面操作，目前还没法实现，原因：springmvc 根本不知道怎么对userInfo类型参数进行解析
     *
     * 需求：通过参数注入的方式，获取当前登录用户对象？
     *
     * 完成上面需求需要使用新的知识点： springmvc参数解析器
     *
     * Springmvc有2种类型参数解析器
     *
     *
     * 参数解析器 -- 解析接口参数用逻辑程序
     *
     * 1>默认的参数解析器--自带参数解析器
     * 2>自定义参数解析器--程序员根据业务需求自定义参数解析器
     *
     * 需求：自定义参数解析器，获取当前登录用户对象？
     *
     */

    @GetMapping("/info")  //使用自定义参数解析器
    public JsonResult info(  @UserParam UserInfo userInfo) {
        return JsonResult.success(userInfo);
    }


    @GetMapping("/updateInfo")  //mvc默认参数解析器
    public JsonResult updateInfo(UserInfo userInfo) {
        return JsonResult.success(userInfo);
    }

    @RequireLogin
    @PostMapping("/save")
    public JsonResult save(TravelComment comment, HttpServletRequest request) {
        String token = request.getHeader("token");
        String userStr = redisService.getCacheObject(RedisKeys.USER_LOGIN_TOKEN.join(token));
        UserInfo userInfo = JSON.parseObject(userStr, UserInfo.class);
        BeanUtils.copyProperties(userInfo,comment);
        comment.setUserId(userInfo.getId());
        travelCommentService.save(comment);
        return JsonResult.success();
    }

    @GetMapping("/query")
    public JsonResult query(Long travelId) {
        List<TravelComment> list = travelCommentService.query(travelId);
        return JsonResult.success(list);
    }
}
