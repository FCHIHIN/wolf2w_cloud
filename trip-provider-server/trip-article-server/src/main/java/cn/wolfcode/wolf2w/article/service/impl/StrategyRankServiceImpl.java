package cn.wolfcode.wolf2w.article.service.impl;

import cn.wolfcode.wolf2w.article.domain.Strategy;
import cn.wolfcode.wolf2w.article.domain.StrategyRank;
import cn.wolfcode.wolf2w.article.mapper.StrategyRankMapper;
import cn.wolfcode.wolf2w.article.query.StrategyRankQuery;
import cn.wolfcode.wolf2w.article.service.IStrategyRankService;
import cn.wolfcode.wolf2w.article.service.IStrategyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* 攻略统计表服务接口实现
*/
@Service
@Transactional
public class StrategyRankServiceImpl extends ServiceImpl<StrategyRankMapper,StrategyRank> implements IStrategyRankService  {

    @Autowired
    private IStrategyService strategyService;

    @Override
    public IPage<StrategyRank> queryPage(StrategyRankQuery qo) {
        IPage<StrategyRank> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<StrategyRank> wrapper = Wrappers.<StrategyRank>query();
        return super.page(page, wrapper);
    }

    @Override
    public List<StrategyRank> queryRank(Integer type) {
        //select * from strategy_rank
        //where type = 3 and statis_time =  (select max(statis_time) from strategy_rank where type = 3)
        //order by  statisnum desc
        //limit 10
        QueryWrapper<StrategyRank> wrapper = new QueryWrapper<>();
        wrapper.eq("type",type);
        wrapper.inSql("statis_time","(select max(statis_time) from strategy_rank where type = "+ type +")");
        wrapper.orderByDesc("statisnum");
        wrapper.last("limit 10");
        List<StrategyRank> list = super.list(wrapper);
        return list;
    }

    @Override
    public void rankDataHandler(int type) {
        QueryWrapper<Strategy> wrapper = new QueryWrapper<>();
        if (type == StrategyRank.TYPE_HOT) {
            wrapper.orderByDesc("viewnum + replynum");
        } else if (type == StrategyRank.TYPE_ABROAD) {
            wrapper.eq("isabroad", 1);
            wrapper.orderByDesc("thumbsupnum + favornum");
        } else if (type == StrategyRank.TYPE_CHINA){
            wrapper.eq("isabroad", 0);
            wrapper.orderByDesc("thumbsupnum + favornum");
        }
        wrapper.last("limit 10");
        List<Strategy> strategies = strategyService.list(wrapper);
        List<StrategyRank> list = new ArrayList<>();
        Date now = new Date();
        for (Strategy strategy : strategies) {
            StrategyRank strategyRank = new StrategyRank();
            strategyRank.setDestId(strategy.getDestId());
            strategyRank.setDestName(strategy.getDestName());
            strategyRank.setStatisTime(now);
            strategyRank.setStrategyId(strategy.getId());
            strategyRank.setStrategyTitle(strategy.getSubTitle());
            strategyRank.setType(type);
            if (type == StrategyRank.TYPE_HOT) {
                strategyRank.setStatisnum(strategy.getViewnum() + strategy.getReplynum() + 0L);
            }else {
                strategyRank.setStatisnum(strategy.getThumbsupnum() + strategy.getFavornum() + 0L);
            }
            list.add(strategyRank);
        }
            super.saveBatch(list);
    }
}
