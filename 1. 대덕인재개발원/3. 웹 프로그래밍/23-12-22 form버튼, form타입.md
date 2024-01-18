#### 1) form버튼
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href = "../css/public.css">

<script>
//함수 만들기
//타입은 상관이 없고 변수의 갯수만 맞추면 된다.
function calc(){
	
	//html요소 접근
	var vprice = document.getElementById('price');
// 	vprice = document.querySelector('#price');
	
// 	vqty = document.getElementById('qty');
	var vqty = document.querySelector('#qty');
	
	// 변수 앞에 let이나  var를 써도 되고, 안 써도 된다.
	// 요소에서 값을 가져오기: String타입이지만 변수를 String타입을 선언해줄 필요는 없다.
	// String타입이어도 연산이 가능하다! 단, +인 경우에는 형변환을 해줘야 한다.
	var priceValue = vprice.value;
	var qtyValue = vqty.value;
	
	//굳이 형변환을 해주고 싶다면, 
// 	parseInt(priceValue) * parseInt(qtyValue)

	// 연산
	var result = priceValue * qtyValue;
	
	// 출력
	alert("결과는 "+result+"입니다.");
	
// 	//출력요소 접근
// 	vres = document.getElementsByClassName('res');
// // 	vres = document.querySelector('res');
// 	//출력내용
// 	pr = "결과는 <span>" + result + "</span>입니다."
	
// 	//출력
// // 	vres[0].innerText = pr;
// 	vres[0].innerHTML = pr;

	//출력요소 접근
	vres2 = document.querySelector('.res'); //class접근에 . 빼먹으면 안된다!
	//출력내용
	pr = "결과는 <span>" + result + "</span>입니다."
	
	//출력
	vres2.innerHTML = pr;
	
}
</script>
<style>
span{
 background-color : pink;
 font-size: 1.5rem;
}

</style>
</head>
<body>
<pre>
 button 클릭시 clac()함수를 호출하여
 입력한 값을 얻어 처리한 뒤
 결과를 class = res 요소에 출력한다.
</pre>

<form>
<!-- 폼에서는 span태그보다는 label태그를 더 잘 쓴다. 
	label for는 id와 일치하며, 없어도 실행된다. inline요소-->
 <label for="price">가격</label>
<!--  <input type="text" name="prcie" id="price" placeholder="가격"><br> -->

<!-- type을 number로 지정했을 때 문자는 입력이 안 된당 -->
 <input type="number" name="prcie" id="price" placeholder="가격"><br>
 
 <label>수량</label>
 <input type="number" name="qty" id="qty" placeholder="수량"><br>
 
 
<!--  <input type="button" value="확인" onclick="calc()"><br> -->
 <input type="button" value="확인" onclick="calc()"><br>
</form>

<br><br>
<div class = "res"></div>


</body>
</html>
```



#### 2) form 타입
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function changeColor(){
	//html 요소 접근
	vcol = document.querySelector('#col');
// 	vcol = document.getElementById('col')
	
	//값 가져오기
	cvalue = vcol.value;
	 //콘솔은또먼데??? f12눌러서 나오는 창 중에 console임!
	console.log(cvalue);
	
	
	//연산, 계산, 비교, 반복...
	
	//출력요소 접근
	vform = document.querySelector('#ff');
	 //자바스크립트에서는 동적인 스타일이 된다. background-color이 아니라 backgroundColor라고 쓴다.
	vform.style.backgroundColor = cvalue
	
}
</script>
<style type="text/css">
/*정적인, 고정된 style*/
 form{
  background-color: lightyellow;
 }
</style>

</head>
<body>
<form id="ff" action = "타입.jsp" method="post">
 <label>이름</label>
 <input type = "text" name = "name" id="name"> <br>
 
 
 <label>이메일</label>
 <input type = "email" name = "email" id="email"> <br>
 
 
 <label>전화번호</label>
 <input type = "tel" name = "tel" id="tel"> <br>
 
 <label>생일</label>
 <input type = "date" name = "bir" id="bir"> <br>
 
 <br>
 <label>색상변경</label>
 <input type="color" name="col" id="col" onchange="changeColor()"><br>
 <br>
 
<!--  이건 걍 그림이고 -->
 <img src="../images/check.png" alt="check.png"><br>
 
<!--  이건 submit과 같은 기능을 하는 이미지다. -->
 <input type="image" src="../images/check.png" alt="check.png">
 <input type="submit" value="전송1">
 <input type="reset" value = "초기화1">
 <br> <br>
 
 <button type="submit"> 전송 </button>
 <button type="reset"> 초기화 </button>
 <button type = "button"> 확인 </button>
<!--  <button type="submit"> <img src="../images/check.png" alt="check.png"> </button> -->
 
</form>
</body>
</html>
```

#### 3) 타입.jsp
```
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table{
	border: 2px solid #ffadad;
}
td{
 width: 300px;
 height: 40px;
 text-align: center;
 }
 .title{
 background-color: #fff4e5;
 }
</style>

</head>
<body>
<h1>JSP: java Server Page</h1>


<%
//전송 데이터 가져오기

request.setCharacterEncoding("UTF-8");

String userName = request.getParameter("name");
String userMail = request.getParameter("email");
String userTel = request.getParameter("tel");
String userBir = request.getParameter("bir");

%>

<table border="1">
<tr>
 <td class="title">이름</td>
 <td><%= userName %></td>
</tr>

<tr>
 <td class="title">전화번호</td>
 <td><%= userTel %></td>
</tr>

<tr> 
 <td class="title">이메일</td>
 <td><%= userMail %></td>
</tr>

<tr> 
 <td class="title">생일</td>
 <td><%= userBir %></td>
</tr>
</table>



</body>
</html>
```