package cn.wolfcode.wolf2w.comment.service;

import cn.wolfcode.wolf2w.comment.domain.StrategyComment;
import cn.wolfcode.wolf2w.comment.domain.TravelComment;


import java.util.List;

public interface ITravelCommentService {
    void save(TravelComment comment);
    void delete(String id);
    void update(TravelComment comment);
    TravelComment get(String id);
    List<TravelComment> list();

    List<TravelComment> query(Long travelId);
}
