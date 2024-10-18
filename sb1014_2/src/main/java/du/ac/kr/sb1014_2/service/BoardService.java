package du.ac.kr.sb1014_2.service;



import du.ac.kr.sb1014_2.entity.Board;

import java.util.List;

public interface BoardService {
	
	List<Board> selectBoardList() throws Exception;

	Board selectLatestPost() throws Exception;

	void insertBoard(Board board) throws Exception;

	Board selectBoardDetail(int boardIdx) throws Exception;

	void updateBoard(Board board) throws Exception;

	void deleteBoard(int boardIdx) throws Exception;
}
