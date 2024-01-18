### 2. Stack과 Queue
둘 다 리스트가 가진 특징을 가지고 있다.
-  동기화 처리가 된다.
-  .clear()메소드도 사용할 수 있다.
-  isEmpty(): Collection에 자료가 있으면 true, 없으면 false를 반환한다.

#### 1) Stack
First In Last Out(FILO, 선입후출) 혹은 LIFO(후입선출)이라고 부르는 방식으로 작동한다.
1. 자료를 입력:  .push()
2. 자료의 출력:  .pop(), .peek()
 -  .pop(): 자료를 꺼내온 뒤 꺼내온 자료를 Stack에서 삭제한다. 가장 마지막 데이터부터 차례대로 꺼내온다.
 - .peek(): 자료의 삭제 없이 가장 마지막 데이터부터 꺼내온다.

###### 그럼 스택은 어디서 쓰일까?
콜스택(메소드가 호출된 후 호출된 자리로 돌아갈 때 자리값을 메소드에 저장하는 것),
실행취소에도 스택이 쓰인다.
웹브라우저에서 앞으로/뒤로가기 내역도 스택을 이용해 처리한다.

###### Stack으로 앞으로/뒤로가기를 만들어보자.
```
package kr.or.ddit.basic;

import java.util.Stack;

import print.Print;

public class StackTest {
	public static void main(String[] args) {
		Browser b = new Browser();
		
		b.history();
		
		b.goURL("1. 네이버");
		b.history();
		
		b.goURL("2. 야후");
		b.history();
		
		b.goURL("3. 구글");
		b.goURL("4. 다음");
		b.history();
		
		System.out.println("뒤로 가기 후");
		b.goBack();
		b.history();
		
//		System.out.println("앞으로 가기 후");
//		b.goForward();
//		b.history();
		
		System.out.println("새로운 사이트로 접속한 후");
		b.goURL("5. 네이트");
		b.history();
	}
}

// 웹브라우저에서 앞으로/뒤로 가기 기능 구현 클래스 작성(스택 이용)
class Browser extends Print{
	//url주소가 저장되어야 하니까 String.
	private Stack<String> back; 	// 이전 방문 내용이 저장될 스택
	private Stack<String> forward;  // 다음 방문 내용이 저장될 스택
	private String currentURL;  	// 현재 페이지
	
	
	//생성자
	public Browser() {
		back = new Stack<String>();
		forward = new Stack<String>();
		currentURL = "";
	}
	
	//사이트를 방문하는 메서드 ==> 매개변수에는 방문할 URL이 저장된다.
	public void goURL(String url) {
		System.out.println(url + " 사이트에 접속합니다.");
		
		//현재페이지가 있으면(공백(초기값)도 null도 아니면)
		//현재 페이지를 back스택에 추가한다.
		if(currentURL != null && !"".equals(currentURL)) {
			back.push(currentURL);
		}
		
		currentURL = url; // 현재 페이지를 이동할 페이지로 변경한다.
		forward.clear();
		// 뒤로 한번 간 상태에서 앞으로가기가 아닌 다른 방법으로
		// 다른 사이트에 이동한다면 앞으로 가기 목록이 사라진다.
	}
	
	// 뒤로가는 메서드  ==> 현재 페이지를 forward 스택에 추가하고,
	// back스택에서 주소를 꺼내와 현재 페이지로 변경한다.
	public void goBack() {
		if(!back.isEmpty()) {
			forward.push(currentURL);
			// 스택에 쌓인 가장 마지막 주소를 현재페이지에 넣어준다.
			currentURL = back.pop();
		}
	}
	
	// 앞으로 가는 메서드 ==> 현재페이지를 back스택에 추가하고,
	// forward스택에서 주소를 꺼내와 현재 페이지로 변경한다.
	public void goForward() {
		if(!forward.isEmpty()) {
			back.push(currentURL);
		}
		currentURL = forward.pop();
	}
	
	//방문 기록을 확인(출력)하는 메서드
	public void history() {
		printBox("방문기록");
		
		System.out.println("back ==> " + back);
		System.out.println("현재 ==> " + currentURL);
		System.out.println("forward ==> " + forward);
	}
	
}
```

