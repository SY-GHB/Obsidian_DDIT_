package kr.or.ddit.basic.IO0105;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JTable.PrintMode;

public class PhoneBookIOTest {
	private Scanner sc = new Scanner(System.in);
	private HashMap<String, Phone> map = new HashMap<String, Phone>();
	//데이터가 변경되었는지 여부를 나타내는 변수. (초기화안시켜도 false긴 함)
	private boolean datachk = false;
     
	public static void main(String[] args) {
		PhoneBookIOTest pb = new PhoneBookIOTest();
		
		pb.useBook();
		
		//여기서는 실행만 하자.
	}
	
	
	/* 12월 22일에 했던 숙제를 들고왔다.
	 * 
	 * 추가 조건
	 * 1) '6. 전화번호 저장' 메뉴를 추가하고 구현한다. (저장 파일명: phoneBook.data)
	 * 2) 프로그램이 시작될 때 저장된 파일이 있으면 그 데이터를 읽어와 Map에 저장한다.
	 * 3) 종료할 때 Map의 데이터가 변경되었으면 (추가or수정or삭제가 이루어졌다면) 파일로 저장 후 종료되도록 한다.
	 */
	
	public void useBook() {
		while (true) {
			printMenu();
			startPhone();
			System.out.println("메뉴 선택 >> ");
			int sel = sc.nextInt();
			switch (sel) {
			case 1:
				insertNum();
				break;
			case 2:
				updateNum();
				break;
			case 3:
				deleteNum();
				break;
			case 4:
				searchNum();
				break;
			case 5:
				printListNum();
				break;
			case 6:
				saveNum();
				break;
			case 0:
				if(datachk==true) {
					saveNum();
				}
				System.out.println("전화번호부를 종료합니다.");
				return;
			default:
				break;
			}
		}
	}
	
