package cn.wolfcode.wolf2w.comment.repository;

import cn.wolfcode.wolf2w.comment.domain.TravelComment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TravelCommentRepository extends MongoRepository<TravelComment,String> {
    /**
     * 查找指定游记下的评论集合
     * @param travelId
     * @return
     */
    List<TravelComment> findByTravelId(Long travelId);
}
