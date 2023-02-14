package cn.wolfcode.wolf2w.article.service;

import cn.wolfcode.wolf2w.article.domain.Destination;
import cn.wolfcode.wolf2w.article.domain.Region;
import cn.wolfcode.wolf2w.article.query.RegionQuery;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;


public interface IRegionService extends IService<Region> {
    /**
     * 查询
     * @param qo
     * @return
     */
    Page<Region> queryPage(RegionQuery qo);

    /** 删除
     * @param id
     */
    void deleteById(Long id);


    /**
     * 查询热门区域
     * @return
     */
    List<Region> hot();


}
