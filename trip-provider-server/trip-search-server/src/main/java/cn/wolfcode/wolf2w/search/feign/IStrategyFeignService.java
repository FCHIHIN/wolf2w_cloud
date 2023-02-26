package cn.wolfcode.wolf2w.search.feign;

import cn.wolfcode.wolf2w.article.domain.Strategy;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "trip-article-server")
public interface IStrategyFeignService {

    @GetMapping("/strategies/list")
    JsonResult<List<Strategy>> list();
    @GetMapping("/strategies/queryByDestId")
    JsonResult<List<Strategy>> queryByDestId(@RequestParam("destId") Long destId);

    @GetMapping("/strategies/detail")
    JsonResult<Strategy> detail(@RequestParam("id") Long id);
}
