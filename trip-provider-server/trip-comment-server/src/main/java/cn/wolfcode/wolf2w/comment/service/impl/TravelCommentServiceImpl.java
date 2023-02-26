package cn.wolfcode.wolf2w.comment.service.impl;

import cn.wolfcode.wolf2w.comment.domain.StrategyComment;
import cn.wolfcode.wolf2w.comment.domain.TravelComment;
import cn.wolfcode.wolf2w.comment.query.StrategyCommentQuery;
import cn.wolfcode.wolf2w.comment.repository.StrategyCommentRepository;
import cn.wolfcode.wolf2w.comment.repository.TravelCommentRepository;
import cn.wolfcode.wolf2w.comment.service.IStrategyCommentService;
import cn.wolfcode.wolf2w.comment.service.ITravelCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TravelCommentServiceImpl implements ITravelCommentService {
    @Autowired
    private TravelCommentRepository repository;

    @Override
    public void save(TravelComment comment) {
        comment.setId(null);
        comment.setCreateTime(new Date());

        // 关联评论的id
        String refid = comment.getRefComment().getId();
        if (StringUtils.hasText(refid)) {
            // 关联的评论
            TravelComment refComment = repository.findById(refid).get();
            comment.setRefComment(refComment);
            comment.setType(TravelComment.TRAVLE_COMMENT_TYPE);
        } else {
            comment.setType(TravelComment.TRAVLE_COMMENT_TYPE_COMMENT);
        }

        repository.save(comment);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void update(TravelComment comment) {

    }

    @Override
    public TravelComment get(String id) {
        return null;
    }

    @Override
    public List<TravelComment> list() {
        return null;
    }

    @Override
    public List<TravelComment> query(Long travelId) {
        return repository.findByTravelId(travelId);
    }
}

