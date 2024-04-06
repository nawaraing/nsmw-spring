package com.naeddoco.nsmwspring.model.productCategoryModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("productCategoryDAO")
@Slf4j
public class ProductCategoryDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// 잘 팔린 상품들 중 일정 갯수의 데이터를 습득하는 쿼리
	private static final String SELECTALL_PRODUCT_BY_LIMIT = "SELECT " +
			  												 "P.PRODUCT_ID, " +
			  												 "P.PRODUCT_NAME, " +
			  												 "P.PRODUCT_DETAIL, " +
			  												 "C.CATEGORY_NAME, " +
			  												 "P.SALE_PRICE, " +
			  												 "I.IMAGE_PATH " +
			  												 "FROM PRODUCT_CATEGORY PC " +
			  												 "INNER JOIN PRODUCT P ON PC.PRODUCT_ID = P.PRODUCT_ID " +
			  												 "INNER JOIN CATEGORY C ON PC.CATEGORY_ID = C.CATEGORY_ID " +
			  												 "INNER JOIN PRODUCT_IMAGE PI ON P.PRODUCT_ID = PI.PRODUCT_ID " +
			  												 "INNER JOIN IMAGE I ON PI.IMAGE_ID = I.IMAGE_ID " +
			  												 "ORDER BY (SELECT SUM(BUY_QUANTITY) FROM ORDER_INFO O WHERE P.PRODUCT_ID = O.PRODUCT_ID) DESC " +
			  												 "LIMIT ?";
			                                                
	// 카테고리가 동일한 제품들 중 가장 잘 팔리는 상품의 데이터를 습득하는 쿼리
	private static final String SELECTONE_PRODUCT_BY_CATEGORY_ID = "SELECT " +
																   "P.PRODUCT_ID, " +
																   "P.PRODUCT_NAME, " +
																   "P.PRODUCT_DETAIL, " +
																   "C.CATEGORY_NAME, " +
																   "P.SALE_PRICE, " +
																   "I.IMAGE_PATH " +
																   "FROM PRODUCT_CATEGORY PC " +
																   "INNER JOIN PRODUCT P ON PC.PRODUCT_ID = P.PRODUCT_ID " +
																   "INNER JOIN CATEGORY C ON PC.CATEGORY_ID = C.CATEGORY_ID " +
																   "INNER JOIN PRODUCT_IMAGE PI ON P.PRODUCT_ID = PI.PRODUCT_ID " +
																   "INNER JOIN IMAGE I ON PI.IMAGE_ID = I.IMAGE_ID " +
																   "WHERE PC.CATEGORY_ID = ? " +
																   "ORDER BY (SELECT SUM(BUY_QUANTITY) FROM ORDER_INFO O WHERE O.PRODUCT_ID = PC.PRODUCT_ID) DESC " +
																   "LIMIT 1";
																   
	// 새로운 상품 정보를 등록하는 쿼리
	private static final String INSERT = "INSERT INTO PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_ID) VALUES (?, ?)";
	
	// 상품 정보를 삭제하는 쿼리
	private static final String DELETE = "DELETE FROM PRODUCT_CATEOGRY WHERE PRODUCT_ID = ?";
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	
	
	public List<ProductCategoryDTO> selectAll(ProductCategoryDTO productCategoryDTO) {
		
		log.debug("selectAll 진입");
		
		if(productCategoryDTO.getSearchCondition().equals("getProductByLimit")) {
			
			log.debug("getProductByLimit 진입");
			
			Object[] args = { productCategoryDTO.getAncLimit() };
			
			try {
				
				return jdbcTemplate.query(SELECTALL_PRODUCT_BY_LIMIT, args, new GetProductByLimitRowMapper());

			}catch (Exception e) {
				
				log.error("getProductByLimit 예외 발생");
				
				return null;
				
			}
			
		}
		
		log.error("selectAll 실패");
		
		return null;

	}
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/		
	
	public ProductCategoryDTO selectOne(ProductCategoryDTO productCategoryDTO) {
		
		log.debug("selectOne 진입");
		
		if(productCategoryDTO.getSearchCondition().equals("getProductByCategory")) {
			
			log.debug("getProductByCategory 진입");
			
			Object[] args = { productCategoryDTO.getCategoryID() };

			try {

				return jdbcTemplate.queryForObject(SELECTONE_PRODUCT_BY_CATEGORY_ID, args, new GetProductByCategoryRowMapper());

			} catch (Exception e) {
				
				log.error("getProductByCategory 예외 발생");

				return null;

			}
			
		}
		
		log.error("selectOne 실패");
		
		return null;

	}
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	
	
	// PK제외 모든 항목
	public boolean insert(ProductCategoryDTO productCategoryDTO) {
		
		log.debug("insert 진입");
		
		if(productCategoryDTO.getSearchCondition().equals("insertProductByAdmin")) {
			
			log.debug("insertProductByAdmin 진입");
			
			int result = jdbcTemplate.update(INSERT, productCategoryDTO.getProductID(), productCategoryDTO.getCategoryID());
			
			if (result <= 0) {
				
				log.error("insertProductByAdmin 실패");
				
				return false;
				
			}
			
			log.debug("insertProductByAdmin 성공");
			
			return true;
			
		}
		
		log.error("insert 실패");
		
		return false;
		
	}
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean delete(ProductCategoryDTO productCategoryDTO) {

		log.debug("delete 진입");
		
		if (productCategoryDTO.getSearchCondition().equals("updateAdminProductListData")) {

			log.debug("updateAdminProductListData 진입");
			
			int result = jdbcTemplate.update(DELETE, productCategoryDTO.getProductID());

			if(result <= 0) {
				
				log.error("updateAdminProductListData 실패");
				
				return false;
			}
			
			log.debug("updateAdminProductListData 성공");
			
			return true;
		}
		
		log.error("delete 실패");
		
		return false;
	}
	
}

@Slf4j
class GetProductByLimitRowMapper implements RowMapper<ProductCategoryDTO> {

	@Override
	public ProductCategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("GetProductByLimitRowMapper 진입");

		ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
		productCategoryDTO.setProductID(rs.getInt("P.PRODUCT_ID"));
		productCategoryDTO.setAncProductName(rs.getString("P.PRODUCT_NAME"));
		productCategoryDTO.setAncProductDetail(rs.getString("P.PRODUCT_DETAIL"));
		productCategoryDTO.setAncSalePrice(rs.getInt("P.SALE_PRICE"));
		productCategoryDTO.setAncImagePath(rs.getString("I.IMAGE_PATH"));
		productCategoryDTO.setAncCategoryName(rs.getString("C.CATEGORY_NAME"));
		
		log.debug("GetProductByLimitRowMapper 완료");

		return productCategoryDTO;

	}

}

@Slf4j
class GetProductByCategoryRowMapper implements RowMapper<ProductCategoryDTO> {

	@Override
	public ProductCategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("GetProductByCategoryRowMapper 진입");

		ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
		productCategoryDTO.setProductID(rs.getInt("P.PRODUCT_ID"));
		productCategoryDTO.setAncProductName(rs.getString("P.PRODUCT_NAME"));
		productCategoryDTO.setAncProductDetail(rs.getString("P.PRODUCT_DETAIL"));
		productCategoryDTO.setAncSalePrice(rs.getInt("P.SALE_PRICE"));
		productCategoryDTO.setAncImagePath(rs.getString("I.IMAGE_PATH"));
		productCategoryDTO.setAncCategoryName(rs.getString("C.CATEGORY_NAME"));
		
		log.debug("GetProductByCategoryRowMapper 완료");

		return productCategoryDTO;

	}

}