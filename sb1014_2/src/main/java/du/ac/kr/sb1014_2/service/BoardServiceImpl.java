package du.ac.kr.sb1014_2.service;



import du.ac.kr.sb1014_2.entity.Board;
import du.ac.kr.sb1014_2.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Override
	public List<Board> selectBoardList() throws Exception {
		return boardRepository.selectBoardList();
	}

	@Override
	public Board selectLatestPost() throws Exception {
		List<Board> results = boardRepository.selectLatestPost();
		return results.isEmpty() ? null : results.get(0);


	}

	@Override
	public void insertBoard(Board board) throws Exception {
		board.setCreatedDatetime(new Date());
		board.setCreatorId("admin");
		board.setHitCnt(0);
		board.setDeletedYn("N");
		boardRepository.save(board);
	}

	@Transactional
	@Override
	public Board selectBoardDetail(int boardIdx) throws Exception{
		Board board = boardRepository.selectBoardDetail(boardIdx);
		boardRepository.updateHitCount(boardIdx);
		return board;
	}

	@Transactional
	@Override
	public void updateBoard(Board board) throws Exception {
		boardRepository.updateBoard(board);
	}

	@Transactional
	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		boardRepository.deleteBoard(boardIdx);
	}
}	

