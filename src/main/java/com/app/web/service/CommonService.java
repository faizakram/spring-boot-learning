package com.app.web.service;

import java.util.List;
import java.util.Optional;

import com.app.web.dto.CommonDTO;
import com.app.web.model.EnumItemMaster;
import com.app.web.model.User;

/**
 * Common Service
 * 
 * @author Faiz Akram
 *
 */
public interface CommonService {
	/**
	 * getUser
	 * 
	 * @param userId
	 * @return
	 */
	public User getUser(Long userId);

	/**
	 * get EnumItem
	 * 
	 * @param bidTypeId
	 * @return
	 */
	public EnumItemMaster getEnumItem(Long bidTypeId);

	/**
	 * 
	 * @param object
	 * @param key
	 * @return
	 */
	public Object isNotPresent(Optional<?> object, String key);

	/**
	 * get Master Key
	 * 
	 * @return
	 */
	public List<CommonDTO> getMasterKey();

	/**
	 * getEnumItem
	 * @param enumCode
	 * @param enumItemCode
	 * @return
	 */
	EnumItemMaster getEnumItem(String enumCode, String enumItemCode);

}
