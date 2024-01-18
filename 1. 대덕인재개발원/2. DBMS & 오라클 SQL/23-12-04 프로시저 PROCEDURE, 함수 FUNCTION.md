
#### 1. 프로시저 PROCEDURE
``STORED PROCEDURE: 서버에 저장된, 미리 컴파일된 SQL문장들``

-  익명 블록에 의해서 제공되는 기능이다.
	반복문의 기본이 루프고, 루프의 헤더에 while이나 for문이 오면 while반복이나 for반복이 되는 것처럼 익명블록에 FUNCTION을 쓰면 함수가, PROCEDURE 을 쓰면 프로시져가... 라고 생각하면 된다.

###### 프로시저의 장점
- 저장 프로시저가 처음 수행될 때 문법을 검사하고 컴파일된다. 컴파일된 버전은 프로시져 캐시에 저장되므로 이후에 호출될 때 빠르게 수행될 수 있다.
	프로시저를 실행할 때 프로시저의 전체 코드가 다 전달되어 실행되는 것이 아니라 한두단어만으로 전달되어 실행되는 것이다.
- 클라이언트 간 처리루틴을 공유할 수 있다. 모든 응용 프로그램에서 사용할 수 있도록 기능을 캡슐화하므로 일관성 있는 데이터 변경을 보장한다.
- 데이터베이스 내부 구조 보안
	view와 동일한 개념, 작동한 결과는 사용자에게 제공하되 사용자로부터 내부에 접근하는 것을 방지한다.
- 서버 보호, 자료 무결성 권한 구현
	- view 사용 시 table 을 직접 access하는 것이 아니라 view에 대한 권한만 주어 table의 특정 컬럼과 레코드만 access하도록 제한할 수 있다. 
	- 저장함수도 이와 같은 개념으로 서버 데이터를 보호하는데 사용 가능
- 쿼리처리속도 향상
	저장함수 안에 지정된 모든 sql구문을 하나의 batch로 인식하여 한꺼번에 분석, 최적화시키고 실행한다. 각각의 sql구문을 클라이언트로부터 받아서 매번 분석, 최적화, 실행과정을 반복하는 것에 비해 처리속도가 현저히 향상된다.
- 내부 내트워크 트래픽 감소
	클라이언트에서 서버로 보내야 할sql구문을 서버가 미리 저장, 고로 클라이언트에서 대량의 sql구문을 보내는 대신에 stored procedure의 이름과 매개변수만 보내면 되므로 network traffic이 그만큼 줄어드는 효과를 얻을 수 있다.


###### 프로시저 사용 형식
```
CREATE [OR REPLACE] PROCEDURE 프로시져 이름
  [(argument [mode] [{:= | default}] expression, ...)] -- 여긴 세미콜론 안붙는다.
{IS|AS}
-- IS/AS 다음에 DECLARE가 생략됐다고 생각하면 된다.
-- 선언 영역이라고 생각하면 됨, 선언문이 나올 수 있다.
BEGIN
 PL/SQL_BLOCK;
END;
```

|항목|내용|
|---|---|
|or replace|프로시저가 생성된 경우 다시 생성|
|argument|매개변수 이름|
|mode| IN 입력전용 OUT 출력전용 INOUT 입출력|
|expression|매개변수의 값이 없을 때 기본값 설정|
|pl/sql_block|PL/SQL 블록|

-  IN, 입력: 프로시저 입장에서의 입력이다.
- OUT, 출력: 프로시저 입장에서의 출력으로, 변수를 통해 데이터를 프로시저 밖으로 전달할 때 사용하는 매개변수이다. 
	반환과는 다른 개념이다.  반환: 해당 객체 이름을 통해 값이 되돌려지는 것. 
	예를 들어, SUBSTR은 함수명이 쓰인 위치에 반환값을 반환해준다.
- INOUT, 입출력: 프로시저 입장에서의 입력이다.

###### 프로시저 실행방법
-  프로시저는 반환값이 없기 때문에 SELECT문이나 조건절에 사용할 수 없다.
-  반면, INSERT, UPDATE, DELETE는 반환값이 필요하지 않으니 프로시져가 적당하다.
``그럼 SELECT는 FUNCTION쓰면되나? ...반환값이 여러개면 FUNCTION도 곤란하다.``

1) 출력 매개변수가 없을 때
```
EXEC|EXECUTE 프로시져명 [(매개변수 list)]; 
```

2) 프로시져에 출력용 매개변수가 사용된 경우
- 이를 전달받을 변수가 반드시 필요: 익명블록 또는 다른 프로시져 등이 필요
```
-- 익명블록의 BEGIN영역 내에서
  프로시져명[(매개변수 list)]; 형태로 기술
```

-  사용예: 오늘이 2020년 7월 28일이라고 가정하였다. 's001' 회원이 다음과 같이 상품을 구매했을 때 이 정보를 cart 테이블에 저장하는 프로시져를 작성하시오.
	프로시져명: proc_insert _cart, 입력자료: 오늘 날짜, 회원번호, 상품코드