#### 2) Queue
Stack과 달리 선입선출의 방식으로 작동하며, 인터페이스만 있기 때문에 
```
Queue<String> queue = new LinkedList<String>();
```
방식으로 생성할 수 있다.

1. 자료를 입력:  .offer()
2. 자료의 출력:  .poll(), .peek()
 -  .poll(): 자료를 꺼내온 뒤 꺼내온 자료를 Queue에서 삭제한다. 가장 앞의 데이터부터 차례대로 꺼내온다.
 - .peek(): 자료의 삭제 없이 가장 앞쪽 데이터부터 꺼내온다.
 
###### 그럼 큐는 어디서 쓰일까?
프린터가 프린트할 때 프린트 명령을 할 때 썼던 문서들이 큐에 저장된다.
문서번호가 1, 2, 3 번호로 명령을 내렷으면 1번문서 출력 후 2번문서가 출력된다.(선입선출)
차례대로, 순서대로 처리되는 곳은 주로 큐로 쓴다.

### 3. 정렬
정렬과 관련된 interface는 Comparable, Comparator 이렇게 두가지가 있다.
	인터페이스란? 추상클래스만 모여있는 덩어리. 당연히 메소드 재정의가 필요하겠다.

==Comparable==은 Collection에 추가되는 데이터 자체에 정렬 기준을 넣고 싶을 때 구현하는 인터페이스(내부 정렬 기준)이고, Comparable에서는 <u>compareTo()</u>메서드를 재정의해야 한다.

==Comparator==는 외부에 별도로 정렬 기준을 구현하고 싶을 때 구현하는 인터페이스(외부 정렬 기준)이고, Comparator에서는 <u>compare()</u>메서드를 재정의해야 한다.

String 클래스, Wrapper 클래스, Date클래스, File클래스에는 내부 정렬 기준이 이미 구현되어 있는데, 이들은 기본적으로 오름차순이다.

```
정렬 작업은 Collections.sort(컬렉션객체); 메서드를 이용하여 정렬한다.
```

#### 1) Comparable : 내부 정렬 기준

생성자를 만들고, getter/setter를 선언하고, compareTo()메서드를 재정의했다.
```
class Member implements Comparable<Member>{
	private int num; // 회원번호
	private String name; //회원이름
	private String tel; //전화번호
	
	public Member(int num, String name, String tel) {
		super();
		this.num = num;
		this.name = name;
		this.tel = tel;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "Member [num=" + num + ", name=" + name + ", tel=" + tel + "]";
	}

	// 회원 이름의 오름차순 정렬 기준 만들기
	// this와 지금 들어오는놈을 비교하기때문에 한놈만 들어오는것이다.
	// 오름차순 정렬을 만들 건데,
	// String 내부기준이 이미 오름차순이므로 그대로 return해주면 된다.
	
	@Override
	public int compareTo(Member mem) {
		
//		if(this.getName().compareTo(mem.getName())>0) {
//			return 1;
//		}else if(this.getName().compareTo(mem.getName())<0) {
//			return -1;
//		}else {
//			return 0;
//		} 이걸 줄여서 ↓로 만들었다.
		
		return this.getName().compareTo(mem.getName());
	}

}
```

