package cn.wolfcode.wolf2w.article.service;

import cn.wolfcode.wolf2w.article.domain.StrategyCatalog;
import cn.wolfcode.wolf2w.article.query.StrategyCatalogQuery;
import cn.wolfcode.wolf2w.article.vo.CatalogVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 攻略分类服务接口
 */
public interface IStrategyCatalogService extends IService<StrategyCatalog>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<StrategyCatalog> queryPage(StrategyCatalogQuery qo);

    /**
     * 分类下拉框
     * @return
     */
    List<CatalogVO> queryCatalogGroup();

    List<StrategyCatalog> queryByDestId(Long destId);
}
