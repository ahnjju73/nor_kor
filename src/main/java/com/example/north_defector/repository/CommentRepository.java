package com.example.north_defector.repository;

import com.example.north_defector.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByBoardNo(Integer boardNo);
    List<Comment> findAllByBoardNoAndUpCommNoIsNullOrderByCreatedAtDesc(Integer boardNo);
    List<Comment> findAllByUpCommNoOrderByCreatedAtDesc(Integer upCommNo);

}
