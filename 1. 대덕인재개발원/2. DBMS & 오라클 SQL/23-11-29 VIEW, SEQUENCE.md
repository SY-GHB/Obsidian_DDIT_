#### 뷰 VIEW
*PLSQL에서는 (좁은 의미의)커서라고도 한다.*

-  TABLE과 유사한 객체로, 가상의 테이블이라 한다.
-  SELECT문의 결과 집합이다. (그러나 SELECT문에 종속적인 것은 아니다.)
	자바 클래스에도 이름이 없는 클래스(익명 클래스)가 있긴 함, 클릭 이벤트에서만 호출된다.
- VIEW를 사용하는 경우
 1) 필요한 데이터가 여러 테이블에 분산되어 있고 빈번히 조회해야 하는 경우, 필요한 자료를 미리 VIEW로 구성하고 필요시 테이블처럼 조회하면 된다.
	 우리가 조인할 때 프롬절에 서브쿼리를 쓴 건 뷰의 형태로 끌고 온 것이다.
	 FROM절에는 테이블과 뷰만 올 수 있다는 얘기다.
	 
	 테이블과 실시간으로 변하는 차트를 바로 연결하는 것이 아니라,
	 테이블-뷰-차트를 연결하고 뷰를 조회하면 다시 쿼리를 작성하지 않아도
	 차트에 바로바로 반영할 수 있다.
2) 특정자료에 대한 접근을 제한하고자 할 때(보안을 위함이다.)

##### 사용형식
```
CREATE [OR REPLACE] VIEW 뷰이름 [(컬럼명,...)]
AS 
 SELECT 문
 [WITH CHECK OPTION]
 [WITH READ ONLY];

 -- SELECT문의 결과가 컬럼명으로 저장된다. 즉, SELECT문의 컬럼 갯수와 같아야 한다.
-- 단, SELECT절에 사용되어진 컬럼에 별칭이 있다면 별칭을 더 우선한다.
 -- 여기 사용된 컬럼의 갯수와 뷰이름 뒤에 오는 컬럼명의 갯수는 같아야 한다.
 --SELECT문은 결과가 나올 수 있는 완벽한 SELECT문이어야 한다.
```
- 'REPLACE' : 이미 '뷰이름'이 존재하면 OVERWRITE하고, 존재하지 않으면 생성한다.
	하나의 명령문이다. 생략할 수 있지만, 어느 형식에서 쓰고 어느 형식에서 생략하는지 기억하기 어려우니까 REPLACE가 붙어있다고 외워버려라.
- '[(컬럼명,...)]': 뷰에서 사용할 컬럼명. 생략한 이후에는 이하 두가지 경우가 있다.
	1. 원본SELECT문의 SELECT절에 컬럼별칭이 사용된 경우에는 별칭이 뷰의 컬럼명이 된다.
	2. 원본SELECT문의 SELECT절에 컬럼별칭을 사용하지 않은 경우에는 SELECT문의 컬럼명이 뷰의 컬럼명이 된다.
	 뷰이름에서 지정해준 컬럼명>SELECT문의 별칭 컬럼명>SELECT문의 컬럼명 순
- 'WITH CHECK OPTION' : 원본 SELECT문에 사용된 WHERE 조건절을 위배하는 결과를 초래할 뷰의 변경(INSERT, UPDATE)이 금지된다.
- 'WITH READ ONLY': 읽기 전용 뷰의 생성
	아무 조건이 걸리지 않은 VIEW를 수정하면 원본 테이블마저 변경된다.
	이런 일을 막기 위해  READ ONLY를 쓴다.
	서로 상충하는 부분이 있어, WITH CHECK OPTION과 WITH READ ONLY는 함께 쓸 수 없다.
	둘 다 쓰지 않으면 VIEW에 대해서 SQL명령을 제한 없이 사용할 수 있다.
- 어떤 경우에나 SELECT문의 대상인 원본 테이블의 내용 변경은 자유롭고 원본 테이블의 변경 결과는 즉시 뷰에 반영된다.

-  뷰 생성 예시
```
CREATE OR REPLACE VIEW V_PROD1--(PID, PNAME, PRICE, COST)
AS
--(판매가가 100만원 이상 되는 상품의~)
 SELECT PROD_ID AS 상품번호,
        PROD_NAME AS 상품명,
        PROD_COST AS 매입가,
        PROD_PRICE AS 매출가
    FROM PROD
    WHERE PROD_PRICE>=1000000;
```

