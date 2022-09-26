package com.app.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.web.dto.HashTagDTO;
import com.app.web.model.Tag;
import com.app.web.utils.CommonConstant;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
	@Query("select new com.app.web.dto.HashTagDTO(t.id, t.name) from Tag t where t.isActive = true")
	List<HashTagDTO> getHashTags();
	@Query("select new com.app.web.dto.HashTagDTO(t.id, t.name) from Tag t where t.name=:param1 and t.isActive = true")
	HashTagDTO findByName(@Param(CommonConstant.PARAM1) String tagName);

}
