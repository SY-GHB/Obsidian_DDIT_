#### 1. $(function(){}), $(document).ready(function(){})
body태그가 생성되기 전에 body태그에 있는 내용을 검색하고 싶다면,
JavaScript에서는 window.onload=function 를 사용했었다. 

JQuery에서는 거기에 더해 $(document).ready(function(){})이나, 
더 줄여서는 $(function(){})을 사용할 수 있다.

여기에 function(){}을 ()=>{}로 줄여 쓸 수 있으니(람다 함수)
```
window.onload = function(){}
$(document).ready(function(){})
$(document).ready(()=>{})
$(function(){})
$(()=>{})
```
모두 같은 방식으로 사용하는 항목이다.

#### 2. html(), text()
JavaScript에서의 innerHTML이 html()메소드,
innerText가 text()메소드와 비슷한 기능이라고 보면 된다.

다만, html()은 검색한 것의 가장 처음 요소만 가져오기에 모든 요소를 가져오고 싶다면 반복문을 써 주어야 한다.
text()는 검색된 내용을 다 가져온다.
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/public.css">
<script src="../js/jquery-3.7.1.min.js"></script>
<script>
//$(function(){})이나 $(()=>{})이나
//window.onload()나 $(document).ready(function(){})이나 다 같은거다..

$(document).ready(()=>{
	$('#btn1').on('click', ()=>{
		alert($('body').html());
	})
	$('#btn2').on('click', ()=>{
		alert($('body').text());
	})
	$('#btn3').on('click', ()=>{
// 		alert($('#result2 p').html());
		vphtml = $('#result2 p').html();
		
		//html로 가져왔으니 html로 내보내면 실제로 작동하고
		//text로 내보내면 span태그가 문자로 찍힌다.
// 		$('#result3').html(vphtml);
		$('#result3').text(vphtml);
	})
	
	$('#btn4').on('click', ()=>{
// 		alert($('#result2 p').text());
		vphtml = $('#result2 p').text();
		
		//텍스트로 가져왔으니 두개의 결과값이 같다.
		$('#result3').html(vphtml);
		$('#result3').text(vphtml);
	})
	
	$('#btn5').on('click', ()=>{
		//매개변수는 하나만 올 수도 있고 두개 다 올 수도 있다. index역할이 is
		vp4="";
		$('#result4 p').each(function(i, y){
			//첫번째것만 다섯번 반복한다.
// 			alert($('#result4 p').html());
			
// 			vp4 += $(this).html()+"<br>";
// 			vp4 += $(y).html()+"<br>";
			vp4 += $('p').eq(i).html()+"<br>";
// 			vp4 += $('p['+i+']').html()+"<br>";
		})
		
// 		$('#result5').text(vp4);
		$('#result5').html(vp4);
		
	})//반복문
})

```
 반복문 
 $(요소선택).each(function(매개변수1, 매개변수2){…});
for(i=0; i<  ; i++)
에서 i역할을 하는게 매개변수 1
매개변수 2는 생략가능, p[i] 와 매개변수2는 같다 

 $.each(“요소선택, function(매개변수1, 매개변수2){…});
3검색된 DOM요소의 개수만큼 지정된 fn함수를 호출한다 

```
</script>
<style>
span{
 background: orange;
}
</style>

</head>
<body>

<div class="box">
 <h4>$('body').html(): 대상 body의 모든 태그를 포함한 문장 <br>
 $('body').text(): 대상 body객체의 문자만</h4>
 <input type="button" value="html" id="btn1">
 <input type="button" value="text" id="btn2">
 <br><br>
 <div id="result1"></div>
</div>

<div class="box">
 <h4>$('p').html(): 대상 p객체의 첫번째 p태그에 대해서만 태그를 포함한 문장 <br>
 $('p').text(): 대상 p객체의 모든 문자만</h4>
 <input type="button" value="html" id="btn3">
 <input type="button" value="text" id="btn4">
 <br><br>
 <div id="result2">
  <p><span>비와 </span>카푸치노</p>
  <p><span>바람이 </span>분다</p>
  <p><span>Never </span>really over</p>
  <p><span>사건의 </span>지평선</p>
  <p><span>오르트</span>구름</p>
 </div>
 
 
 <div id ="result3"></div>
 
</div>

