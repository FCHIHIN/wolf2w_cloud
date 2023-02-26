package cn.wolfcode.wolf2w.search.controller;

import cn.wolfcode.wolf2w.article.domain.Destination;
import cn.wolfcode.wolf2w.article.domain.Strategy;
import cn.wolfcode.wolf2w.article.domain.Travel;
import cn.wolfcode.wolf2w.common.util.ParamMap;
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
import cn.wolfcode.wolf2w.search.query.SearchQueryObject;
import cn.wolfcode.wolf2w.search.service.ISearchService;
import cn.wolfcode.wolf2w.search.vo.SearchResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    @Autowired
    private IDestinationFeignService destinationFeignService;
    @Autowired
    private IStrategyFeignService strategyFeignService;
    @Autowired
    private ITravelFeignService travelFeignService;
    @Autowired
    private IUserInfoFeignService userInfoFeignService;
    @Autowired
    private ISearchService searchService;

    @GetMapping("/q")
    public JsonResult search(SearchQueryObject qo) throws UnsupportedEncodingException {
        qo.setKeyword(URLDecoder.decode(qo.getKeyword(), "UTF-8"));
        switch(qo.getType()){
            case SearchQueryObject.TYPE_DEST: return this.searchDest(qo);
            case SearchQueryObject.TYPE_STRATEGY: return this.searchStrategy(qo);
            case SearchQueryObject.TYPE_TRAVEL: return this.searchTravel(qo);
            case SearchQueryObject.TYPE_USER: return this.searchUser(qo);
            default: return this.searchAll(qo);
        }
    }

    private JsonResult searchUser(SearchQueryObject qo) {
        return JsonResult.success(ParamMap.newInstance().put("page",this.createUserInfoPage(qo)).put("qo",qo));
    }

    private JsonResult searchTravel(SearchQueryObject qo) {
        return JsonResult.success(ParamMap.newInstance().put("page",this.createTravelPage(qo)).put("qo",qo));
    }

    private JsonResult searchStrategy(SearchQueryObject qo) {
            return JsonResult.success(ParamMap.newInstance().put("page",this.createStrategyPage(qo)).put("qo",qo));
    }

    private JsonResult searchAll(SearchQueryObject qo) {
        SearchResultVO vo = new SearchResultVO();
        Page<Destination> destPage = this.createDestPage(qo);
        vo.setDests(destPage.getContent());

        Page<Strategy> strategyPage = this.createStrategyPage(qo);
        vo.setStrategies(strategyPage.getContent());

        Page<Travel> travelPage = this.createTravelPage(qo);
        vo.setTravels(travelPage.getContent());

        Page<UserInfo> userInfoPage = this.createUserInfoPage(qo);
        vo.setUsers(userInfoPage.getContent());

        vo.setTotal(destPage.getTotalElements() + strategyPage.getTotalElements()
                + travelPage.getTotalElements() + userInfoPage.getTotalElements());

        return JsonResult.success(ParamMap.newInstance().put("result",vo).put("qo",qo));
    }

    private Page<UserInfo> createUserInfoPage(SearchQueryObject qo) {
        return searchService.searchWithHighlight(UserInfoEs.INDEX_NAME,UserInfo.class,qo,"city","info");
    }

    private Page<Travel> createTravelPage(SearchQueryObject qo) {
        return searchService.searchWithHighlight(TravelEs.INDEX_NAME,Travel.class,qo,"title","summary");
    }

    private Page<Strategy>  createStrategyPage(SearchQueryObject qo) {
        return searchService.searchWithHighlight(StrategyEs.INDEX_NAME,Strategy.class,qo,"title","subTitle","summary");
    }
    private Page<Destination>  createDestPage(SearchQueryObject qo) {
        return searchService.searchWithHighlight(DestinationEs.INDEX_NAME,Destination.class,qo,"name","info");
    }

    private JsonResult searchDest(SearchQueryObject qo) {
        SearchResultVO vo = new SearchResultVO();
        // 目的地精确查询
            String destName = qo.getKeyword();
            // 获取destination对象
            Destination destination = null;
            JsonResult<Destination> djr = destinationFeignService.queryByName(destName);
            if (djr != null && djr.getCode() == JsonResult.CODE_SUCCESS) {
                destination = djr.getData();

            }
            // 用destName查攻略,游记,用户
            JsonResult<List<Strategy>> sjr = strategyFeignService.queryByDestId(destination.getId());
            if (sjr != null && sjr.getCode() == JsonResult.CODE_SUCCESS) {
                List<Strategy> strategies = sjr.getData();
                vo.setStrategies(strategies);
                vo.setTotal(vo.getTotal() + strategies.size());
            }
            JsonResult<List<Travel>> tjr = travelFeignService.queryByDestId(destination.getId());
            if (tjr != null && tjr.getCode() == JsonResult.CODE_SUCCESS) {
                List<Travel> travels = tjr.getData();
                vo.setTravels(travels);
                vo.setTotal(vo.getTotal() + travels.size());
            }
            JsonResult<List<UserInfo>> ujr = userInfoFeignService.queryCity(destination.getName());
            if (ujr != null && ujr.getCode() == JsonResult.CODE_SUCCESS) {
                List<UserInfo> userInfos = ujr.getData();
                vo.setUsers(userInfos);
                vo.setTotal(vo.getTotal() + userInfos.size());
            }
        return JsonResult.success(ParamMap.newInstance().put("result",vo).put("dest",destination).put("qo",qo));
    }

}
