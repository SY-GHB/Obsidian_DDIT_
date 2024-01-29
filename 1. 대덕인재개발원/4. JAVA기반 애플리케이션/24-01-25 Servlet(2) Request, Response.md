오늘은 JSP문서에서 클라이언트에게 데이터를 받고 서블릿으로 넘겨 응답을 받는 연습을 했다.

### 1. JSP문서 만들기
사용자의 입력을 받을 JSP문서를 만들었다.
생성위치는 dynamic web project > Webcontent 아래 만들어 주었다.

```
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
```
JSP를 만들면 이런 문장이 자동으로 생성되는데, 이 문장은 JSP처리에 대한 설정이다.
contentType은 JSP가 처리되고 생성할 문서에 대한 타입 지정,
charset은 브라우저가 받아볼 (생성될 문서의) 인코딩 방식,
pageEncoding은 JSP파일 자체의 인코딩 방식을 지정한다.


```
<%
	String name = "홍길동";
%>

<%= name %>
```
JSP에서 Java 명령을 사용하기 위해서는 ``<% %>``태그 안에 명령어를 삽입하는데,
이를 ``스크립트릿``이라고 한다.
반면, 변수나 수식의 결과를 출력하기 위해 ``<%= %>`` 태그 안에 변수나 수식을 삽입하는데, 이는 ``표현식(Expression)``이라고 한다.

 JSP에서 사용자에게 데이터를 받는 태그로 form태그가 있는데,
 form태그는 이런 속성들로 구성된다.
 
-  action
form에서 만들어진 데이터를 받아서 처리할 문서명 또는 서블릿 URL주소. ``"http://localhost/webTest/requestTest01.do"``라는 주소가 있다면, ``http://localhost``(도메인명)은 생략하고 ``/webTest``(컨텍스트)부터만 써줘도 된다.

- method 
전송방식(GET, POST)을 지정하는 속성이다.
지정하지 않으면 기본값으로 GET이 설정된다.

- target
응답이 나타날 프레임을 지정한다.

- enctype
서버로 파일을 전송할 때 사용하는 속성으로 속성값은 'multipart/form-data'

form태그 속에서 input, select, textarea 태그를 쓰면 사용자에게서 데이터를 입력받을 수 있다. 이 태그들에는 name 속성이 되는데, 추후 서블릿에서 데이터를 받을 때 이 name의 값이 파라미터명이 된다.


### 2. 서블릿 만들기
#### 1) 서블릿 파일 만들기
JSP파일에서 보낸 데이터를 받아 처리할 서블릿을 만들었다.
생성장소는 dynamic web project > Java Resources > Src 아래 패키지를 만들고 생성하였다.

서블릿 생성 시 URL mappings에서 위에 만들어두었던 서블릿 주소 중 컨텍스트 뒤의 내용, ``/requestTest01.do``를 입력한 뒤 Consturctors from superclass는 체크를 해제하고 doPost와 doGet만 자동생성되도록 체크한 뒤 생성하면 된다.
#### 2) doGet(), doPost()?
만들어진 서블릿에는 doGet()메소드와 doPost()메소드가 있는데,
전송방식에 맞는 메소드에 데이터를 받아서 처리하는 코드를 짜 주거나,
```
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	....작성내용,,,,
	}
```

혹은 doGet()포스트에 작성하고 doPost()에서 doGet()메소드를 호출하면 어떤 방식으로 보내더라도 작동할 수 있다.
```
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
```


#### 3) 데이터 받아서 처리하기
제일 먼저 클라이언트가 보낸 데이터의 문자 인코딩 방식을 지정한다.
```
request.setCharacterEncoding("utf-8");
```
이는 Post 방식에 필요한 처리지만, 웬만하면 둘 다 해놓자.

클라이언트가 보낸 데이터를 받는 방법

- request.getParameter("파라미터명");
주어진 "파라미터명"에 설정된 "값"을 가져온다.
파라미터명은 input, select, textarea에 설정된 name속성의 값이다.
가져오는 값의 자료형은 String이다. input type="number"여도 String이다.
만약에 정수형을 입력받아 정수형으로 활용하고 싶다면 Integer.parseInt()를 해 주어야 한다.

- request.getParameterValues("파라미터명");
위와 같은 역할이지만, "파라미터명"이 같은 데이터들이 여러개일 때 사용한다. 
가져오는 값의 자료형은 String[]이다.


- request.getParameterMap()
전송된 모든 파라미터를 Map객체에 담아서 반환하는 메서드이다.
이 Map객체의 key값은 "파라미터명"이며 자료형은 String이고,
value값은 해당 파라미터에 저장된 값이며 자료형은 String[]이다.

#### 4) 출력하기
```
response.setCharacterEncoding("utf-8");
response.setContentType("text/html; charset=utf-8");
```
인코딩방식과 contentType을 지정하고,