<div class="box">
 <h4>$('p').html(): 대상 p객체의 첫번째 p태그에 대해서만 태그를 포함한 문장 <br>
 반복문을 이용하여 전체 p태그를 대상으로 실행</h4>
 <input type="button" value="html" id="btn5">
 <br><br>
 <div id="result4">
  <p><span>비와 </span>카푸치노</p>
  <p id="wind"><span >바람이 </span>분다</p>
  <p><span>Never </span>really over</p>
  <p><span>사건의 </span>지평선</p>
  <p><span>오르트</span>구름</p>
 </div>
 
 <div id ="result5"></div>
 
</div>
</body>
</html>
```

#### 3. 엘리먼트 관련 선택자
선택자란 ... HTML 요소를 선택해 가져올 수 있게 하는 것이다.
![[Pasted image 20240111113059.png]]
###### 코드: 형제선택자
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/public.css">
<script src="../js/jquery-3.7.1.min.js"></script>
<script>
$(()=>{
	
	$('#btn1').on('click', function(){
		
		//title속성의 값이 korea인 p요소를 선택
		$('p[title=korea]').css('background', 'pink');
	
		//title속성을 가진 요소를 선택하라
		$('p[title]').css('color','blue');
		
		//type속성이 password인 요소의 테두리 변경
		$('input[type=password]').css('border','1px solid red');
		
		//class속성에 hello글자가 포함된 요소 선택하기: *=
		$('p[class*=hello]')
		$('input[class*=hello]')
		
		//위의 두개를 콤마로 이어쓸 수 있다.
// 		$('p[class*=hello], input[class*=hello]').css('border','2px solid green');
	
		$('*[class*=hello]').css('border','5px solid green');
		
		//class속성에 hello 단어가 포함된 요소 선택 : ~=
		$('*[class~=hello]').css('border','5px solid gold');
		
	})
	
	$('#btn2').on('click', function(){
		//input  요소 테두리 lime , 배경색  핑크
		$('#result2 input').css({
			"border": "1px solid lime",
			"background" : "pink"
		});
		
		//input   type이 button 인 요소 배경색 노란
		$('#result2 input[type=button]').css('background','yellow');
		
		//Button 요소와 input type이 button 인 요소 배경색 하늘색
		$('#result2 button, #result2 input[type=button]').css('background', 'skyblue');
		
		//Type이 submit 요소의 배경색 녹색
		$('*[type=submit]').css('background', 'green');
		
		//Type이 reset인 요소의 배경색 노랑 
		$('*[type=reset]').css('background', 'yellow');
		
		//Type이 text, password 인 요소의 테두리 파랑
		$('*[type=text], *[type=password]').css('border', '1px solid blue');
		
		//Type이 file, image인요소의 테두리 빨강
		$('*[type=file], *[type=img]').css('border', '1px solid red');
	})
	
})
</script>
</head>
<body>

<div class="box">
 <h4></h4>
 <input type="button" value="확인" id="btn1">
 <br><br>
 <div id="result1">
  <p class="hellojava"> 일반 문단입니다 </p>
  <p title="툴팁으로 보입니다"> 타이틀을 가지고 있는 문단입니다 </p>
  <p title="korea"> 일반 문단입니다 </p>
   회원번호  <input class="hello html" type="text"><br>
   비밀번호  <input type="password">
   <input type="button" value="확인">
 </div>
</div>

<div class="box">
 <h4></h4>
		 <input type="button" value="확인" id="btn2">
		 <br><br>
		 <div id="result2">
		 <!-- onsubmit="return false;" 실행안한다는의미 -->
		 <form action="속성선택.jsp" method="post" > 
		Text : <input type="text"/><br> 
		Password : <input type="password" /><br><br> 
		
		Radio : <input type="radio" name="radioGroup" id="radioA" value="A"/> A 
		<input type="radio" name="radioGroup" id="radioB" value="B"/> B<br><br> 
		
		Checkbox : <input type="checkbox" name="checkboxes" id="checkbox1" value="1"/> 1 
		<input type="checkbox" name="checkboxes" id="checkbox2" value="2"/> 2<br><br> 
		
		Textarea : <br> <textarea rows="15" cols="50" id="myTextarea" id="myTextarea"></textarea><br><br> 
		Image : <input type="image" src="../images/check.png"><br><br> 
		File : <input type="file"><br><br> 
		
		Buttons : <button type="button" id="normalButton">Normal</button> 
		<button type="submit" id="submitButton">Submit</button> 
		<button type="reset" id="resetButton">Reset</button> <br><br>
		
		<input type="button" value="일반버튼"> 
		<input type="submit" value="전송버튼"> 
		<input type="reset" value="초기화버튼"> <br><br>
		</form>
 </div>
</div>
</body>
</html>
```

