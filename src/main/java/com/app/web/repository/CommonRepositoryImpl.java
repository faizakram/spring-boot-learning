package com.app.web.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.app.web.dto.HashTagDTO;
import com.app.web.dto.PaginationInfo;
import com.app.web.dto.PostDTO;
import com.app.web.dto.UserRequest;
import com.app.web.model.Post;
import com.app.web.model.PostComment;
import com.app.web.utils.CommonConstant;
import com.app.web.utils.DateUtil;

@Repository
public class CommonRepositoryImpl implements CommonRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<Post> getPostsByLoggedInUser(Long userId, String followingEnumCode, PaginationInfo paginationInfo) {
		String hql = "select distinct(p) from Post p join p.createdBy u where u.id=:param1 or u.id "
				+ "in(select ur.follower.id from UserRelation ur where ur.user.id=:param1 and "
				+ "ur.enumItemMaster.enumItemCode=:param2) order by p.createdOn desc";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, userId);
		query.setParameter(CommonConstant.PARAM2, followingEnumCode);
		query.setFirstResult((paginationInfo.getCurPage() - 1) * paginationInfo.getPageSize());
		query.setMaxResults(paginationInfo.getPageSize());
		return query.getResultList();
	}

	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<Object[]> getPostsByUserId(Long userId, PaginationInfo paginationInfo) {
		String hql = "select p, ur, " + "SUM(case when em.enumItemCode =:param1 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param2 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param4 then 1 else 0 end) " + "from Post p "
				+ "left join p.postComments pc " 
				+ "left join pc.enumItemMaster em "
				+ "join p.createdBy ur where ur.id=:param5 " + "group by p.id " + "order by p.createdOn desc";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, CommonConstant.LIKE_ENUM_CODE);
		query.setParameter(CommonConstant.PARAM2, CommonConstant.COMMENT_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM3, CommonConstant.FAVOURITE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM4, CommonConstant.SHARE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM5, userId);
		query.setFirstResult((paginationInfo.getCurPage() - 1) * paginationInfo.getPageSize());
		query.setMaxResults(paginationInfo.getPageSize());
		return query.getResultList();
	}

	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<Post> getPosts(PaginationInfo paginationInfo, boolean istrending) {
		String hql = "";
		if (istrending) {
			hql = "select distinct(p) from Post p join p.postComments pc "
					+ "where pc.createdOn <=:param1 and pc.createdOn>=:param2 group by p.id order by count(pc.id) desc";

		} else
			hql = "select distinct(p) from Post p order by p.createdOn desc";
		Query query = entityManager.createQuery(hql);
		if (istrending) {
			Date today = DateUtil.getCurrentTimestampInUTC();
			Date lastDay = DateUtil.addDaysToDate(today, -7);
			query.setParameter(CommonConstant.PARAM1, today);
			query.setParameter(CommonConstant.PARAM2, lastDay);
		}

		query.setFirstResult((paginationInfo.getCurPage() - 1) * paginationInfo.getPageSize());
		query.setMaxResults(paginationInfo.getPageSize());
		return query.getResultList();
	}

	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<Object[]> getTrendingPosts(PaginationInfo paginationInfo) {
		String hql = "select p, user, " + "SUM(case when em.enumItemCode =:param1 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param2 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param4 then 1 else 0 end) " + "from Post p "
				+ "join p.createdBy user " + "left join p.postComments pc " + "left join pc.enumItemMaster em "
				+ "where pc.createdOn <=:param5 and pc.createdOn>=:param6 " + "group by p.id "
				+ "order by count(pc.id) desc";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, CommonConstant.LIKE_ENUM_CODE);
		query.setParameter(CommonConstant.PARAM2, CommonConstant.COMMENT_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM3, CommonConstant.FAVOURITE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM4, CommonConstant.SHARE_ENUM_ITEM_CODE);
		Date today = DateUtil.getCurrentTimestampInUTC();
		Date lastDay = DateUtil.addDaysToDate(today, -7);
		query.setParameter(CommonConstant.PARAM5, today);
		query.setParameter(CommonConstant.PARAM6, lastDay);
		query.setFirstResult((paginationInfo.getCurPage() - 1) * paginationInfo.getPageSize());
		query.setMaxResults(paginationInfo.getPageSize());
		return query.getResultList();
	}

	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<Object[]> getPosts(PaginationInfo paginationInfo) {
		String hql = "select p, user, " + "SUM(case when em.enumItemCode =:param1 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param2 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param4 then 1 else 0 end) " + "from Post p "
				+ "join p.createdBy user " + "left join p.postComments pc " + "left join pc.enumItemMaster em "
				+ "group by p.id " + "order by p.createdOn desc";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, CommonConstant.LIKE_ENUM_CODE);
		query.setParameter(CommonConstant.PARAM2, CommonConstant.COMMENT_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM3, CommonConstant.FAVOURITE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM4, CommonConstant.SHARE_ENUM_ITEM_CODE);
		query.setFirstResult((paginationInfo.getCurPage() - 1) * paginationInfo.getPageSize());
		query.setMaxResults(paginationInfo.getPageSize());
		return query.getResultList();
	}

	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<Object[]> getPostsUser(PaginationInfo paginationInfo, Long userId) {
		String hql = "select p, ur, " 
				+ "SUM(case when em.enumItemCode =:param1 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param2 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param4 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param1 and pcuser.id=:param5 then pc.id else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 and pcuser.id=:param5 then pc.id else 0 end) " 
				+ "from Post p "
				+ "left join p.postComments pc left join pc.createdBy pcuser " 
				+ "left join pc.enumItemMaster em " 
				+ "join p.createdBy ur where ur.id not in(select "
				+ "ure.follower.id from UserRelation ure where ure.user.id "
				+ "=:param5 and ure.enumItemMaster.enumItemCode =:param6) " 
				+ "group by p.id " 
				+ "order by p.createdOn desc";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, CommonConstant.LIKE_ENUM_CODE);
		query.setParameter(CommonConstant.PARAM2, CommonConstant.COMMENT_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM3, CommonConstant.FAVOURITE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM4, CommonConstant.SHARE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM5, userId);
		query.setParameter(CommonConstant.PARAM6, CommonConstant.BLOCKED_ENUM_ITEM_CODE);
		query.setFirstResult((paginationInfo.getCurPage() - 1) * paginationInfo.getPageSize());
		query.setMaxResults(paginationInfo.getPageSize());
		return query.getResultList();
	}

	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<Object[]> getTrendingPostsUser(PaginationInfo paginationInfo, Long userId) {
		String hql = "select p, ur, " + "SUM(case when em.enumItemCode =:param1 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param2 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param4 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param1 and pcuser.id=:param5 then pc.id else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 and pcuser.id=:param5 then pc.id else 0 end) "
				+ "from Post p left join p.postComments pc left join pc.createdBy pcuser " 
				+ "left join pc.enumItemMaster em "
				+ "join p.createdBy ur " 
				+ "where pc.createdOn <=:param6 and pc.createdOn>=:param7 group by p.id "
				+ "order by count(pc.id) desc";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, CommonConstant.LIKE_ENUM_CODE);
		query.setParameter(CommonConstant.PARAM2, CommonConstant.COMMENT_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM3, CommonConstant.FAVOURITE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM4, CommonConstant.SHARE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM5, userId);
		Date today = DateUtil.getCurrentTimestampInUTC();
		Date lastDay = DateUtil.addDaysToDate(today, -7);
		query.setParameter(CommonConstant.PARAM6, today);
		query.setParameter(CommonConstant.PARAM7, lastDay);
		query.setFirstResult((paginationInfo.getCurPage() - 1) * paginationInfo.getPageSize());
		query.setMaxResults(paginationInfo.getPageSize());
		return query.getResultList();
	}

	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<UserRequest> getByUser(String search) {
		String hql = "select new com.app.web.dto.UserRequest(u.id, u.username, "
				+ "u.displayName, u.profileImg, case when u.emailVerification='Verified' then true else false end)  from User u where "
				+ "u.username like :param1 or u.displayName like :param1 or "
				+ "u.email like :param1 order by u.displayName";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, String.format(CommonConstant.LIKE_QUERY, search));
		query.setMaxResults(12);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<HashTagDTO> getByTag(String search) {
		String hql = "select new com.app.web.dto.HashTagDTO(t.id, t.name, size(t.postTagUsers)) "
				+ "from Tag t where t.name like :param1 and t.isActive=:param2 order by t.name";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, String.format(CommonConstant.LIKE_QUERY, search));
		query.setParameter(CommonConstant.PARAM2, true);
		query.setMaxResults(12);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<PostDTO> geByPost(String search) {
		String hql = "select new com.app.web.dto.PostDTO(p.id, p.title, p.description, p.mediaFileName) from "
				+ "Post p where p.title like :param1 or p.description like :param1 order by p.createdOn desc";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, String.format(CommonConstant.LIKE_QUERY, search));
		query.setMaxResults(12);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<Object[]> getPostByTagId(Long tagId, PaginationInfo paginationInfo) {
		String hql = "select p, user, " + "SUM(case when em.enumItemCode =:param1 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param2 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param4 then 1 else 0 end) " + "from Post p "
				+ "join p.createdBy user " + "left join p.postComments pc " + "left join pc.enumItemMaster em "
				+ "where p.id in (select ptu.post.id from PostTagUser ptu where ptu.tag.id=:param5) " + "group by p.id "
				+ "order by p.createdOn desc";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, CommonConstant.LIKE_ENUM_CODE);
		query.setParameter(CommonConstant.PARAM2, CommonConstant.COMMENT_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM3, CommonConstant.FAVOURITE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM4, CommonConstant.SHARE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM5, tagId);
		query.setFirstResult((paginationInfo.getCurPage() - 1) * paginationInfo.getPageSize());
		query.setMaxResults(paginationInfo.getPageSize());
		return query.getResultList();
	}

	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<Object[]> getPostsByUserId(Long userId, PaginationInfo paginationInfo, Long loggedInUserId) {
		String hql = "select p, ur, " 
				+ "SUM(case when em.enumItemCode =:param1 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param2 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param4 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param1 and pcuser.id=:param5 then pc.id else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 and pcuser.id=:param5 then pc.id else 0 end) " 
				+ "from Post p "
				+ "left join p.postComments pc left join pc.createdBy pcuser " 
				+ "left join pc.enumItemMaster em "
				+ "join p.createdBy ur where ur.id=:param6 " 
				+ "group by p.id " 
				+ "order by p.createdOn desc";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, CommonConstant.LIKE_ENUM_CODE);
		query.setParameter(CommonConstant.PARAM2, CommonConstant.COMMENT_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM3, CommonConstant.FAVOURITE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM4, CommonConstant.SHARE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM5, loggedInUserId);
		query.setParameter(CommonConstant.PARAM6, userId);
		query.setFirstResult((paginationInfo.getCurPage() - 1) * paginationInfo.getPageSize());
		query.setMaxResults(paginationInfo.getPageSize());
		return query.getResultList();
	}


	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<Object[]> getPostByTagId(Long tagId, PaginationInfo paginationInfo, Long loggedInUserId) {
		String hql = "select p, user, " + "SUM(case when em.enumItemCode =:param1 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param2 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param4 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param1 and pcuser.id=:param6 then pc.id else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 and pcuser.id=:param6 then pc.id else 0 end) " 
				+ "from Post p "
				+ "join p.createdBy user " 
				+ "left join p.postComments pc left join pc.createdBy pcuser " 
				+ "left join pc.enumItemMaster em where "
				+ "p.id in (select ptu.post.id from PostTagUser ptu where ptu.tag.id=:param5) " 
				+ "group by p.id "
				+ "order by p.createdOn desc";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, CommonConstant.LIKE_ENUM_CODE);
		query.setParameter(CommonConstant.PARAM2, CommonConstant.COMMENT_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM3, CommonConstant.FAVOURITE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM4, CommonConstant.SHARE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM5, tagId);
		query.setParameter(CommonConstant.PARAM6, loggedInUserId);
		query.setFirstResult((paginationInfo.getCurPage() - 1) * paginationInfo.getPageSize());
		query.setMaxResults(paginationInfo.getPageSize());
		return query.getResultList();
	}

	@Override
	public Object[] getPostById(Long postId, Long loggedInUserId) {
		String hql = "select p, user, " 
				+ "SUM(case when em.enumItemCode =:param1 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param2 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param4 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param1 and pcuser.id=:param6 then pc.id else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 and pcuser.id=:param6 then pc.id else 0 end) " 
				+ "from Post p "
				+ "join p.createdBy user " 
				+ "left join p.postComments pc left join pc.createdBy pcuser " 
				+ "left join pc.enumItemMaster em where "
				+ "p.id =:param5 " 
				+ "group by p.id " 
				+ "order by p.createdOn desc";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, CommonConstant.LIKE_ENUM_CODE);
		query.setParameter(CommonConstant.PARAM2, CommonConstant.COMMENT_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM3, CommonConstant.FAVOURITE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM4, CommonConstant.SHARE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM5, postId);
		query.setParameter(CommonConstant.PARAM6, loggedInUserId);
		return (Object[]) query.getSingleResult();
	}

	@Override
	public Object[] getPostById(Long postId) {
		String hql = "select p, user, " + "SUM(case when em.enumItemCode =:param1 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param2 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param4 then 1 else 0 end) " 
				+ "from Post p "
				+ "join p.createdBy user " 
				+ "left join p.postComments pc " 
				+ "left join pc.enumItemMaster em where "
				+ "p.id =:param5 " 
				+ "group by p.id " 
				+ "order by p.createdOn desc";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, CommonConstant.LIKE_ENUM_CODE);
		query.setParameter(CommonConstant.PARAM2, CommonConstant.COMMENT_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM3, CommonConstant.FAVOURITE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM4, CommonConstant.SHARE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM5, postId);
		return (Object[]) query.getSingleResult();
	}

	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<Object[]> getMyFavoritePosts(Long userId, PaginationInfo paginationInfo) {
		String hql = "select p, user, " + "SUM(case when em.enumItemCode =:param1 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param2 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param4 then 1 else 0 end), "
				+ "SUM(case when em.enumItemCode =:param1 and ur.id=:param5 then pc.id else 0 end), "
				+ "SUM(case when em.enumItemCode =:param3 and ur.id=:param5 then pc.id else 0 end) " 
				+ "from Post p join p.createdBy user "
				+ "left join p.postComments pc " 
				+ "left join pc.enumItemMaster em "
				+ "left join pc.createdBy ur where ur.id=:param5 and "
				+ "em.enumItemCode =:param3 and user.id not in(select "
				+ "ure.follower.id from UserRelation ure where "
				+ "ure.user.id =:param5 and "
				+ "ure.enumItemMaster.enumItemCode =:param6) " 
				+ "group by p.id "
				+ "order by p.createdOn desc";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, CommonConstant.LIKE_ENUM_CODE);
		query.setParameter(CommonConstant.PARAM2, CommonConstant.COMMENT_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM3, CommonConstant.FAVOURITE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM4, CommonConstant.SHARE_ENUM_ITEM_CODE);
		query.setParameter(CommonConstant.PARAM5, userId);
		query.setParameter(CommonConstant.PARAM6, CommonConstant.BLOCKED_ENUM_ITEM_CODE);
		query.setFirstResult((paginationInfo.getCurPage() - 1) * paginationInfo.getPageSize());
		query.setMaxResults(paginationInfo.getPageSize());
		return query.getResultList();
	}

	@Override
	@SuppressWarnings(CommonConstant.UNCHECKED)
	public List<PostComment> getComments(Long postId, List<String> type, PaginationInfo paginationInfo) {
		String hql = "select pc from PostComment pc join pc.enumItemMaster eim "
				+ "join pc.post p where p.id =:param1 and pc.commentId is null "
				+ "and eim.enumItemCode in(:param2) order by pc.createdOn desc";
		Query query = entityManager.createQuery(hql);
		query.setParameter(CommonConstant.PARAM1, postId);
		query.setParameter(CommonConstant.PARAM2, type);
		query.setFirstResult((paginationInfo.getCurPage() - 1) * paginationInfo.getPageSize());
		query.setMaxResults(paginationInfo.getPageSize());
		return query.getResultList();
	}
	

}
