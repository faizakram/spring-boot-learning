package com.app.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.web.dto.CommonDTO;
import com.app.web.model.EnumItemMaster;
import com.app.web.utils.CommonConstant;

/**
 * 
 * EnumItem Repository
 *
 */
@Repository
public interface EnumItemMasterRepository extends JpaRepository<EnumItemMaster, Long> {

	@Query("select new com.app.web.dto.CommonDTO(eim.id, eim.enumItemName) from EnumItemMaster eim")
	List<CommonDTO> getMasterKey();

	@Query("select e from EnumItemMaster e join e.enumMaster em where em.enumCode=:param1 and e.enumItemCode=:param2")
	Optional<EnumItemMaster> findByEnumItemCode(@Param(CommonConstant.PARAM1) String enumCode, @Param(CommonConstant.PARAM2) String enumItemCode);

}
