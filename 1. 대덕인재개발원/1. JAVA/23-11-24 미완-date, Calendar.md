package kr.or.ddit.study11; < 여기꼭봐라

import java.util.Date;

public class DateExample {
	//이런식으로 디버그를 참/거짓으로 설정해놓고
	boolean debug = true;
	public static void main(String[] args) {
		DateExample de = new DateExample();
		de.dateMethod1();
		
	}
	//원하는/개발을 위한 출력이 필요할때 나오게 하자
	private void dateMethod1() {
		if(debug)System.out.println("dateMethod1");
		
		
		Date d1 = new Date();
		System.out.println("현재 시간");
		System.out.println(d1);
		
		//컴퓨터 프로그램이 잠깐 멈춘다 (밀리세컨드 단위)
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date d2 = new Date();
		System.out.println(d2);
		
		//getTime: 1970년도부터 현재까지 진행된 시간
		System.out.println(d2.getTime());

		// getTime은 long타입이다.
		long time = d2.getTime() - d1.getTime();
		System.out.println(time + "ms");
		
	}
}


SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
대문자 MM은 월, 소문자 mm은 분.
sdf.parse(문자열변수); 우리가 적어준 데이터포맷에 맞게 문자열을 시간으로 변경해준다.
실수할 확률이 너무 많아서   자바에서 try-catch를 꼭 하라고 설정되어있다.

반대로 String a = sdf.format(시간타입 데이터); 를 해주면 시간타입 데이터가 문자열이 된다.

어제처럼 split하고 문자열을 int로 바꾸고.. .할 필요가 없다.

달...력만들기? 이걸...하라고요? 제가요..?? 쟤 화난것같은데..?

메모리를 낭비하지 않기 위해서 스트링 버퍼랑 스트링빌더를 사용한다. 스트링빌더는 별로 안전하지 않다.(그래도 속도는 좀 더 빠르다)
스트링버퍼에 계속 값이 저장돼서 속도가 더 빠르다. 
그냥 스트링은 계속 메모리를 할당한다.

스트링에서 a += "a"; 라고 썼다면 버퍼에서는 a.append("a");라고 쓴다.
3~4분 걸릴 거 28밀리초걸린다...
스트링버퍼는 메모리를 하나만 차지한다.

싱글스레드: 작업을 하나 하고 있을 때 다른 작업 불가,
멀티스레드: 여러 작업을 동시에 할 수 있다. 컴퓨터는 기본적으로 멀티스레드-타이핑할때 카톡을 받을 수 있다.

		
		/*
		 * 
		 * StringBuffer와  stringBuilder는  String과 비슷하나
		 * 저장된 문자열을 수정할 수 있음.
		 * 
		 * StringBuffer : 스레드 이용 시 발생딜 수 있는 동기화 문제도 해결된 안전한 사용 가능
		 * StringBuilder: 스레드에 안전하지 않음
		 * 
		 * 주요메서드
		 * .append()		 			: 저장된 문자열에 추가
		 * .delete(int start, int end)	: start~end까지 삭제 
		 * .deleteCharAt(int indx) 		: idx문자 삭제
		 * .insert(int offset, 데이터)	: offset 위치에 데이터 저장
		 * .reverse()					: 저장된 문자열의 문자를 역순으로 반환
		 * .toString()					: 문자열로 반환
		 * 
		 */
		

run시켰는데 얘가아니라 다른 클래스의 출력문구가 작동되면 내가 작업중인 곳의 메인을 잘못 만든거다..
main을 자꾸 mian으로 적더라


	/*
	 * 배운 것
	 * 1. getTime은 1970년도부터 지금까지 진행된 시간으로,long 타입의 데이터이다.
	 * 
	 * 2.
	 * 	 2-1.날짜포맷.parse(문자열); 을 하면 날짜포맷에 맞게 문자열을 시간으로 변환해준다.
	 *      ㄴ형태:SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	 *   	yyyy년도, MM 월, dd일, hh시, mm 분
	 *   	ㄴ 예시: Date date = sdf.parse(dateStr);
	 *   2-2. 반대의 경우 날짜포맷.format(날짜데이터); 를 하면 문자열로 바꿀 수 있다.
	 *   	ㄴ 예시: String date_Str = sdf.format(date);
	 *   
	 *   
	 * 3. Calendar 클래스 Calendar.getInstance();
	 *  3-1. 날짜세팅 cal.set(Calendar.YEAR, 1999); 
	 *  	달은 0부터 시작하고, YEAR, MONTH, DATE, HOUR, MINUTE.. 다양하게 쓸 수 있다.
	 *  
	 *  3-2.int day = cal.get(Calendar.DAY_OF_WEEK);
	 *  	그 주의 몇 번째 날인지 구해주는 방법이다.
	 *  
	 *  3-3. cal.add(Calendar.DATE, 1)
	 *  	더하거나 빼는 연산도 가능하다.
	 *  
	 *  이제 이걸로 캘린더를 만들어보자.
	 */