#### 4. 속성관련 선택자
![[Pasted image 20240111121034.png]]

###### 코드
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/public.css">
<script src="../js/jquery-3.7.1.min.js"></script>
<script>
$(()=>{
	
	$('#btn1').on('click', function(){
		
		//title속성의 값이 korea인 p요소를 선택
		$('p[title=korea]').css('background', 'pink');
	
		//title속성을 가진 요소를 선택하라
		$('p[title]').css('color','blue');
		
		//type속성이 password인 요소의 테두리 변경
		$('input[type=password]').css('border','1px solid red');
		
		//class속성에 hello글자가 포함된 요소 선택하기: *=
		$('p[class*=hello]')
		$('input[class*=hello]')
		
		//위의 두개를 콤마로 이어쓸 수 있다.
// 		$('p[class*=hello], input[class*=hello]').css('border','2px solid green');
	
		$('*[class*=hello]').css('border','5px solid green');
		
		//class속성에 hello 단어가 포함된 요소 선택 : ~=
		$('*[class~=hello]').css('border','5px solid gold');
		
	})
	
	$('#btn2').on('click', function(){
		//input  요소 테두리 lime , 배경색  핑크
		$('#result2 input').css({
			"border": "1px solid lime",
			"background" : "pink"
		});
		
		//input   type이 button 인 요소 배경색 노란
		$('#result2 input[type=button]').css('background','yellow');
		
		//Button 요소와 input type이 button 인 요소 배경색 하늘색
		$('#result2 button, #result2 input[type=button]').css('background', 'skyblue');
		
		//Type이 submit 요소의 배경색 녹색
		$('*[type=submit]').css('background', 'green');
		
		//Type이 reset인 요소의 배경색 노랑 
		$('*[type=reset]').css('background', 'yellow');
		
		//Type이 text, password 인 요소의 테두리 파랑
		$('*[type=text], *[type=password]').css('border', '1px solid blue');
		
		//Type이 file, image인요소의 테두리 빨강
		$('*[type=file], *[type=img]').css('border', '1px solid red');
	})
	
})
</script>
</head>
<body>

<div class="box">
 <h4></h4>
 <input type="button" value="확인" id="btn1">
 <br><br>
 <div id="result1">
  <p class="hellojava"> 일반 문단입니다 </p>
  <p title="툴팁으로 보입니다"> 타이틀을 가지고 있는 문단입니다 </p>
  <p title="korea"> 일반 문단입니다 </p>
   회원번호  <input class="hello html" type="text"><br>
   비밀번호  <input type="password">
   <input type="button" value="확인">
 </div>
</div>

<div class="box">
 <h4></h4>
		 <input type="button" value="확인" id="btn2">
		 <br><br>
		 <div id="result2">
		 <!-- onsubmit="return false;" 실행안한다는의미 -->
		 <form action="속성선택.jsp" method="post" > 
		Text : <input type="text"/><br> 
		Password : <input type="password" /><br><br> 
		
		Radio : <input type="radio" name="radioGroup" id="radioA" value="A"/> A 
		<input type="radio" name="radioGroup" id="radioB" value="B"/> B<br><br> 
		
		Checkbox : <input type="checkbox" name="checkboxes" id="checkbox1" value="1"/> 1 
		<input type="checkbox" name="checkboxes" id="checkbox2" value="2"/> 2<br><br> 
		
		Textarea : <br> <textarea rows="15" cols="50" id="myTextarea" id="myTextarea"></textarea><br><br> 
		Image : <input type="image" src="../images/check.png"><br><br> 
		File : <input type="file"><br><br> 
		
		Buttons : <button type="button" id="normalButton">Normal</button> 
		<button type="submit" id="submitButton">Submit</button> 
		<button type="reset" id="resetButton">Reset</button> <br><br>
		
		<input type="button" value="일반버튼"> 
		<input type="submit" value="전송버튼"> 
		<input type="reset" value="초기화버튼"> <br><br>
		</form>
 </div>
</div>
</body>
</html>
```

#### 5. 입력양식(form) 선택자
![[Pasted image 20240112200109.png]]

