package com.example.demo.service;

import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Notification;
import com.example.demo.entity.Reply;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private BoardRepository boardRepository;


    // 댓글 저장
    @Transactional
    public void addComment(Long boardId, String username, String content) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        String author = board.getUsername();
        Comment comment = new Comment();
        comment.setBoardId(boardId);  // 게시글 ID 설정
        comment.setUsername(username);
        comment.setContent(content);
        commentRepository.save(comment);
        if (!author.equals(username)) {
            String message = username + "님이 '" + board.getTitle() + "'에 댓글을 달았습니다.";
            Notification notification = new Notification(message, author);
            notificationRepository.save(notification);
        }
    }

    // 게시글에 대한 댓글 목록 가져오기
    public List<Comment> getComments(Long boardId) {
        List<Comment> comments = commentRepository.findByBoardId(boardId);  // 게시글 ID로 댓글 조회
        // 각 댓글에 대한 답글을 추가
        for (Comment comment : comments) {
            List<Reply> replies = replyRepository.findByCommentId(comment.getId());  // 댓글 ID로 답글 조회
            comment.setReplies(replies);
        }
        return comments;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findUserComments();  // 모든 댓글을 조회
    }



    @Transactional
    public void deleteComment(Long commentId) throws Exception{
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void addReply(Long boardId,Long commentId,String username, String content) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        String author = board.getUsername();
        Reply reply = new Reply();
        reply.setBoardId(boardId);
        reply.setCommentId(commentId);
        reply.setUsername(username);
        reply.setContent(content);
        replyRepository.save(reply);
        if (!author.equals(username)) {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
            String message = username + "님이 '" + comment.getContent() + "'에 답글을 달았습니다.";
            Notification notification = new Notification(message, author);
            notificationRepository.save(notification);
        }
    }

    public List<Reply> getReplies(Long boardId) {
        return replyRepository.findByboardId(boardId);
    }

    @Transactional
    public void deleteReply(Long replyId) throws Exception{
        replyRepository.deleteById(replyId);
    }



}
