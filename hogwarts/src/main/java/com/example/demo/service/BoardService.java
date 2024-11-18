package com.example.demo.service;

import com.example.demo.entity.Board;
import com.example.demo.entity.Notification;
import com.example.demo.repository.BoardRepository;

import com.example.demo.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public long getTotalBoards() {
        return boardRepository.count();
    }


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
    public Board selectBoardDetail(Long id,boolean increaseHitCount) throws Exception{
        Board board = boardRepository.selectBoardDetail(id);
        if (increaseHitCount) {
            boardRepository.updateHitCount(id);  // 조회수 증가
        }
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


    @Transactional
    public void toggleLike(Long id, String username) {
        // 게시글 찾기
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        String author = board.getUsername();

        // 사용자가 이미 좋아요를 눌렀는지 확인
        if (board.getLikedUsernames().contains(username)) {
            // 이미 좋아요를 눌렀으면 좋아요 취소
            board.setLikeCount(board.getLikeCount() - 1);  // 좋아요 수 감소
            board.getLikedUsernames().remove(username);  // 좋아요한 사용자 목록에서 제거
        } else {
            // 좋아요를 누르지 않았다면 좋아요 추가
            board.setLikeCount(board.getLikeCount() + 1);  // 좋아요 수 증가
            board.getLikedUsernames().add(username);  // 좋아요한 사용자 목록에 추가
            if (!author.equals(username)) {  // 자신이 좋아요를 눌렀을 경우 알림을 보내지 않음
                String message = username + "님이 '" + board.getTitle() + "'에 좋아요를 눌렀습니다.";
                Notification notification = new Notification(message, author);
                notificationRepository.save(notification);  // 알림 저장
            }
        }

        // 변경된 게시글 저장
        boardRepository.save(board);


    }
}
