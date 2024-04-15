package com.naeddoco.nsmwspring.model.gradeModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("gradeDAO")
@Slf4j
public class GradeDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/* 
	// GRADE테이블의 모든 정보 조회-미사용
	 private static final String SELECTALL = "SELECT GRADE_ID, GRADE_NAME, LOWER_LIMIT, UPPER_LIMIT "
											+ "FROM GRADE"; 

	// 등급명, 최솟값, 최댓값은 DB에 직접 넣어놓고 사용 예정
	 private static final String INSERT = "INSERT INTO GRADE (GRADE_NAME, LOWER_LIMIT, UPPER_LIMIT) VALUES (?,?,?)"; */
	
/*-----------------------------------[ selectAll ] ---------------------------------------------------------------------------------------------------------*/	
	
	/* public List<GradeDTO> selectAll(GradeDTO gradeDTO) {
		
		log.trace("selectAll 진입");

		if (gradeDTO.getSearchCondition().equals("selectAllGrade")) {

			log.trace("selectAllGrade 진입");

			try {
				return (List<GradeDTO>)jdbcTemplate.query(SELECTALL, new GradeRowMapper());
			
			} catch (Exception e) {

				log.error("selectAllGrade 예외/실패");
				return null;

			}

		}

		log.error("selectAll 실패");
		return null;

	}
	*/
}
/*-----------------------------------[ RowMapper ] ---------------------------------------------------------------------------------------------------------*/

/*
@Slf4j
class GradeRowMapper implements RowMapper<GradeDTO> {
	@Override
	public GradeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GradeDTO data = new GradeDTO();
		
		log.trace("GradeRowMapper 진입");
		
		data.setGradeID(rs.getInt("GRADE_ID"));
		data.setGradeName(rs.getString("GRADE_NAME"));
		data.setLowerLimit(rs.getInt("LOWER_LIMIT"));
		data.setUpperLimit(rs.getInt("UPPER_LIMIT"));
			
		log.debug("GRADE_ID : " + Integer.toString(rs.getInt("GRADE_ID")));
		log.debug("COUPON_NAME : " + rs.getString("COUPON_NAME"));
		log.debug("LOWER_LIMIT : " + Integer.toString(rs.getInt("LOWER_LIMIT")));
		log.debug("UPPER_LIMIT : " + Integer.toString(rs.getInt("UPPER_LIMIT")));
		
		log.trace("GradeRowMapper 완료");
		
		return data;
	}
}
*/