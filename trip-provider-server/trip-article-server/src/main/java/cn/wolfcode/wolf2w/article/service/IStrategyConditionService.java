package cn.wolfcode.wolf2w.article.service;

import cn.wolfcode.wolf2w.article.domain.StrategyCondition;
import cn.wolfcode.wolf2w.article.query.StrategyConditionQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 攻略条件统计表服务接口
 */
public interface IStrategyConditionService extends IService<StrategyCondition>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<StrategyCondition> queryPage(StrategyConditionQuery qo);

    /**
     * 攻略条件统计
     * @param type
     * @return
     */
    List<StrategyCondition> queryCondition(Integer type);

    /**
     * 维护指定类型的攻略条件列表
     * @param type
     */
    void conditionDataHandler(int type);
}
