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

	private static final String SELECTALL = "";
	
	//해당 회원이 가지고 있는 카테고리 이름 모두 확인
	private static final String SELECTALL_MEMBER_CATEGORY = "SELECT C.CATEGORY_NAME "
														+ "FROM CATEGORY C "
														+ "JOIN MEMBER_CATEGORY MC ON C.CATEGORY_ID = MC.CATEGORY_ID "
														+ "WHERE MC.MEMBER_ID = ?";

	private static final String SELECTONE = "";
	
	private static final String INSERT = "INSERT INTO MEMBER_CATEGORY " + "(CATEGORY_ID, MEMBER_ID) " + "VALUES(?, ?)";
	
	private static final String UPDATE = "";
	
	private static final String DELETE = "";


	public List<MemberCategoryDTO> selectAll(MemberCategoryDTO memberCategoryDTO) {

		log.trace("selectAll 처리 진입");

		if(memberCategoryDTO.getSearchCondition().equals("selectAll")) {

				return (List<MemberCategoryDTO>)jdbcTemplate.query(SELECTALL, new MemberCategoryRowMapper());

		} else if (memberCategoryDTO.getSearchCondition().equals("memberCategory")) {

			log.trace("memberCategory 처리 진입");

			Object args[] = { memberCategoryDTO.getMemberID() };

			log.debug("[Object[0]의 값 : " + args[0]);

			try {

				return jdbcTemplate.query(SELECTALL_MEMBER_CATEGORY, args, new SelectAllMemberCategoryRowMapper());

			} catch (Exception e) {

				log.error("memberCategory 예외/실패");

				return null;

			}

		}

		return null;

	}

	
	public MemberCategoryDTO selectOne(MemberCategoryDTO memberCategoryDTO) {

//		Object[] args = { memberCategoryDTO.getMemberCategoryID() };
//		log.debug("selectOne start");
//	
//		try {
//			return jdbcTemplate.queryForObject(SELECTONE, args, new MemberCategoryRowMapper());
//		} catch (Exception e) {
//			log.debug("selectOne 예외처리");
			return null;
//		}
	}

	
	public boolean insert(MemberCategoryDTO memberCategoryDTO) {
	
		log.trace("insert 진입");
		if (memberCategoryDTO.getSearchCondition().equals("joinMemberCategory")) {

			int result = jdbcTemplate.update(INSERT, memberCategoryDTO.getCategoryID(),
													memberCategoryDTO.getMemberID());
			if (result <= 0) {
				log.error("insert 실패");
				return false;
			}
			log.trace("insert 성공");
			return true;
		}
		return false;
	}

	
	public boolean update(MemberCategoryDTO memberCategoryDTO) {

//		int result = jdbcTemplate.update(UPDATE);
//		if(result <= 0) {
//			log.debug("update 실패");
			return false;
//		}
//		log.debug("update 성공");
//		return true;
	}

	
	public boolean delete(MemberCategoryDTO memberCategoryDTO) {
		
//		int result = jdbcTemplate.update(DELETE);
//		if(result <= 0) {
//			log.debug("delete 성공");
			return false;
//		}
//		log.debug("delete 성공");
//		return true;
	}	
}

//개발자의 편의를 위해 RowMapper 인터페이스를 사용
@Slf4j
class MemberCategoryRowMapper implements RowMapper<MemberCategoryDTO> {
	@Override
	public MemberCategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberCategoryDTO data = new MemberCategoryDTO();
		
		data.setMemberCategoryID(rs.getInt("MEMBER_CATEGORY_ID"));
		data.setCategoryID(rs.getInt("CATEGORY_ID"));
		data.setMemberID(rs.getString("MEMBER_ID"));
			
		log.debug(Integer.toString(rs.getInt("MEMBER_CATEGORY_ID")));
		log.debug(Integer.toString(rs.getInt("CATEGORY_ID")));
		log.debug(rs.getString("MEMBER_ID"));
		
		return data;
	}
	
}

@Slf4j
class SelectAllMemberCategoryRowMapper implements RowMapper<MemberCategoryDTO> {
   @Override
   public MemberCategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      MemberCategoryDTO data = new MemberCategoryDTO();
      
      data.setAncCategoryName(rs.getString("C.CATEGORY_NAME"));
      
      return data;
   }
   
}