- 만약 생성된 뷰의 항목 중 하나를 변경한다면?
```
UPDATE V_PROD1
 SET 매출가 = 10000
 WHERE 상품번호='P102000005';
```
어떠한 제약도 가하지 않은 VIEW의 변경은 원본테이블의 변경을 불러온다.
이 때, 변경된 항목이 SELECT 구문에 설정된 조건에 위배된다면 뷰에서 빠진다. 
원본테이블에서 조건을 다시 충족하도록 설정한다면 다시 뷰에서 보인다.

###### WITH CHECK OPTION을 사용한 경우
```
CREATE OR REPLACE VIEW V_PROD1
AS
 SELECT PROD_ID AS 상품번호,
        PROD_NAME AS 상품명,
        PROD_COST AS 매입가,
        PROD_PRICE AS 매출가
    FROM PROD
    WHERE PROD_PRICE>=1000000
    WITH CHECK OPTION;
```

여기서 
```
UPDATE V_PROD1
 SET 매출가 = 10000
 WHERE 상품번호='P102000005';
```
명령을 실행하면, view WITH CHECK OPTION where-clause violation라는 오류가 뜬다.
원본 SELECT문의 조건에 맞지 않는 조건으로 실행했으니 오류가 뜨는 것이다.
만약, 조건에 맞는(백만원보다 넘는) 가격으로 변경한다면 오류가 뜨지 않고 잘 실행될것이다.

###### WITH READ ONLY를 사용한 경우
```
CREATE OR REPLACE VIEW V_PROD1
AS
 SELECT PROD_ID AS 상품번호,
        PROD_NAME AS 상품명,
        PROD_COST AS 매입가,
        PROD_PRICE AS 매출가
    FROM PROD
    WHERE PROD_PRICE>=1000000
    WITH READ ONLY;
```

여기서 
```
UPDATE V_PROD1
 SET 매출가 = 10000
 WHERE 상품번호='P102000005';
```
명령을 실행하면, cannot perform a DML operation on a read-only view가 뜬다.
읽기 전용으로 VIEW를 생성했기 때문에 원본 테이블에 변형을 가할 수 없다.
반면, 읽기 전용의 VIEW라 할지라도 원본테이블의 변경은 VIEW에 바로바로 업데이트된다.

##### 뷰 사용시 주의사항
- 뷰 생성시 제약사항(WITH)절이 있는 경우, ORDER BY절을 사용할 수 없다.
- 뷰가 집계함수, GROUP BY, DISTINCT 함수를 사용하여 생성된 경우, 뷰에 DML(INSERT/UPDATE/DELETE)를 사용할 수 없다.
- 뷰를 구성하는 SELECT문에서 표현식(DECODE, CASE WHEN~THEN), 일반 함수를 사용하여 만들어진 경우 해당 컬럼에 대한 수정이 제한된다.
	뷰의 변경사항이 원본테이블을 변경시키는데, 표현식인경우 원본이라고 할만한 게 없어서이다.
- CURRVAL, NEXTVAL 등의 의사컬럼(PSEUDO COLUMN)은 사용할 수 없다.
	(CURRENT VALUE를 줄여서 CURRVAL이라고 부른다.)
	두 의사컬럼은 시퀀스에서 갖고 있는 컬럼이다.
	뷰를 사용할 때에는 뷰 안에 시퀀스를 넣으면 안 된다.
	시퀀스: 테이블에서 독립적이다(여러 테이블에서 동시에 쓸 수 있다.(근데 잘못쓰면 듬성듬성한 결과가 나올 수 있다....SIDE EFFECT?))
- ROWID, ROWNUM, LEVEL등의 의사컬럼이 사용될 때에는 별칭을 사용해야 한다.
	ROWID 행의 값이 저장되는 물리적 위치
	LEVEL 오라클에서만 제공되는, 계층적 구조로 나타내주는 것... mysql같은덴 제공되지 않는다.


#### 시퀀스 SEQUENCE
- 차례대로 증가(감소)되는 값을 생성하는 객체
- 테이블에 독립적이다: 여러 테이블에 사용될 수 있다.
	그러나 여러 군데에 잘못 사용했다간 여러 군데에 동시에 문제가 일어난다...
	이를 SIDE EFFECT 라고 한다.
