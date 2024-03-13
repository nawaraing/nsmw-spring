--회원 테이블
CREATE TABLE MEMBER(
	-- 회원아이디(영문, 숫자 15자 제한), 기본키 
	M_ID VARCHAR2(15) PRIMARY KEY,
	-- 회원이름(박 하늘별님구름햇님보다사랑스러우리) // 한글 3byte
	M_NAME VARCHAR2(60) NOT NULL,
	-- 회원비밀번호(영문, 숫자 15자 제한)
	M_PASSWORD VARCHAR2(15) NOT NULL,
	-- 출생년도
	-- 사용자가 '0001-01-01'과 같은 형식으로 입력해야함
	DOB DATE NOT NULL,
	--성별 // (남 or 여)
	GENDER VARCHAR2(3) NOT NULL,
	--연락처(숫자 11 + -2개)
	--하이픈 입력을 강제해야함
	PHONE_NUMBER VARCHAR2(13) NOT NULL,
	--이메일
	EMAIL VARCHAR2(255) NOT NULL,
	--우편번호
	M_POSTCODE INT NOT NULL,
	--도로명주소
	M_ADDRESS VARCHAR2(255) NOT NULL,
	--상세주소
	M_DETAILED_ADDRESS VARCHAR2(255) NOT NULL,
	--회원등급(admin == 5byte)
	GRADE VARCHAR2(5) NOT NULL,
	--건강상태(뷰에서 선택지 형식으로 1. 눈 건강 이상, 2. 간 건강 이상 ....)
	HEALTH VARCHAR2(255),
	--로그인 종류
	LOGIN_TYPE VARCHAR2(15),
	--카카오 ID
	KAKAO_ID VARCHAR2(19)
);

----------------------------------------------------회원 샘플 코드 --------------------------------------------------------------------------
--회원가입
INSERT INTO MEMBER (M_ID, M_NAME, M_PASSWORD, DOB, GENDER, PHONE_NUMBER, EMAIL, M_POSTCODE, M_ADDRESS, M_DETAILED_ADDRESS, GRADE, HEALTH) 
VALUES ('YUMI', '티모', '1234', TO_DATE('2099-12-30', 'YYYY-MM-DD'), 
'남', '010-2525-2525', 'teemo@gmail.com', 99999, '경기도 용인시', '군인숙소','USER', '눈');

--중복검사
SELECT M_ID FROM MEMBER WHERE M_ID = 'teemo';
--회원목록
SELECT * FROM MEMBER;
--로그인
SELECT M_ID, M_NAME, DOB, GENDER, GRADE, HEALTH FROM MEMBER WHERE M_ID='teemo' AND M_PASSWORD = '1234';
--회원 건강상태
SELECT HEALTH FROM MEMBER WHERE M_ID='teemo';

--회원정보변경(미완성)
UPDATE MEMBER
SET
  M_NAME = '티티모',
  M_PASSWORD = '123456',
  DOB = TO_DATE('2024-01-20', 'YYYY-MM-DD'),
  GENDER = '여',
  PHONE_NUMBER = '010-5252-5252',
  EMAIL = 'teteemo@gmail.com',
  M_POSTCODE = '66666',
  M_ADDRESS = '제일비전타워 13층',
  M_DETAILED_ADDRESS = 'Jclass 강의장',
  GRADE = 'USER',
  HEALTH = '간'
WHERE M_ID = 'teemo';


--회원탈퇴
DELETE FROM MEMBER WHERE M_ID = 'teemoo';

DROP TABLE MEMBER;