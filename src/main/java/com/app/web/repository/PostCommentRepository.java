package com.app.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.web.model.PostComment;
import com.app.web.utils.CommonConstant;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

	@Query("select pc from PostComment pc join pc.enumItemMaster eim join pc.post p where p.id =:param1 and pc.commentId is null and eim.enumItemCode in(:param2) order by pc.createdOn desc")
	List<PostComment> getComments(@Param(CommonConstant.PARAM1) Long postId, @Param(CommonConstant.PARAM2) List<String> masterKeys);
	@Query("select pc from PostComment pc where pc.createdBy.id=:param1 and pc.id =:param2")
	PostComment findByCommentIdAndCreatedBy(@Param(CommonConstant.PARAM1) Long userId,@Param(CommonConstant.PARAM2) Long id);
	@Query("select count(pc) > 0 from PostComment pc join pc.enumItemMaster eim join pc.post p where pc.createdBy.id=:param1 and p.id =:param2 and eim.id=:param3 and eim.enumItemCode=:param4")
	boolean isAlreadyFavourite(@Param(CommonConstant.PARAM1)Long userId, @Param(CommonConstant.PARAM2)Long postId, @Param(CommonConstant.PARAM3)Long type, @Param(CommonConstant.PARAM4) String masterKeyName);

}
