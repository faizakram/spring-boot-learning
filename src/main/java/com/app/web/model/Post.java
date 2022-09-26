package com.app.web.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

import lombok.Data;


/**
 * The persistent class for the post database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Post.findAll", query="SELECT p FROM Post p")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	private String description;

	@Column(name="end_date")
	private Date endDate;

	@Column(name="fixed_bid")
	private Double fixedBid;

	@Column(name="is_purchased")
	private boolean isPurchased;

	@Column(name="media_file_name")
	private String mediaFileName;

	@Column(name="min_bid")
	private Double minBid;

	@Column(name="created_on")
	private Timestamp createdOn;
	
	@Column(name="modified_on")
	private Timestamp modifiedOn;

	@Column(name="start_date")
	private Date startDate;

	private String title;

	//bi-directional many-to-one association to EnumItemMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bid_type")
	private EnumItemMaster bidType;

	//bi-directional many-to-one association to EnumItemMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type")
	private EnumItemMaster type;
	
	//bi-directional many-to-one association to PostTagUser
	
	@OneToMany(mappedBy="post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<PostTagUser> postTagUsers;
	
	//bi-directional many-to-one association to PostTagUser
	@OneToMany(mappedBy="post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<PostComment> postComments;
	
	public Post() {
	}
	
	public Post(Long id) {
		this.id = id;
	}

}