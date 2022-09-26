package com.app.web.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.web.dto.CommonDTO;
import com.app.web.exception.ErrorCodeHelper;
import com.app.web.exception.ErrorInfo;
import com.app.web.exception.ServiceException;
import com.app.web.model.EnumItemMaster;
import com.app.web.model.User;
import com.app.web.repository.EnumItemMasterRepository;
import com.app.web.repository.UserRepository;
import com.app.web.utils.CommonConstant;
import com.app.web.utils.ErrorConstant;

@Service
@Transactional
public class CommonServiceImpl implements CommonService {

	@Autowired
	private ErrorCodeHelper errorCodeHelper;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EnumItemMasterRepository enumItemMaster;

	@Override
	public User getUser(Long userId) {
		return (User) isNotPresent(userRepository.findById(userId), CommonConstant.USER);
	}

	@Override
	public EnumItemMaster getEnumItem(Long id) {
		return (EnumItemMaster) isNotPresent(enumItemMaster.findById(id), CommonConstant.ENUM_ITEM);
	}
	
	@Override
	public EnumItemMaster getEnumItem(String enumCode ,String enumItemCode) {
		return (EnumItemMaster) isNotPresent(enumItemMaster.findByEnumItemCode(enumCode, enumItemCode), CommonConstant.ENUM_ITEM);
	}

	@Override
	public Object isNotPresent(Optional<?> object, String key) {
		if (!object.isPresent()) {
			ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(ErrorConstant.E1010_ERROR_CODE,
					ErrorConstant.E1010_ERROR_DESCRIPTION, key);
			throw new ServiceException(errorInfo);
		}
		return object.get();
	}

	@Override
	public List<CommonDTO> getMasterKey() {
		return enumItemMaster.getMasterKey();
	}
}
