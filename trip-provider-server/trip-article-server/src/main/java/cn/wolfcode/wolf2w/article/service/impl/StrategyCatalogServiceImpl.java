package cn.wolfcode.wolf2w.article.service.impl;

import cn.wolfcode.wolf2w.article.domain.Strategy;
import cn.wolfcode.wolf2w.article.domain.StrategyCatalog;
import cn.wolfcode.wolf2w.article.mapper.StrategyCatalogMapper;
import cn.wolfcode.wolf2w.article.query.StrategyCatalogQuery;
import cn.wolfcode.wolf2w.article.service.IStrategyCatalogService;
import cn.wolfcode.wolf2w.article.service.IStrategyService;
import cn.wolfcode.wolf2w.article.vo.CatalogVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* 攻略分类服务接口实现
*/
@Service
@Transactional
public class StrategyCatalogServiceImpl extends ServiceImpl<StrategyCatalogMapper,StrategyCatalog> implements IStrategyCatalogService {

    @Autowired
    private IStrategyService strategyService;

    @Override
    public IPage<StrategyCatalog> queryPage(StrategyCatalogQuery qo) {
        IPage<StrategyCatalog> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<StrategyCatalog> wrapper = Wrappers.<StrategyCatalog>query();
        return super.page(page, wrapper);
    }

    @Override
    public List<CatalogVO> queryCatalogGroup() {
        List<CatalogVO> list = new ArrayList<>();
        //        select dest_id, dest_name,GROUP_CONCAT(id) ids,GROUP_CONCAT(name) names
//        from strategy_catalog group by dest_name, dest_id
        QueryWrapper<StrategyCatalog> wrapper = new QueryWrapper<>();
        wrapper.select("dest_id, dest_name,GROUP_CONCAT(id) ids,GROUP_CONCAT(name) names");
        wrapper.groupBy("dest_name");
        List<Map<String, Object>> maps = super.listMaps(wrapper);
        // 取出查出来的
        for (Map<String, Object> map : maps) {
            CatalogVO catalogVO = new CatalogVO();
            String destName = map.get("dest_name").toString();
            String names = map.get("names").toString();
            String ids = map.get("ids").toString();
            List<StrategyCatalog> catalogs = parseCatalogList(ids,names);
            catalogVO.setDestName(destName);
            catalogVO.setCatalogList(catalogs);
            list.add(catalogVO);
        }
        return list;
    }

    @Override
    public List<StrategyCatalog> queryByDestId(Long destId) {
        //第一步：查询目的地下攻略分类
        QueryWrapper<StrategyCatalog> wrapper = new QueryWrapper<>();
        wrapper.eq("dest_id",destId);
        List<StrategyCatalog> list = super.list(wrapper);
        //第二步：攻略分类下，攻略文章集合
        for (StrategyCatalog strategyCatalog : list) {
            QueryWrapper<Strategy> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("catalog_id", strategyCatalog.getId());
            List<Strategy> strategyList = strategyService.list(queryWrapper);
            strategyCatalog.setStrategies(strategyList);
        }
        return list;
    }

    public List<StrategyCatalog> parseCatalogList(String ids, String names) {
        List<StrategyCatalog> list = new ArrayList<>();
        String[] id = ids.split(",");
        String[] name = names.split(",");
        for (int i = 0; i < id.length; i++) {
            StrategyCatalog strategyCatalog = new StrategyCatalog();
            strategyCatalog.setId(Long.parseLong(id[i]));
            strategyCatalog.setName(name[i]);
            list.add(strategyCatalog);
        }
        return list;
    }

}
