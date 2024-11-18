package du.ac.kr.sb1029.controller;



import du.ac.kr.sb1029.entity.Notice;
import du.ac.kr.sb1029.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;

	@GetMapping("/")
	public String index() {
		return "redirect:/board/openBoardList.do";
	}

	@GetMapping("/board/openBoardList.do")
	public String openBoardList(Model model, @PageableDefault(page = 0, size = 2) Pageable pageable) throws Exception {
		List<Notice> list = noticeService.selectBoardList();
		final int start = (int) pageable.getOffset();
		final int end = Math.min(start + pageable.getPageSize(), list.size());
		final Page<Notice> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
		model.addAttribute("list", page);
		return "/board/boardList";
	}
	
	@RequestMapping("/board/openBoardWrite.do")
	public String openBoardWrite() throws Exception{
		return "/board/boardWrite";
	}


	
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(Notice notice) throws Exception{
		noticeService.insertBoard(notice);
		return "redirect:/board/openBoardList.do";
	}
	
	@GetMapping("/board/openBoardDetail.do")
	public String openBoardDetail(@RequestParam int boardIdx, Model model) throws Exception{
		Notice notice = noticeService.selectBoardDetail(boardIdx);
		model.addAttribute("notice", notice);
		return "/board/boardDetail";
	}
	
	@RequestMapping("/board/updateBoard.do")
	public String updateBoard(Notice notice) throws Exception{
		noticeService.updateBoard(notice);
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(int boardIdx) throws Exception{
		noticeService.deleteBoard(boardIdx);
		return "redirect:/board/openBoardList.do";
	}
}
