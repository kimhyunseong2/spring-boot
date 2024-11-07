package com.example.demo.repository;

import com.example.demo.entity.Board;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;


import javax.transaction.Transactional;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b WHERE b.username IN (SELECT m.username FROM Member m WHERE m.role = 'ADMIN') ORDER BY b.createdDate DESC")
    List<Board> selectLatestPost();

}
