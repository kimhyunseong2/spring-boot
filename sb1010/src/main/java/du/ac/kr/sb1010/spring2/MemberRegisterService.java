package du.ac.kr.sb1010.spring2;

import du.ac.kr.sb1010.config.ManualBean;
import du.ac.kr.sb1010.spring.DuplicateMemberException;
import du.ac.kr.sb1010.spring.Member;
import du.ac.kr.sb1010.spring.MemberDao;
import du.ac.kr.sb1010.spring.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@ManualBean
@Service
public class MemberRegisterService {
	@Autowired
	private MemberDao memberDao;

	public MemberRegisterService() {
	}
	
	public MemberRegisterService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public Long regist(RegisterRequest req) {
		Member member = memberDao.selectByEmail(req.getEmail());
		if (member != null) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}
		Member newMember = new Member(
				req.getEmail(), req.getPassword(), req.getName(), 
				LocalDateTime.now());
		memberDao.insert(newMember);
		return newMember.getId();
	}
}
