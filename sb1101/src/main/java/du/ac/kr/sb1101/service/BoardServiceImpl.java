package du.ac.kr.sb1101.service;




import du.ac.kr.sb1101.entity.Board;
import du.ac.kr.sb1101.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

