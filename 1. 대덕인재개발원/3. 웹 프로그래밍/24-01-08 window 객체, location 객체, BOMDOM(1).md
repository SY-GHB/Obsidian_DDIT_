#### * ) Window 객체 메소드

![[Pasted image 20240110191609.png]]

#### * ) location  객체 메소드
![[Pasted image 20240110193156.png]]
![[Pasted image 20240110193203.png]]

#### 1) window.opener
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/public.css">
<script>
proc1=()=>{
	
// 	window.open("페이지이름♡", "target/name", "spec");
	window.open("subinput.html", "_blank", "width=500 height=300");
	
}
</script>
</head>
<body>

<div class="box">
 <h4></h4>
 <input type="button" value="확인" onclick="proc1()">
 <br><br>
 <div id="result1"></div>
</div>
</body>
</html>
```

새로운 창이 열렸을 때, 그 부모되는 창을 opener라고 부른다.

#### 2) timeout_interval
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/public.css">
<script>
//body보다 script가 먼저 실행되므로 

window.onload = function(){
 res1 = document.getElementById('result1');
 res2 = document.getElementById('result2');
}

proc1=()=>{
	
	//0~255사이 랜덤 발생(10진수)
	ranR = Math.floor(Math.random()*256);
	ranG = Math.floor(Math.random()*256);
	ranB = Math.floor(Math.random()*256);
	
	//setTimeout ↓1초가 지난 후에 이 함수를 작동시킨다는얘기임
	setTimeout(function(){
		//10진수 컬러표현
// 		res1.style.backgroundColor = `rgb(${ranR}, ${ranG}, ${ranB})`; 
		
		//16진수로 바꾸는 과정 - toString(16)
		ranR16 = ranR.toString(16);
		ranG16 = ranG.toString(16);
		ranB16 = ranB.toString(16);
		
		res1.style.backgroundColor = "#"+ranR16+ranG16+ranB16; 
	}, 1000);
}

proc2=(but)=>{
	//but: 시작버튼 this를 파라미터로 받은 매개변수-지역변수
	//람다함수를 쓰면 this의 대상이 바뀐다. 
	//이거안됨, proc2 = function(){ this.style.display = "none";} 이랬을 때 먹힘
	but.style.display = "none"; 
	
	//람다함수에서 this를 쓰고 싶다면 매개변수를 사용해야 한다. 함수 호출 시 this를 파라미터로 넘긴다.
	
	//but변수를 전역변수로 변경 - stop()에서 사용하가능하도록 만들자.
	gbut = but;
	
	
	//setInterval ↓1초가 지날 때마다 이 함수를 작동시킨다는 의미
	//전역변수로 id라는 변수를 선언했다.
	id = setInterval(function(){
		
		console.log(id);
		//setInterval 돌때마다 랜덤수가 발생해야하니까 
		//색 지정 코드를 함수 안에 넣어줘야 한다.
		ranR2 = Math.floor(Math.random()*256);
		ranG2 = Math.floor(Math.random()*256);
		ranB2 = Math.floor(Math.random()*256);
		
		res2.style.backgroundColor = `rgb(${ranR2}, ${ranG2}, ${ranB2})`; 
		
	}, 300);
}

stop=()=>{
	//실행종료
	clearInterval(id);
	
	//시작버튼을 다시 보여주게 설정
	//1. 시작버튼을 검색하기
	
	//2. 지역변수였던 but을 전역변수로 가져와서 사용하기
	gbut.style.display = "inline";
	
}

</script>

<style>
#result1, #result2{
	height: 50px;
}

</style>
</head>
<body>

<div class="box">
 <h4></h4>
 <input type="button" value="확인" onclick="proc1()">
 <br><br>
 <div id="result1"></div>
</div>

<div class="box">
 <h4></h4>
 <!-- onclick한 객체를 this로 받을 수 있다. -->
 <!-- 람다함수에서는 this를 사용하려면 매개변수로 넘겨줘야 한다. -->
 <input type="button" value="시작" id="start" onclick="proc2(this)">
 <input type="button" value="종료" onclick="stop()">
 <br><br>
 <div id="result2"></div>
</div>
</body>
</html>
```


#### 3) subinput
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/public.css">
<script>

proc1=()=>{
	userName = prompt("이름을 입력하세요.");
	
	parout =  opener.document.getElementById('result1');
	
	parout.innerHTML = userName;
	//script에서는 하이픈이아니라 카멜로 이어붙여쓴다.
	parout.style.fontSize="1.5rem";
	parout.style.background = "#afd4a7";
	
	window.close();
}

