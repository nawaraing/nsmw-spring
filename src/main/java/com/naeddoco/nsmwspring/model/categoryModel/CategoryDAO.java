package com.naeddoco.nsmwspring.model.categoryModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.productModel.ProductDTO;

@Repository("categoryDAO")
public class CategoryDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 쿼리문
	private static final String SELECTALL = "";

	private static final String SELECTONE = "SELECT CATEGORY_ID, CATEGORY_NAME FROM CATEGORY WHERE CATEGORY_NAME = ?";

	private static final String UPDATE = "";

	private static final String DELETE = "";

/*------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<CategoryDTO> selectAll(CategoryDTO categoryDTO) {

		return (List<CategoryDTO>) jdbcTemplate.query(SELECTALL, new categoryRowMapper());

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

	public boolean update(ProductDTO productDTO) {
		int result = jdbcTemplate.update(UPDATE, productDTO.getProductName(), productDTO.getProductDetail(),
				productDTO.getCostPrice(), productDTO.getRetailPrice(), productDTO.getSalePrice(),
				productDTO.getStock(), productDTO.getIngredient(), productDTO.getDosage(),
				productDTO.getExpirationDate(), productDTO.getRegisterDate(), productDTO.getModifyDate(),
				productDTO.getSaleState());

		if (result <= 0) {

			return false;

		}

		return true;

	}

	// 상품 영구 삭제기능 미지원
	public boolean delete(ProductDTO productDTO) {

		int result = jdbcTemplate.update(DELETE);

		if (result <= 0) {

			return false;

		}

		return true;

	}

}

// 개발자의 편의를 위해 RowMapper 인터페이스를 사용
class categoryRowMapper implements RowMapper<CategoryDTO> {
	
	@Override
	public CategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		System.out.println("[로그] categoryRowMapper 처리 진입");

		CategoryDTO categoryDTO = new CategoryDTO();

		categoryDTO.setCategoryID(rs.getInt("CATEGORY_ID"));
		categoryDTO.setCategoryName(rs.getString("CATEGORY_NAME"));
		
		return categoryDTO;

	}

}