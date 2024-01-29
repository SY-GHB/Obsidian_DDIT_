#### 1. 세션이란
지난시간에 쿠키에 대해서 알아보았다.

쿠키와 비슷한 역할을 하지만 클라이언트 쪽에 저장되는 쿠키와 달리, 세션은 서버 쪽에 저장된다.
또한, 문자열만 값으로 가질 수 있었던 쿠키와 달리 세션은 어떤 형태의 데이터든 값으로 가질 수 있다.

#### 2. 세션 저장하기
##### 1) Session 객체 마련하기

새로운 Session객체를 생성하거나 현재의 Session 객체를 가져올 수 있다.

- Reuqest객체.getSession([true]);
```
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
	HttpSession session = request.getSession(true);
}
```
현재 Session이 존재하면 현재 Session을 반환하고, 존재하지 않으면 새로운 Session을 생성하여 반환한다.

-  Reuqest객체.getSession(false);
```
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession(false);
}
```
현재 Session이 존재하면 현재 Session을 반환하고, 존재하지 않으면 Session 생성 없이 null을 반환한다.

##### 2) Session객체에 값 저장하기

setAttribute()메서드로 Session에 값을 저장한다.
```
HttpSession session = request.getSession();
session.setAttribute("test", "연습");
session.setAttribute("age", 30);
```

#### 2. 세션 읽어오기
##### 1) Session객체 구하기
```
HttpSession session = request.getSession();
```

##### 2) Session객체의 값 구하기
###### 1. 세션 데이터를 하나만 구하고 싶다면
```
Session객체.getAttribute("key값");
```
형식을 통해 Session의 값을 구한다. 
.getAttribute("key값") 메소드는 Key값이 없다면 null을 반환한다.

###### 2. 전체 세션 데이터를 확인하고 싶다면
1. Enumeration객체와 getAttributeNames() 메소드를 이용하면 모든 세션의 key값을 구해올 수 있다.
```
Enumeration<String> sessionkey = session.getAttributeNames();
```

2. 반복문을 이용하여 세션의 값을 꺼낸다.
```
while(sessionkey.hasMoreElements()) {
	cnt++;
	// 1개의 key값 구하기
	String key = sessionkey.nextElement();
	
	out.println("<li>" + key + " : "
	+ session.getAttribute(key) + "</li>");
}
```

hasMoreElements()메소드는 Enumeration에 요소가 더 있으면 true, 없으면 false를 반환하는 메소드이고,
nextElement()메소드는 Enumeration에 1개 이상의 요소가 남아있는 경우에 다음의 요소를 반환하는 메소드이다.

###### 3. 세션 ID 구하기
세션ID는 세션을 구분하기 위한 교유한 값이다.
getID()메소드를 사용하여 구할 수 있다.
```
sessoin객체.getID();
```

###### 4. 세션 생성 시간 구하기
1970년 1월 1일부터 세션이 생성된 시간까지 경과한 시간을 밀리세컨드 단위로 반환한다. 
getCreationTime()메소드를 사용하여 구할 수 있다.
```
session.getCreationTime();
```

###### 5. 세션에 접근한 마지막 시간 구하기
1970년 1월 1일부터 세션에 마지막으로 접근한(=가장 최근에 접근한)시간까지 경과한 시간을 밀리세컨드 단위로 반환한다.
```
session.getLastAccessedTime();
```

###### 6. 세션 유효 시간 구하기
초 단위의 세션유효시간을 설정하거나 구할 수 있다.
이 유효시간 내에 세션에 한번도 접근하지 않으면 세션이 삭제된다.

세션의 유효시간을 설정하는 것은 setMaxInactiveInterval(시간) 메소드, 세션의 유효시간을 구하는 것은 getMaxInactiveInterval() 메소드이다.

유효시간을 따로 설정하지 않으면 1800초(30분)으로 설정된다.
```
session.setMaxInactiveInterval(60);
session.getMaxInactiveInterval();
```


#### 3. 세션 삭제하기
##### 1) Session객체 구하기
```
HttpSession session = request.getSession();
```
##### 2) Session 데이터 삭제하기
- 개별적인 Session값 삭제하기: removeAttribute();메서드 이용
```
Session객체.removeAttribute("삭제할 key값");
```

- Session객체 자체를 삭제하기: invalidate(); 메소드 이용
```
Session객체.invalidate();
```