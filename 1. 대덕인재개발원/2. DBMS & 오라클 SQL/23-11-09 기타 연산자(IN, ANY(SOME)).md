 -- LIKE: 상위분류가 같으면 묶어준다. (ex. 2023년 4월을 뽑을 때 4월1일~4월30일을 지정하는 게 아니라 4월만 적용하고 날짜는 뭐가 들어와도 괜찮다는 식), 문자열만 사용 가능하며, 효율성이 떨어져서 되도록 안쓰는게좋다.
 -- BETWEEN: ex. BETWEEN 2023-04-01 AND 2023-04-30 문자열, 날짜, 숫자 다 가능함.
##### 4. 기타 연산자
###### 1) IN
- 주어진 다수의 값 중 어느 하나의 값과 일치하면 전체 결과가 참이 되는 연산자.
- 반대인 경우 NOT 연산자 사용.
- OR 연산자로 바꾸어 사용할 수 있다.
- 주로 <u>비연속적인 다수의 값을 판별</u>할 때 사용한다.
- ==expr IN (값1[,값2,...])== 의 사용형식.
	'expr'의 평가 결과가 '값1' ~ '값n' 중의 하나와 일치하면 TRUE 반환 
	=기능이 포함되어 있어서 관계연산자를 사용할 필요가 없다.
	= 포함은 SOME ANY ALL에는 없는 기능이라서 얘네들은 관계연산자가 필요하다.
	expr은 함수가 나올 수도 있고, 컬럼명이 들어갈 수도 있다.
- 사용예
 HR계정의 사원테이블에서 부서번호가 30, 60, 80, 100에 속한 사원의
 사원번호, 사원명, 부서번호, 급여를 조회하시오.
 ```
 SELECT EMPLOYEE_ID AS 사원번호,
        EMP_NAME AS 사원명,
        DEPARTMENT_ID AS 부서번호,
        SALARY AS 급여
    FROM HR.EMPLOYEES
     WHERE DEPARTMENT_ID=30
        OR DEPARTMENT_ID=60
        OR DEPARTMENT_ID=80
        OR DEPARTMENT_ID=100 
    ORDER BY 3;  -- SELECT문의 세번째 컬럼, 즉 부서번호명으로 정렬한다는 얘기다.
    
(IN연산자 사용)
SELECT EMPLOYEE_ID AS 사원번호,
        EMP_NAME AS 사원명,
        DEPARTMENT_ID AS 부서번호,
        SALARY AS 급여
    FROM HR.EMPLOYEES
     WHERE DEPARTMENT_ID IN (30, 60, 80, 100)
     ORDER BY 3; 
     
(ANY 연산자 사용)
SELECT EMPLOYEE_ID AS 사원번호,
        EMP_NAME AS 사원명,
        DEPARTMENT_ID AS 부서번호,
        SALARY AS 급여
    FROM HR.EMPLOYEES
     WHERE DEPARTMENT_ID = ANY(30, 60, 80, 100)
     ORDER BY 3; 
 ```

###### 2) ANY(SOME)
- ANY와 SOME은 동일 기능 제공
- 주어진 다수의 값 중 어느 하나의 값과 제시된 관계 연산자의 결과가 만족되면 전체 결과가 참이 되는 연산자.
- 반대인 경우 NOT 연산자 사용
- OR 연산자로 변환 가능하다.
-  ==expr 관계연산자ANY(SOME)(값1[, 값2, ...])== 의 사용형식
- 사용예
 1. 회원테이블에서 1000, 1500, 2000보다 큰 마일리지를 보유하는 회원들을 조회하시오.
 Alias 는 회원번호, 회원명, 거주지역, 마일리지
 ```
 SELECT MEM_ID AS 회원번호,
        MEM_NAME AS 회원명,
        SUBSTR(MEM_ADD1,1,2) AS 거주지역,
        MEM_MILEAGE AS 마일리지
      FROM MEMBER
      WHERE MEM_MILEAGE >ANY(1000,1500,2000)
      ORDER BY 4;
 ```
  2. 회원테이블에서 충남에 거주하는 회원들의 마일리지보다 큰 마일리지를 보유하는 회원들을 조회하시오 .Alias 는 회원번호, 회원명, 거주지역, 마일리지  
	  알지 못하는 정보를 조회해서 다른 쿼리에 이용하는 것이 서브쿼리, 여기서는 충남~마일리지가 서브쿼리다.
   ```
	SELECT MEM_MILEAGE
     FROM MEMBER
     WHERE SUBSTR(MEM_ADD1,1,2)='충남';
     
     SELECT MEM_ID AS 회원번호,
            MEM_NAME AS 회원명,
            SUBSTR(MEM_ADD1,1,2) AS 거주지역,
            MEM_MILEAGE AS 마일리지
      FROM MEMBER
      WHERE MEM_MILEAGE >ANY(SELECT MEM_MILEAGE
                         FROM MEMBER
                         WHERE SUBSTR(MEM_ADD1,1,2)='충남')
      ORDER BY 4;
   ```
