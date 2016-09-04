package com.mycompany.database.smartalbum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mycompany.database.smartalbum.model.Shelf;

@Repository
public interface IShelfJpaRepository extends JpaRepository<Shelf, Long>{
	
	Shelf findShelfByName(@Param("name") String name);
	
	@Query("select distinct s from Shelf s where (s.shared = true and s.owner.preDefined = true) order by s.name")
	List<Shelf> queryUserShelves();
}
