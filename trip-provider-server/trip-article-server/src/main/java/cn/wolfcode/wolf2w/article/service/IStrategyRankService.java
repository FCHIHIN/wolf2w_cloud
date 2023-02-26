package cn.wolfcode.wolf2w.article.service;

import cn.wolfcode.wolf2w.article.domain.Strategy;
import cn.wolfcode.wolf2w.article.domain.StrategyRank;
import cn.wolfcode.wolf2w.article.query.StrategyRankQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 攻略统计表服务接口
 */
public interface IStrategyRankService extends IService<StrategyRank>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<StrategyRank> queryPage(StrategyRankQuery qo);

    /**
     * 攻略推荐排行
     * @param type
     * @return
     */
    List<StrategyRank> queryRank(Integer type);

    /**
     * 根据类型实现排行数据维护
     * @param type
     */
    void rankDataHandler(int type);
}
