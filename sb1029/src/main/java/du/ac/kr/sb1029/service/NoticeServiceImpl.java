package du.ac.kr.sb1029.service;




import du.ac.kr.sb1029.entity.Notice;
import du.ac.kr.sb1029.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@Override
	public List<Notice> selectBoardList() throws Exception {
		return noticeRepository.findAll();
	}


	@Override
	public void insertBoard(Notice notice) throws Exception {
		notice.setCreatedDatetime(LocalDateTime.now().toString().substring(0,10));
		notice.setCreatorId("admin");
		notice.setHitCnt(0);
		notice.setDeletedYn("N");
		noticeRepository.save(notice);
	}

	@Transactional
	@Override
	public Notice selectBoardDetail(int boardIdx) throws Exception {
		Notice notice = noticeRepository.findById(boardIdx)
				.orElseThrow(() -> new Exception("Notice not found"));// null 체크

		// 조회수 증가
		notice.setHitCnt(notice.getHitCnt() + 1);

		// 변경된 엔티티 저장
		noticeRepository.save(notice);

		return notice;
	}

	@Transactional
	@Override
	public void updateBoard(Notice notice) throws Exception {
		Notice enotice = noticeRepository.findById(notice.getBoardIdx())
				.orElseThrow(() -> new Exception("Notice not found")); // null 체크

		// 필요한 필드만 업데이트
		enotice.setTitle(notice.getTitle());
		enotice.setContents(notice.getContents());
		enotice.setUpdatedDatetime(new Date()); // 현재 시간으로 설정
		enotice.setUpdaterId("admin"); // 수정자 ID 설정

		// 엔티티 저장 (변경된 내용을 반영)
		noticeRepository.save(enotice);
	}

	@Transactional
	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		noticeRepository.deleteById(boardIdx);
	}
}	