	//전화번호 정보가 저장된 파일을 읽어오는 메소드
	private void startPhone() {
		try {
			//파일을 읽어와서 거기에 있는 파일을 Map에 저장하자.
			ObjectInputStream oin =null;
			try {
			 oin = new ObjectInputStream(new BufferedInputStream(
							new FileInputStream("e:/d_other/phoneBook.data")));
			} catch (Exception e) {
				return;
			}
			
			Object obj = null;
			
//			System.out.println("\n♡전화기가 켜집니다. 전화번호 불러오기 시작♡");
			
			//방법 1로 저장했을 때
			// map = (HashMap<String, Phone>)oin.readObject();
			
			// 방법 2로 저장했을 때
			while((obj=oin.readObject())!=null) {
				Phone ph = (Phone) obj;
				map.put(ph.getName(), ph);
			}
			
//			System.out.println("\t전화번호 불러오기 성공!\n");
			
		} catch (IOException e) {
			System.out.println("전화번호 불러오기 ...실패?");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//번호 저장(6번 메뉴) map에 추가된 데이터들을 파일로 옮겼다.
	ObjectOutputStream oout =null;
	private void saveNum() {
		try {
			
			 oout = 
					new ObjectOutputStream(new BufferedOutputStream(
							new FileOutputStream("e:/d_other/phoneBook.data")));
			
			 //방법1) Map객체를 파일로 저장한다.
//			oout.writeObject(map);
			
			//방법2) Phone객체를 저장하는 방법이다.
			Set<String> keySet = map.keySet();
			Iterator<String> it = keySet.iterator();
			
			while(it.hasNext()) {
				String key = it.next(); //키값 구하기
				Phone value = map.get(key);
				oout.writeObject(value);
			}
			//반복문으로 하나씩 넣어줄거면 마지막에 null을 넣어줘야 한다.
			oout.writeObject(null);
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(oout!=null) {
				try {
					oout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		//save작업을 했으면 굳이 종료할 때 또 저장할 이유가 없으므로  false로 돌려주자.
		datachk = false;
	}

	//메뉴 출력
	public void printMenu() {
		System.out.println("───────────────────────────────────────────────");
		System.out.println("\t\t메뉴");
		System.out.println("───────────────────────────────────────────────");
		System.out.println("\t\t1. 전화번호 등록");
		System.out.println("\t\t2. 전화번호 수정");
		System.out.println("\t\t3. 전화번호 삭제");
		System.out.println("\t\t4. 전화번호 검색");
		System.out.println("\t\t5. 전화번호 전체 출력");
		System.out.println("\t\t6. 전화번호 저장");
		System.out.println("\t\t0. 프로그램 종료");
		System.out.println("───────────────────────────────────────────────");
	}
	
	// 이미 등록된 사람인지 확인하는 메소드
	public String checkNumOk(String name) {
		boolean check = map.containsKey(name);
		
		if(check==true) {
			System.out.println("이미 전화번호부에 등록된 사람입니다.");
			System.out.println("이름을 다시 입력해주세요.");
			name = sc.next();
		}
		return name;
	}
	
	// 아직 전화번호부에 없는 사람인지 확인하는 메소드
	public String checkNumNot(String name) {
		boolean check = map.containsKey(name);
		
		if(check==false) {
			System.out.println("전화번호부에 등록되지 않은 사람입니다.");
			System.out.println("이름을 다시 입력해주세요.");
			name = sc.next();
		}
		return name;
	}
	
	// 전화번호를 등록하는 메소드
	public void insertNum() {
		System.out.println("새롭게 등록할 전화번호 정보를 입력하세요.");
		
		System.out.println("이름 >> ");
		String name = sc.next();
		name = checkNumOk(name);
		
		System.out.println("전화번호 >> ");
		String tel = sc.next();
		
		sc.nextLine(); //입력 버퍼 비우기
		System.out.println("주소 >> ");
		String addr = sc.nextLine();
		
		Phone phone = new Phone();
		phone.setName(name);
		phone.setTel(tel);
		phone.setAddr(addr);
		
		map.put(name, phone);
		System.out.println(name + "정보가 입력되었습니다.");
		
		//이런 방법도 있다: 다만, 이 방법은 Phone에 String, String, String을 매개변수로 받는 생성자가 있을 때 사용가능하다.
//		map.put(name, new Phone(name, tel, addr));
		datachk = true;
	}
	
	//전화번호를 수정하는 메소드
	public void updateNum() {
		System.out.println("검색할 이름을 입력하세요 >> ");
		String name = sc.next();
		name = checkNumNot(name);

		System.out.println("새로운 번호 >> ");
		String tel = sc.next();
		map.get(name).setTel(tel);
		
		
		sc.nextLine(); // 입력 버퍼 비우기
		System.out.println("새로운 주소 >> ");
		String addr = sc.nextLine();
		map.get(name).setAddr(addr);
		
		try {
			ObjectOutputStream oout = 
					new ObjectOutputStream(new BufferedOutputStream(
							new FileOutputStream("e:/d_other/phoneBook.data")));
			
			Set<String> keySet = map.keySet();
			Iterator<String> it = keySet.iterator();
			while(it.hasNext()) {
				String key = it.next(); //키값 구하기
				Phone value = map.get(key);
				oout.writeObject(value);
			}
			
			oout.writeObject(null);
			oout.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(name+"씨의 수정이 완료되었습니다.");
		datachk = true;
	}
	
	//전화번호를 삭제하는 메소드
	public void deleteNum() {
		System.out.println("삭제할 이름을 입력하세요 >> ");
		String name = sc.next();
		name = checkNumNot(name);
		
		map.remove(name);
		System.out.println(name+"씨의 삭제가 완료되었습니다.");
		
		
		//기존의 삭제작업은 map에서만 지우는 삭제였으니 파일에서도 삭제하는 과정을 추가해봤다.
		try {
			ObjectOutputStream oout = 
					new ObjectOutputStream(new BufferedOutputStream(
							new FileOutputStream("e:/d_other/phoneBook.data")));
			
			Set<String> keySet = map.keySet();
			Iterator<String> it = keySet.iterator();
			while(it.hasNext()) {
				String key = it.next(); //키값 구하기
				Phone value = map.get(key);
				oout.writeObject(value);
			}
			
			oout.writeObject(null);
			oout.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		datachk = true;
	}
	
	// 전화번호를 검색하는 메소드
	public void searchNum() {
		System.out.println("검색할 이름을 입력하세요 >> ");
		String name = sc.next();
		name = checkNumNot(name);
		
		System.out.println("───────────────────────────────────────────────");
		System.out.println("이름\t전화번호\t\t주소");
		System.out.println("───────────────────────────────────────────────");
		System.out.println(name +"\t"+ map.get(name).getTel() +"\t"+ map.get(name).getAddr());
		System.out.println("───────────────────────────────────────────────");
	}
	
	
	public void printListNum() {
		System.out.println("───────────────────────────────────────────────");
		System.out.println("번호\t이름\t전화번호\t\t주소");
		System.out.println("───────────────────────────────────────────────");
		// 여기에.출력문이.들어가야겟죠?
		
		
		Set<String> keySet = map.keySet();
		
		if(keySet.size()==0) {
			System.out.println("\t등록된 번호가 없습니다.");
		}
			
		Iterator<String> it = keySet.iterator();
		int i = 1;		
		for (String name : keySet) {
			Phone p = map.get(name);
			System.out.println(i+"\t"+p.getName()+"\t"+p.getTel()+"\t"+p.getAddr());
			i++;
		}
		
		System.out.println("───────────────────────────────────────────────");
	}
}


class Phone implements java.io.Serializable{
	String name;
	String addr;
	String tel;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
	/*
	 Scanner가 가진 메서드들의 특징
	 - next(), nextInt(), nextDouble() ....
	  ==> 사이띄기(space), Tap, Enter키를 구분문자로 분리해서 분리된 자료만 읽어간다.
	  
	 - nextLine()
	  ==> 한 줄 단위로 읽어간다. 즉, 자료를 입력하고 Enter키를 누르면 Enter키까지 읽어가서 Enter키를 뺀 나머지 데이터를 반환한다.
	 
	 
	 */
	
}