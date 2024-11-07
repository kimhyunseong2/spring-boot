package com.example.demo.controller;

import com.example.demo.entity.Board;

import com.example.demo.entity.Member;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
public class boardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;


    @GetMapping("/boardList")
    public String boardList(Model model) {
        List<Board> boards =  boardService.selectAllPosts();
        model.addAttribute("list", boards);
        return "/board/boardList";
    }

    @GetMapping("/boardWrite")
    public String boardWrite(Model model) {

        Board board = new Board();
        model.addAttribute("board", board);
        return "/board/boardWrite";
    }
    @PostMapping("/insertBoard")
    public String insertBoard(@ModelAttribute Board board) throws Exception {
        // 로그인한 사용자 이름을 가져와서 Board 객체에 설정
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // 로그인한 사용자 이름

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("로그인한 사용자가 아닙니다."); // 예외 처리
        }
        board.setUsername(username);  // Board 객체에 사용자 이름 설정
        board.setCreatedDate(LocalDateTime.now());
        board.setModifyDate(LocalDateTime.now());

        boardService.insertBoard(board);

        // 게시글 목록으로 리다이렉트
        return "redirect:/board/boardList";
    }
}


