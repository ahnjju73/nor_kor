package com.example.north_defector.repository;

import com.example.north_defector.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Board findByBoardNo(Integer boardNo);
}
