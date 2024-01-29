디자인패턴들 중 하나인 싱글톤 패턴과 MVC패턴에 대해 정리해두었다.
디자인 패턴이란? 프로그램 등을 개발하는 중 발생한 문제를 정리해 상황에 따라 간편하게 적용해 쓸 수 있는 것을 정리하여 특정한 규약을 통해 쉽게 쓸 수 있는 형태로 만든 것이다.

### 1. 싱글톤 패턴

#### 1) 정의
싱글톤 패턴이란, 전체 프로그램에서 특정 클래스의 인스턴스를 단 하나만 생성되도록 보장하는 패턴이다. 생성자를 통해 여러번 호출이 되더라도 새 인스턴스를 생성하는 것이 아니라 최초 호출 시에 만들어 두었던 인스턴스를 재활용하는 패턴이다.

단 하나의 객체만 만드는 경우는 DB에 접속할 객체가 필요하다던지,
	 DB에는 접속자 수 제한이 있는데, 접근할 때마다 객체가 생성된다고 치면 나 혼자 DB접속을 여러 개 차지할 수도 있다.
생성 시 큰 연산작업이 필요한 객체를 만드는 경우 등이 있겠다.

#### 2) 만드는 법
클래스 외부에서 new 연산자로 생성자를 호출할 수 없도록 생성자의 앞에 private 접근 제한자를 붙인다.

그리고 자신의 타입인 정적 필드를 하나 선언하고, 자신의 객체를 생성해 초기화한다. 
이 정적 필드에도 private 접근 제한자를 붙여 외부에서 필드값을 변경하지 못하도록 막는다.

외부에서 호출할 수 있는 정적 메소드인 getInstance()를 선언하고, 정적 필드에서 참조하고 있는 자신의 객체를 리턴해준다.
이 메소드를 호출하는 방법만이 싱글톤으로 선언된 객체를 가져올 수 있는 유일한 방법이어야 한다.

```
public class 클래스{
	// 2. 정적 필드 선언
	private static 클래스 singleton = new 클래스();
	
	// 1. 생성자 앞에 접근제한자 붙이기.
	private 클래스() {}
	
	// 3. '정적'메소드 getInstance 선언, 자신의 객체 리턴하기.
	static 클래스 getInstance() {
		return singleton;
	}
}
```

