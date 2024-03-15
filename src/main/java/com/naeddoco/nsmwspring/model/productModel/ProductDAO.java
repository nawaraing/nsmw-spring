package com.naeddoco.nsmwspring.model.productModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("productDAO")
public class ProductDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 쿼리문
	private static final String SELECTALL_LAST_ONE = "SELECT PRODUCT_ID FROM PRODUCT ORDER BY REGISTER_DATE DESC LIMIT 1";

	private static final String SELECTONE = "";

	private static final String INSERT = "INSERT INTO PRODUCT ("
										 + "PRODUCT_NAME, "
										 + "PRODUCT_DETAIL, "
										 + "COST_PRICE, "
										 + "RETAIL_PRICE, "
										 + "SALE_PRICE, "
										 + "STOCK, "
										 + "INGREDIENT, "
										 + "DOSAGE, "
										 + "EXPIRATION_DATE, "
										 + "REGISTER_DATE, "
										 + "MODIFY_DATE, "
										 + "SALE_STATE ) "
										 + "VALUES ( " 
										 + "?, " 
										 + "?, " 
										 + "?, " 
										 + "?, " 
										 + "?, " 
										 + "?, " 
										 + "?, " 
										 + "?, " 
										 + "?, "
										 + "CURRENT_TIMESTAMP, " 
										 + "CURRENT_TIMESTAMP, " 
										 + "? )";

	private static final String UPDATE = "";

	private static final String DELETE = "";

/*------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<ProductDTO> selectAll(ProductDTO productDTO) {
		
		if(productDTO.getSearchCondition().equals("getLastOne")) {
		
			return (List<ProductDTO>) jdbcTemplate.query(SELECTALL_LAST_ONE, new productRowMapper());
			
		}
		
		return null;

	}

	public ProductDTO selectOne(ProductDTO productDTO) {

		Object[] args = { productDTO.getProductID() };

		try {

			return jdbcTemplate.queryForObject(SELECTONE, args, new productRowMapper());

		} catch (Exception e) {

			return null;

		}

	}

	// PK제외 모든 항목
	public boolean insert(ProductDTO productDTO) {

		System.out.println("[로그] product insert 처리 진입");

		int result = 0;

		if (productDTO.getSearchCondition().equals("crawlProduct")) {

			result = jdbcTemplate.update(INSERT, productDTO.getProductName(), productDTO.getProductDetail(),
					productDTO.getCostPrice(), productDTO.getRetailPrice(), productDTO.getSalePrice(),
					productDTO.getStock(), productDTO.getIngredient(), productDTO.getDosage(),
					productDTO.getExpirationDate(), productDTO.getSaleState());

		}

		if (result <= 0) {

			return false;

		}

		return true;

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
class productRowMapper implements RowMapper<ProductDTO> {
	
	@Override
	public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		System.out.println("[로그] productRowMapper 처리 진입");

		ProductDTO productDTO = new ProductDTO();
		
		productDTO.setProductID(rs.getInt("PRODUCT_ID"));

		return productDTO;

	}

}