</script>
</head>
<body>

<div class="box">
 <h4></h4>
 <input type="button" value="확인" onclick="proc1()">
 <br><br>
 <div id="result1"></div>
</div>
</body>
</html>
```

#### 4) location 객체
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/public.css">
<script>
proc1=()=>{
	
	vhref = location.href;
	vproto = location.protocol;
	//host = hostname이랑 port를 같이가져옴
	vhostname = location.hostname;
	vport = location.port;
	vhost = location.host;
	
	//출력내용 보기
	str = `vhref = ${vhref} <br> vproto = ${vproto} <br> vhostname = ${vhostname} <br> vport=${vport} <br> vhost = ${vhost}`
	document.getElementById('result1').innerHTML = str;
}

lohref = () =>{
	location.href = "window_opener.html";
}

goPage=()=>{
	//이름 입력
	userName = prompt("이름 입력");
	location.href = "loctest.jsp?name=" + userName;
}

goReload = () =>{
	//result5에 랜덤으로 발생하는 문자열을 출력 
	arr = ["Hello", "HappyNewYear", "FullSpeedAhead", "GoldenAgeWillReturnAgain", "BurnMeToTheGround", "예싸몬빠이아"];
	
	ran = Math.floor(Math.random()*arr.length);
	console.log(ran);
	
	res = `<h1>${arr[ran]}</h1>`;
	
	vreout= document.getElementById('result5');
	vreout.innerHTML = res;
	vreout.style.color="red";
	vreout.style.fontSize="2.0rem";
	
	setTimeout(function(){
		location.reload();
	}, 2000)
	
}

</script>
</head>
<body>

<div class="box">
 <h4></h4>
 <input type="button" value="확인" onclick="proc1()">
 <br><br>
 <div id="result1"></div>
</div>

<div class="box">
 <h4></h4>
 
 특정 페이지로 이동(버튼에서 직접): 
 
 특정 페이지로 이동(script 함수에서 지정):
 <!-- href는 사이트 주소, html, jsp, servlet등이 올 수 있다. -->
 <input type="button" value="페이지 이동" onclick="location.href ='timeout_interval.html'"> <br>
 특정 페이지로 이동(script 함수에서 지정):
 <input type="button" value="페이지 이동" onclick="lohref()"> <br>
 <br>
 
<!--  <input type="button" value="확인" onclick="proc2()"> -->
 <br><br>
 <div id="result2"></div>
</div>

<div class="box">
 <h4></h4>
 a태그에서 location.href를 이용 <br><br>
 
 1. a태그의 기본 href="페이지"<br>
 <a href="window_opener.html" target="_blank">새창열기</a> <br><br>
 
 2. a태그의 href에서 js함수 호출: jsp파일로 이동<br>
 <a href="javascript: goPage()">새창열기</a><br><br>
 
 3. onclick에서 js함수 호출 - location.href: jsp파일로 이동<br>
 <a href="#" onclick="goPage()">멤버리스트</a><br><br>
 
 4. onclick = "locatioin.href='주소'"<br>
 <!-- name=korea 사이에 공백잇으면안됨. -->
 <a href="#" onclick="location.href='loctest.jsp?name=korea'">멤버리스트</a><br><br>
 <br><br>
 <div id="result3"></div>
</div>


<div class="box">
 <h4></h4>
 <form action="loctest.jsp" method="get">
 <input type= "hidden" name="name" value="홍길동">
 <input type="submit" value="확인">
 <input type="button" value="확인" onclick="location.href='loctest.jsp?name=강감찬'">
 </form>
 <br><br>
 <div id="result4"></div>
</div>

<div class="box">
 <h4></h4>
 새로운 페이지 이동 - assign()
 <!-- assign()은 히스토리가 있어 뒤로가기가 가능하지만 replace()는 불가함 -->
 <input type="button" value="확인" onclick="location.assign('loctest.jsp?name=강감찬')">
 <br><br>
 새로운 페이지 이동 - replace() 뒤로가기 불가능
 <input type="button" value="확인" onclick="location.replace('loctest.jsp?name=강감찬')">
  <br><br>
 현재문서를 다시 리로드 - reload()
 <input type="button" value="확인" onclick="goReload()">
 <div id="result5"></div>
</div>

</body>
</html>
```

