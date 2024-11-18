package du.ac.kr.sb1021_3.spring;

import du.ac.kr.sb1021_3.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemberRegisterService {

	@Autowired
	private MemberDao memberDao;


	public Long regist(RegisterRequest req) {
		Member member = memberDao.selectByEmail(req.getEmail());
		if (member != null) {
			throw new DuplicateMemberException("dup email " + req.getEmail());
		}
		Member newMember = new Member(
				req.getEmail(),  req.getName(), req.getPassword(),
				LocalDateTime.now());
		memberDao.insert(newMember);
		return newMember.getId();
	}
}
