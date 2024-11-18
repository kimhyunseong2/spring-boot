package com.example.demo.controller;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Member;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/role/admin")
    public String admin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        model.addAttribute("username", username);
        long totalUsers = userService.getTotalUsers();
        long totalBoards = boardService.getTotalBoards();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalBoards", totalBoards);


        return "/role/admin";
    }

    @GetMapping("/role/users")
    public String getAllUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);

        List<Member> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "/role/users";
    }


    @GetMapping("/role/comments")
    public String getAllComments(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);

        List<Comment> comments = commentService.getAllComments();
        model.addAttribute("comments", comments);

        return "/role/comments";
    }

    @GetMapping("/role/accessDenied")
    public void accessDenied() {
    }
}
