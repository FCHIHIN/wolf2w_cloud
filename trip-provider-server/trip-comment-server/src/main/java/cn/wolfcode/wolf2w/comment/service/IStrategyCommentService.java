package cn.wolfcode.wolf2w.comment.service;

import cn.wolfcode.wolf2w.comment.domain.StrategyComment;
import cn.wolfcode.wolf2w.comment.query.StrategyCommentQuery;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IStrategyCommentService {
    void save(StrategyComment strategyComment);
    void delete(String id);
    void update(StrategyComment strategyComment);
    StrategyComment get(String id);
    List<StrategyComment> list();

    Page<StrategyComment> query(StrategyCommentQuery qo);
}
