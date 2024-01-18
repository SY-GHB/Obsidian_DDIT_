package kr.or.ddit.mvc.controller;

import java.util.List;
import java.util.Scanner;

import kr.or.ddit.mvc.service.IMemberService;
import kr.or.ddit.mvc.service.MemberServiceImple;
import kr.or.ddit.mvc.vo.MemberVO;

public class MemberController {
	//Service 객체의 참조값이 저장될 변수 선언
	private Scanner sc;
	private IMemberService service;
	
	//생성자
	public MemberController() {
		sc = new Scanner(System.in);
		//Service 객체 생성
		service = new MemberServiceImple();
	}
	
	public static void main(String[] args) {
		new MemberController().startMember();
	}
	
	//시작 메서드
	public void startMember() {
		while(true) {
			printMenu();
			int sel = sc.nextInt();
			sc.nextLine(); //버퍼비우기
			
			switch (sel) {
			case 1:
				insertMember();
				break;
			case 2:
				deleteMember();
				break;
			case 3:
				updateMember();
				break;
			case 4:
//				jt.udateData2();
				break;
			case 5:
				getAllMember();
				break;
			case 0:
				System.out.println("프로그램을 종료합니다.");
				return;
			default:
				System.out.println("작업번호를 잘못 고르셨습니다.");
				break;
			}
		}
		
	}

	public void printMenu() {
		System.out.println();
		System.out.println();
		System.out.println("────────────────────────────────────────────────────────────");
		System.out.println("원하는 메뉴를 선택하세요.");
		System.out.println("────────────────────────────────────────────────────────────");
		System.out.println("1. 자료 추가");
		System.out.println("2. 자료 삭제");
		System.out.println("3. 자료 수정");
		System.out.println("4. 선택 자료 수정");
		System.out.println("5. 전체 자료 출력");
		System.out.println("0. 작업 끝");
		System.out.println("────────────────────────────────────────────────────────────");
		System.out.println();
		System.out.println();
	}
	
	private void insertMember() {
		System.out.println("추가할 정보를 입력하세요.");
		
		int count = 0;
		String mem_id = null;
		
		do {
			
			System.out.println(" 아이디 입력 >> ");
			mem_id =sc.nextLine();
			
			count = service.countID(mem_id);
			
			if(count>0) {
				System.out.println("이미 있는 아이디입니다.");
			}
		} while (count>0);
		
		System.out.println(" 비밀번호 입력 >> ");
		String mem_pass = sc.nextLine();
		System.out.println(" 이름 입력 >> ");
		String mem_name = sc.nextLine();
		System.out.println(" 전화번호 입력 >> ");
		String mem_tel = sc.nextLine();
		System.out.println(" 주소 입력 >> ");
		String mem_addr = sc.nextLine();
		
		//입력이 완료되면 입력한 자료들을 VO객체에 저장한다.
		MemberVO memVo = new MemberVO();
		memVo.setMem_id(mem_id);
		memVo.setMem_pass(mem_pass);
		memVo.setMem_name(mem_name);
		memVo.setMem_tel(mem_tel);
		memVo.setMem_addr(mem_addr);
		
		//Service의 insert메소드를 호출해서 입력한다.
		int cnt = service.insertMember(memVo);
		
		if(cnt>0) {
			System.out.println("insert작업 성공");
		}else System.out.println("insert작업 실패");
	}
	
	private void deleteMember() {
		int count = 0;
		String mem_id = null;
		
		do {
			System.out.println(" 삭제할 아이디 입력 >> ");
			mem_id =sc.nextLine();
			
			count = service.countID(mem_id);
			
			if(count<1) {
				System.out.println("존재하지 않는 아이디입니다.");
			}
		} while (count<1);
		
		int cnt = service.deleteMember(mem_id);
		if(cnt>0) {
			System.out.println("delete작업 성공");
		}else System.out.println("delete작업 실패");
	}
	
	private void updateMember() {
		
		int count = 0;
		String mem_id = null;
		
		do {
			System.out.println(" 수정할 아이디 입력 >> ");
			mem_id =sc.nextLine();
			
			count = service.countID(mem_id);
			
			if(count<1) {
				System.out.println("존재하지 않는 아이디입니다.");
			}
		} while (count<1);
		
		System.out.println(" 비밀번호 입력 >> ");
		String mem_pass = sc.nextLine();
		System.out.println(" 이름 입력 >> ");
		String mem_name = sc.nextLine();
		System.out.println(" 전화번호 입력 >> ");
		String mem_tel = sc.nextLine();
		System.out.println(" 주소 입력 >> ");
		String mem_addr = sc.nextLine();
		
		MemberVO memVo = new MemberVO();
		memVo.setMem_id(mem_id);
		memVo.setMem_pass(mem_pass);
		memVo.setMem_name(mem_name);
		memVo.setMem_tel(mem_tel);
		memVo.setMem_addr(mem_addr);
		
		
		int cnt = service.updateMember(memVo);
		
		if(cnt>0) {
			System.out.println("delete작업 성공");
		}else System.out.println("delete작업 실패");
	}
	
	public void getAllMember() {
		List<MemberVO> memList = service.getAllMember();
		System.out.println("────────────────────────────────────────────────────────────");
		System.out.println("ID\tPASS\tNAME\tTEL\t\tADDR");
		System.out.println("────────────────────────────────────────────────────────────");
		
		if(memList.isEmpty()) {
			System.out.println("\t\t회원이 존재하지 않습니다..");
		}
		
		for(MemberVO mv : memList) {
			System.out.println(mv.getMem_id()+"\t"+mv.getMem_pass()+"\t"+mv.getMem_name()
			+"\t"+mv.getMem_tel()+"\t"+mv.getMem_addr());
		}
		System.out.println("────────────────────────────────────────────────────────────");
	}
}
