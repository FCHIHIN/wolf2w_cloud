package cn.wolfcode.wolf2w.search.feign;

import cn.wolfcode.wolf2w.article.domain.Travel;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "trip-article-server")
public interface ITravelFeignService {

    @GetMapping("/travels/list")
    JsonResult<List<Travel>> list();

    @GetMapping("/travels/queryByDestId")
    JsonResult<List<Travel>> queryByDestId(@RequestParam("destId") Long destId);

    @GetMapping("/travels/detail")
    JsonResult<Travel> detail(@RequestParam("id") Long id);
}
