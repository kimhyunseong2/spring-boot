package com.example.demo.repository;

import com.example.demo.entity.Board;

import com.mysql.cj.protocol.x.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b WHERE b.id = :id")
    Board selectBoardDetail(Long id);

    @Modifying
    @Query("UPDATE Board b set b.hit = b.hit + 1 where b.id = :id")
    void updateHitCount(Long id);

    @Override
    Optional<Board> findById(Long id);

    List<Board> findByTitleContaining(String keyword);

    void deleteByUsername(String username);
}
