package com.example.vuestargram.repogitory;

import com.example.vuestargram.model.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepogitory  extends JpaRepository<Board, Long> {
    // lazy로 설정할 경우
    @EntityGraph(attributePaths = {"user"}) // board에 있는 user 프로퍼티
    Optional<Board> findById(long id);
}
