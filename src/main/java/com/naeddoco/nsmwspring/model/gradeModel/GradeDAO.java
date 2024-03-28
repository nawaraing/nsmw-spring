package com.naeddoco.nsmwspring.model.gradeModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
	
	//GRADE테이블의 모든 정보 조회
	private static final String SELECTALL = "SELECT GRADE_ID, GRADE_NAME, LOWER_LIMIT, UPPER_LIMIT "
											+ "FROM GRADE";

	private static final String SELECTONE = "";
	
	//등급명, 최솟값, 최댓값은 DB에 미리 넣어놓고 사용 예정
	private static final String INSERT = "";
	
	private static final String UPDATE = "";
	
	private static final String DELETE = "";
	
	
// --------------------------------------------------------------------------------------------------------------------------------
	public List<GradeDTO> selectAll(GradeDTO gradeDTO) {
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

	public GradeDTO selectOne(GradeDTO gradeDTO) {

//		Object[] args = { gradeDTO.getGradeID() };
//		log.debug("selectOne start");
//	
//		try {
//			return jdbcTemplate.queryForObject(SELECTONE, args, new GradeRowMapper());
//		} catch (Exception e) {
//			log.debug("selectOne 예외처리");
			return null;
//		}
	}

	
	public boolean insert(GradeDTO gradeDTO) {
	
//		int result = jdbcTemplate.update(INSERT);
//		if(result <= 0) {
//			log.debug("insert 실패");
			return false;
//		}
//		log.debug("insert 성공");
//		return true;
	}

	
	public boolean update(GradeDTO gradeDTO) {

//		int result = jdbcTemplate.update(UPDATE);
//		if(result <= 0) {
//			log.debug("update 실패");
			return false;
//		}
//		log.debug("update 성공");
//		return true;
	}

	
	public boolean delete(GradeDTO gradeDTO) {
		
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
class GradeRowMapper implements RowMapper<GradeDTO> {
	@Override
	public GradeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		GradeDTO data = new GradeDTO();
		
		data.setGradeID(rs.getInt("GRADE_ID"));
		data.setGradeName(rs.getString("GRADE_NAME"));
		data.setLowerLimit(rs.getInt("LOWER_LIMIT"));
		data.setUpperLimit(rs.getInt("UPPER_LIMIT"));
			
		log.debug(Integer.toString(rs.getInt("GRADE_ID")));
		log.debug(rs.getString("COUPON_NAME"));
		log.debug(Integer.toString(rs.getInt("LOWER_LIMIT")));
		log.debug(Integer.toString(rs.getInt("UPPER_LIMIT")));
		
		return data;
	}
	
}