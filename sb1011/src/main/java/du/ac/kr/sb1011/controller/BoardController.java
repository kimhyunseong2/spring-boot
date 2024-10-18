package du.ac.kr.sb1011.controller;


import du.ac.kr.sb1011.dto.BoardDto;
import du.ac.kr.sb1011.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping("/")
	public String index(){
		return "redirect:/board/boardMain.do";
	}

	@GetMapping("/board/boardMain.do")
	public String boardMain(Model model)throws Exception{
		BoardDto latestPost = boardService.selectLatestPost(); // 최신 게시글 조회
		model.addAttribute("latestPost", latestPost);
		return "board/boardMain";
	}


	@GetMapping("/board/openBoardList.do")
	public String openBoardList(Model model) throws Exception {

		List<BoardDto> list = boardService.selectBoardList();
		model.addAttribute("list", list);
		return "/board/boardList";
	}

	
	@GetMapping("/board/openBoardWrite.do")
	public String openBoardWrite() throws Exception{
		return "/board/boardWrite";
	}
	
	@PostMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board) throws Exception{
		if (board.getTitle() == null || board.getTitle().isEmpty()) {
			throw new IllegalArgumentException("Title is required.");
		}
		if (board.getContents() == null || board.getContents().isEmpty()) {
			throw new IllegalArgumentException("Contents are required.");
		}
		boardService.insertBoard(board);
		return "redirect:/board/openBoardList.do";
	}
	
	@GetMapping("/board/openBoardDetail.do")
	public String openBoardDetail(@RequestParam int boardIdx, Model model) throws Exception{
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		model.addAttribute("board", board);
		return "/board/boardDetail";
	}
	
	@PostMapping("/board/updateBoard.do")
	public String updateBoard(BoardDto board) throws Exception{
		boardService.updateBoard(board);
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		return "redirect:/board/openBoardList.do";
	}
}
