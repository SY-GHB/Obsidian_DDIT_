##### B) 숙제: Map을 이용한 호텔 예약 프로그램
ㅎㅎ수업시간에 내주셨는데 수업시간에 다하고 야자시간 넓게 썼다.
라고 생각했지만
검사하는 메소드에서 잘못된 값이 들어오면 제대로 된 값이 들어올때까지 반복시킬거니까 if가 아니라 while 을 써줘야 했다. thanks to 성진!
```
package homework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class HotelTest {
	HashMap<Integer, Room> map = new HashMap<Integer, Room>();
	Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		HotelTest ht = new HotelTest();
		ht.openHotel();
		ht.useHotel();
	}
	
	//메뉴를 출력하는 메소드
	public int printMenu() {
		System.out.println("───────────────────────────────────────────────");
		System.out.println("\t호텔 문을 열었습니다. 어서오십시오.");
		System.out.println("───────────────────────────────────────────────");
		System.out.println("");
		System.out.println("───────────────────────────────────────────────");
		System.out.println("\t  어떤 업무를 하시겠습니까?");
		System.out.println("───────────────────────────────────────────────");
		System.out.println("1. 체크인   2. 체크아웃   3. 객실상태    4. 업무종료");
		System.out.println("───────────────────────────────────────────────");
		
		System.out.println("메뉴 선택 >> ");
		return sc.nextInt();
	}
	
	public void useHotel() {
		while (true) {
			int sel = printMenu();
			switch (sel) {
			case 1:
				checkIn();
				break;
			case 2:
				checkOut();
				break;
			case 3:
				roomList();
				break;
			case 4:
				System.out.println("호텔 업무를 종료합니다. 안녕히 가십시오.");
				return;
			default:
				break;
			}
		}
	}
	
	//호텔문을 열자마자 201호부터 409호까지의 객실을 만들어주는 메소드♥
	public void openHotel() {
		for (int i = 201; i <= 209; i++) {
			Room cabin = new Room(i, "싱글룸", "-");
			map.put(i, cabin);
		}
		
		for (int i = 301; i <= 309; i++) {
			Room cabin = new Room(i, "더블룸", "-");
			map.put(i, cabin);
		}
		
		for (int i = 401; i <= 409; i++) {
			Room cabin = new Room(i, "스위트룸", "-");
			map.put(i, cabin);
		}
	}
	
	//실제 그 방이 우리 호텔에 있는지 검사하는 메소드
	public int notInHotel(int room) {
		while(!map.containsKey(room)) {
			System.out.println( room + "호 객실은 존재하지 않는 객실입니다.");
			System.out.println( "원하는 객실을 다시 입력해 주세요.");
			System.out.println( "객실 선택>> ");
			room = sc.nextInt();
		}
		return room;
	}
	
	//이미 예약된 방인지 검사하는 메소드
	public int checkReserv(int room) {
		if(!map.get(room).getName().equals("-")) {
			System.out.println( room + "호 객실은 이미 예약되었습니다.");
			System.out.println( "원하는 객실을 다시 입력해 주세요.");
			System.out.println( "객실 선택>> ");
			room = sc.nextInt();
			return room;
		}
		return room;
	}
	
	//체크아웃 시 체크인한 사람이 있는지 확인하는 메소드
	public int checkNotReserv(int room) {
		while(map.get(room).getName().equals("-")) {
			System.out.println( room + "호 객실은 체크인한 사람이 없습니다.");
			System.out.println( "원하는 객실을 다시 입력해 주세요.");
			System.out.println( "객실 선택>> ");
			room = sc.nextInt();
		}
		return room;
	}
	
	//체크인 메소드
	public void checkIn() {
		System.out.println("체크인하실 방 번호를 입력해주세요.");
		System.out.println( "객실 선택>> ");
		int room = sc.nextInt();
		room = notInHotel(room);
		room = checkReserv(room);
		
		System.out.println("체크인하실 분의 성함을 입력해주세요.");
		System.out.println("이름 입력>> ");
		
		sc.nextLine(); // 버퍼 비워주기
		String name = sc.nextLine();
		
		map.get(room).setName(name);
		System.out.println(name + "님의 " + room + "호 체크인이 완료되었습니다.");
	}
	
	//체크아웃 메소드
	private void checkOut() {
		System.out.println("체크아웃하실 방 번호를 입력해주세요.");
		System.out.println( "객실 선택>> ");
		int room = sc.nextInt();
		room = notInHotel(room);
		room = checkNotReserv(room);
		
		map.remove(room).setName("-");
		System.out.println(room + "호 체크아웃이 완료되었습니다.");
	}
	
	//객실리스트를 보여주는 메소드, 투숙객 이름도 보여줄것이다!!
	private void roomList() {
		System.out.println("───────────────────────────────────────────────");
		System.out.println("\t현재 객실 상태");
		System.out.println("───────────────────────────────────────────────");
		System.out.println("방 번호\t방 종류\t투숙객 이름");
		System.out.println("───────────────────────────────────────────────");
		/*이 안에 출력반복문이 들어가겟쥬~*/
		
		//키(룸 번호)들을 뽑아와서 set형으로 만들어줬다.
		// 그리고 이걸 이터레이터로 바꿔서 출력하면 순서가 엉망되지용♥
		// key값을 뽑아와서 리스트로 만들고 정렬 후 출력해보자.
		Set<Integer> keySet = map.keySet();
//		Iterator<Integer> it = keySet.iterator();
		ArrayList<Integer> list = new ArrayList<Integer>(keySet);
		Collections.sort(list);
		for (Integer i : list) {
			Room r = map.get(i);
			System.out.println(r.getRoom()+"\t"+r.getType()+"\t"+r.getName());
		}
		
		
//		for (Integer i : keySet) {
//			Room r = map.get(i);
//			System.out.println(r.getRoom()+"\t"+r.getType()+"\t"+r.getName());
//		}
		
		
		
		System.out.println("───────────────────────────────────────────────");		
	}

}


class Room {
	
	int room;
	// 각 층 1~9호, 2층 싱글룸 3층 더블룸 4층 스위트룸
	String type;
	String name;
	
	public Room(int room, String type, String name) {
		super();
		this.room = room;
		this.type = type;
		this.name = name;
	}
	
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

```



