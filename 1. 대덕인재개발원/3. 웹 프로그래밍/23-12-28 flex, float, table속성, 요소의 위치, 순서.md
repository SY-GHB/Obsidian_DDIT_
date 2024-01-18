###### 수업필기
caption-side 테이블 캡션 위치 지정
empty-cells 빈 셀에 테두리와 배경을 표시할지 여부 설정
(테이블 안에 내용이 없을 때 테두리를 그릴거냐 안그릴거냐? 초기값은 그림) - collapse가 아닐 경우에 얘기임
 table속성.html에서 실습해봤다.

text-align-last? 마지막줄정렬? 마지막줄만 다르게 정렬할수있나본데
text-align: justify 양쪽정렬

위에서 아래로 우선순위
but 클래스가 우선순위가 더 높다.

레이아웃 표시1 display속성
block, inline, inline-block, none-안보임. 왜??
처음엔 안보이게 설정했다가 나중에 이벤트가 발생하면 보이도록 설정할 때 none을 준다.
없었다가 다시 생기는 게 block이라면 옆에있던 블럭이 바닥으로 내려간다.
flex나 greed도 줄 수 있음

positon 요소의 위치를 지정하는 속성
static 정적으로 배치된다(기본값) : 코딩한 순서대로 나온다는 얘기
relative 정적인 위치를 기준으로 배치된다
absolute 컨테이너를 기준으로 배치된다-왼쪽 위 기준.스크롤바를 올렷다내렷다하면 간다.
fixed 고정위치 항상 같은 위치(컨테이너 원점)에 배치된다.-스크롤바를 움직여도 제자리에 잇다.

z-index 스택순서지정
다른 요소와 겹치는 경우 요소의 스택 순서를 지정할 수 있다.
양수와 음수를 사용할 수 잇고, 스택 순서가 큰 요소가 낮은 요소 앞에 위치한다
z-index가 지정되지 않고 겹치면 코드에서 제일 마지막에 배치된 요소가 맨 위에 표시된다.


overflow 속성 : 영역에 맞지 않는 콘텐츠를 제어, 높이가 지정된 블록요소에서만 자곧ㅇ
visible – 기본 값이지만 과적된 내용이 제어되지 않는 상태
hidden – 과적된 부분은 보이지 않는 상태
scroll – 스크롤 바가 추가되어 내용을 볼 수 있는 상태
auto – 필요 시 스크롤 바 생성

clear : float 속성 중단 시 사용
float 속성이 부여된 요소의 옆 요소는 비어있는 영역을 채우듯 표현된다.
해당 속성을 적용하고 싶지 않은 경우 사용한다.

border는 width style color 세가지 속성으로 이루어지는데 이 중 메인되는 속성은 style이다.
이게없으면 작동을안함.

Semantic Elements : 웹 페이지의 구조를 쉽게 이해할 수 있도록 정의된 태그. 단순 컨테이너 역할
개발자 간 혼란을 없애고, 검색 엔진 등에서 소스를 쉽게 판독

header와 section은 headline tag하나는 가지고 잇어야 한다.
안준다고해서 실행안되는건아니지만 웹 접근성 표준에 정의되어있는 사항임.

새탭에서 페이지를 바꿀거냐 아이프레임에서바꿀거냐 그런거 설정하는게 타겟
딴데서 만든(예를들어 아이프레임을 햇다면) 네임을 가져와서 아이프레임으로 하나봄
a태그에 target="ifr" 이런식으로,,,
iframe문서 다시 확인할것

#### 1) flex 레이아웃
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
body *{
 box-sizing: border-box;
 margin: 2px;
 padding: 5px;
 border-radius: 50px;
/*  border: 2px dotted gold; */
}
#parent1{
  display: flex;
  flex-direction: column;
}

#second{
 display: flex;
 flex-direction: row;
}

header, footer{
  height: 80px;
  background-image: linear-gradient(skyblue, lightyellow);
}
aside, section{
 height: 500px;
}
aside{
/*   flex: 1,1,auto; */
 flex: 30%;
 background: #fadadd;
 padding: 50px;
}
section{
 flex: 70%;
 background: #effedd;
}

section h3, h4{
 width: 90%;
 margin: 10px auto;
 text-align: center;
}

img{
  width: 50%;
  height: 51%;
}

