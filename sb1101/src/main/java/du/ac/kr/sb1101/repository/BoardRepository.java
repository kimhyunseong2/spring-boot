package du.ac.kr.sb1101.repository;


import du.ac.kr.sb1101.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
//    @Query("SELECT new Board(b.boardIdx, b.title, b.contents, b.hitCnt, b.creatorId, b.createdDatetime " +
//            ", b.updaterId, b.updatedDatetime, b.deletedYn) FROM Board b WHERE b.deletedYn = 'N' ORDER BY b.boardIdx DESC")
    @Query("SELECT b FROM Board b WHERE b.deletedYn = 'N' ORDER BY b.boardIdx DESC")
    List<Board> selectBoardList();

    @Query("SELECT b FROM Board b WHERE b.boardIdx = :boardIdx AND b.deletedYn = 'N' ")
    Board selectBoardDetail(int boardIdx);

    @Modifying
    @Query("UPDATE Board b set b.hitCnt = b.hitCnt + 1 where b.boardIdx = :boardIdx")
    void updateHitCount(int boardIdx);

    @Query("SELECT b FROM Board b WHERE b.deletedYn='N' ORDER BY b.createdDatetime DESC")
    List<Board> selectLatestPost();

    @Modifying
    @Query("UPDATE Board b set b.title = :#{#board.title}, b.contents = :#{#board.contents}, b.updatedDatetime = current_timestamp , b.updaterId = 'admin' WHERE b.boardIdx = :#{#board.boardIdx}")
    void updateBoard(Board board);

    @Modifying
    @Query("UPDATE Board b set b.deletedYn ='Y', b.updatedDatetime = current_timestamp, b.updaterId = 'admin' WHERE b.boardIdx = :boardIdx")
    void deleteBoard(int boardIdx);
}