```

--------------------------------------
상품코드        수량
------------------- -------------------
P202000007      2
EXECUTE proc_insert_cart('P202000007', 2,'s001',SYSDATE);

P202000012      1
EXECUTE proc_insert_cart('P202000012', 1,'s001',SYSDATE);

-- 만약 m001이라는 사람이 삿다면?
EXECUTE proc_insert_cart('P302000014', 5,'m001',SYSDATE);
-- 만약 s001보다 먼저 구매한 b001이라는 사람이 또 삿다면?
EXECUTE proc_insert_cart('P302000014', 5,'b001',SYSDATE);

P302000014      5
EXECUTE proc_insert_cart('P302000014', 5,'s001',SYSDATE);


```

```
CREATE OR REPLACE PROCEDURE proc_insert_cart (
    P_CID IN CART.CART_PROD%TYPE,
    P_QTY IN NUMBER,
    P_MID IN CART.CART_MEMBER%TYPE,
    P_DATE IN DATE)
IS
 -- 정공법
 L_DATE CHAR(8) := TO_CHAR(P_DATE,'YYYYMMDD');
 L_NUM NUMBER(5) :=0;
 L_CART_NO CHAR(13); 
 L_FLAG NUMBER :=0; -- 해당일의 로그인한 회원 수
 L_MID CART.CART_MEMBER%TYPE;
BEGIN
 -- CART 테이블에 오늘 날짜 매출 정보가 존재하는지 여부 판단
 SELECT COUNT(*) INTO L_FLAG
   FROM CART
   WHERE CART_NO LIKE L_DATE||'%';
   -- L_DATE(20200728)로 시작하는 모든 카트번호를 세어서 L_FLAG에 넣어주자는 얘기다.
   IF L_FLAG = 0 THEN
    L_CART_NO := L_DATE||TRIM('00001');
    -- 오라클에서 문자열을 결합할 때 항상 사이에 공백이 들어가기때문에
    -- TRIM을 써줘야 한다.
	ELSE
    SELECT MAX(CART_NO) INTO L_CART_NO
      FROM CART
     WHERE CART_NO LIKE L_DATE||'%';
     -- 가장 큰 카트번호를 갖는 사람이 두사람인데
     -- 그걸 하나의 변수에 집어넣으려고 했으니 오류가 났던 거다.
     -- DISTINCT를 써 주자.
     SELECT DISTINCT(CART_MEMBER) INTO L_MID
     FROM CART
     WHERE CART_NO = L_CART_NO;
	 
     IF P_MID != L_MID THEN 
       L_NUM := TO_NUMBER(SUBSTR(L_CART_NO,9))+1 ;
       L_CART_NO := L_DATE||TRIM(TO_CHAR(L_NUM,'00000'));
     END IF;
	END IF;
  INSERT INTO CART(CART_MEMBER, CART_NO, CART_PROD, CART_QTY)
     VALUES (P_MID, L_CART_NO, P_CID, P_QTY);
    COMMIT;
END;
```

-  사용예2: 분류테이블에 다음 자료를 입력 받아 추가하고 입력한 분류코드와 이름을 반환하는 프로시져를 작성하시오. 
	LPROD_ID는 시퀀스를 사용할 것, 프로시져 이름은 proc_insert_lprod
```
------------------------------
 LPROD_GU         LPROD_NM
------------------------------
   P103         거실용 전자제품
   P104         주방용 전자제품
   P105         청소용 전자제품

-- 일단 사용할 시퀀스를 만들어주자.
-- 12까지는 데이터가 있으니 13부터 시작해주기로 했다.
create sequence seq_lprod_id
 start with 13;
 
CREATE OR REPLACE PROCEDURE proc_insert_lprod(
 P_LGU LPROD.LPROD_GU%TYPE, 
 P_LNM LPROD.LPROD_NM%TYPE,
 P_CONTENT OUT VARCHAR2)
 -- OUT의 경우 출력할 내용을 적어줘야 한다.
 -- IF문이면 각 상황에 하나씩 출력내용을 적어줘야 한다.
 
 IS
 
  L_FLAG NUMBER := 0;
  -- 데이터를 넣기 전에 데이터가 있는지 확인한 후
  -- 이미 데이터가 있으면 안 넣고 없으면 넣을 목적으로 썼다.
  
 BEGIN
  SELECT COUNT(*) INTO L_FLAG
   FROM LPROD
   WHERE LPROD_GU=P_LGU;
   
   IF L_FLAG != 0 THEN 
    DBMS_OUTPUT.PUT_LINE(P_LGU||'코드를 갖는 분류가 이미 존재합니다.');
    P_CONTENT := '추가된 자료 없음';
  ELSE 
    INSERT INTO LPROD
    VALUES(seq_lprod_id.NEXTVAL, P_LGU, P_LNM);
    COMMIT;
    P_CONTENT := '분류코드는: '||P_LGU||', 분류명은: '||P_LNM;
  END IF;
 END;

--------------------여기까지 프로시저를 만드는 과정이었다---------------------

 -- 프로시저 실행
 
 DECLARE
 
	 L_CONTENT VARCHAR2(100);
 BEGIN
	  proc_insert_lprod('P103', '거실용 전자제품', L_CONTENT);
	  DBMS_OUTPUT.PUT_LINE('입력한 자료는 '|| L_CONTENT);
 END;
 
 
```
-  사용예 3: 2020년 7월 판매자료를 이용하여 재고수불테이블을 갱신하시오, 업데이트날짜는 7월 31일이다.