### Scanner가 가진 메소드의 특징
###### 1) buffer
컴퓨터에서 제일 빠른 놈은 CPU고, 제일 느린놈은 입출력장치다.
비단 입출력장치뿐만아니라 뭐든 다 CPU보단 느리다.
여튼 뭐든 간에, CPU가 내린 명령을 다 수행해야 다음 명령을 내리는데,
CPU입장에서는 명령과 명령 사이의 대기시간이 있게 된다. 그 대기시간에 굉장히 많은 일을 할 수 있는데 그 대기시간이 아깝지 않은가? 

그걸 줄이기 위해 만들어낸 개념이 버퍼다. CPU와 CPU보다 속도가 느린 장치 사이에 중간정도의 속도를 가진 저장장치(임시기억장소, RAM에 있다고 한다)를 만드는 것이다. (cache도 유사한?기능인가?)

###### 2) Scanner가 가진 메소드
- .next(), .nextInt()
 탭, 띄어쓰기, 엔터가 구분문자(입력데이터를 분리하는 역할)로 쓰인다.

- .nextLine()
  한 줄 전체를 가져오는 메소드로 엔터를 만날 때까지 데이터를 가져와 엔터를 뺀 부분까지를 반환한다. 즉, 엔터가 구분문자가 아닌 유효한 문자로 인식된다.
  
