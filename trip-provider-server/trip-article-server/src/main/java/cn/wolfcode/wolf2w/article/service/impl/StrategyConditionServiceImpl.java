package cn.wolfcode.wolf2w.article.service.impl;

import cn.wolfcode.wolf2w.article.domain.Strategy;
import cn.wolfcode.wolf2w.article.domain.StrategyCondition;
import cn.wolfcode.wolf2w.article.mapper.StrategyConditionMapper;
import cn.wolfcode.wolf2w.article.query.StrategyConditionQuery;
import cn.wolfcode.wolf2w.article.service.IStrategyConditionService;
import cn.wolfcode.wolf2w.article.service.IStrategyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* 攻略条件统计表服务接口实现
*/
@Service
@Transactional
public class StrategyConditionServiceImpl extends ServiceImpl<StrategyConditionMapper,StrategyCondition> implements IStrategyConditionService  {
    @Autowired
    private IStrategyService strategyService;

    @Override
    public IPage<StrategyCondition> queryPage(StrategyConditionQuery qo) {
        IPage<StrategyCondition> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<StrategyCondition> wrapper = Wrappers.<StrategyCondition>query();
        return super.page(page, wrapper);
    }

    @Override
    public List<StrategyCondition> queryCondition(Integer type) {

//sql
        ///select * from strategy_condition
        //where type = 3  and statis_time in
        //        (select max(statis_time) from strategy_condition where type = 3)
        ///order by count desc
        QueryWrapper<StrategyCondition> wrapper = new QueryWrapper<>();
        wrapper.eq("type",type);
        wrapper.inSql("statis_time","(select max(statis_time) from strategy_condition where type = "+type+")");
        wrapper.orderByDesc("count");
        List<StrategyCondition> list = super.list(wrapper);
        return list;
    }

    @Override
    public void conditionDataHandler(int type) {
        //sql
        ///select * from strategy_condition
        //where type = 3  and statis_time in
        //        (select max(statis_time) from strategy_condition where type = 3)
        ///order by count desc
        QueryWrapper<Strategy> wrapper = new QueryWrapper<>();
        if (type == StrategyCondition.TYPE_THEME) {
           wrapper.select("theme_id refid,theme_name name,count(id) count");
           wrapper.groupBy("theme_id,theme_name");
        } else if (type == StrategyCondition.TYPE_CHINA) {
            wrapper.select("theme_id refid,theme_name name,count(id) count");
            wrapper.eq("isabroad", 0);
            wrapper.groupBy("dest_id","dest_name");
        } else if (type == StrategyCondition.TYPE_ABROAD) {
            wrapper.select("theme_id refid,theme_name name,count(id) count");
            wrapper.eq("isabroad", 1);
            wrapper.groupBy("dest_id","dest_name");
        }
        List<Map<String, Object>> maps = strategyService.listMaps(wrapper);
        List<StrategyCondition> list = new ArrayList<>();
        Date now = new Date();
        for (Map<String, Object> map : maps) {
            StrategyCondition strategyCondition = new StrategyCondition();
            strategyCondition.setStatisTime(now);
            strategyCondition.setName(map.get("name").toString());
            strategyCondition.setRefid(Long.parseLong(map.get("refid").toString()));
            strategyCondition.setType(type);
            strategyCondition.setCount(Integer.parseInt(map.get("count").toString()));
            list.add(strategyCondition);
        }
        super.saveBatch(list);
    }
}
