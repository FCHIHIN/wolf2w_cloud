package cn.wolfcode.wolf2w.article.service.impl;

import cn.wolfcode.wolf2w.article.domain.*;
import cn.wolfcode.wolf2w.article.mapper.StrategyContentMapper;
import cn.wolfcode.wolf2w.article.mapper.StrategyMapper;
import cn.wolfcode.wolf2w.article.mapper.StrategyThemecdsMapper;
import cn.wolfcode.wolf2w.article.query.StrategyQuery;
import cn.wolfcode.wolf2w.article.service.IDestinationService;
import cn.wolfcode.wolf2w.article.service.IStrategyCatalogService;
import cn.wolfcode.wolf2w.article.service.IStrategyService;
import cn.wolfcode.wolf2w.article.service.IStrategyThemeService;
import cn.wolfcode.wolf2w.article.util.UploadUtil;
import cn.wolfcode.wolf2w.article.vo.DestVO;
import cn.wolfcode.wolf2w.article.vo.ThemeVO;
import cn.wolfcode.wolf2w.common.redis.service.RedisService;
import cn.wolfcode.wolf2w.common.redis.util.RedisKeys;
import cn.wolfcode.wolf2w.common.util.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 攻略服务接口实现
 */
@Service
@Transactional
public class StrategyServiceImpl extends ServiceImpl<StrategyMapper, Strategy> implements IStrategyService {

    @Autowired
    private IStrategyCatalogService strategyCatalogService;

    @Autowired
    private IStrategyThemeService strategyThemeService;

    @Autowired
    private IDestinationService destinationService;

    @Autowired
    private StrategyContentMapper strategyContentMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private StrategyThemecdsMapper strategyThemecdsMapper;


    @Override
    public IPage<Strategy> queryPage(StrategyQuery qo) {
        IPage<Strategy> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Strategy> wrapper = Wrappers.query();
        wrapper.eq(qo.getDestId() != null, "dest_id", qo.getDestId());
        wrapper.eq(qo.getThemId() != null, "dest_name", qo.getThemId());
        Integer type = qo.getType();
        Long refid = qo.getRefid();
        if (type != null && refid != null) {
            if (type == StrategyCondition.TYPE_THEME) {
                wrapper.eq("theme_id", refid);
            } else if (type == StrategyCondition.TYPE_ABROAD || type == StrategyCondition.TYPE_CHINA) {
                wrapper.eq("dest_id", refid);
            }
        }
        wrapper.orderByDesc(StringUtils.hasText(qo.getOrderBy()), qo.getOrderBy());
        return super.page(page, wrapper);
    }

    @Override
    public StrategyContent getConent(Long id) {
        return strategyContentMapper.selectById(id);
    }

    @Override
    public List<Strategy> queryViewnumTop3(Long destId) {
        QueryWrapper<Strategy> wrapper = new QueryWrapper<>();
        wrapper.eq("dest_id", destId);
        wrapper.orderByDesc("viewnum");
        wrapper.last("limit 3");
        return super.list(wrapper);
    }

    @Override
    public List<ThemeVO> queryThemeCds() {
//         select
//         theme_id refid,theme_name name,count(id) count,
//         group concat(dest_id) destids ,group concat(dest_name) destnames
//         from strategy
//         group by theme_id,theme_name
//         order by count desc
        List<StrategyThemecds> list = strategyThemecdsMapper.selectList(null);
        List<ThemeVO> themeVOS = new ArrayList<>();
        for (StrategyThemecds strategyThemecds : list) {
            ThemeVO themeVO = new ThemeVO();
            // 获取目的地集合
            String destids = strategyThemecds.getDestids();
            String destnames = strategyThemecds.getDestnames();
            List<DestVO> destVOS = themeVO.parseList(destids, destnames);

            themeVO.setThemeName(strategyThemecds.getName());
            themeVO.setDests(destVOS);
            themeVOS.add(themeVO);
        }
        return themeVOS;
    }

    @Override
    public Map<String, Object> veiwnumIncr(Long sid) {
        String key = this.strategyHashInit(sid);
        redisService.incrementCacheMapValue(key, "viewnum", 1);
        return redisService.getCacheMap(key);

    }

    @Override
    public Map<String, Object> replynumIncr(Long sid) {
        String key = this.strategyHashInit(sid);
        redisService.incrementCacheMapValue(key, "replynum", 1);
        return redisService.getCacheMap(key);

    }

    @Override
    public Map<String, Object> favornumIncr(Long sid, Long uid) {
        // 拼接uid key
        String key = RedisKeys.USER_STRATEGY_FAVOR.join(uid.toString());
        // 判断key存不存在,不存在初始化
        if (!redisService.hasKey(key)) {
            HashSet<Long> sids = new HashSet<>();
            sids.add(-1L);
            redisService.setCacheSet(key, sids);
        }
        // 拼接sid key
        String hkey = RedisKeys.STRATEGY_STATIS_HASH.join(sid.toString());
        boolean flag = false;
        // 判断set里面有没有sid key,收藏数存在就-1,不存在就+1
        if (redisService.isCacheSetContains(key, sid)) {
            redisService.incrementCacheMapValue(hkey, "favornum", -1);
            redisService.deleteCacheSetValue(key, sid);

        } else {
            redisService.incrementCacheMapValue(hkey, "favornum", 1);
            redisService.addCacheSetValue(key, sid);
            flag = true;
        }
        Map<String, Object> map = redisService.getCacheMap(hkey);
        map.put("result", flag);
        // add一个结果result
        return map;
    }

