package self.sh.comm.member.model.service;

import self.sh.comm.member.model.vo.Member;

public interface MemberService {

	Member login(Member inputMember);

	int idDupCheck(Member member);

	int signUp(Member signUpMember);

	int nickDupCheck(Member member);

	int emailDupCheck(Member member);

}
