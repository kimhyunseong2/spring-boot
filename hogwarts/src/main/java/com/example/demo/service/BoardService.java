package com.example.demo.service;

import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public List<Board> selectLatestPost() throws Exception {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("hit")));
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage.getContent();

    }

    public List<Board> selectAllPosts()  {
        return boardRepository.findAll();
    }

    public void insertBoard(Board board) {
        boardRepository.save(board);
    }

    @Transactional
    public Board selectBoardDetail(Long id) throws Exception{
        Board board = boardRepository.selectBoardDetail(id);
        boardRepository.updateHitCount(id);
        return board;
    }


    @Transactional
    public void updateBoard(Board board) {
        Board updateBoard = boardRepository.findById(board.getId())
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        updateBoard.setTitle(board.getTitle());
        updateBoard.setContent(board.getContent());
        updateBoard.setModifyDate(board.getModifyDate());
        boardRepository.save(updateBoard);
    }

    @Transactional
    public void deleteBoard(Long id) throws Exception{
        boardRepository.deleteById(id);
    }

    public List<Board> searchPostsByTitle(String keyword) {
        return boardRepository.findByTitleContainingIgnoreCase(keyword);
    }



}
