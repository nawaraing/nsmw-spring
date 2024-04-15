package com.naeddoco.nsmwspring.model.categoryModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("categoryDAO")
@Slf4j
public class CategoryDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 쿼리문
	// CATEGORY 테이블의 모든 정보 조회
	private static final String SELECTALL = "SELECT CATEGORY_ID, CATEGORY_NAME FROM CATEGORY";

	//상품 크롤링시 CATEGORY_ID를 확인하기위해 사용
	private static final String SELECTONE = "SELECT CATEGORY_ID, CATEGORY_NAME FROM CATEGORY WHERE CATEGORY_NAME = ?";
	

/*-----------------------------------[ selectAll ] ---------------------------------------------------------------------------------------------------------*/	
	
	public List<CategoryDTO> selectAll(CategoryDTO categoryDTO) {

		log.trace("selectAll 진입");

		try {
			return (List<CategoryDTO>) jdbcTemplate.query(SELECTALL, new categoryRowMapper());

		} catch (Exception e) {

			log.error("selectAll 예외/실패");
			return null;

		}

	}

/*-----------------------------------[ selectOne ] ---------------------------------------------------------------------------------------------------------*/	

	public CategoryDTO selectOne(CategoryDTO categoryDTO) {

		Object[] args = { categoryDTO.getCategoryName() };

		try {
			return jdbcTemplate.queryForObject(SELECTONE, args, new categoryRowMapper());

		} catch (Exception e) {
			
			log.error("selectOne 예외/실패");
			return null;

		}
	}
}

/*-----------------------------------[ RowMapper ] ---------------------------------------------------------------------------------------------------------*/

@Slf4j
// 개발자의 편의를 위해 RowMapper 인터페이스를 사용
class categoryRowMapper implements RowMapper<CategoryDTO> {
	
	@Override
	public CategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.trace("categoryRowMapper 처리 진입");

		CategoryDTO categoryDTO = new CategoryDTO();

		categoryDTO.setCategoryID(rs.getInt("CATEGORY_ID"));
		categoryDTO.setCategoryName(rs.getString("CATEGORY_NAME"));
		
		log.trace("categoryRowMapper 완료");
		
		return categoryDTO;

	}

}