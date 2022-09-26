package com.app.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.web.model.User;
import com.app.web.utils.CommonConstant;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	@Query("select case when (count(u) > 0) then true else false end from User u where u.email=:param1")
	boolean isUserAlreadyExist(@Param(CommonConstant.PARAM1) String email);

	@Query("select u from User u where u.email =:param1 and u.emailVerification=:param2")
	User findForEmailVerification(@Param(CommonConstant.PARAM1) String email,
			@Param(CommonConstant.PARAM2) String verificationKey);

	@Query("select u from User u where u.metaMaskId=?1")
	User getUser(String metaMaskId);

//	@Query("select new com.app.web.dto.UserRequest(u.id, u.username, u.displayName, u.profileImg) from User u where u.username like %?1% or u.displayName like %?1% or u.email")
//	Page<UserRequest> getByUser(String search, Pageable pageable);

}
