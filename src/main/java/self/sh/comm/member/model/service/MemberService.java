package self.sh.comm.member.model.service;

import self.sh.comm.member.model.vo.Member;

public interface MemberService {

	Member login(Member inputMember);

	int idDupCheck(Member member);

	int signUp(Member signUpMember);

	int nickDupCheck(Member member);

	int emailDupCheck(Member member);

	int selectApiMemberCount(Member member);

	Member selectApiMember(Member member);

	int snssignUp(Member member);

}