###### 3) 데이터가 오가는 방식
![[Pasted image 20231222173320.png]]
위와 같은 그림이 있다고 하자.
CPU에서 n1= nextInt();라는 명령을 내리면,
그 명령은 먼저 buffer에  데이터가 있는지 확인하고 데이터가 없다면 buffer가 입력장치에 해당 데이터를 가져오도록 신호를 보낸다.
그에 반응한 입력장치가 데이터를 입력하면 그것이 buffer로, cpu로 가는 흐름이다.

만약 우리가,
```
int n1=scan.nextInt();
int n2=scan.nextInt();
int n3=scan.nextInt(); 이라는 명령을 내리고,
```
1. 1(공백)2(공백)3(엔터)을 입력했다고 치자.

2. 그러면 입력장치에 넣은 ``1(공백)2(공백)3(엔터)``는 buffer로 가고, n1= scan.nextInt()는 buffer에 있는 데이터 중 구분문자 앞, 그러니까 ``1``을 가져간다. 이제 buffer에는 ``(공백)2(공백)3(엔터)``가 남았다.

3. n2 = scan.nextInt()는 buffer에 있는 데이터 중 ``2`` 앞의 ``(공백)``은 구분문자이지 유효한 데이터가 아니므로 ``2``만 가져간다.  이제 buffer에는 ``(공백)3(엔터)``가 남았다.

4. n3 = scan.nextInt()가 buffer의 ``3``을 가져갔다. 이제 buffer에는 ``(엔터)``가 남았다. 사용자는 그냥 1 2 3만 입력했지만, 입력하는 과정에 엔터를 누르므로 찌꺼기처럼 남은 것이다.

###### 4) 문제가 생기는 상황
이렇게 찌꺼기가 생긴 다음에 nextInt()나 next()명령이 실행되면 아무 문제가 없지만,
그 뒤에 nextLine()이 오면 문제가 생기게 된다. 어떤 식의 문제냐면, 아래 예시를 보자.

-  1번 예시
```
String addr = scan.next();
int tel = scan.nextInt();
```

1.  addr=scan.next()라는 명령을 내리고, ``대전시(공백)중구(공백)대흥동(엔터)`` 라는 입력데이터가 buffer까지 갔다고 하자.

2. 그러면 addr는 구분문자 이전, 그러니까 ``대전시``만 들고간다. 그러면 buffer에 ``(공백)중구(공백)대흥동(엔터)`` 가 남은 상태인데,

3. 그 다음에 nextInt()와 만나게 되면 구분문자인 공백은 건너뛰고 ``중구``를 가져가게 된다. 그렇지만 nextInt()는 int형 데이터를 가져가는 명령인데, String인 ``중구``를 가져갈 수 있을리가 만무하다. 

-  2번 예시
```
int num = scan.nextInt();
String name = scan.nextLine();
```

1. scan.nextInt()라는 명령에 1이라는 데이터를 입력했다. 그러면 buffer에는 ``1(엔터)``가 들어가있게 된다.
2. num이 ``1``을 가져가고, buffer에는 ``(엔터)``가 남게 된다.
3. scan.nextLine()이 buffer에서 데이터를 찾다가, 찌꺼기처럼 남은 ``(엔터)``를 유효한 데이터로 들고가게 된다.

사용자가 입력한 적 없지만, String name이라는 변수에 찌꺼기로 남은 엔터, ""가 자동으로 입력되는 것이다.

###### 5) 해결법
요약하자면, 문제는 nextLine()명령 실행 전에 남은 ``(엔터)``가 nextLine() 명령에서 유효한 데이터로 인식되어버리는 점이다.
그렇다면 해결은 어떻게 할까?... 찌꺼기로 남은 ``(엔터)``를 지워버리면 된다. 
어떻게? nextLine()을 써서!
아래를 보자.

```
int num = scan.nextInt();

scan.nextLine();
String name = scan.nextLine();
```

이러면 String name = scan.nextLine(); 명령 윗줄의 명령이 buffer에 찌꺼기처럼 남은 ``(공백)``을 가져가서 버리게 된다. 문제는 길었지만, 해결은 간단하다. 야호!
