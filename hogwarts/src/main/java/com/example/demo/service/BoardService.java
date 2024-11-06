package com.example.demo.service;

import com.example.demo.entity.Board;
import com.example.demo.entity.User;
import com.example.demo.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;


    public Board selectLatestPost() throws Exception {
        List<Board> results = boardRepository.selectLatestPost();
        return results.isEmpty() ? null : results.get(0);

    }

    public List<Board> selectAllPosts()  {
        return boardRepository.findAll();
    }


}
