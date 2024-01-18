#### 6. ROLLUP과 CUBE함수
- 집계함수를 사용하면 그룹별 합계를 구할 수 있으나, 다중그룹의 경우 각 그룹별 중간 집계는 구하지 못한다.
- 이 둘은 다양한 구분으로 다중 그룹을 형성하고 각 그룹별 중간 집계를 반환할 수 있는 함수이다.
- 여론조사의 분석 등에 주로 사용한다.

##### 1) ROLLUP(컬럼명1[, 컬럼명2, ... 컬럼명n])
- 반드시 <u>GROUP BY 절 안에서 사용</u>해야 한다.
-  ROLLUP에 사용된 '컬럼명1'부터 '컬럼명1+컬럼명2', ...'컬럼명1+컬럼명2+...컬럼명n' 을 각각 적용한 집계를 반환한다.
	이를 레벨이라고 한다. 예를 들어, 반별, 성별에 따라 그룹을 나눈다고 했을 때,
	~반 여성, ~반 남성 등이 가장 아래 수준의 레벨이고, ~반이 그 위 레벨이다.
	GROUP BY로는 각 중간단계의 합계(각 레벨의 부분집계)를 구할수는 없다.
- 사용된 컬럼의 갯수가 n개일 때 반환되는 집계의 종류는 n+1개이다. 
	이 +1은 전체집계로, GROUP BY로는 전체집계가 나오지 않는다.
- 사용예: 2020년 장바구니테이블에서 월별, 회원별, 제품별 판매수량합계를 조회하시오. 
```
 SELECT SUBSTR(CART_NO, 5, 2)||'월' 월,
        CART_MEMBER AS 회원번호,
        CART_PROD AS 제품번호,
        SUM(CART_QTY) AS 판매수량합계
    FROM CART
    WHERE CART_NO LIKE '2020%'
    GROUP BY ROLLUP(SUBSTR(CART_NO, 5, 2), CART_MEMBER, CART_PROD)
    ORDER BY 1, 2, 3
    
SELECT SUM(CART_QTY) FROM CART WHERE CART_NO LIKE '2020%';
-- 사용된 컬럼의 갯수가 4개이므로, 집계의 종류는 5개이다.
```
##### 2) CUBE(컬럼명1[, 컬럼명2, ... 컬럼명n])
-  반드시<u> GROUP BY 절 안에서 사용</u>해야 한다.
-  CUBE에 사용된 컬럼들로 구성할 수 있는 모든 가능한 조합으로 집계를 반환한다.
-  사용된 컬럼의 갯수가 n개일 때 반환되는 집계의 종류는 2의 n승 개이다.
	예를 들어,  조건 ABC가 있다고 치면 CUBE를 사용했을 때
	A, B, C, AB, AC, BC, ABC, 전체집계 까지- 총 8개(2의 3승개)의 조합이 있을 수 있다.
	너무 많은 데이터가 반환되므로 사용빈도가 적다.
-  사용예
```
 SELECT SUBSTR(CART_NO, 5, 2)||'월' 월,
        CART_MEMBER AS 회원번호,
        CART_PROD AS 제품번호,
        SUM(CART_QTY) AS 판매수량합계
    FROM CART
    WHERE CART_NO LIKE '2020%'
    GROUP BY CUBE(SUBSTR(CART_NO, 5, 2), CART_MEMBER, CART_PROD)
    ORDER BY 1, 2, 3
	
	-- 여기선 컬럼이 4개 사용되었으므로, 집계의 종류만 16개다.
	-- 여기에 데이터가 들어가면 양이 엄청나게 불어난다.
```

##### 3) 부분 ROLLUP(CUBE)
- ROLLUP(CUBE)절 후에 컬럼이 기술된 경우를 말한다.
- ROLLUP(CUBE)절 밖에 기술된 컬럼은 단순히 GROUP BY 절에만 적용된다.
- 사용예
```
SELECT SUBSTR(CART_NO, 5, 2)||'월' 월,
        CART_MEMBER AS 회원번호,
        CART_PROD AS 제품번호,
        SUM(CART_QTY) AS 판매수량합계
    FROM CART
    WHERE CART_NO LIKE '2020%'
    GROUP BY SUBSTR(CART_NO, 5, 2), ROLLUP(CART_MEMBER, CART_PROD)
    ORDER BY 1, 2, 3
    
	-- SUBSTR(CART_NO, 5, 2)는 항상 적용되므로 전체집계는 나오지 않는다. 
	-- 월+(1. 제품번호+회원번호, 2. 회원번호, 3. -) 집계종류의 갯수는 총3개다.
```
 
#### 7. NULL처리 함수
- 오라클의 모든 컬럼은 값을 배정받지 못하면 데이터 타입에 상관 없이 NULL값으로 초기화된다.(DEFAULT 옵션 사용은 예외)
- 이 NULL값을 갖는 컬럼이 연산에 사용되면 결과는 모두 NULL이 되어 데이터 왜곡 발생의 위험성이 상존한다.
- NULL자료 처리를 위해 NVL, NVL2, NULLIF등의 함수가 제공된다.
- ★컬럼의 값이 NULL값인지를 판단하는 조건문에서도 <u>'='연산자는 NULL여부를 판단하지 못한다.</u>

