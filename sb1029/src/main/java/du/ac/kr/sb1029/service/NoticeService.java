package du.ac.kr.sb1029.service;





import du.ac.kr.sb1029.entity.Notice;

import java.util.List;

public interface NoticeService {
	
	List<Notice> selectBoardList() throws Exception;

	void insertBoard(Notice notice) throws Exception;

	Notice selectBoardDetail(int boardIdx) throws Exception;

	void updateBoard(Notice notice) throws Exception;

	void deleteBoard(int boardIdx) throws Exception;
}
