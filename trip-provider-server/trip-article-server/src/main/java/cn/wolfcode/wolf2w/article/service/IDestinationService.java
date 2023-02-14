package cn.wolfcode.wolf2w.article.service;

import cn.wolfcode.wolf2w.article.domain.Destination;
import cn.wolfcode.wolf2w.article.domain.Region;
import cn.wolfcode.wolf2w.article.query.DestinationQuery;
import cn.wolfcode.wolf2w.article.query.RegionQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IDestinationService extends IService<Destination> {
    /**
     * 查看目的地功能
     * @param rid
     * @return
     */
    List<Destination> viewDestination(Long rid);

    /**
     * 查询
     * @param qo
     * @return
     */
    Page<Destination> queryPage(DestinationQuery qo);

    /**
     * 导航吐司
     * @param destId
     * @return
     */
    List<Destination> toasts(Long destId);

    /**
     * 查询热门区域的儿子
     * @param rid
     * @return
     */
    List<Destination> queryByRidSon(Long rid);
}
