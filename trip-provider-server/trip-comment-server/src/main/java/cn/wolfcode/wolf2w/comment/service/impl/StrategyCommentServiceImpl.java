package cn.wolfcode.wolf2w.comment.service.impl;

import cn.wolfcode.wolf2w.comment.domain.StrategyComment;
import cn.wolfcode.wolf2w.comment.query.StrategyCommentQuery;
import cn.wolfcode.wolf2w.comment.repository.StrategyCommentRepository;
import cn.wolfcode.wolf2w.comment.service.IStrategyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StrategyCommentServiceImpl implements IStrategyCommentService {

    @Autowired
    private StrategyCommentRepository strategyCommentRepository;
    @Autowired
    private MongoTemplate template;

    @Override
    public void save(StrategyComment strategyComment) {
        strategyComment.setId(null);
        strategyComment.setCreateTime(new Date());
        strategyCommentRepository.save(strategyComment);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void update(StrategyComment strategyComment) {

    }

    @Override
    public StrategyComment get(String id) {
        return null;
    }

    @Override
    public List<StrategyComment> list() {
        return null;
    }

    @Override
    public Page<StrategyComment> query(StrategyCommentQuery qo) {
        Query query = new Query();
        if (qo.getStrategyId() != null) {
            Criteria criteria = Criteria.where("strategyId").is(qo.getStrategyId());
            query.addCriteria(criteria);
        }
        //SQL: select * from xxx where xxx limit ?,?
        //MQL: db.xxx.find({...}).skip(?).limit(?);
        //spring-data-mongodb 框架中没有现成分页方法， 需要自定定义

        //页面要显示分页需要啥？？
        //web 阶段分页对象：PageResult--7核心属性
        // 页面传入： currentPage pageSize  查询：data/list   totalCount  计算：prePage nextPage totalPage

        //查询--totalCount
        //SQL: select count(id) from xxx where xxx
        //MQL: db.xxx.find({...}).count();
        //xxxService.selectForCount(qo);

        long totalcount = template.count(query, StrategyComment.class);
        if (totalcount == 0) {
            return Page.empty();
        }
        //查询--list
        //SQL: select * from xxx where xxx limit?,?
        //MQL: db.xxx.find({...}).skip(?).limit(?);

        //方案1：分页
        //query.skip((qo.getCurrentPage() - 1 ) * qo.getPageSize()).limit(qo.getPageSize());
        //方案2：分页
        //参数1：当前页(从0开始)， 参数2：每页显示条数

        Pageable pageable = PageRequest.of(qo.getCurrentPage() - 1, qo.getPageSize());
        query.with(pageable);
        List<StrategyComment> list = template.find(query, StrategyComment.class);

        return new PageImpl<>(list, pageable, totalcount);

    }
}
