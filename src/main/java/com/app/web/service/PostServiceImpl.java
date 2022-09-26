package com.app.web.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.app.web.dto.CommentDTO;
import com.app.web.dto.HashTagDTO;
import com.app.web.dto.PaginationInfo;
import com.app.web.dto.PostDTO;
import com.app.web.dto.SearchResult;
import com.app.web.exception.ErrorCodeHelper;
import com.app.web.exception.ErrorInfo;
import com.app.web.exception.ServiceException;
import com.app.web.model.EnumItemMaster;
import com.app.web.model.Post;
import com.app.web.model.PostComment;
import com.app.web.model.PostTagUser;
import com.app.web.model.Tag;
import com.app.web.model.User;
import com.app.web.repository.CommonRepository;
import com.app.web.repository.PostCommentRepository;
import com.app.web.repository.PostRepository;
import com.app.web.repository.TagRepository;
import com.app.web.repository.UserRelationRepository;
import com.app.web.utils.CommonConstant;
import com.app.web.utils.DateUtil;
import com.app.web.utils.ErrorConstant;

@Service
@Transactional
public class PostServiceImpl implements PostService {

	@Autowired
	private CommonService commonService;

	@Autowired
	private PostCommentRepository postCommentRepository;
	@Autowired
	private UserRelationRepository userRelationRepository;
	@Autowired
	private UserService userService;

	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private ErrorCodeHelper errorCodeHelper;

	@Autowired
	private CommonRepository commonRepository;

	@Override
	public Long addPost(Long userId, PostDTO postDto) {
		Post post = new Post();
		Timestamp currentDate = DateUtil.getCurrentTimestampInUTC();
		post.setCreatedOn(currentDate);
		post.setPostTagUsers(new ArrayList<>());
		return addOrUpdatePost(userId, post, postDto, currentDate);
	}

	/**
	 * 
	 * @param post
	 * @param post Dto
	 * @return
	 */
	private Long addOrUpdatePost(Long userId, Post post, PostDTO postDto, Timestamp currentDate) {
		post.setBidType(new EnumItemMaster(postDto.getBidTypeId()));
		post.setCreatedBy(new User(userId));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setStartDate(DateUtil.convertStringToDate(postDto.getStartDate(), CommonConstant.DATE_FORMAT_MM_DD_YYYY));
		post.setEndDate(
				DateUtil.convertStringToDate(postDto.getExpirationDate(), CommonConstant.DATE_FORMAT_MM_DD_YYYY));
		post.setFixedBid(postDto.getFixedBid());
		post.setMinBid(postDto.getMinBid());
		post.setPurchased(postDto.isPurchased());
		post.setType(new EnumItemMaster(postDto.getType()));
		post.setMediaFileName(postDto.getMediaFileName());
		post.setModifiedOn(currentDate);
		if (!ObjectUtils.isEmpty(postDto.getHashTagIds()))
			addorUpdatePostTagUser(post, postDto.getHashTagIds(), userId, currentDate);
		postRepository.save(post);
		return postRepository.save(post).getId();
	}

	/**
	 * add or Update Post Tag User
	 * 
	 * @param post
	 * @param hashTags
	 * @param userId
	 * @param currentDate
	 */
	private void addorUpdatePostTagUser(Post post, List<Long> hashTags, Long userId, Timestamp currentDate) {
		post.getPostTagUsers().clear();
		for (Long tagId : hashTags) {
			PostTagUser postTagUser = new PostTagUser();
			postTagUser.setCreatedOn(currentDate);
			postTagUser.setPost(post);
			postTagUser.setUserId(new User(userId));
			postTagUser.setTag(new Tag(tagId));
			post.getPostTagUsers().add(postTagUser);
		}
	}

	@Override
	public Long updatePost(Long userId, PostDTO postDTO) {
		Timestamp currentDate = DateUtil.getCurrentTimestampInUTC();
		return addOrUpdatePost(userId, getPostById(postDTO.getId()), postDTO, currentDate);
	}

	/**
	 * get Post By Id
	 * 
	 * @param postId
	 * @return
	 */
	private Post getPostById(Long postId) {
		return (Post) commonService.isNotPresent(postRepository.findById(postId), CommonConstant.POST);
	}

