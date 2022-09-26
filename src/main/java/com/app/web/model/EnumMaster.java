package com.app.web.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the enum_master database table.
 * 
 */
@Data
@Entity
@Table(name="enum_master")
@NamedQuery(name="EnumMaster.findAll", query="SELECT e FROM EnumMaster e")
public class EnumMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="enum_code")
	private String enumCode;

	//bi-directional many-to-one association to EnumItemMaster
	@OneToMany(mappedBy="enumMaster")
	private List<EnumItemMaster> enumItemMasters;

}