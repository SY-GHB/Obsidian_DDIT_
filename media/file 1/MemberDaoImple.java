package kr.or.ddit.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.mvc.vo.MemberVO;
import kr.or.ddit.util.DBUtill3;

public class MemberDaoImple implements IMemberDao{

	@Override
	public int insertMember(MemberVO memVo) {
		int cnt =0;	//반환값이 저장될 변수
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtill3.getConnection();
			String sql = "insert into mymember(MEM_ID, MEM_PASS, MEM_NAME, MEM_TEL, MEM_ADDR)"
					+ "values (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memVo.getMem_id());
			pstmt.setString(2, memVo.getMem_pass());
			pstmt.setString(3, memVo.getMem_name());
			pstmt.setString(4, memVo.getMem_tel());
			pstmt.setString(5, memVo.getMem_addr());
			
			cnt = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) try {pstmt.close();}catch (Exception e) {e.printStackTrace();}
			if(conn!=null) try {conn.close();}catch (Exception e) {e.printStackTrace();}
		}
		return cnt;
	}

	@Override
	public int deleteMember(String mem_id) {
		int cnt =0;	//반환값이 저장될 변수
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtill3.getConnection();
			String sql = "delete from mymember where mem_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			
			cnt = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) try {pstmt.close();}catch (Exception e) {e.printStackTrace();}
			if(conn!=null) try {conn.close();}catch (Exception e) {e.printStackTrace();}
		}
		return cnt;
	}

	@Override
	public int updateMember(MemberVO memVo) {
		int cnt =0;	//반환값이 저장될 변수
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBUtill3.getConnection();
			String sql = "update mymember set MEM_PASS=?, MEM_NAME=?,"
					+ "MEM_TEL=?, MEM_ADDR=?"
					+ "where mem_id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memVo.getMem_pass());
			pstmt.setString(2, memVo.getMem_name());
			pstmt.setString(3, memVo.getMem_tel());
			pstmt.setString(4, memVo.getMem_addr());
			pstmt.setString(5, memVo.getMem_id());
			
			cnt = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) try {pstmt.close();}catch (Exception e) {e.printStackTrace();}
			if(conn!=null) try {conn.close();}catch (Exception e) {e.printStackTrace();}
		}
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMember() {
		List<MemberVO> memList = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtill3.getConnection();
			String sql ="select * from mymember order by 1";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			memList = new ArrayList<MemberVO>();
			while(rs.next()) {
				//1개의 레코드가 저장될 VO객체 생성
				MemberVO memVo = new MemberVO();
				
				//ResultSet객체의 데이터를 꺼내서 VO객체에 저장한다.
				memVo.setMem_id(rs.getNString("mem_id"));
				memVo.setMem_pass(rs.getNString("mem_pass"));
				memVo.setMem_name(rs.getNString("mem_name"));
				memVo.setMem_tel(rs.getNString("mem_tel"));
				memVo.setMem_addr(rs.getNString("mem_addr"));
				
				//List에 VO객체를 추가한다.
				memList.add(memVo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) try {rs.close();}catch (Exception e) {e.printStackTrace();}
			if(pstmt!=null) try {pstmt.close();}catch (Exception e) {e.printStackTrace();}
			if(conn!=null) try {conn.close();}catch (Exception e) {e.printStackTrace();}
		}
		
		
		return memList;
	}

	@Override
	public int countID(String mem_id) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtill3.getConnection();
			String sql = "select count(*) cnt from mymember where mem_id=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt("cnt");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) try {pstmt.close();}catch (Exception e) {e.printStackTrace();}
			if(conn!=null) try {conn.close();}catch (Exception e) {e.printStackTrace();}
		}
		
		return cnt;
	}

}
