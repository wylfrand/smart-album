package com.mycompany.database.smartalbum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mycompany.database.smartalbum.model.Image;
import com.mycompany.database.smartalbum.search.vo.ImageResume;

@Repository
public interface IImageJpaRepository  extends JpaRepository<Image, Long>{
	
	@Query("select im from Image im where im.album.id = :albumId")
	Page<Image> queryfindImagesByAlbumIdAndSearchRequest(@Param("albumId") Long albumId,Pageable page);
	
	@Query("select im from Image im where im.user.id = :userId")
	Page<Image> queryfindImagesByUserIdAndSearchRequest(@Param("userId") Long userId,Pageable page);
	
	Image findImageByNameAndAlbumId(@Param("name") String name,@Param("id") Long id);
	
	Image findImageByNameAndUserId(@Param("name") String name,@Param("id") Long id);
	
	@Query("select im.name from Image im where im.album.id = :albumId")
	List<String> queryfindImageNamesByAlbumId(@Param("albumId") Long albumId);
	
	public List<ImageResume> findAllImageResumeByAlbumId(@Param("albumId") Long albumId);
	
}