- SEQUENCE가 주로 사용되는 경우
1) 테이블에 PK로 사용할 적당한 컬럼이 존재하지 않는 경우, 특별한 의미를 부여하지 않아도 되는 PK 생성
	가장 많은 경우: 게시판 게시글 번호, 우리 장바구니번호의 뒷자리 5개
2) 컬럼의 일부 또는 컬럼 값에 자동으로 증가되는 값이 필요한 경우

-  사용형식
```
CREATE SEQUENCE 시퀀스명
 [START WITH 값] 
 [INCREMENT BY 값] 
 [MAXVALUE n | NOMAXVALUE] 
 [MINVALUE n | NOMINVALUE] 
 [CYCLE | NOCYCLE] 
 [CACHE n | NOCACHE]   
 [ORDER | NOORDER] 
```
1)  [START WITH 값] - 시작 값, 기본값은 MINVALUE값 
2)  [INCREMENT BY 값] - 증감 값, 기본은 1(음수 사용 가능)
3)  [MAXVALUE n | NOMAXVALUE] - 최대값 설정, 기본은 NOMAXVALUE(10^27까지 사용)
4)  [MINVALUE n | NOMINVALUE] - 최소값 설정, 기본은 NOMINVALUE (1)이다.(오라클에서 사용하는 최소값까지 사용가능)
5)  [CYCLE | NOCYCLE] - 최대(최소)값까지 도달한 경우 다시 시작할지의 여부, 기본은 NOCYCLE
6)  [CACHE n | NOCACHE] - CACHE사용하여 시퀀스 생성여부. 기본은 CACHE 20, NOCACHE는 필요할 때 만든다는 의미.  
	  CACHE:  CPU(메모리보다 더 빠름)와 메모리(CPU만큼은 아니어도 빠름) 사이의 속도차이를 완충해주는 것.  
 7) [ORDER | NOORDER] - 정의된 조건대로 시퀀스 발생을 보장할지 여부. 기본은 NOORDER, 보증하지 않는다는 의미다.

-  시퀀스에 사용되는 의사컬럼

|의사컬럼명|사용형식|의미|
|---|---|---|
|CURRVAL| 시퀀스명.CURRVAL |시퀀스 객체가 가지고 있는 현재 값을 반환.|
|NEXTVAL| 시퀀스명.NEXTVAL |시퀀스 객체의 다음 값을 반환.|

시퀀스가 처음 생성된 후 반드시 첫 번째로 NEXTVAL이 사용되어야 시퀀스에 값이 배정된다.
	만들어진 시퀀스 객체에 메모리 주소를 할당하는 것이 NEXTVAL,
	처음에 CURRVAL을 먼저 쓰면 값에 배정이 안 되었기 때문에 아직 값이 정의되지 않았다는 오류메세지가 뜨며 작동하지 않는다.
 
 -  지나간 시퀀스 값은 다시 사용할 수 없다.
 
 
-  사용예: 상품분류테이블에 다음 자료를 추가 입력하시오.
```

SELECT * FROM LPROD;
----------------------------------
 LPROD_ID   LPROD_GU     LPROD_NM
----------------------------------
  10       P501           농산물
  
  INSERT INTO LPROD(LPROD_ID, LPROD_GU, LPROD_NM)  -- 하나씩 넣어주면 된다.
  VALUES (seq_lprod_id.NEXTVAL, 'P501', '농산물');
  
  11       P502           수산물
  
  INSERT INTO LPROD(LPROD_ID, LPROD_GU, LPROD_NM)
  VALUES (seq_lprod_id.NEXTVAL, 'P502', '수산물');
  
  12       P503           임산물
  
  INSERT INTO LPROD(LPROD_ID, LPROD_GU, LPROD_NM)
  VALUES (seq_lprod_id.NEXTVAL, 'P503', '임산물');
  
  COMMIT;
  
  -- 기본키는 LPROD_GU이다.
  ** LPROD_ID는 시퀀스를 생성하여 사용하시오. 시퀀스 이름은 seq_lprod_id이다.
 
 CREATE SEQUENCE seq_lprod_id  - 1. 먼저 생성해주고
  START WITH 10
```