section div{
  width: 90%;
  height: 40%;
  border: 3px dotted pink;
  margin: 10px auto;
}

</style>
</head>
<body>
<div id="parent1">
 <header></header>
 <div id="second">
  <aside>
  <h3>LINKs</h3>
   <ul>
    <li><a href="http://www.w3schools.com">w3schools</a></li>
    <li><a href="http://www.naver.com">naver</a></li>
    <li><a href="http://www.daum.com">daum</a></li>
   </ul>
   
   <figure>
    <img src="../images/spongebob.png" alt="spongebob.png">
    <figcaption>스폰지밥</figcaption>
   </figure>
  </aside>
  
  <section>
   <h3>여기는~</h3>
   <div id="top">
    <h4>div와 span</h4>
    <p></p>
   </div>
   <div id="bottom">
    <h4>simentic tag</h4>
    <p></p>
   </div>
  </section>
 </div>
 <footer></footer>
</div>

</body>
</html>
```
#### 2) float 레이아웃
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
body{
 min-width:900px;
}
div {
  box-sizing: border-box; /*크기 설정할 때 border나 padding등을 주기 위함*/
  border-radius: 20px;
  border: thin solid lightgray;
  margin: 3px;
  padding: 5px;
}
#header, #footer{
  height: 80px;
  background-image: linear-gradient(skyblue,white);
}
#left, #right, #main{
  height: 500px;
}
#left, #right{
/*   width: 24%; */
 width: calc(25% - 1%); /* - 기호 앞뒤에 공백이 있어야 한다.*/
}
#left{
  float: left;
}
#right{
  float:right;
}
#main{
/*   width: 50%; */
 width: calc(50% - 1%);
  float: left;
}
#footer{
  clear: both;
}

</style>
</head>
<body>
<div id="header">header</div>
<div id="left">left</div>
<div id="main">main</div>
<div id="right">right</div>
<div id="footer">footer</div>
</body>
</html>
```
#### 3) float 정렬
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
img {
	width: 220px;
	height: 200px;
	float: left;
	margin: 20px;
}

p{
/*   float: right; */
  width: 700px;
}

</style>
</head>
<body>
<img src="../images/사자.jpg" alt="사자.jpg">

<p>
대부분 무리지어 생활한다. 한 무리는 성숙한 젊은 수컷 1-3마리와 암컷 10마리, 늙은 암컷 5마리, 덜 성숙한 암컷 2마리, 덜 성숙한 수컷 1마리, 한 살 이상의 새끼 1-2마리, 한 살 이하의 새끼 1-5마리로 이루어지는데, 이것을 프라이드(pride)라고 한다. 프라이드는 거의 일정한 행동권을 갖는데 보통 40-50km2 반경에서 이루어진다. 프라이드 가운데 가장 큰 프라이드를 슈퍼프라이드라고 부르는데 약 30마리로 구성된 것도 알려져 있다.

사자의 무리는 슈퍼프라이드 규모라 해도 완전히 성숙한 수컷사자는 오직 우두머리의 역할을 하는 1마리 뿐이다. 수컷사자는 하루에 20시간을 자거나 쉬면서 보낸다. 반면 암컷사자들은 하루종일 사냥한다. 큼직한 먹이로 포식을 했을 때는 꼼짝 않고 24시간 내내 쉰다. 그러나 배가 고프면 먹이를 찾아서 24km나 가기도 한다. 사자는 자신의 텃세권 안에서 다른 동물이 사냥하지 못하게 한다.

수컷사자들은 덤불에 냄새가 나는 분비물을 배설하는데 3-4주 가며, 또 포효함으로써 영역을 알리고 침입자에게 나가라고 경고한다. 보통 포효는 8km까지 전달된다고 한다. 그러면 침입자는 이미 주인이 있는 영역임을 알게 된다. 그러나 경고를 무시하고 나가지 않으면, 침입자가 죽는 경우도 생긴다.

