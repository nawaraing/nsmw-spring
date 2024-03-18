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

	private static final String SELECTONE = "";
	
	private static final String INSERT = "";
	
	private static final String UPDATE = "";
	
	private static final String DELETE = "";


	public List<MemberCategoryDTO> selectAll(MemberCategoryDTO memberCategoryDTO) {
		log.debug("selectAll start");
		return (List<MemberCategoryDTO>)jdbcTemplate.query(SELECTALL, new MemberCategoryRowMapper());
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
	
//		int result = jdbcTemplate.update(INSERT);
//		if(result <= 0) {
//			log.debug("insert 실패");
			return false;
//		}
//		log.debug("insert 성공");
//		return true;
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