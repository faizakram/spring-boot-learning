package com.app.web.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the post_tag_user database table.
 * 
 */
@Data
@Entity
@Table(name="post_tag_user")
@NamedQuery(name="PostTagUser.findAll", query="SELECT p FROM PostTagUser p")
public class PostTagUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="created_on")
	private Timestamp createdOn;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User userId;
	
	//bi-directional many-to-one association to Post
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	//bi-directional many-to-one association to Tag
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "tag_id")
	private Tag tag;

}