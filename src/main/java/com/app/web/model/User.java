package com.app.web.model;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * The persistent class for the user database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="meta_mask_id")
	private String metaMaskId;
	
	private String bio;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(name="display_name")
	private String displayName;

	private String email;

	@Column(name="personal_site")
	private String personalSite;

	@Column(name="profile_banner")
	private String profileBanner;

	@Column(name="profile_img")
	private String profileImg;

	@Column(name="twitter_username")
	private String twitterUsername;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<UserRelation> userRelations;
	
	@OneToMany(mappedBy="follower", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<UserRelation> userFollowerRelations;

	private String url;
	// bi-directional many-to-one association to EnumItemMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_type")
	private EnumItemMaster postType;

	private String username;
	
	@Column(name="email_verification")
	private String emailVerification;

	public User() {
	}

	public User(Long id) {
		this.id = id;
	}
}