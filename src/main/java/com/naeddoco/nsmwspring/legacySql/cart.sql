--장바구니 테이블
CREATE TABLE CART (
	-- 장바구니 번호
    C_ID INT PRIMARY KEY,      
    -- 회원 아이디 (MEMBER 테이블의 MID를 참조)
    M_ID VARCHAR2(15) NOT NULL,         
    -- 상품 번호 (PRODUCT 테이블의 PID를 참조)
    P_ID INT NOT NULL,      
    -- 장바구니에 담은 상품 수량
    C_QTY INT NOT NULL
);

-------------------------------------------------------카트 샘플 코드 --------------------------------------------------------------------------
--장바구니에 담기
INSERT INTO CART (C_ID, M_ID, P_ID, C_QTY) VALUES (NVL((SELECT MAX(C_ID) FROM CART), 0)+1, 'teemo', 1, 1);

--조인해서 장바구니에 출력하기 
SELECT C.C_ID, C.P_ID, M.M_ID, C.C_QTY, P.P_NAME, P.SELLING_PRICE, P.IMAGEPATH
FROM CART C
JOIN PRODUCT P ON C.P_ID = P.P_ID
JOIN MEMBER M ON C.M_ID = M.M_ID
WHERE M.M_ID = 'teemo';

--총합까지 연산
--SELECT C.C_ID,C.M_ID, C.P_ID, C.C_QTY, P.P_NAME, P.SELLING_PRICE, P.IMAGEPATH, (C.C_QTY * P.SELLING_PRICE) AS TOTAL_PRICE 
--FROM CART C
--JOIN PRODUCT P ON C.P_ID = P.P_ID;

-- 장바구니 전체출력
SELECT * FROM CART;