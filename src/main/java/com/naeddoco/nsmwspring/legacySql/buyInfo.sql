--구매내역 테이블
CREATE TABLE BUYINFO (
	-- 구매번호
    B_ID INT PRIMARY KEY,   
    -- 회원 아이디
    -- NOT NULL 제거_01_13
    M_ID VARCHAR2(15),     
    -- 상품 번호
    P_ID INT NOT NULL,          
	-- 쿠폰 번호        
    CP_ID VARCHAR2(10),    
    -- 5. 주문번호
    ORDER_NUM INT NOT NULL,        
	-- 6. 배송 상태         
	-- 오타수정 결재 -> 결제
    DELI_STATE VARCHAR2(75) DEFAULT '결제완료' NOT NULL,     
    -- 7. 구매 수량
    B_QTY INT NOT NULL,
    -- 8. 결제 금액
    PAYMENT_PRICE INT NOT NULL,
    -- 9. 구매일
    BUY_TIME TIMESTAMP NOT NULL,
    --우편번호
	B_POSTCODE INT NOT NULL,
	--도로명주소
	B_ADDRESS VARCHAR2(255) NOT NULL,
	--상세주소
	B_DETAILED_ADDRESS VARCHAR2(255) NOT NULL,
	--리뷰 작성여부
	HAS_REVIEW INT DEFAULT 0 NOT NULL
);

--------------------------------------------------구매내역 샘플 코드-------------------------------------------------------------------------------------------------------------
-- 구매내역 추가
INSERT INTO BUYINFO 
(B_ID, M_ID, P_ID, CP_ID, ORDER_NUM, B_QTY, PAYMENT_PRICE, BUY_TIME, B_POSTCODE, B_ADDRESS, B_DETAILED_ADDRESS)
VALUES (
    NVL((SELECT MAX(B_ID) FROM BUYINFO), 0) + 1,
    'teemo', 
    1, 
    'CP001', 
    1, 
    3, 
    50000, 
    CURRENT_TIMESTAMP, -- 현재 시간을 타임스탬프로 설정
    12345, 
    '서울시 강남구', 
    '123번지 456호'
);

-- 주문번호의 최대값 +1 을 준다
SELECT NVL(MAX(ORDER_NUM),0)+1 AS MAX_ORDER_NUM FROM BUYINFO;

-- 판매량 반환
SELECT P_ID, SUM(B_Qty) AS TOTAL_QTY
FROM BUYINFO
WHERE P_ID = 1
GROUP BY P_ID;

SELECT B_ID, M_ID, P_ID, CP_ID, ORDER_NUM, DELI_STATE, B_QTY, PAYMENT_PRICE, BUY_TIME, B_POSTCODE, B_ADDRESS, B_DETAILED_ADDRESS, HAS_RIVIEW
FROM BUYINFO 
WHERE M_ID = 'teemo';

SELECT * FROM BUYINFO;

UPDATE BUYINFO SET HAS_RIVIEW = 0 WHERE B_ID = 1;

--DROP TABLE BUYINFO;