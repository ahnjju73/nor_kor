package com.example.north_defector.repository;

import com.example.north_defector.domain.BoardLike;
import com.example.north_defector.domain.types.BoardLikePk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikePk> {
    Integer countAllByBoardNo(Integer boardNo);
}
