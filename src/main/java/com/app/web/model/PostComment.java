package com.app.web.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Data;

/**
 * The persistent class for the post_comment database table.
 * 
 */
@Data
@Entity
@Table(name = "post_comment")
@NamedQuery(name = "PostComment.findAll", query = "SELECT p FROM PostComment p")
public class PostComment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String comment;

	// bi-directional many-to-one association to AddOnsList
	@OrderBy("createdOn DESC")
	@OneToMany(mappedBy = "commentId")
	private List<PostComment> postComments;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id")
	private PostComment commentId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	// bi-directional many-to-one association to EnumItemMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type")
	private EnumItemMaster enumItemMaster;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	public PostComment() {
	}

	public PostComment(Long id) {
		this.id = id;
	}

}