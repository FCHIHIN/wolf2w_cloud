package cn.wolfcode.wolf2w.article.service;

import cn.wolfcode.wolf2w.article.domain.Strategy;
import cn.wolfcode.wolf2w.article.domain.StrategyContent;
import cn.wolfcode.wolf2w.article.query.StrategyQuery;
import cn.wolfcode.wolf2w.article.vo.ThemeVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;


/**
 * 攻略服务接口
 */
public interface IStrategyService extends IService<Strategy>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<Strategy> queryPage(StrategyQuery qo);

    StrategyContent getConent(Long id);

    List<Strategy> queryViewnumTop3(Long destId);

    /**
     * 主题推荐
     * @return
     */
    List<ThemeVO> queryThemeCds();

    /**
     * 阅读数增加
     * @param sid
     * @return
     */
    Map<String, Object> veiwnumIncr(Long sid);

    /**
     * 评论数统计
     * @param sid
     * @return
     */
    Map<String, Object> replynumIncr(Long sid);

    /**
     * 收藏
     * @param sid
     * @param id
     * @return
     */
    Map<String, Object> favornumIncr(Long sid, Long id);

    /**
     * 收藏图标判断
     * @param sid
     * @param uid
     * @return
     */
    boolean isUserFavor(Long sid, Long uid);

    /**
     * 点赞
     * @param sid
     * @param id
     * @return
     */
    Map<String, Object> thumbnumIncr(Long sid, Long id);

    /**
     * 初始化数据
     */
    void statisDataInit();

    /**
     * 统计数据持久化
     */
    void statisDataPersistence();

    /**
     *
     * @param destId
     * @return
     */
    List<Strategy> queryByDestId(Long destId);
}
