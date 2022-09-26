package com.app.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.web.dto.UserRequest;
import com.app.web.model.UserRelation;
import com.app.web.utils.CommonConstant;

@Repository
public interface UserRelationRepository extends JpaRepository<UserRelation, Long> {

	@Query("select new com.app.web.dto.UserRequest(f.id, f.username, f.displayName, f.bio, f.profileImg, ur.id, case when u.emailVerification='Verified' then true else false end) from UserRelation ur join ur.enumItemMaster eim join ur.follower f join ur.user u where u.id=:param1 and eim.id =:param2")
	List<UserRequest> getRelationVice(@Param(CommonConstant.PARAM1) Long userId,
			@Param(CommonConstant.PARAM2) Long masterKeyId);

	@Query("select new com.app.web.dto.UserRequest(u.id, u.username, u.displayName, u.bio, u.profileImg, ur.id, u.emailVerification='Verified') from UserRelation ur join ur.enumItemMaster eim join ur.follower f join ur.user u where f.id=:param1 and eim.id =:param2")
	List<UserRequest> getRelationVersa(@Param(CommonConstant.PARAM1) Long userId, @Param(CommonConstant.PARAM2) Long masterKeyId);
	
	@Query("select count(ur) from UserRelation ur join ur.enumItemMaster eim join ur.follower f join ur.user u where f.id=:param1 and eim.enumItemCode =:param2")
	Long getFollowerCount(@Param(CommonConstant.PARAM1) Long userId, @Param(CommonConstant.PARAM2) String followerEnumCode);
	
	@Query("select ur.id from UserRelation ur join ur.enumItemMaster eim join ur.follower f "
			+ "join ur.user u where f.id=:param1 and u.id=:param2 and eim.enumItemCode =:param3")
	Long getRelationId(@Param(CommonConstant.PARAM1) Long userId, 
			@Param(CommonConstant.PARAM2) Long postUserId, 
			@Param(CommonConstant.PARAM3) String followerEnumCode);

}