그리고 메인메소드로 가서 적용했다.
```
public class ListSortTest02 extends Print{
	public static void main(String[] args) {
		ArrayList<Member> memList = new ArrayList<Member>();
		
		memList.add(new Member(1, "홍길동", "010-1111-1111"));
		memList.add(new Member(5, "이순신", "010-2222-1111"));
		memList.add(new Member(9, "성춘향", "010-3333-1111"));
		memList.add(new Member(3, "강감찬", "010-4444-1111"));
		memList.add(new Member(6, "일지매", "010-5555-1111"));
		memList.add(new Member(2, "변학돈", "010-6666-1111"));
		
		
		System.out.println("정렬 전...");
		for(Member mem: memList) {
			System.out.println(mem);
		}
		
		// Member타입에 정렬기준이 부여되지 않았을 때
		// 내부정렬기준으로 정렬하라고 시키면 에러가 난다.
		// 그래서 Member타입에 정렬기준을 부여하고-CompareTo를 재정의하고- 왔다.
		Collections.sort(memList);
		
		System.out.println("정렬 후...");
		for(Member mem: memList) {
			System.out.println(mem);
		}
	}
}
```

#### 2) Comparator :  외부 정렬 기준 
comopare( )메서드의 반환값은 0, 양수, 음수가 있는데,
0은 두 값이 같음을, 양수는 앞뒤의 순서를 바꿈을, 음수는 앞뒤의 순서를 바꾸지 않음을 의미한다.
	예를 들어 오름차순일 경우, 앞의 값이 크면 양수, 같으면 0, 뒤의 값이 크면 음수를 반환한다는 얘기다.


이런 리스트가 있다고 해보자.
```
public class ListSortTest01 {
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("일지매");
		list.add("홍길동");
		list.add("성춘향");
		list.add("변학도");
		list.add("이순신");
		
		System.out.println("정렬 전: "+list);
		
		// 정렬 작업은 Collections.sort메서드를 이용하여 정렬한다.
		// Collections.sort()메서드는 기본적으로 내부 정렬 기준으로 정렬한다.
		// (String의 기본 내부 정렬 기준에는 오름차순이 적용되어 있다.)
		Collections.sort(list);
		
		System.out.println("정렬 후: " +list);
		
		Collections.shuffle(list); // 자료 섞기
		System.out.println("섞은 후: " +list);
		
		// 외부 정렬 기준을 적용해서 정렬하기
		// 이제 만든 외부정렬을 적용해보자.
		// 형식) Collections.sort(리스트, 외부정렬기준객체)
		Collections.sort(list, new Desc());
		System.out.println("정렬 후: " +list);
	}
}
```


외부 정렬 기준으로 내림차순을 구현해보자.
Comparator 는 compare()메서드를 재정의해야 한다.
```
class Desc implements Comparator<String>{
	// String은 이미 내부정렬기준이 오름차순으로 구현되어있다.
	@Override
	public int compare(String str1, String str2) {
		//내림차순으로 구현하기
			//1. 이미 앞의 수가 뒤보다 크다면, 내림차순이라면
		if( str1.compareTo(str2) > 0) {
			//음수를 반환해주면 된다.
			return -1;
			//2. 지금 오름차순으로 되어있다면
		}else if(str1.compareTo(str2) < 0) {
			//순서를 바꾸라고-양수를 반환하면 된다.
			return 1;
			// 같다면 0을 반환한다.
		}else {
			return 0;
		}
	}
}
```

```
이 경우,
return str1.compareTo(str2)*-1;
을 할 수도 있다.
이 경우에서는 String 타입의 데이터이므로 str1.compareTo 를 썼지만, int타입일 경우 Wrapper클래스를 사용한다.

return new Integer(num1.getNum()).compareTo(num2.getNum()) *-1;
혹은
return Integer.compare(num1.getNum(), num2.getNum()) * -1;
을 할 수 있다는 얘기다.

```

