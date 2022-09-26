package com.app.web.model;

import java.io.Serializable;

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
 * The persistent class for the enum_item_master database table.
 * 
 */
@Data
@Entity
@Table(name="enum_item_master")
@NamedQuery(name="EnumItemMaster.findAll", query="SELECT e FROM EnumItemMaster e")
public class EnumItemMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="enum_item_code")
	private String enumItemCode;

	@Column(name="enum_item_name")
	private String enumItemName;

	//bi-directional many-to-one association to EnumMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="enum_id")
	private EnumMaster enumMaster;
	
	public EnumItemMaster() {
	}
	public EnumItemMaster(Long id) {
		this.id = id;
	}
}