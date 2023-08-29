package self.sh.comm.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import self.sh.comm.member.model.vo.Member;

@Repository

public class MemberDAO {
	private Logger logger = LoggerFactory.getLogger(MemberDAO.class);
	@Autowired
	private SqlSessionTemplate sqlSession;
	public int idDupCheck(Member member) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("memberMapper.idDupCheck",member);
	}
	public int signUp(Member signUpMember) {
		// TODO Auto-generated method stub
		return sqlSession.insert("memberMapper.signUp",signUpMember);
	}
	public int nickDupCheck(Member member) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("memberMapper.nickDupCheck",member);
	}
	public int emailDupCheck(Member member) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("memberMapper.emailDupCheck",member);
	}
	public Member login(Member inputMember) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("memberMapper.login",inputMember);
	}
	public int selectApiMemberCount(Member member) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("memberMapper.selectApiMemberCount",member);
	}
	public Member selectApiMember(Member member) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("memberMapper.selectApiMember",member);
	}
	public int snssignUp(Member member) {
		// TODO Auto-generated method stub
		return sqlSession.insert("memberMapper.snssignUp",member);
	}
	
	
}