```
PrintWriter out = response.getWriter();
```
처리할 내용을 응답으로 보내기 위한 스트림객체를 생성한 뒤,

```
		out.println("<html>");
		out.println("<head><meta charset='utf-8'>"
				+ "<title>Request객체연습</title></head>");
		out.println("<body>");
		
		out.println("<h2>Request의 파라미터 데이터 받기</h2>");
		out.println("<table border='1'>");
		
		out.println("<tr><td>이름</td>");
		out.println("<td>"+userName+"</td></tr>");

		out.println("<tr><td>직업</td>");
		out.println("<td>"+job+"</td></tr>");
		
		out.println("<tr><td>취미</td>");
		out.println("<td>");
```

print()메소드와 println()메소드를 활용하여 html태그들을 출력하면 된다.
print()와 println()의 차이는 줄바꿈을 하느냐 하지 않느냐의 차이다. ln이 한다.


### 3. foward와 redirect
[[숙제 -  redirect와 foward의 차이|여기]]에도 한 번 정리했었다.

#### 1) foward 방식

##### 작동방식
특정 서블릿이나 JSP에 대한 요청을 다른 서블릿이나 JSP로 넘겨준다.
이 때, HttpServletRequest객체와 HttpServletResponse객체를 공유하기때문에 파라미터를 넘길 수 있다.
URL주소는 처음 요청할 때의 주소가 바뀌지 않으며, 서버 내부에서만 접근이 가능하다.

##### foward 방식으로 이동하기
이동되는 페이지로 데이터를 넘기려면
Request객체의 setAttribute()메서드를 이용하여 데이터를 저장하여 보내고,
받는 쪽에서는 getAttribute()메서드를 이용하여 데이터를 읽어온다.

저장형식
```
Request객체.setAttribute("key값", 저장할 데이터);
```

읽기형식(서블릿 및 다른 JSP문서에서 읽는다.)
```
Request객체.getAttribute("key값");
```

##### forward방식으로 이동하기
```
RequestDispatcher rd = request.getRequestDispatcher("/forwardTarget.do")
rd.forward(request, response);
```
request.getRequestDispatcher()메서드에 이동할 서블릿이나 JSP를 지정해준다.
이 때 경로는 ContextPath 이후의 경로를 지정한다.
이동할 전체 경로가 ``'/webTest/forwardTarget.do'``인 경우에는 ``/forwardTarget.do``만 작성한다는 소리다.

##### forward방식으로 받아보기
forward방식으로 온 데이터를 서블릿에서 받아보려면 상기한대로 getAttribute()메소드를 통해 받는다.

이 때, 넘어오는 데이터의 자료형은 Object이므로 꼭 형변환이 필요하다.

#### 2) redirect 방식
##### 작동방식
응답시 브라우저에게 이동할 URL을 전송하여 브라우저가 해당 URL로 이동하는 방식이다. 이동할 때는 GET방식으로 요청하기 때문에 URL의 길이에 제한이 있다.

redirect는 처음 요청할 때 만들어진 Request객체와 Response객체를 이동한 문서에서 유지하지 못한다. 브라우저에서 새롭게 요청하기 때문에 다른 Request 객체와 Response 객체로 전환된다.

##### redirect 방식으로 이동하기
Response객체의 sendRedirect()메서드를 이용한다.
형식은 이러하다.
```
Response객체.sendRediredt("이동할 문서의 전체 URL주소");
```

만약, 이동할 주소에 한글이 있을 경우에는  URLEncoder을 이용하여 인코딩 방식 지정 후 저장한다.

잠깐!
만약에 경로에 프로젝트명을 그대로 적어줘서 사용했는데, 프로젝트명을 변경하거나 프로젝트의 위치를 바꾸었다면 수정할 게 많아질 테다. 그런 상황을 방지하려면,

``"webTest/redirectTarget.do"``를 ``request.getContextPath()+"/redirectTarget.do"``로 적어두면 된다. 변경되거나 바뀐 프로젝트명을 찾아줄것이다.

redirect방식은 GET방식으로 데이터를 전송하지만, 
```
response.sendRedirect(request.getContextPath()+"/redirectTarget.do");
```
이런 방식으로 보낸다면 URL에 쿼리스트링이 생기지 않는다.

처음 보냈던 request와 response객체는 redirect 후 소멸되므로, 만약 첫 request 에서 보냈던 어떤 값들이 두번째 문서에서 필요하게 된다면 이런 식으로 만들 수 있다.


```
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//request객체에서 값을 가져온다.
	String userName = request.getParameter("userName");
	//주고싶은 또 다른값이 있다면 설정해준다
	request.setAttribute("tel", "010-9999-9999");
	
	//가져온 값을 이용해 직접 쿼리스트링을 만들어준다.
	response.sendRedirect(request.getContextPath()+"
	/redirectTarget.do");
	response.sendRedirect(request.getContextPath()
			+"/redirectTarget.do?userName="+userName
			+"&tel=010-9999-999");
	}
```