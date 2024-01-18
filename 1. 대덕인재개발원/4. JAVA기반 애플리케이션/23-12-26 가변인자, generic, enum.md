#### 1. 가변인자
만약 정수형 데이터를 매개변수로 받아 그 데이터들의 합계를 구하는 메소드를 만들고 싶은데 그 정수형 데이터가 몇 개가 들어올지 알 수 없다면 어떻게 해야 할까? 

전통적으로는 배열을 사용해왔지만, 가변인자라는 것도 사용할 수 있다.

- 메소드가 사용하는 매개변수의 갯수가 실행될 때마다 다를 때 사용한다.
- 가변인자는 메소드 안에서 배열로 실행된다.
- ``자료형...변수`` 형태로 기술한다. ex) ``int... data``
- 가변인자와 일반 매개변수를 같이 사용할 경우에는 가변인자를 가장 뒤에 배치해야 한다: 가변인자가 가져갈 데이터를 구별하기 위함이다.
	public int sumArr(int name, int...data) (O)
	public int sumArr(int...data, int name) (X)

##### 1) 배열을 이용한 메소드
```
public int sumArr(int[] data) {
	int sum = 0;
	for(int a : data) {
		sum += a;
	}
	return sum;
}
```

##### 2) 가변인자를 사용한 메소드
```
public int sumArg(int... data) {
	int sum = 0;
	for(int a : data) {
		sum += a;
	}
	return sum;
```

###### *배열 초기화 방법의 차이~
```
public static void main(String[] args) {
	ArgsTest at = new ArgsTest();
	//arr와 arr2 초기화 방법의 차이
	// arr로 값을 넣어서 초기화해준 배열(배열 선언과 동시에 초기화)은 나중에 값을 변경하는 게 불가능하고
	// arr2처럼 배열 선언과 값을 넣어주는 건 나중에 값을 변경하는 게 가능하다.
	int[] arr = {72, 12, 54};
//	arr = {1,2,3}; 이건안된다.
	int [] arr2 = null;
	arr2 = new int[] {5,10,15}; //
	
	System.out.println(at.sumArr(arr));
	System.out.println(at.sumArr(new int[] {1,2,3,4,5}));
	System.out.println(at.sumArg(100,200,300));
	System.out.println();
	
	System.out.println(at.sumArg2("홍길동", 1,2,3,4,5,6,1,2,8,2,9,3,58,1));
	
	}
}
```

#### 2. 제네릭
제네릭을 사용하기 전에는 어떤 종류의 데이터를 받을지 알 수 없을 때 Object로 받았었다. 저장은 쉬웠지만 꺼낼 때는 형변환을 해야 하는 등의 불편함이 있었고, 그걸 보완한 게 generic이다.

제네릭이 적용되는 클래스를 우리가 직접 만들어볼수도 있다.
제네릭을 1. 변수, 2. 메소드의 반환값, 3. 매개변수 선언위치에 적용할 수 있다.

- 형식
```
class 클래스명<제네릭 타입글자>{
	//변수선언에 제네릭을 사용할 경우
	제네릭타입글자 변수명;

	...
	//메소드의 반환값에 제네릭을 사용할 경우.
	제네릭타입글자 메서드명(매개변수들...){
		...
		return 값;
	}

	//매개변수를 지정할 때 제네릭을 사용하는 경우
	반환값자료형 메서드명(제네릭타입글자 변수명, ...){
		...
	}
}
```

-  제네릭타입글자란?
	별거없고 영어 대문자 1글자를 사용하면 된다.
	자주 볼 수 있는 글자로는 T, K, V, E가 있는데, 꼭 이것만 쓸 필요는 없다.
	T는 type, K는 key, V는 value, E는 element의 약자다.

-  제네릭을 이용한 메소드를 만들어보자.
```
class MyGeneric<T>{
	private T value;
	
	//매개변수에 제네릭을 지정해서 사용하는 경우
	public void setValue(T value) {	
		this.value = value;
	}
	
	// 반환값에 사용하는 경우
	public T getValue() {	
		return value;
	}
}
```

-  이제 제네릭을 사용하면 된다.
```
MyGeneric<String> my1 = new MyGeneric<String>();
my1.setValue("아름다운우리강산");
		
MyGeneric<Integer> my2 = new MyGeneric<Integer>();
my2.setValue(50000000);
```
이런 식으로 선언했다면, 가져올 땐 형변환 없이 바로 꺼내올 수 있다.
넣을 수 있는 애들이 String뿐이면 가져올때도 String밖에 없으니까!

#### 3. enum-열거형

열거형은 클래스처럼 보이게 하는 상수로, 서로 관련있는 상수들의 집합을 나타낸다.

##### 1) 작성방법

1. 클래스처럼 독립된 java파일에 만들기
2. 하나에 java파일에 클래스와 같이 만들기
3. 클래스의 내부에 내부 클래스처럼 만들기

##### 2) 열거형의 속성 및 메서드

1. 열거형변수.name() : 열거형 상수의 이름을 문자열로 반환
2. 열거형변수.ordinal(): 열거형 상수가 정의된 순서값(index)
3. 열거형이름.valueOf("열거형상수명"): 인자값에 지정한 '열거형상수명'과 일치하는 '열거형상수'를 반환한다.
4. 열거형이름.상수명: 열거형이름.valueOf("열거형상수명") 과 같다.
5. 열거형이름.values: 열거형의 모든 상수들을 배열로 반환한다.

##### 3) 열거형 선언하기
###### 방법1)

enum 열거형이름{상수명1, 상수명2, ....}

###### 방법2)
1. 상수명(값들...)형식의 선언
enum 열거형이름{
	상수명1(값들1, ....), 
	상수명2(값들2, ....), 
		....
	상수명n(값들n, ....);


2. 값들이 저장될 변수 선언('값들'갯수만큼 변수를 선언한다.)
private 자료형이름 변수명1;
private 자료형이름 변수명2;
....

3. 열거형의 생성자를 작성한다.
열거형의 생성자는 '열거형상수'에 값들을 세팅하는 역할을 수행한다.
열거형 생성자의 접근제한자는 묵시적으로 private 이다.
생성자의 매개변수는 '값들'과 갯수가 같고, 자료형이 맞아야 한다.

private 열거형이름(자료형이름 변수명1, ...){
	위에서 선언한 변수들을 초기화하는 작업을 주로 한다.
	...
}

4. 위에서 선언한 변수들을 외부에서 사용할 수 있도록 getter메서드를 생성한다.
4번이 끝나고서야 중괄호가 닫힌다.
}

###### 예제)
```
public class EnumTest{
	public enum Season{
	//1. 상수명(값들)의 선언
	봄("3월부터 5월까지", 13), 
	여름("6월부터 8월까지", 28),
	가을("9월부터 11월까지", 15),
	겨울("12월부터 2월까지", 1);

	//2. 값들이 저장될 변수 선언
	private String span;
	private int temp;

	//3. 생성자 선언: 묵시적으로 private이다.
	[private] Season(String months, int temp){
		span = months;
		this.temp = temp;
	} 

	//4. getter 선언
	public String getSpan(){
		return span;
	}
	
	public String getTemp(){
		return temp;
	}
}
```

이제 메인에 가서 불러내자.
```
Season ss = Season.valueOf("겨울");
System.out.println("name: " + ss.name());
System.out.println("ordinal: " + ss.ordinal());
System.out.println("span: " + ss.getSpan());
System.out.println("temp: " + ss.getTemp());
System.out.println();
```


열거형은 같은 열거형끼리만 비교할 수 있다!