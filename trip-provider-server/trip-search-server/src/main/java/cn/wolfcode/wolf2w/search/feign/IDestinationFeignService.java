package cn.wolfcode.wolf2w.search.feign;

import cn.wolfcode.wolf2w.article.domain.Destination;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "trip-article-server")
public interface IDestinationFeignService {

    @GetMapping("/destinations/list")
    JsonResult<List<Destination>> list();

    @GetMapping("/destinations/queryByName")
    JsonResult<Destination> queryByName(@RequestParam("name") String name);

    @GetMapping("/destinations/detail")
    JsonResult<Destination> detail(@RequestParam("id") Long id);
}