0) IS NULL,IS NOT NULL
- NULL 여부 판단을 위해 조건문에 사용되는 연산자(함수는 아니다!)

-  사용예: HR계정의 사원테이블에서 영업실적코드가 NULL이 아닌 사원수를 조회하시오.
```
    SELECT COUNT(*) AS 사원수
     FROM HR.EMPLOYEES
     WHERE COMMISSION_PCT IS NOT NULL;
```

1) NVL(expr, VAL1)
- NULL 처리 함수 중 가장 널리 사용되는 함수.
	업데이트문에서 NULL을 잘못 사용하면 원본데이터에 큰 손상을 줄 수 있으니 NULL은 항상 조심해서 다뤄야 한다.
- 'expr'의 값이 NULL이면 'VAL1'값을 반환하고 NULL이 아니면 'expr'값을 반환한다.
- 'expr'과 VAL1은 같은 데이터 타입이어야 한다.

-  사용예1: 2020년 6월 모든 상품별 판매수량합계를 조회하시오. Alias는 상품번호, 상품명, 판매수량합계, 판매금액합계
```
SELECT  B.PROD_ID AS 상품번호,
        B.PROD_NAME AS 상품명,
	    NVL(SUM(A.CART_QTY),0) AS 판매수량합계,
	    NVL(SUM(A.CART_QTY*B.PROD_PRICE),0) AS 판매금액합계
	FROM CART A
	 -- 이 쿼리에서 카트테이블을 A라고 부르겠다, 즉 테이블 별칭을 정해준 것이다. 
	 -- 이 쿼리 안에서만 쓰이는 별칭이므로, 쿼리를 나가면 쓸 수 없다.
 RIGHT OUTER JOIN PROD B ON(A.CART_PROD=B.PROD_ID AND A.CART_NO LIKE '202006%')
 GROUP BY B.PROD_ID, B.PROD_NAME
 ORDER BY 1;
 -- 상품번호당 상품명이 하나씩 붙어있다 하더라도 GROUP BY에는 두개 다 써줘야 한다.
 -- 테이블이 두 개 이상 사용되면 WHERE절은 필수가 된다. 조인에서 다시 설명해주신다고 한다.
 -- RIGHT OUTER JOIN은 아직 안 배웠다.....
```

- 사용예2:  HR계정 사원테이블에서 영업실적에 따른 보너스를 계산하여 지급액을 조회하시오.
Alias는 사원번호, 사원명, 기본급, 영업실적코드, 보너스, 지급액.
보너스 = 기본급*영업실적코드*0.6
지급액 = 기본급+보너스
```
SELECT EMPLOYEE_ID AS 사원번호,
       EMP_NAME AS 사원명,
       SALARY AS 기본급,
       DEPARTMENT_ID AS 부서코드,
       NVL(TO_CHAR(COMMISSION_PCT, '990.99'),'영업실적 없음') AS 영업실적코드,
       NVL(ROUND(SALARY*COMMISSION_PCT*0.6),0) AS 보너스,
       SALARY +  NVL(ROUND(SALARY*COMMISSION_PCT*0.6),0) AS 지급액
 FROM HR.EMPLOYEES
 ORDER BY 4;
```


2) NVL2(expr, VAL1, VAL2)
- 'expr'의 값이 NULL이 아니면 'VAL1'값을 반환하고 NULL이면 'VAL2'값을 반환한다.
- VAL1과 VAL2는 반드시 같은타입의 자료여야 한다.

-  사용예: 상품테이블에서 상품의 색상정보(PROD_COLOR)가 NULL 이면 '색상정보 없음'을,  NULL이 아니면 해당 색상정보를 출력하시오. Alias는 상품코드, 상품명, 색상. 단, NVL2를 사용하시오.
```
SELECT PROD_ID AS 상품코드,
       PROD_NAME AS 상품명,
       NVL2(PROD_COLOR, PROD_COLOR,'색상정보 없음') AS 색상
FROM PROD;
```
   
   
3) NULLIF(COL1, COL2)
- 'COL1'과 'COL2'를 비교하여 같은 값이면 NULL을 반환하고, 다른 값이면 'COL1'을 반환한다.
- COL1과 COL2는 같은 타입이거나 자동형변환이 될 수 있는 타입이어야-비교할 수 있는 타입이어야 한다.
-  사용예: 상품테이블에서 매입가와 판매가가 같은 상품을 찾아 비고란에 '단종예정상품'이라는 문자열을 출력하고, 같지 않으면 판매가를 출력하시오. Alias는 상품번호, 상품명, 비고
```
 SELECT PROD_ID AS 상품번호,
        PROD_NAME AS 상품명,
        NVL(TO_CHAR(NULLIF(PROD_PRICE, PROD_COST),'9,999,999'), '단종예정상품')
	        AS "비고(판매가)"
	        -- PROD_PRICE가 문자열인 '단종예정상품'과 같은 타입이어야 하므로 TO_CHAR사용
  FROM PROD;
```