```
사용예) 
 1) 2020년 7월 판매된 상품의 상품코드와 상품별 수량집계 ==> 커서로 만들어져야 한다.
 
 SELECT CART_PROD,
        SUM(CART_QTY)
    FROM CART
    WHERE CART_NO LIKE '202007%'
    GROUP BY CART_PROD;

-- 이 SELECT문은 이런 식으로 출력될 것이다.
-----------------
제품코드   수량
-----------------
무슨제품   몇개
어쩌고제품  몇개

-- 이런 식의 결과를 하나하나 넣어주려면 커서를 사용해야 한다.

 (프로시져 만들기)
 CREATE OR REPLACE PROCEDURE proc_update_remain(
 P_CID PROD.PROD_ID%TYPE,
 P_QTY NUMBER)
 IS
 BEGIN
  UPDATE REMAIN A
   SET A.REMAIN_O = A.REMAIN_O+P_QTY,
       A.REMAIN_J_99 = A.REMAIN_J_99-P_QTY,
       A.REMAIN_DATE = TO_DATE('20200731')
  WHERE A.REMAIN_YEAR='2020' 
   AND  A.PROD_ID = P_CID;
   COMMIT;
 END;

(이하 익명블록)
 DECLARE 
 
 L_CNT NUMBER :=0;
 
  -- 앞선 이유로 만들어 준 커서
  CURSOR CUR_UPDATE_REMAIN
  IS
      SELECT CART_PROD AS CID,
             SUM(CART_QTY) AS CSUM
        FROM CART
        WHERE CART_NO LIKE '202007%'
        GROUP BY CART_PROD;

 BEGIN
 -- FOR반복을 사용하였기 때문에 커서사용이 굉장히 간편해졌다.
  FOR REC IN CUR_UPDATE_REMAIN LOOP
     L_CNT := L_CNT+1;
     -- 만든 프로시저를 여기에서 사용하였다.
    proc_update_remain(REC.CID, REC.CSUM);
  END LOOP;
  DBMS_OUTPUT.PUT_LINE('처리건수: '||L_CNT);
  
 END;
```


#### 2. 함수 FUNCTION
**USER DEFINED FUNCTION**
-  FUNCTION은 PROCEDURE이 갖는 장점을 동일하게 갖고 있다.
- 반환값이(하나) 있다. 즉, 일반 오라클 내장함수처럼 사용할 수 있다는 것이다. 자주 반복되는 서브쿼리, 복잡한 계산식을 사용자가 만들어서 일반 함수처럼 사용할 수 있다.
- 반환할 데이터 타입을 리턴으로 선언해야 한다. 반환값이 있으니까!!
- 실행영역에서 리턴문이 반드시 있어야 한다.

-  FUNCTION 사용형식
```

CREATE [OR REPLACE] FUNCTION 펑션 이름
  [(argument [mode] [{:= | default}] expression, ...)] 
RETURN 리턴할 데이터타입
{IS|AS} -- IS/AS 
BEGIN
 PL/SQL_BLOCK; -- 여기서 진짜 반환이 이루어진다!!!
END;

```
-  펑션의 MODE는 IN밖에 못 쓴다. OUT을 할 수 없으니 OUT, INOUT모두 안 됨
-  처음으로 기술되는 RETURN 뒤에는 리턴값이 아니라 리턴할 데이터 타입이 기술되어야 한다.
- IS/AS 다음에 DECLARE가 생략됐다고, 선언 영역이라고 생각하면 된다.
	즉, 선언문이 나올 수 있다.


-  사용예: 년, 월로 이루어진 기간을 입력하여 해당 기간동안 발생된 상품별 매입집계를 조회하시오.
```
CREATE OR REPLACE FUNCTION fn_sum_buyprod(
 P_SDATE VARCHAR2,
 P_EDATE VARCHAR2,
 P_CID PROD.PROD_ID%TYPE)
 -- 여기까지가 매개변수
 
 RETURN NUMBER
 -- 리턴값이 아닌, 리턴 타입이다.
 
 IS
  L_BQTY NUMBER := 0; -- 매입수량집계
  L_SDATE DATE := TO_DATE(P_SDATE||'01');
  L_EDATE DATE := LAST_DAY(TO_DATE(P_EDATE||'01'));
  
 BEGIN
  SELECT SUM(BUY_QTY) INTO L_BQTY
  FROM BUYPROD
  WHERE BUY_DATE BETWEEN L_SDATE AND L_EDATE
   AND BUY_PROD = P_CID;
   RETURN L_BQTY;
   
 END;
 
---------------------만든 함수를 실행해보자------------------------------
 SELECT PROD_ID AS 상품코드,
       NVL(fn_sum_buyprod('202006', '202006', PROD_ID),0) AS 매입수량합계
  FROM PROD
  CORDER BY 1;
```