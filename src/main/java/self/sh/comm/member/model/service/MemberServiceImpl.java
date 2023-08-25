package self.sh.comm.member.model.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import self.sh.comm.member.model.dao.MemberDAO;
import self.sh.comm.member.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDAO dao;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	private Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	@Override
	public Member login(Member inputMember) {
		// TODO Auto-generated method stub
		Member loginMember = dao.login(inputMember);
		if(bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			loginMember.setMemberPw(null);
			
		}else {
			loginMember = null;
		}
		return loginMember;
	}
	@Override
	public int idDupCheck(Member member) {
		// TODO Auto-generated method stub
		return dao.idDupCheck(member);
	}
	@Override
	public int signUp(Member signUpMember) {
		// TODO Auto-generated method stub
		String temp = signUpMember.getMemberPw();
		signUpMember.setMemberPw(bcrypt.encode(temp));
		return dao.signUp(signUpMember);
	}
	@Override
	public int nickDupCheck(Member member) {
		// TODO Auto-generated method stub
		return dao.nickDupCheck(member);
	}
	@Override
	public int emailDupCheck(Member member) {
		// TODO Auto-generated method stub
		return dao.emailDupCheck(member);
	}
}
