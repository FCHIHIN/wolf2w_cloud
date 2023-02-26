package cn.wolfcode.wolf2w.article.service;

import cn.wolfcode.wolf2w.article.domain.Banner;
import cn.wolfcode.wolf2w.article.query.BannerQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * Banner服务接口
 */
public interface IBannerService extends IService<Banner>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<Banner> queryPage(BannerQuery qo);

    /**
     * 通过指定类型查询banner
     * @param type
     * @return
     */
    List<Banner> queryByType(int type);
}
