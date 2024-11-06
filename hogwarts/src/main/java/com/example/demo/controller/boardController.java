package com.example.demo.controller;

import com.example.demo.entity.Board;
import com.example.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class boardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/boardList")
    public String boardList(Model model) {
        List<Board> boards =  boardService.selectAllPosts();
        model.addAttribute("list", boards);
        return "/board/boardList";
    }
}
