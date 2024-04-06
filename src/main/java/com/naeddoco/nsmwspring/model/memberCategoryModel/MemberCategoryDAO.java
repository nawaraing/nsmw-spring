package com.naeddoco.nsmwspring.model.memberCategoryModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("memberCategoryDAO")
@Slf4j
public class MemberCategoryDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//해당 회원이 가지고 있는 카테고리 이름 모두 확인
	private static final String SELECTALL_MEMBER_CATEGORY = "SELECT " +
															"C.CATEGORY_ID, " +
															"C.CATEGORY_NAME " +
															"FROM CATEGORY C " +
															"JOIN MEMBER_CATEGORY MC ON C.CATEGORY_ID = MC.CATEGORY_ID " +
															"WHERE MC.MEMBER_ID = ?";
	
	// 회원가입시 멤버카테고리에 추가
	private static final String INSERT = "INSERT INTO MEMBER_CATEGORY ( " +
										 "CATEGORY_ID, " +
										 "MEMBER_ID " +
										 ") VALUES ( " +
										 "?, " +
										 "? )";

	public List<MemberCategoryDTO> selectAll(MemberCategoryDTO memberCategoryDTO) {

		log.debug("selectAll 진입");

		if (memberCategoryDTO.getSearchCondition().equals("memberCategory")) {

			log.debug("memberCategory 진입");

			Object args[] = { memberCategoryDTO.getMemberID() };

			try {

				return jdbcTemplate.query(SELECTALL_MEMBER_CATEGORY, args, new MemberCategoryRowMapper());

			} catch (Exception e) {

				log.error("memberCategory 예외 발생");

				return null;

			}

		}
		
		log.error("selectAll 실패");

		return null;

	}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	
	public boolean insert(MemberCategoryDTO memberCategoryDTO) {
	
		log.debug("insert 진입");
		
		if (memberCategoryDTO.getSearchCondition().equals("joinMemberCategory")) {
			
			log.debug("joinMemberCategory 진입");
			
			int result = 0;
			
			try {

				result = jdbcTemplate.update(INSERT, memberCategoryDTO.getCategoryID(), memberCategoryDTO.getMemberID());
			
			} catch (Exception e) {

				log.error("joinMemberCategory 예외 발생");

				return false;

			}
			
			if (result <= 0) {
				
				log.error("joinMemberCategory 실패");
				
				return false;
				
			}
			
			log.debug("joinMemberCategory 성공");
			
			return true;
			
		}
		
		log.debug("insert 실패");
		
		return false;
		
	}

}

//개발자의 편의를 위해 RowMapper 인터페이스를 사용
@Slf4j
class MemberCategoryRowMapper implements RowMapper<MemberCategoryDTO> {
	
	@Override
	public MemberCategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("MemberCategoryRowMappe 진입");
		
		MemberCategoryDTO memberCategoryDTO = new MemberCategoryDTO();
		
		memberCategoryDTO.setCategoryID(rs.getInt("C.CATEGORY_ID"));
		memberCategoryDTO.setAncCategoryName(rs.getString("C.CATEGORY_NAME"));
		
		log.debug("MemberCategoryRowMappe 완료");
		
		return memberCategoryDTO;
	}
	
}