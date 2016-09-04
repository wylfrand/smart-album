package com.mycompany.database.smartalbum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mycompany.database.smartalbum.model.Album;

@Repository
public interface IAlbumJpaRepository extends JpaRepository<Album, Long>{
	Album findAlbumByName(@Param("name") String name);
}
