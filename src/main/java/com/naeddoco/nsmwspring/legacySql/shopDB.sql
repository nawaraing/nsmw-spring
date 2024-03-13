-- 테이블 전체목록--
SELECT * FROM MEMBER;
SELECT * FROM PRODUCT;
SELECT * FROM CART;
SELECT * FROM COUPON;
SELECT * FROM BUYINFO;
SELECT * FROM REVIEW;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- ※경고※ --
-- 테이블 삭제 명령어 --
DROP TABLE MEMBER;
DROP TABLE PRODUCT;
DROP TABLE CART;
DROP TABLE COUPON;
DROP TABLE BUYINFO;
DROP TABLE REVIEW;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- 컬럼명 변경
ALTER TABLE PRODUCT RENAME COLUMN REG_DATE TO REG_TIME;

-- 트리거를 확인하는 쿼리
SELECT trigger_name, trigger_body
FROM user_triggers
WHERE table_name = 'REVIEW';

SELECT TRIGGER_NAME, TABLE_NAME, TRIGGER_TYPE, TRIGGERING_EVENT
FROM USER_TRIGGERS;


-- 트리거 활성화 쿼리(디비버에서 사용 권장)
-- 트리거를 생성한다 이름은 INSERT_REVIEW_TRIGGER
-- CREATE OR REPLACE TRIGGER INSERT_REVIEW_TRIGGER // OR REPLACE 이미 있다면 변경
CREATE TRIGGER INSERT_REVIEW_TRIGGER
-- 트리거 발동 시기는 리뷰 테이블에 INSERT가 작동한 후
AFTER INSERT ON REVIEW
-- 트리거를 행에 적용하는 문구(기본양식)
FOR EACH ROW
-- 트리거에서 작동할 쿼리
BEGIN
    --BUYINFO테이블에서 HAS_REVIEW 1로 업데이트한다
    UPDATE BUYINFO
    SET HAS_REVIEW = 1
    -- :new.B_ID = INSERT(트리거가 발생하는 쿼리)가 작동했을 때 사용된 B_ID
    WHERE B_ID = :new.B_ID;
-- 트리거 에서 작동할 쿼리 종료
END;


-- 트리거
-- 리뷰 삭제 시 구매내역에서 리뷰유무 0으로 업데이트
CREATE TRIGGER DELETE_REVIEW_TRIGGER
-- 트리거 발동 시기는 리뷰 테이블에 DELETE가 작동한 후
AFTER DELETE ON REVIEW
-- 트리거를 행에 적용하는 문구(기본양식)
FOR EACH ROW
-- 트리거에서 작동할 쿼리
BEGIN
    -- BUYINFO 테이블에서 HAS_REVIEW를 0으로 업데이트한다
    UPDATE BUYINFO
    SET HAS_REVIEW = 0
    -- :old.B_ID = DELETE(트리거가 발생하는 쿼리)가 작동하기 전의 B_ID
    WHERE B_ID = :old.B_ID;
-- 트리거에서 작동할 쿼리 종료
END;


--트리거 삭제
DROP TRIGGER INSERT_REVIEW_TRIGGER;
DROP TRIGGER DELETE_REVIEW_TRIGGER;