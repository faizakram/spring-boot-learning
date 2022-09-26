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

import lombok.Data;

/**
 * The persistent class for the tag database table.
 * 
 */
@Data
@Entity
@NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	@Column(name = "created_on")
	private Timestamp createdOn;
	@Column(name = "is_active")
	private boolean isActive;

	private String name;

	// bi-directional many-to-one association to PostTagUser
	@OneToMany(mappedBy = "tag", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<PostTagUser> postTagUsers;

	public Tag() {
	}

	/**
	 * 
	 * @param id
	 */
	public Tag(long id) {
		this.id = id;
	}

}