	@Override
	public PostDTO getPost(Long id) {
		return setPostInCommon(commonRepository.getPostById(id), new HashMap<>(), new HashMap<>(), null, false);
	}

	@Override
	public PostDTO getPost(Long id, Long userId) {
		return setPostInCommon(commonRepository.getPostById(id, userId), new HashMap<>(), new HashMap<>(), userId,
				true);
	}

	private List<HashTagDTO> setHashTag(Post post) {
		List<PostTagUser> postTagUsers = post.getPostTagUsers();
		if (!ObjectUtils.isEmpty(postTagUsers)) {
			return postTagUsers.stream().map(m -> new HashTagDTO(m.getTag().getId(), m.getTag().getName()))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public Long addComment(Long userId, CommentDTO comment) {

		if (postCommentRepository.isAlreadyFavourite(userId, comment.getPostId(), comment.getType(),
				CommonConstant.FAVOURITE_ENUM_ITEM_CODE)) {
			ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(ErrorConstant.E1011_ERROR_CODE,
					ErrorConstant.E1011_ERROR_DESCRIPTION);
			throw new ServiceException(errorInfo);
		} else if (postCommentRepository.isAlreadyFavourite(userId, comment.getPostId(), comment.getType(),
				CommonConstant.LIKE_ENUM_CODE)) {
			ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(ErrorConstant.E1011_ERROR_CODE,
					ErrorConstant.E1011_ERROR_DESCRIPTION);
			throw new ServiceException(errorInfo);
		}

		return addOrUpdateComment(userId, new PostComment(), comment);
	}

	/**
	 * add Or Update Comment
	 * 
	 * @param userId
	 * @param postComment
	 * @param commnet
	 * @return
	 */
	private Long addOrUpdateComment(Long userId, PostComment postComment, CommentDTO commnet) {
		postComment.setComment(commnet.getComment());
		postComment.setCreatedBy(new User(userId));
		postComment.setCreatedOn(DateUtil.getCurrentTimestampInUTC());
		postComment.setEnumItemMaster(new EnumItemMaster(commnet.getType()));
		if (commnet.getParentCommentId() != null)
			postComment.setCommentId(new PostComment(commnet.getParentCommentId()));
		postComment.setPost(new Post(commnet.getPostId()));
		return postCommentRepository.save(postComment).getId();
	}

	/**
	 * get Post Comment
	 * 
	 * @param postComment
	 * @param userId
	 * @return
	 */
	private CommentDTO getPostComment(PostComment postComment, Long userId) {
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setId(postComment.getId());
		commentDTO.setComment(postComment.getComment());
		commentDTO.setUser(userService.getUserInfo(postComment.getCreatedBy(), false));
		commentDTO.setCommentOwner(ObjectUtils.nullSafeEquals(postComment.getCreatedBy().getId(), userId));
		if (!ObjectUtils.isEmpty(postComment.getCommentId()))
			commentDTO.setParentCommentId(postComment.getCommentId().getId());
		commentDTO.setCommentDate(
				DateUtil.convertDateToString(CommonConstant.DATE_FORMAT_MM_DD_YYYY, postComment.getCreatedOn()));
		commentDTO.setType(postComment.getEnumItemMaster().getId());
		if (!ObjectUtils.isEmpty(postComment.getPostComments())) {
			commentDTO.setComments(new ArrayList<>());
			for (PostComment pcomment : postComment.getPostComments()) {
				commentDTO.getComments().add(getPostComment(pcomment, userId));
			}
		}
		return commentDTO;
	}

	@Override
	public Long updateComment(Long userId, CommentDTO comment) {
		return addOrUpdateComment(userId, (PostComment) commonService
				.isNotPresent(postCommentRepository.findById(comment.getId()), CommonConstant.COMMENT), comment);
	}

	@Override
	public SearchResult<CommentDTO> getComments(Long userId, Long postId, PaginationInfo paginationInfo) {

		List<PostComment> postComments = commonRepository.getComments(postId,
				Arrays.asList(CommonConstant.COMMENT_ENUM_ITEM_CODE, CommonConstant.LIKE_ENUM_CODE), paginationInfo);
		List<CommentDTO> comments = new ArrayList<>();
		for (PostComment postComment : postComments) {
			comments.add(getPostComment(postComment, userId));
		}
		return new SearchResult<>(comments, paginationInfo);
	}

	@Override
	public HashTagDTO addHashTag(Long userId, HashTagDTO hashTag) {
		HashTagDTO hashTagDTO = tagRepository.findByName(hashTag.getTagName());
		if (hashTagDTO != null) {
			return hashTagDTO;
		}
		Tag tag = new Tag();
		tag.setCreatedBy(new User(userId));
		tag.setCreatedOn(DateUtil.getCurrentTimestampInUTC());
		tag.setName(hashTag.getTagName());
		tag.setActive(true);
		tag = tagRepository.save(tag);
		hashTag.setId(tag.getId());
		return hashTag;
	}

	@Override
	public List<HashTagDTO> getHashTags() {
		return tagRepository.getHashTags();
	}

	@Override
	public boolean deleteHashTag(Long id, Long userId) {
		Tag tag = (Tag) commonService.isNotPresent(tagRepository.findById(id), CommonConstant.TAG);
		if (tag.getCreatedBy().getId().equals(userId)) {
			tagRepository.delete(tag);
			return true;
		} else {
			ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(ErrorConstant.E1014_ERROR_CODE,
					ErrorConstant.E1014_ERROR_DESCRIPTION, CommonConstant.USER);
			throw new ServiceException(errorInfo);
		}
	}

	@Override
	public boolean deletePost(Long userId, Long postId) {
		Post post = postRepository.findByPostIdAndCreatedBy(userId, postId);
		if (post == null) {
			ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(ErrorConstant.E1016_ERROR_CODE,
					ErrorConstant.E1016_ERROR_DESCRIPTION);
			throw new ServiceException(errorInfo);
		}
		postRepository.delete(post);
		return true;
	}

	@Override
	public boolean deleteComment(Long userId, Long id) {
		PostComment postComment = postCommentRepository.findByCommentIdAndCreatedBy(userId, id);
		if (postComment == null) {
			ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(ErrorConstant.E1016_ERROR_CODE,
					ErrorConstant.E1016_ERROR_DESCRIPTION);
			throw new ServiceException(errorInfo);
		}
		postCommentRepository.delete(postComment);
		return true;
	}

	@Override
	public SearchResult<PostDTO> getMyFavoritePosts(Long userId, PaginationInfo paginationInfo) {
		return setPostInfo(commonRepository.getMyFavoritePosts(userId, paginationInfo), paginationInfo, userId);
	}

	@Override
	public SearchResult<PostDTO> getPosts(PaginationInfo paginationInfo) {
		return setPostInfo(commonRepository.getPosts(paginationInfo), paginationInfo, null);
	}

	/**
	 * setPostInfo
	 * 
	 * @param postObjects
	 * @param paginationInfo
	 * @param isUserBased
	 * @return
	 */
	private SearchResult<PostDTO> setPostInfo(List<Object[]> postObjects, PaginationInfo paginationInfo,
			Long loggedInUserId) {
		List<PostDTO> postDtos = new ArrayList<>();
		Map<Long, Long> followerCount = new HashMap<>();
		Map<Long, Long> relationIdMap = new HashMap<>();
		for (Object[] postArray : postObjects) {
			postDtos.add(setPostInCommon(postArray, followerCount, relationIdMap, loggedInUserId, false));
		}
		return new SearchResult<>(postDtos, paginationInfo);
	}

	/**
	 * set Post In Common
	 * 
	 * @param postArray
	 * @param followerCount
	 * @param relationIdMap
	 * @param loggedInUserId
	 * @param isEdit
	 * @return
	 */
	private PostDTO setPostInCommon(Object[] postArray, Map<Long, Long> followerCount, Map<Long, Long> relationIdMap,
			Long loggedInUserId, boolean isEdit) {
		Post post = (Post) commonService.isNotPresent(Optional.of(postArray[0]), CommonConstant.POST);
		PostDTO postDto = getPostInfo(post, (User) postArray[1]);
		Long userId = postDto.getUser().getId();
		if (followerCount.get(userId) == null) {
			followerCount.put(userId,
					userRelationRepository.getFollowerCount(userId, CommonConstant.FOLLOWING_ENUM_ITEM_CODE));
		}
		postDto.setFollowerCount(followerCount.get(userId));
		postDto.setLikeCount((Long) postArray[2]);
		postDto.setCommentCount((Long) postArray[3]);
		postDto.setFavouriteCount((Long) postArray[4]);
		postDto.setShareCount((Long) postArray[5]);
		if (!ObjectUtils.isEmpty(loggedInUserId)) {
			postDto.setLiked((Long) postArray[6] > 0);
			postDto.setFav((Long) postArray[7] > 0);
			postDto.setLikeId((Long) postArray[6]);
			postDto.setFavouriteId((Long) postArray[7]);
			if (relationIdMap.get(userId) == null) {
				relationIdMap.put(userId, userRelationRepository.getRelationId(userId, loggedInUserId,
						CommonConstant.FOLLOWING_ENUM_ITEM_CODE));
			}
			postDto.setFollowingId(relationIdMap.get(userId));
			postDto.setFollowing(relationIdMap.get(userId) != null);
		}
		if (ObjectUtils.nullSafeEquals(post.getCreatedBy().getId(), loggedInUserId) && isEdit) {
			postDto.setPostOwner(isEdit);
			postDto.setFixedBid(post.getFixedBid());
			postDto.setMinBid(post.getMinBid());
			postDto.setPurchased(post.isPurchased());
			postDto.setStartDate(
					DateUtil.convertDateToString(CommonConstant.DATE_FORMAT_MM_DD_YYYY, post.getStartDate()));
			postDto.setExpirationDate(
					DateUtil.convertDateToString(CommonConstant.DATE_FORMAT_MM_DD_YYYY, post.getEndDate()));
		}
		return postDto;
	}

	@Override
	public SearchResult<PostDTO> getPostsUser(PaginationInfo paginationInfo, Long userId) {
		return setPostInfo(commonRepository.getPostsUser(paginationInfo, userId), paginationInfo, userId);
	}

	@Override
	public Map<String, Object> getPostBySearch(String search) {
		Map<String, Object> map = new HashMap<>();
		map.put(CommonConstant.TAG, commonRepository.getByTag(search));
		map.put(CommonConstant.POST, commonRepository.geByPost(search));
		map.put(CommonConstant.USER, commonRepository.getByUser(search));
		return map;
	}

	@Override
	public SearchResult<PostDTO> getPostByTagId(Long tagId, PaginationInfo paginationInfo, Long loggedInUserId) {
		if (ObjectUtils.isEmpty(loggedInUserId))
			return setPostInfo(commonRepository.getPostByTagId(tagId, paginationInfo), paginationInfo, null);
		else
			return setPostInfo(commonRepository.getPostByTagId(tagId, paginationInfo, loggedInUserId), paginationInfo,
					loggedInUserId);

	}

	@Override
	public SearchResult<PostDTO> getPostsByUserId(Long userId, PaginationInfo paginationInfo, Long loggedInUserId) {
		if (ObjectUtils.isEmpty(loggedInUserId))
			return setPostInfo(commonRepository.getPostsByUserId(userId, paginationInfo), paginationInfo, null);
		else
			return setPostInfo(commonRepository.getPostsByUserId(userId, paginationInfo, loggedInUserId),
					paginationInfo, loggedInUserId);
	}

	/**
	 * getPostInfo
	 * 
	 * @param post
	 * @param user
	 * @return
	 */
	private PostDTO getPostInfo(Post post, User user) {
		PostDTO postDTO = new PostDTO();
		postDTO.setId(post.getId());
		postDTO.setTitle(post.getTitle());
		postDTO.setMediaFileName(post.getMediaFileName());
		postDTO.setDescription(post.getDescription());
		postDTO.setType(post.getType().getId());
		postDTO.setHashTags(setHashTag(post));
		postDTO.setUser(userService.getUserInfo(user, false));
		postDTO.setPostedOn(
				DateUtil.convertDateToString(CommonConstant.DATE_FORMAT_MM_DD_YYYY_HH_MM_SS, post.getCreatedOn()));
		return postDTO;
	}

	@Override
	public SearchResult<PostDTO> getTrendingPosts(PaginationInfo paginationInfo) {
		return setPostInfo(commonRepository.getTrendingPosts(paginationInfo), paginationInfo, null);
	}

	@Override
	public SearchResult<PostDTO> getTrendingPostsUser(PaginationInfo paginationInfo, Long userId) {
		return setPostInfo(commonRepository.getTrendingPostsUser(paginationInfo, userId), paginationInfo, userId);
	}

}
