package com.naeddoco.nsmwspring.model.categoryModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.imageModel.ImageDAO;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;

import lombok.extern.slf4j.Slf4j;

@Repository("categoryDAO")
@Slf4j
public class CategoryDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 쿼리문
	private static final String SELECTALL = "SELECT CATEGORY_ID, CATEGORY_NAME FROM CATEGORY";

	private static final String SELECTONE = "SELECT CATEGORY_ID, CATEGORY_NAME FROM CATEGORY WHERE CATEGORY_NAME = ?";

/*------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<CategoryDTO> selectAll(CategoryDTO categoryDTO) {
		
		log.debug("selectAll 진입");
		
		try {

			return jdbcTemplate.query(SELECTALL, new categoryRowMapper());
		
		} catch (Exception e) {
			
			log.debug("selectAll 예외 발생");

			return null;

		}

	}

	public CategoryDTO selectOne(CategoryDTO categoryDTO) {
		
		System.out.println("[로그] category selectOne 처리 진입");
		
		System.out.println("[로그] categoryName = " + categoryDTO.getCategoryName());

		Object[] args = { categoryDTO.getCategoryName() };

		try {
		
			return jdbcTemplate.queryForObject(SELECTONE, args, new categoryRowMapper());
			
		} catch (Exception e) {

			return null;

		}

	}

}

@Slf4j
// 개발자의 편의를 위해 RowMapper 인터페이스를 사용
class categoryRowMapper implements RowMapper<CategoryDTO> {
	
	@Override
	public CategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("categoryRowMapper 진입");

		CategoryDTO categoryDTO = new CategoryDTO();

		categoryDTO.setCategoryID(rs.getInt("CATEGORY_ID"));
		categoryDTO.setCategoryName(rs.getString("CATEGORY_NAME"));
		
		log.debug("categoryRowMapper 완료");
		
		return categoryDTO;

	}

}