    @Override
    public boolean isUserFavor(Long sid, Long uid) {
        String key = RedisKeys.USER_STRATEGY_FAVOR.join(uid.toString());

        return redisService.isCacheSetContains(key, sid);

    }

    @Override
    public Map<String, Object> thumbnumIncr(Long sid, Long uid) {
        // 拼接key
        String key = RedisKeys.STRATEGY_THUMB.join(sid.toString(), uid.toString());
        // 统计数key
        String hkey = RedisKeys.STRATEGY_STATIS_HASH.join(sid.toString());
        // 判断key
        boolean flag = false;
        if (!redisService.hasKey(key)) {
            redisService.incrementCacheMapValue(hkey, "thumbsupnuml", 1);
            Date now = new Date();
            Date end = DateUtil.getEndDate(now);  //今天最后1秒
            long time = DateUtil.getDateBetween(now, end);
            redisService.setnxCacheObject(key, 1, time, TimeUnit.SECONDS);
            flag = true;
        }
        Map<String, Object> map = redisService.getCacheMap(hkey);
        map.put("result", flag);
        return map;
    }

    @Override
    public void statisDataInit() {
        // 初始化攻略统计hash数据
        // 查出所有攻略
        List<Strategy> list = super.list();
        for (Strategy strategy : list) {
            this.strategyHashInit(strategy.getId());
        }
        System.out.println("攻略统计初始化成功");
    }

    @Override
    public void statisDataPersistence() {
        //查询redis 中所有hash数据-redis
        // keys strategy_statis_hash:*
        Collection<String> keys = redisService.keys(RedisKeys.STRATEGY_STATIS_HASH.join("*"));
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                Map<String, Object> map = redisService.getCacheMap(key);
                UpdateWrapper<Strategy> wrapper = new UpdateWrapper<>();
                wrapper.eq("id", map.get("strategyId"));
                wrapper.set("viewnum", map.get("viewnum"));
                wrapper.set("replynum", map.get("replynum"));
                wrapper.set("favornum", map.get("favornum"));
                wrapper.set("sharenum", map.get("sharenum"));
                wrapper.set("thumbsupnum", map.get("thumbsupnum"));
                super.update(wrapper);
            }
        }
    }

    @Override
    public List<Strategy> queryByDestId(Long destId) {
        QueryWrapper<Strategy> wrapper = new QueryWrapper<>();
        wrapper.eq("dest_id",destId);
        return super.list(wrapper);
    }

    public String strategyHashInit(Long sid) {
        Strategy strategy = super.getById(sid);
        //拼接hash key
        String key = RedisKeys.STRATEGY_STATIS_HASH.join(sid.toString());
        //判断key是否存在
        if (!redisService.hasKey(key)) {
            //不存在-初始化
            //将strategy对象统计数添加到map中
            //初始化
            Map<String, Object> map = new HashMap<>();
            map.put("strategyId", strategy.getId());
            map.put("viewnum", strategy.getViewnum());
            map.put("replynum", strategy.getReplynum());
            map.put("favornum", strategy.getFavornum());
            map.put("sharenum", strategy.getSharenum());
            map.put("thumbsupnum", strategy.getThumbsupnum());
            redisService.setCacheMap(key, map);
        }
        return key;
    }

    @Override
    public boolean saveOrUpdate(Strategy entity) {
        //目的地id/name
        StrategyCatalog strategyCatalog = strategyCatalogService.getById(entity.getCatalogId());
        entity.setDestId(strategyCatalog.getDestId());
        entity.setDestName(strategyCatalog.getDestName());
        //攻略分类name
        entity.setCatalogName(strategyCatalog.getName());
        //攻略主题name
        StrategyTheme strategyTheme = strategyThemeService.getById(entity.getThemeId());
        entity.setThemeName(strategyTheme.getName());
        //图片---页面添加/编辑进来图片是可能Base64字符串，有可能是具体url地址
        //判断传入的url 是Base64字符串，还是真实图片url地址
        //添加： Base64字符串   //编辑：可能真实图片url地址
        if (UploadUtil.isBase64Pic(entity.getCoverUrl())) {
            String url = UploadUtil.uploadAliBase64(entity.getCoverUrl());
            entity.setCoverUrl(url);
        }
        //是否国外
        List<Destination> toasts = destinationService.toasts(entity.getDestId());
        if (toasts != null && toasts.size() > 0) {
            Destination destination = toasts.get(0);
            if ("中国".equals(destination.getName())) {
                entity.setIsabroad(Strategy.ABROAD_YES);
            } else {
                entity.setIsabroad(Strategy.ABROAD_NO);
            }
        }

        if (entity.getId() == null) {
            //创建时间
            entity.setCreateTime(new Date());
            //各种统计数
            entity.setViewnum(0);
            entity.setReplynum(0);
            entity.setFavornum(0);
            entity.setSharenum(0);
            entity.setThumbsupnum(0);
            super.save(entity);
            // 内容
            StrategyContent content = entity.getContent();
            content.setId(entity.getId());
            strategyContentMapper.insert(content);
        } else {
            super.updateById(entity);
            // 内容
            StrategyContent content = entity.getContent();
            content.setId(entity.getId());
            strategyContentMapper.updateById(content);
        }
        return true;
    }
}