#### 숙제가 있었다.
```
/* 문제) 학번, 이름, 국어, 영어, 수학점수, 총점, 등수를 멤버로 갖는 Student 클래스를 만든다.
 * 이  Student클래스의 생성자에서는 학번, 이름, 국어, 영어, 수학점수만 매개변수로 받아 초기화처리를 한다.
 * (총점은 3과목 점수의 합계로 초기화한다.)
 * 
 * 이 Student객체는 List에 저장하여 관리한다.
 * 
 * 1. List에 저장된 데이터들을 학번의 오름차순으로 내부 정렬 기준을 구현하고,
 * 2. 총점의 내림차순으로 정렬하는데, 3. 총점이 같으면 이름의 오름차순으로 정렬되는 외부 정렬 기준 클래스를
 * 작성하여 정렬된 결과를 출력하시오.
 * 
 * (단, 등수는 List에 전체 데이터가 추가된 후에 저장되도록 한다.)
 * 
 */

public class StudentClass {
	public static void printVar() {
		System.out.println("────────────────────────────────────────────────────────────────────────");
	}
	
	public static void main(String[] args) {
		ArrayList<Student> stuList = new ArrayList<Student>();

		stuList.add(new Student(2303, "변학도", 90, 90, 90));
		stuList.add(new Student(2302, "성춘향", 95, 30, 70));
		stuList.add(new Student(2305, "이순신", 99, 99, 99));
		stuList.add(new Student(2301, "홍길동", 99, 99, 99));
		stuList.add(new Student(2304, "강감찬", 78, 99, 60));

		//등수 부여
		for (Student stu1 : stuList) {
			int rank = 1;
			for (Student stu2 : stuList) {
				if (stu1.getSum() < stu2.getSum()) {
					rank++;
				}
				stu1.setRank(rank);
			}
		}	
		
		
		System.out.println("정렬 전");
		for(Student stu : stuList) {
			System.out.println(stu);
		}
		printVar();
		
		Collections.sort(stuList);
		System.out.println("학번 오름차순으로 정렬 후");
		for(Student stu : stuList) {
			System.out.println(stu);
		}
		printVar();
		
		
		Collections.sort(stuList, new SumDesc());
		System.out.println("총점의 내림차순으로 정렬 후 ");
		for(Student stu : stuList) {
			System.out.println(stu);
		}
		printVar();
		
		Collections.sort(stuList, new NameAsc());
		Collections.sort(stuList, new SumDesc());
		System.out.println("총점 내림차순 & 동점자 이름 오름차순 ");
		for(Student stu : stuList) {
			System.out.println(stu);
		}
		printVar();

	}
}

//총점이 같으면 이름의 오름차순으로 정렬되는 외부 정렬 기준 클래스
class NameAsc implements Comparator<Student>{

	@Override
	public int compare(Student stu1, Student stu2) {
		return stu1.getName().compareTo(stu2.getName());
	}
	
}


class SumDesc implements Comparator<Student>{

	//총점의 내림차순으로 정렬
	@Override
	public int compare(Student stu1, Student stu2) {
		if(stu1.getSum()>stu2.getSum()) {
			return -1;
		}else if(stu1.getSum()<stu2.getSum()) {
			return 1;
		}else {
			return 0;
		}
	}
	
}

class Student implements Comparable<Student>{
	private int Sid;
	private String name;
	private int kor;
	private int eng;
	private int math;
	private int sum;
	private int rank;
	
	public Student(int sid, String name, int kor, int eng, int math) {
		super();
		Sid = sid;
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.sum = kor+eng+math;
	}
	
	
	public int getRank() {
		return rank;
	}


	public void setRank(int rank) {
		this.rank = rank;
	}


	public int getSid() {
		return Sid;
	}

	public void setSid(int sid) {
		Sid = sid;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	
	@Override
	public String toString() {
//		sum = (kor + eng + math);
		return "학번: " + Sid + ", 이름: " + name + ", 국어: " + kor + 
				"점, 영어: " + eng + "점, 수학: " + math + "점, 총합: " 
				+ sum +"점, "+rank+"등.";
	}


	// 학번의 오름차순으로 내부 정렬 기준을 구현하자.
	@Override
	public int compareTo(Student stu) {
		if(this.getSid()<stu.getSid()) {
			return -1;
		}else if(this.getSid()>stu.getSid()) {
			return 1;
		}else {
			return 0;
		}
	}
	
}

```

rank 설정하는데에서 애좀먹었다. set이 뭔지 다시 공부해야 할 필요가 있을 듯하다.