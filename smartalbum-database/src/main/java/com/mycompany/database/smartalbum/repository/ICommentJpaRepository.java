package com.mycompany.database.smartalbum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.database.smartalbum.model.Comment;

@Repository
public interface ICommentJpaRepository extends JpaRepository<Comment, Long>{

}