##### 멀티쓰레드에서 안전한Thread-safe 싱글톤 클래스, 인스턴스 만드는 방법
[출처](https://jeong-pro.tistory.com/86)
###### 1. 게으른 초기화(Thread safe Lazy initialization)
```
public class ThreadSafeLazyInitialization{
	//private static 인스턴스 변수 생성
    private static ThreadSafeLazyInitialization instance;
    //private 생성자로 외부에서 생성 제한-싱글톤
    private ThreadSafeLazyInitialization(){}
    //synchronized
    public static synchronized ThreadSafeLazyInitialization getInstance(){
        if(instance == null){
            instance = new ThreadSafeLazyInitialization();
        }
        return instance;
    }
}
```
동기화 처리가 되어 멀티쓰레드에서 안전하긴 하지만, synchronized특성상 비교적 큰 성능저하가 발생한다.

###### 2.  게으른 초기화+ Double-checked locking
```
public class ThreadSafeLazyInitialization {
	//volatile?은 변수 값을 메인메모리에 저장하겠다는 뜻이라는데??
	//사실은 잘 모르겠습니다.
    private volatile static ThreadSafeLazyInitialization instance;
	//생성자를 private로 선언
    private ThreadSafeLazyInitialization(){}

	//첫번째 if문으로 인스턴스의 존재여부 체크
    public static ThreadSafeLazyInitialization getInstance(){        
        if(instance == null){
            synchronized (ThreadSafeLazyInitialization.class) {
                //두번째 if문으로 체크할 때 동기화시켜 인스턴스 생성
                //처음 생성 이후 synchonized블럭을 타지 않기 때문에 성능저하 완화
                if(instance == null)
                    instance = new ThreadSafeLazyInitialization();
            }
        }
        return instance;
    }
}
```

###### 3. holder에 의한 초기화
```
public class Something {
    private Something() {
    }
    
    private static class LazyHolder {
        public static final Something INSTANCE = new Something();
    }
    
    public static Something getInstance() {
        return LazyHolder.INSTANCE;
    }
}
```
 클래스안에 클래스(Holder)를 두어 JVM의 Class loader 매커니즘과 Class가 로드되는 시점을 이용한 방법
 
 JVM의 클래스 초기화 과정에서 보장되는 원자적 특성을 이용하여 싱글턴의 초기화 문제에 대한 책임을 JVM에 떠넘긴다.

holder안에 선언된 instance가 static이기 때문에 클래스 로딩시점에 한번만 호출될 것이며 final을 사용해 다시 값이 할당되지 않도록 만든 방법.

#### 3) 장점
- 한 번의 new로 고정된 메모리 영역을 사용하므로 메모리 낭비를 방지할 수 있다.
- 객체 로딩 시간이 현저하게 줄어든다.
- 싱글톤으로 만들어진 클래스의 인스턴스는 전역 인스턴스로, 다른 클래스의 인스턴스들이 데이터를 공유하기 쉽다.
- 공통된 객체를 여러개 생성해서 사용해야 하는 경우에 사용할 수 있다.
#### 4) 단점
- 싱글톤 인스턴스가 너무 많은 일을 하거나 너무 많은 데이터를 공유시킬 경우 다른 클래스의 인스턴스 간 결합도가 높아져(의존성이 높아진다) [[SOLID 원칙|개방-폐쇄 원칙]]을 위배하게 된다.
	==> 수정이 어려워진다: 싱글톤의 인스턴스가 변경되면 해당 인스턴스를 참조해야하는 모든 클래스를 수정해야 한다
	==> 테스트가 어려워진다: 서로 독립적이어야 하는 단위 테스트를 하는데 문제가 된다.

-  private 생성자 때문에 상속이 어렵다.
	상속을 통한 자식 클래스를 만들 수 없으며, [[23-11-13 행맨, OOP, 필드, 코딩테스트|다형성]]을 적용하지 못하게 된다.

- 멀티쓰레드 환경에서 동기화 처리가 필요하다.
	해두지 않으면 인스턴스가 두 개 생성되는 경우가 발생할 수 있다.

### 2. MVC 패턴
[출처](https://velog.io/@langoustine/%EC%97%AC%EA%B8%B0%EB%8F%84-MVC-%EC%A0%80%EA%B8%B0%EB%8F%84-MVC-MVC-%ED%8C%A8%ED%84%B4%EC%9D%B4-%EB%AD%90%EC%95%BC)

![[Pasted image 20240115145952.png]]
[이미지 출처](https://woodaelog.com/02.%20MVCpattern/)

#### 1) 정의
어떠한 프로그램을 구성할 때 그 구성요소를 Model-View-Controller 세 가지 역할로 구분한 패턴이다. 소프트웨어의 비즈니스 로직과 화면을 구분하는데 중점을 둔다.

사용자가 Controller를 사용하게 되면(request) Controller는 Model을 호출하여(request inforamtion) 데이터를 받아오고(response inforamtion) 받아온 데이터를 통해 View에서 시각적인 부분을 제어(send data)하여 사용자에게 전달(response)한다.

사용자의 요청을 받는 것은 컨트롤러, 비즈니스 로직을 처리하는 곳은 서비스(서비스는 컨트롤러의 과부화를 줄이기 위해 사용되었다.)→서비스에서 처리된 결과는 모델에 담기고, 모델에 저장된 결과는 뷰를 통해 사용자에게 전달된다. 


[출처](https://cocoon1787.tistory.com/733)
JSP에서 출력과 로직을 전부 처리하는 모델1 방식(controller에 view를 같이 구현),
![[Pasted image 20240115000622.png]]

JSP에서는 출력만 처리하는 모델2 방식이 있다.
![[Pasted image 20240115000638.png]]

모델 1의 경우는 쉽고 빠르게 개발 가능하지만 JSP가 너무 비대해지고 컨트롤러와 뷰가 혼재함으로서 향후 유지보수가 어렵다.
모델 2의 경우 HTML소스와 JAVA소스를 분리해놓아 모델1에 비해 확장 및 유지보수가 쉽지만 설계가 어려우며 개발 난이도가 높다.

#### 2) 구성
##### M. 모델
데이터를 가진 객체 및 데이터가 만들어지는 과정까지 포함하는 개념이다. 프로그램에서 <span style="background:rgba(240, 200, 0, 0.2)">정보, 데이터 부분</span>을 의미한다. 
컨트롤러에게 받은 데이터를 조작하는 역할을 수행하며, 값과 기능을 가지는 객체이다. 
모델의 상태에 변화가 있을 때 컨트롤러와 뷰에 이를 통보한다.
	이를 통해 컨트롤러는 모델의 변화에 따른 적용 가능한 명령을 추가, 제거, 수정하고,
	뷰는 최신의 결과를 보여줄 수 있다.

``모델의 규칙``
1. 사용자가 편집하길 원하는 모든 데이터를 가지고 있어야 한다.
2. <span style="background:rgba(240, 200, 0, 0.2)">뷰나 컨트롤러에 대해 어떤 정보도 알지 말아야 한다</span>.
	컨트롤러 및 뷰에 의존하면 안된다: 둘에 관련된 코드가 있어서는 안 된다.
3. 변경이 일어나면, 변경 통지에 대한 처리방법을 구현해야 한다.
##### V. 뷰
뷰는 입력값이나 체크박스 등 <span style="background:rgba(240, 200, 0, 0.2)">사용자 인터페이스 요소</span>를 나타낸다.
컨트롤러에게서 건네받은 model의 데이터를 사용자에게 시각적으로 보여주기 위한 역할을 수행하며, 사용자에게 보여지는 화면이다.
	HTML/CSS/Javascrip를 모아둔 컨테이너

``뷰의 규칙``
1. <span style="background:rgba(240, 200, 0, 0.2)">모델이 가지고 있는 정보를 따로 저장해서는 안 된다.</span>
	모델로부터 데이터를 받을 때에는
	사용자마다 다르게 보여주어야 하는 데이터에 한해서만 받아오며,
	컨트롤러를 통해서만 받아와야 한다.
2. 모델이나 컨트롤러를 알고 있을 필요가 없다.
	모델에만 의존해야 하고: 모델의 코드가 있어도 되고
	컨트롤러에는 의존하면 안 된다: 컨트롤러의 코드가 있어서는 안 된다.
3. 변경이 일어나면, 변경 통지에 대한 처리방법을 구현해야 한다.
##### C. 컨트롤러
<span style="background:rgba(240, 200, 0, 0.2)">모델과 뷰 사이에서 데이터 흐름을 제어</span>한다.
사용자의 요청을 파악하고, 사용자가 접근한 URL에 따라 적절한 메소드를 호출하여 Service에서 비즈니스 로직을 처리한 뒤 이 결과를 모델에 저장하여 뷰에 전달하는 역할.
모델과 뷰의 역할을 분리하는 요소이다.

``컨트롤러의 규칙``
1.<span style="background:rgba(240, 200, 0, 0.2)"> 모델과 뷰에 대해서 알고 있어야 한다.</span>
	모델과 뷰에 의존해도 된다. 둘의 코드가 있어도 된다는 의미.
2. 모델이나 뷰의 <span style="background:rgba(240, 200, 0, 0.2)">변경을 모니터링</span>해야 한다.

#### 3) 장점
- 비즈니스 로직과 UI로직의 명확한 역할 분리로 서로간의 결합도를 낮출 수 있다.
- 코드의 재사용성 및 확장성을 높일 수 있다.
	개발한 모델과 컨트롤러는 여러 뷰에, 뷰도 다른 모델과 함께 재사용이 가능하다.
- 유지보수 및 테스트가 용이해진다
	변경이 필요한 부분을 보다 쉽게 식별할 수 있고, 수정 및 확장에 필요한 자원이 적어진다.
- 개발자 간의 커뮤니케이션 효율을 높일 수 있다.
	각 요소 간의 역할이 분리되어 있어 개발자들의 역할 분리가 쉽고 코드 충돌을 방지하기가 쉽다. 제 3자의 피드백 전달도 쉽다.
#### 4) 한계점
- 뷰와 모델의 의존성을 완전히 분리시킬 수 없다.
- 컨트롤러의 비중이 높아지면 Massive View Controller(대규모 MVC)현상을 피할 수 없다.
	다수의 뷰와 모델이 컨트롤러와 연결되어 컨트롤러가 불필요하게 커지는 현상
	MVP(Moder-View-Presenter), MVVM(Model-View-ViewModel) 패턴 등의 대안이 있다.