###### loctest.jsp
```
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
 request.setCharacterEncoding("UTF-8");

 String userName = request.getParameter("name");
 
 out.print(userName);
 
%>

<%= userName %>

</body>
</html>
```

#### 5) DomTest01
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/public.css">
<script>
proc1 = () =>{
	vu1 = document.getElementById('u1');
	list = vu1.getElementsByTagName('li');
	
	str = "";
	for (i = 0; i < list.length; i++) {
		alert(list[i].firstChild.data);
		str += list[i].firstChild.data +"<br>";
	}
	
	document.querySelector('#result1').innerHTML = str;
}

proc2=()=>{
	
	vu2 = document.querySelector('#u2');
	list = vu2.querySelectorAll('li');
	
	for (i = 0; i < list.length; i++) {
		txt = list[i].firstChild.data;
		
		
		vimg = document.createElement('img');
		vimg.src="../images/" + txt;
		vimg.alt= txt;
		
		list[i].appendChild(vimg);
	}
}

//매개변수가 하나일 때는 괄호를 생략할수도 있다.
proc3= pimg =>{
	vdata = pimg.firstChild.data
	
	vimg = document.createElement('img');
	vimg.src="../images/" + vdata;
	vimg.alt = vdata;
	vimg.style.borderRadius = "50%";
	
	pimg.appendChild(vimg);
}

window.onload=function(){
	//getElementById에서는 부모자식관계 등을 기술할 수 없다.
	// querySelector는 된다
	
	//접근 검색
	list = document.querySelectorAll('#u4 li');
	//이벤트 등록
	for (i = 0; i < list.length; i++) {
	/*	//addEventListener - 이벤트가 발생하는지 찾음?
		list[i].addEventListener('click', function(){
			//기능구현	
		}*/
		
		//newImg() 하면 함수의 결과값이 들어온다.
		//newImg여야 함수가 참조된다.
		list[i].addEventListener('click', newImg)
		
	}
}


newImg= function(){
	//this: 클릭한 li객체- list[i]가 된다. 그래서 람다함수안썻다.
	//li객체의 text값 가져오기-li안에 문자가 하나밖에없음:firstChild-그안의데이터를가져와야하니까.data
	livalue = this.firstChild.data;
	
	vimg = document.createElement('img');
	vimg.src = "../images/"+livalue;
	vimg.alt = livalue;
	
	this.appendChild(vimg);
}

</script>
<style>
 img{
   width: 120px;
   height: 120px;
 }
</style>
</head>
<body>

<div class="box">
 <h4></h4>
 <ul id="u1">
  <li>List Item1</li>
  <li>List Item2</li>
  <li>List Item3</li>
  <li>List Item4</li>
  <li>List Item5</li>
 </ul>
 <input type="button" value="확인" onclick="proc1()">
 <br><br>
 <div id="result1"></div>
</div>

<div class="box">
 <h4></h4>
 <ul id="u2">
  <li>짬뽕이.jpg</li>
  <li>아메리카노.jpg</li>
  <li>에스프레소.jpg</li>
  <li>수국.jpg</li>
  <li>사막.jpg</li>
 </ul>
 <input type="button" value="확인" onclick="proc2()">
 <br><br>
 <div id="result2"></div>
</div>

<div class="box">
 <h4>&lt;li onclick="proc3(this)">&lt;/li> <br>  각 li요소에 onclick=""을 줬다.</h4>
 <ul id="u3">
  <li onclick="proc3(this)">짬뽕이.jpg</li>
  <li onclick="proc3(this)">아메리카노.jpg</li>
  <li onclick="proc3(this)">에스프레소.jpg</li>
  <li onclick="proc3(this)">수국.jpg</li>
  <li onclick="proc3(this)">사막.jpg</li>
 </ul>
 <br><br>
 <div id="result3"></div>
</div>


<div class="box">
 <h4>js에서 일괄적으로 이벤트 핸들러를 구현한다.</h4>
 li객체[index].addEventListener('click', newImg)<br>
 newImg: 함수이름, 참조값만 전달, 호출방식이 아니다!!
 <ul id="u4">
  <li>짬뽕이.jpg</li>
  <li>아메리카노.jpg</li>
  <li>에스프레소.jpg</li>
  <li>수국.jpg</li>
  <li>사막.jpg</li>
 </ul>
 <br><br>
 <div id="result4"></div>
</div>

</body>
</html>
```