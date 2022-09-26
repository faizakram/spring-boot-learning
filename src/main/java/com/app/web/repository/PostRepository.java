package com.app.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.web.model.Post;
import com.app.web.utils.CommonConstant;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

	@Query("select p from Post p join p.createdBy u where u.id=:param1 order by p.createdOn desc")
	List<Post> getPosts(@Param(CommonConstant.PARAM1) Long userId);

	@Query("select p from Post p join p.createdBy u where u.id=:param1 and p.id=:param2")
	Post findByPostIdAndCreatedBy(@Param(CommonConstant.PARAM1) Long userId, @Param(CommonConstant.PARAM2) Long postId);

	@Query("select pc.id, p from Post p join p.postComments pc join pc.enumItemMaster eim join pc.createdBy u where u.id=:param1 and eim.enumItemCode=:param2 order by pc.createdOn desc")
	List<Object[]> getPostsByUserIdAndMasterCode(@Param(CommonConstant.PARAM1) Long userId,
			@Param(CommonConstant.PARAM2) String favouriteEnumCode);
}
