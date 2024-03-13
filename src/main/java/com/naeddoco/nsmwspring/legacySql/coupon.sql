--쿠폰 테이블
CREATE TABLE COUPON (
	-- 쿠폰 번호 (영문과 숫자가 혼합된 문자열 10자)
    CP_ID VARCHAR2(10) PRIMARY KEY,
    -- 회원 아이디 (MEMBER 테이블의 MID를 참조)
    M_ID VARCHAR2(15) NOT NULL,
    -- 쿠폰 이름
    CP_NAME VARCHAR2(75) NOT NULL, 
    -- 사용기간
    PERIOD TIMESTAMP NOT NULL,   
    -- 5. 할인율 (%로 저장)
    DISCOUNT INT NOT NULL,
    -- 사용 여부 (사용가능 TRUE, 사용불가 FALSE)
    USED VARCHAR2(10) DEFAULT '미사용' NOT NULL,
    -- 적용 가능한 카테고리
    CATEGORY VARCHAR2(75) NOT NULL
);


--------------------------------------------------쿠폰 샘플 코드-------------------------------------------------------------------------------------------------------------
--쿠폰생성(만약 부여를 한다고하면 쿠폰번호는 컨트롤러에서 만들어서 set)--
INSERT INTO COUPON (CP_ID, M_ID, CP_NAME, PERIOD, DISCOUNT, CATEGORY)
VALUES (
 '4', 	--쿠폰번호--
 'teemo',		--소유자MID--
 '임시쿠폰 1',	--쿠폰이름--
 SYSTIMESTAMP,	--현재시간 +30일--
 30,			-- 할인율
 '눈'
);
--쿠폰업데이트--
UPDATE COUPON 
SET USED = '사용'
WHERE CP_ID = '5';

--쿠폰목록출력(마이페이지, 사용, 미사용 정렬 후 만료일 순 정렬)--
-- 내림차순으로 해야 사용이위로 옴
SELECT CP_ID, M_ID, CP_NAME, PERIOD, DISCOUNT, CATEGORY, USED 
FROM COUPON
WHERE M_ID = 'teemo'
ORDER BY USED DESC, PERIOD ASC;

--쿠폰목록출력(쿠폰적용, 미사용쿠폰을 만료일 순으로 정렬하여 사용)--
SELECT CP_ID, M_ID, CP_NAME, PERIOD, DISCOUNT, CATEGORY, USED 
FROM COUPON
WHERE M_ID = 'teemo' AND USED = '미사용'
ORDER BY PERIOD ASC;