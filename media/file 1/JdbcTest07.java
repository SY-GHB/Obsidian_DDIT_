package kr.or.ddit.jdbcTest0112;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.utill0112.DBUtill;

/*
	회원을 관리하는 프로그램을 작성하시오. (mymember테이블 이용)
	아래 메뉴의 기능을 모두 구현하시오.(CRUD 기능 구현 연습)
	메뉴 예시
	 1. 자료 추가			==> insert (C)
	 2. 자료 삭제			==> delete (D)
	 3. 자료 수정			==> update (U)
	 4. 전체 자료 출력		==> select (R)
	 0. 작업 끝
	--------------------------
	 
	 조건
	 1) 자료 추가에서 '회원ID'는 중복되지 않는다. (중복되면 다시입력받는다.)
	 2) 자료 삭제는 '회원ID'를 입력받아서 처리한다.
	 3) 자료 수정에서 '회원ID'는 변경되지 않는다.
 */

public class JdbcTest07 {
	static Scanner sc = new Scanner(System.in);
	
	Connection conn = DBUtill.getConnection();
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int result = 0;
	
	public static void main(String[] args) {
		JdbcTest07 jt = new JdbcTest07();
		while(true) {
			jt.printMenu();
			
			int sel = sc.nextInt();
			sc.nextLine(); //버퍼비우기
			switch (sel) {
			case 1:
				jt.insertData();
				break;
			case 2:
				jt.deleteData();
				break;
			case 3:
				jt.updateData();
				break;
			case 4:
				jt.printData();
				break;
			case 0:
				System.out.println("프로그램을 종료합니다.");
				return;
			default:
				break;
			}
		}
	}
	
	public void printMenu() {
		System.out.println();
		System.out.println();
		System.out.println("원하는 메뉴를 선택하세요.");
		System.out.println("──────────────────────────────────────");
		System.out.println("1. 자료 추가");
		System.out.println("2. 자료 삭제");
		System.out.println("3. 자료 수정");
		System.out.println("4. 전체 자료 출력");
		System.out.println("0. 작업 끝");
		System.out.println("──────────────────────────────────────");
		System.out.println();
		System.out.println();
	}
	
	public void insertData() {
		String sql = "insert into mymember(MEM_ID, MEM_PASS, MEM_NAME, MEM_TEL, MEM_ADDR)"
				+ "values (?, ?, ?, ?, ?)";
		
		System.out.println(" 아이디 입력 >> ");
		String id =sc.nextLine();
		
		//중복ID검사
		id = alreadyId(id);
		
		System.out.println(" 비밀번호 입력 >> ");
		String pass = sc.nextLine();
		System.out.println(" 이름 입력 >> ");
		String name = sc.nextLine();
		System.out.println(" 전화번호 입력 >> ");
		String tel = sc.nextLine();
		System.out.println(" 주소 입력 >> ");
		String adrr = sc.nextLine();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			pstmt.setString(3, name);
			pstmt.setString(4, tel);
			pstmt.setString(5, adrr);
			
			result = pstmt.executeUpdate();
			if(result<1) {
				System.out.println("입력에 실패하였습니다.");
			}else {
				System.out.println("입력에 성공하였습니다.");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteData() {
		String sql = "delete from mymember where mem_id = ?";
		System.out.println(" 삭제할 아이디 입력 >> ");
		String id = sc.nextLine();
		
		id=realId(id);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
			if(result<1) {
				System.out.println("삭제에 실패하였습니다.");
			}else {
				System.out.println("삭제에 성공하였습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateData() {
		String sql = "update mymember set MEM_PASS=?, MEM_NAME=?,"
				+ "MEM_TEL=?, MEM_ADDR=?"
				+ "where mem_id=?";
		
		System.out.println("정보를 수정하고 싶은 회원의 id를 입력하세요 >> ");
		String id =sc.nextLine();
		id=realId(id);
		
		System.out.println("수정할 pass >> ");
		String pass = sc.nextLine();
		System.out.println("수정할 name >> ");
		String name = sc.nextLine();
		System.out.println("수정할 tel >> ");
		String tel = sc.nextLine();
		System.out.println("수정할 addr >> ");
		String addr = sc.nextLine();
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pass);
			pstmt.setString(2, name);
			pstmt.setString(3, tel);
			pstmt.setString(4, addr);
			pstmt.setString(5, id);
			
			result = pstmt.executeUpdate();
			if(result<1) {
				System.out.println("수정에 실패하였습니다.");
			}else {
				System.out.println("수정에 성공하였습니다.");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void printData() {
		String sql ="select * from mymember";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			System.out.println("ID\tPASS\tNAME\tTEL\tADDR");
			System.out.println("──────────────────────────────────────");
			while(rs.next()) {
				System.out.println(rs.getString("MEM_ID")+"\t"+rs.getString("MEM_PASS")+"\t"+
							rs.getString("MEM_NAME")+"\t"+rs.getString("MEM_TEL")+"\t"+rs.getString("MEM_ADDR"));
			}
			System.out.println("──────────────────────────────────────");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String alreadyId(String id) {
		while(true) {
			
			String idchk = "select * from mymember where mem_id=?";
			try {
				pstmt = conn.prepareStatement(idchk);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					System.out.println("이미 있는 아이디입니다. 다시 입력해주세요. >> ");
					id =sc.nextLine();
				}else {
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return id;
	}
	
	public String realId(String id) {
		while(true) {
			String idchk = "select * from mymember where mem_id=?";
			try {
				pstmt = conn.prepareStatement(idchk);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					break;
				}else {
					System.out.println("존재하지 않는 아이디입니다. 다시 입력해주세요. >> ");
					id =sc.nextLine();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return id;
	}
}
