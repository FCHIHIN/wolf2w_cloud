package cn.wolfcode.wolf2w.article.service;

import cn.wolfcode.wolf2w.article.domain.Travel;
import cn.wolfcode.wolf2w.article.domain.TravelContent;
import cn.wolfcode.wolf2w.article.query.TravelQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 游记服务接口
 */
public interface ITravelService extends IService<Travel>{
    /**
    * 分页
    * @param qo
    * @return
    */
    IPage<Travel> queryPage(TravelQuery qo);


    /**
     * 查看内容
     * @param id
     * @return
     */
    TravelContent getContent(Long id);

    /**
     * 审批
     * @param id
     * @param state
     */
    void audit(Long id, int state);

    /**
     * 查询阅读量前三的游记
     * @param destId
     * @return
     */
    List<Travel> queryViewnumTop3(Long destId);

    List<Travel> queryByDestId(Long destId);
}
