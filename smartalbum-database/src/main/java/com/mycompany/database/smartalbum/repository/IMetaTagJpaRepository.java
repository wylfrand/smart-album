package com.mycompany.database.smartalbum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mycompany.database.smartalbum.model.MetaTag;

@Repository
public interface IMetaTagJpaRepository  extends JpaRepository<MetaTag, Long>{
	MetaTag findMetaTagByTag(@Param("tag") String tag);
	List<MetaTag> findPopularTags();
	List<MetaTag> findSuggestTags(String tag);
}
