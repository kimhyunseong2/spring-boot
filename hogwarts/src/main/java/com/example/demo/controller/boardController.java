package com.example.demo.controller;

import com.example.demo.entity.*;

import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;
import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;


@Controller
@RequestMapping("/board")
public class boardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/boardList")
    public String boardList(Model model,@PageableDefault(page = 0, size = 5) Pageable pageable,
                                        @RequestParam(value = "keyword", required = false) String keyword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        String role = authentication.getAuthorities().toString();
        model.addAttribute("username", username);
        model.addAttribute("role", role);

        List<Board> boards;

        if (keyword != null && !keyword.isEmpty()) {
            // 제목에 검색어가 포함된 게시글만 검색
            boards = boardService.searchPostsByTitle(keyword);
        } else {
            // 모든 게시글을 가져옴
            boards = boardService.selectAllPosts();
        }

        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), boards.size());
        final Page<Board> page = new PageImpl<>(boards.subList(start, end), pageable, boards.size());
        model.addAttribute("list", page);
        model.addAttribute("keyword", keyword);

        return "board/boardList";
    }

    @GetMapping("/boardWrite")
    public String boardWrite(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        model.addAttribute("username", username);
        Board board = new Board();
        model.addAttribute("board", board);
        return "/board/boardWrite";
    }
    @PostMapping("/insertBoard")
    public String insertBoard(@ModelAttribute Board board) throws Exception {
        // 로그인한 사용자 이름을 가져와서 Board 객체에 설정
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // 로그인한 사용자 이름
        String role = authentication.getAuthorities().toString();
        board.setRole(role);

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

    @GetMapping("/boardDetail")
    public String boardDetail(@RequestParam Long id,@RequestParam(defaultValue = "true") boolean increaseHitCount, Model model) throws Exception {
        Board board = boardService.selectBoardDetail(id,increaseHitCount);

        List<Comment> comments = commentService.getComments(id);  // 게시글 ID로 댓글 목록 조회
        List<Reply> replies = commentService.getReplies(id);


        model.addAttribute("comments", comments);
        model.addAttribute("replies", replies);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("board", board);
        model.addAttribute("loggedInUsername", username);
        return "board/boardDetail";
    }

    @PostMapping("/addComment")
    public String addComment(@RequestParam Long boardId, @RequestParam String content) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        commentService.addComment(boardId, username, content);


        return "redirect:/board/boardDetail?id=" + boardId + "&increaseHitCount=false";
    }

    @PostMapping("/addReply")
    public String addReply(@RequestParam Long boardId, @RequestParam Long commentId, @RequestParam String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        commentService.addReply(boardId,commentId, username, content);
        return "redirect:/board/boardDetail?id=" + boardId + "&increaseHitCount=false";
    }

    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam Long commentId,@RequestParam Long boardId) throws Exception {
        commentService.deleteComment(commentId);
        return "redirect:/board/boardDetail?id=" + boardId + "&increaseHitCount=false";
    }

    @PostMapping("/deleteReply")
    public String deleteReply(@RequestParam Long replyId,@RequestParam Long boardId) throws Exception {
        commentService.deleteReply(replyId);
        return "redirect:/board/boardDetail?id=" + boardId + "&increaseHitCount=false";
    }


    @PostMapping("/updateBoard")
    public String updateBoard(Board board) throws Exception {
        boardService.updateBoard(board);
        return "redirect:/board/boardList";
    }

    @PostMapping("/deleteBoard")
    public String deleteBoard(Long id) throws Exception {
        boardService.deleteBoard(id);
        return "redirect:/board/boardList";
    }


    @PostMapping("/toggleLike")
    public String toggleLike(@RequestParam Long id, Authentication authentication) {
        String username = authentication.getName();  // 현재 로그인한 사용자 이름

        // 좋아요 추가/취소 처리
        boardService.toggleLike(id, username);

        // 게시글 상세 화면으로 리다이렉트
        return "redirect:/board/boardDetail?id=" + id + "&increaseHitCount=false";
    }


}


