package kr.or.ddit.mvc.service;

import java.util.List;

import kr.or.ddit.mvc.dao.IMemberDao;
import kr.or.ddit.mvc.dao.MemberDaoImple;
import kr.or.ddit.mvc.vo.MemberVO;

public class MemberServiceImple implements IMemberService {
	//DAO객체의 참조값이 저장될 변수 선언 : 오라클용이 아닌 어떤(mybatis나 이것저것...) DAO도 담을 수 있게 하기 위해 - 다형성을 위함
	//변수는 인터페이스로 선언해두고 실제 객체는 객체로 구현해두는것이다.
	private IMemberDao dao;

	//생성자
	public MemberServiceImple() {
		//DAO객체 생성
		dao = new MemberDaoImple();
	}
	
	@Override
	public int insertMember(MemberVO memVo) {
		// TODO Auto-generated method stub
		return dao.insertMember(memVo);
	}

	@Override
	public int deleteMember(String mem_id) {
		// TODO Auto-generated method stub
		return dao.deleteMember(mem_id);
	}

	@Override
	public int updateMember(MemberVO memVo) {
		// TODO Auto-generated method stub
		return dao.updateMember(memVo);
	}

	@Override
	public List<MemberVO> getAllMember() {
		// TODO Auto-generated method stub
		return dao.getAllMember();
	}

	@Override
	public int countID(String mem_id) {
		// TODO Auto-generated method stub
		return dao.countID(mem_id);
	}

}