사자는 일부다처제의 생활을 하고 있으며 이로 인하여 사자의 무리에서 장성한 수사자는 단 한마리 뿐이다. 이 때문에 장성한 수사자는 무리에서 이탈하여 독립 생활을 한다. 수컷사자의 경우 포유류 중에 유난히 잠이 많아 사냥은 암컷사자들이 대신 담당하면서 수사자는 암사자들이 갖다 바치는 사냥감으로 살아간다. 한마디로 수컷사자는 포유류 중, 가장 게으르다고 볼 수 있다. 수컷사자는 무리의 우두머리일 경우 다른 수사자와의 힘겨루기를 할 때만 그 용맹성을 보인다. 무리에 침입한 수컷사자가 기존의 대장이였던 수컷사자를 상대로 이기고 영역을 차지한다. 이는 독립한 수컷이 혼자 살아가기 위해서이다. 수명은 약 9~13년이다. 인간기준으로 54세~78세다. 다만 수컷사자는 9년에서 11년 암컷사자는 11년에서 16년 정도 산다.</p>

<img src="../images/사자.jpg" alt="사자.jpg">
</body>
</html>
```
#### 4) table 속성
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table {
	border: 1px solid blue;
/* 	border-collapse: collapse;*/
	border-spacing: 20px;
	empty-cells: show;
}
td {
	width: 200px;
	height: 50px;
/* 	text-align: center; */
}

th {
	height: 60px;
}

/*tr:nth-child(2n){
  background: lightblue;
}

tr:nth-child(2n+1){
  background: lightyellow;
}*/

tr:first-child{ /*막내는 last-child*/
  background: black;
  color: white;
  }

.ev{
  background: lightgreen;
}

.od{
  background: lightgray;
}

/*tr:first-child{ /*막내는 last-child/
  background: black;
  color: white;
}*/

</style>
</head>
<body>
<table border='1'>

<tr class="od">
 <th>제목1</th>
 <th>제목2</th>
 <th>제목3</th>
 <th>제목4</th>
</tr>

 <tr class="ev">
  <td>1</td>
  <td>2</td>
  <td>3</td>
  <td>4</td>
 </tr>
 
 <tr class="od">
  <td>1</td>
  <td>2</td>
  <td>3</td>
  <td></td>
 </tr>
 
 <tr class="ev">
  <td>1</td>
  <td>2</td>
  <td>3</td>
  <td></td>
 </tr>
 
 <tr class="od">
  <td>1</td>
  <td>2</td>
  <td>3</td>
  <td></td>
 </tr>
 
 <tr class="ev">
  <td>1</td>
  <td>2</td>
  <td>3</td>
  <td></td>
 </tr>
 
</table>
</body>
</html>
```
#### 5) 레이아웃 포지션
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>

div{
 width: 100px;
 height: 100px;
 margin: 10px;
}

.dd1{
  background: yellow;
}
.dd2{
  background: pink;
}
.dd3{
  background: cyan;
}
.dd4{
  background: blue;
}

#d5{
  position: relative;
  /*왼쪽 위 점을 기준으로 이동한다.*/
  top: 150px;
  left: 50px;
}

#d7{
 position: absolute;
 top: 250px;
 left: 300px;
}

#d8{
  position: fixed;
  top: 200px;
  right: 200px;
  color: white;
}

</style>
</head>
<body>

<div id="d1" class="dd1"></div>
<div id="d2" class="dd2"></div>
<div id="d3" class="dd3"></div>
<div id="d4" class="dd4"></div>
<!-- 이 줄은 static -->
<hr>

<div id="d5" class="dd1">
  position: relative;<br>
  top: 150px;<br>
  left: 50px;
  </div>
<div id="d6" class="dd2"></div>
<div id="d7" class="dd3">
 position: absolute;<br>
 top: 250px;<br>
 left: 300px;
 </div>
<div id="d8" class="dd4">
  position: fixed;<br>
  top: 200px;<br>
  right: 200px;
  </div>

</body>
</html>
```
#### 6) 요소순서 z-index
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>

div{

 width: 100px;
 height: 100px;
 margin: 10px;
 position: absolute;
 
}

.dd1{
  background: yellow;
  top: 200px;
  left: 200px;
  z-index: 5;
}
.dd2{
  background: pink;
  top: 230px;
  left: 250px;
  z-index: 
}
.dd3{
  background: cyan;
  top: 280px;
  left: 270px;
  z-index: 6;
}
.dd4{
  background: blue;
  top: 350px;
  left: 350px;
  z-index: 1;
}

</style>
</head>
<body>
<div id="d1" class="dd1"></div>
<div id="d2" class="dd2"></div>
<div id="d3" class="dd3"></div>
<div id="d4" class="dd4"></div>
</body>
</html>
```