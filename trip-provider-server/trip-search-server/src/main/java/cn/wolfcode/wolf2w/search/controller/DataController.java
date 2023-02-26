package cn.wolfcode.wolf2w.search.controller;

import cn.wolfcode.wolf2w.article.domain.Destination;
import cn.wolfcode.wolf2w.article.domain.Strategy;
import cn.wolfcode.wolf2w.article.domain.Travel;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import cn.wolfcode.wolf2w.domain.UserInfo;
import cn.wolfcode.wolf2w.search.domain.DestinationEs;
import cn.wolfcode.wolf2w.search.domain.StrategyEs;
import cn.wolfcode.wolf2w.search.domain.TravelEs;
import cn.wolfcode.wolf2w.search.domain.UserInfoEs;
import cn.wolfcode.wolf2w.search.feign.IDestinationFeignService;
import cn.wolfcode.wolf2w.search.feign.IStrategyFeignService;
import cn.wolfcode.wolf2w.search.feign.ITravelFeignService;
import cn.wolfcode.wolf2w.search.feign.IUserInfoFeignService;
import cn.wolfcode.wolf2w.search.service.IDestinationEsService;
import cn.wolfcode.wolf2w.search.service.IStrategyEsService;
import cn.wolfcode.wolf2w.search.service.ITravelEsService;
import cn.wolfcode.wolf2w.search.service.IUserInfoEsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {

    @Autowired
    private IUserInfoFeignService userInfoFeignService;
    @Autowired
    private IUserInfoEsService userInfoEsService;
    @Autowired
    private IDestinationFeignService destinationFeignService;
    @Autowired
    private IDestinationEsService destinationEsService;
    @Autowired
    private IStrategyFeignService strategyFeignService;
    @Autowired
    private IStrategyEsService strategyEsService;
    @Autowired
    private ITravelFeignService travelFeignService;
    @Autowired
    private ITravelEsService travelEsService;

    @GetMapping("/dataInit")
    public JsonResult dataInit() {
        // 远程获取list
        JsonResult<List<UserInfo>> userInfoJR = userInfoFeignService.list();
        // 判断
        if (userInfoJR != null && userInfoJR.getCode() == JsonResult.CODE_SUCCESS) {
            // 塞入es
            List<UserInfo> userInfos = userInfoJR.getData();
            for (UserInfo userInfo : userInfos) {
                UserInfoEs userInfoEs = new UserInfoEs();
                BeanUtils.copyProperties(userInfo, userInfoEs);
                userInfoEsService.save(userInfoEs);
            }
        }
        // 远程获取list
        JsonResult<List<Destination>> destinationJR = destinationFeignService.list();
        // 判断
        if (destinationJR != null && destinationJR.getCode() == JsonResult.CODE_SUCCESS) {
            // 塞入es
            List<Destination> destinations = destinationJR.getData();
            for (Destination destination : destinations) {
                DestinationEs destinationEs = new DestinationEs();
                BeanUtils.copyProperties(destination, destinationEs);
                destinationEsService.save(destinationEs);
            }
        }

        // 远程获取list
        JsonResult<List<Strategy>> strategyJR = strategyFeignService.list();
        // 判断
        if (strategyJR != null && strategyJR.getCode() == JsonResult.CODE_SUCCESS) {
            // 塞入es
            List<Strategy> strategies = strategyJR.getData();
            for (Strategy strategy : strategies) {
                StrategyEs strategyEs = new StrategyEs();
                BeanUtils.copyProperties(strategy, strategyEs);
                strategyEsService.save(strategyEs);
            }
        }

        // 远程获取list
        JsonResult<List<Travel>> travelJR = travelFeignService.list();
        // 判断
        if (travelJR != null && travelJR.getCode() == JsonResult.CODE_SUCCESS) {
            // 塞入es
            List<Travel> travels = travelJR.getData();
            for (Travel travel : travels) {
                TravelEs travelEs = new TravelEs();
                BeanUtils.copyProperties(travel, travelEs);
                travelEsService.save(travelEs);
            }
        }
        return JsonResult.success();
    }
}
