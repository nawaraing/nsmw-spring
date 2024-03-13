--리뷰 테이블
CREATE TABLE REVIEW (
	--리뷰번호
	R_ID INT PRIMARY KEY,
	--회원ID
	--NOT NULL 제거_01_13
	M_ID VARCHAR2(15),
	--구매번호
	B_ID INT NOT NULL,
	--별점
	SCORE INT NOT NULL,
	--리뷰내용
	CONTENTS VARCHAR2(1500) NOT NULL,
	--작성일
	CREATE_TIME TIMESTAMP NOT NULL,
	--업로드 한 이미지 이름
	IMAGENAME VARCHAR2(100)
);




--------------------------------------------------리뷰 샘플 코드-------------------------------------------------------------------------------------------------------------
INSERT INTO REVIEW 
(R_ID, M_ID, B_ID, SCORE, CONTENTS, CREATE_TIME)
VALUES (
    NVL((SELECT MAX(R_ID) FROM REVIEW), 0) + 1,
    'YUMI', 
    1, 
    5, 
    '값 싸고 맛있는 영양제3', 
    CURRENT_TIMESTAMP
);


SELECT * FROM REVIEW;

drop table REVIEW;

SELECT R.R_ID, R.M_ID, R.B_ID, R.SCORE, R.CONTENTS, R.CREATE_TIME, B.P_ID, P.P_NAME, M.M_NAME, M.EMAIL 
FROM REVIEW R
JOIN BUYINFO B ON B.B_ID = R.B_ID
JOIN PRODUCT P ON B.P_ID = P.P_ID
JOIN MEMBER M ON R.M_ID = M.M_ID
WHERE R.R_ID = 1;
