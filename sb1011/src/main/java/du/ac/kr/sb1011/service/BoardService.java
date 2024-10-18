package du.ac.kr.sb1011.service;



import du.ac.kr.sb1011.dto.BoardDto;

import java.util.List;

public interface BoardService {
	
	List<BoardDto> selectBoardList() throws Exception;

	BoardDto selectLatestPost() throws Exception;
	
	void insertBoard(BoardDto board) throws Exception;

	BoardDto selectBoardDetail(int boardIdx) throws Exception;

	void updateBoard(BoardDto board) throws Exception;

	void deleteBoard(int boardIdx) throws Exception;
}
