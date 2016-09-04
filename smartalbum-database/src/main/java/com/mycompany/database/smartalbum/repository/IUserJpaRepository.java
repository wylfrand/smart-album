package com.mycompany.database.smartalbum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mycompany.database.smartalbum.model.User;

@Repository
public interface IUserJpaRepository extends JpaRepository<User, Long>{
	User findUserByEmail(@Param("email") String email);
	User findUserById(@Param("id") Long id);
	User findByLoginAndPasswordHash(@Param("login") String login,@Param("passwordHash") String passwordHash);
	
}
