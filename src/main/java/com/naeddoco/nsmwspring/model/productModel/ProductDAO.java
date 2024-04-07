package com.naeddoco.nsmwspring.model.productModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("productDAO")
@Slf4j
public class ProductDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;
			
	// 최근에 추가된 하나의 데이터를 가져오는 쿼리문
	private static final String SELECTALL_GET_LAST_ONE = "SELECT PRODUCT_ID " + 
														 "FROM PRODUCT " +
													     "ORDER BY REGISTER_DATE " +
													     "DESC " +
													     "LIMIT 1";
	
	// 검색한 문자열에 해당하는 상품의 데이터를 가져오는 쿼리문
	private static final String SELECTALL_CHAR_SEARCH = "SELECT " +
														"P.PRODUCT_ID, " +
														"P.PRODUCT_NAME, " +
														"P.PRODUCT_DETAIL, " +
														"P.COST_PRICE, " +
														"P.RETAIL_PRICE, " +
														"P.SALE_PRICE, " +
														"P.STOCK, " +
														"P.INGREDIENT, " +
														"P.DOSAGE, " +
														"P.EXPIRATION_DATE, " +
														"P.REGISTER_DATE, " +
														"P.MODIFY_DATE, " +
														"P.SALE_STATE, " +
														"(SELECT GROUP_CONCAT(C.CATEGORY_NAME SEPARATOR ';')  FROM CATEGORY C  INNER JOIN PRODUCT_CATEGORY PC ON C.CATEGORY_ID = PC.CATEGORY_ID  WHERE PC.PRODUCT_ID = P.PRODUCT_ID) AS CATEGORIES, " +
														"(SELECT GROUP_CONCAT(I.IMAGE_PATH SEPARATOR ';')  FROM IMAGE I  INNER JOIN PRODUCT_IMAGE PI ON I.IMAGE_ID = PI.IMAGE_ID  WHERE PI.PRODUCT_ID = P.PRODUCT_ID) AS IMAGE_PATHS " +
														"FROM PRODUCT P " +
														"WHERE P.PRODUCT_NAME LIKE ?";
	
	// 하나의 상품의 데이터를 가져오는 쿼리문
	private static final String SELECTONE_GET_PRODUCT_DETAIL = "SELECT " +
															   "P.PRODUCT_ID, " +
															   "P.PRODUCT_NAME, " +
															   "P.PRODUCT_DETAIL, " +
															   "P.COST_PRICE, " +
															   "P.RETAIL_PRICE, " +
															   "P.SALE_PRICE, " +
															   "P.STOCK, " +
															   "P.INGREDIENT, " +
															   "P.DOSAGE, " +
															   "P.EXPIRATION_DATE, " +
															   "P.REGISTER_DATE, " +
															   "P.MODIFY_DATE, " +
															   "P.SALE_STATE, " +
															   "CA.CATEGORY_NAME, " +
															   "I.IMAGE_PATH " +
															   "FROM PRODUCT P " +
															   "JOIN PRODUCT_CATEGORY PC ON P.PRODUCT_ID = PC.PRODUCT_ID " +
															   "JOIN CATEGORY CA ON PC.CATEGORY_ID = CA.CATEGORY_ID " +
															   "JOIN PRODUCT_IMAGE PI ON P.PRODUCT_ID = PI.PRODUCT_ID " +
															   "JOIN IMAGE I ON PI.IMAGE_ID = I.IMAGE_ID " +
															   "WHERE P.PRODUCT_ID = ?";
	
	// 크롤링한 상품 데이터를 추가하는 쿼리문
	private static final String INSERT = "INSERT INTO PRODUCT ( " +
			 							 "PRODUCT_NAME, " +
			 							 "PRODUCT_DETAIL, " +
			 							 "COST_PRICE, " +
			 							 "RETAIL_PRICE, " +
			 							 "SALE_PRICE, " +
			 							 "STOCK, " +
			 							 "INGREDIENT, " +
			 							 "DOSAGE, " +
			 							 "EXPIRATION_DATE, " +
			 							 "REGISTER_DATE, " +
			 							 "MODIFY_DATE, " +
			 							 "SALE_STATE ) " + 
									     "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ? )";
	
	// 상품의 정보를 갱신하는 쿼리
	private static final String UPDATE_PRODUCT_INFO = "UPDATE PRODUCT SET " +
			 							 			  "PRODUCT_NAME = ?, " +
			 							 			  "PRODUCT_DETAIL = ?, " +
			 							 			  "COST_PRICE = ?, " +
			 							 			  "RETAIL_PRICE = ?, " +
			 							 			  "SALE_PRICE = ?, " +
			 							 			  "STOCK = ?, " +
			 							 			  "INGREDIENT = ?, " +
			 							 			  "DOSAGE = ?, " +
			 							 			  "EXPIRATION_DATE = ?, " +
			 							 			  "MODIFY_DATE = CURRENT_TIMESTAMP " +
			 							 			  "WHERE PRODUCT_ID = ?";
	
	// 상품의 판매 상태를 갱신하는 쿼리
	private static final String UPDATE_PRODUCT_SALE_STATE = "UPDATE PRODUCT SET " +
														    "SALE_STATE = ? " +
														    "WHERE PRODUCT_ID = ?";

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<ProductDTO> selectAll(ProductDTO productDTO) {

		log.debug("selectAll 진입");

		if (productDTO.getSearchCondition().equals("getLastOne")) {

			log.debug("getLastOne 진입");

			try {

				return jdbcTemplate.query(SELECTALL_GET_LAST_ONE, new getLastOneRowMapper());

			} catch (Exception e) {

				log.error("getLastOne 예외 발생");

				return null;

			}

		} else if(productDTO.getSearchCondition().equals("selectAdminProductListDatas")) {
			
			log.debug("selectAdminProductListDatas 진입");
			
			String sqlQuery = SELECTALL_CHAR_SEARCH;
			
			// ORDER BY를 동적으로 사용하기 위해서 ?를 사용하지 않고, 직접 인자를 붙여주기 위한 방법
			sqlQuery += " ORDER BY " + productDTO.getSortColumnName() + " " + productDTO.getSortMode();
			
			Object[] args = { productDTO.getSearchKeyword() };

			try {

				return jdbcTemplate.query(sqlQuery, args, new selectAllCharSearchRowMapper());

			} catch (Exception e) {

				log.error("selectAdminProductListDatas 예외 발생");

				return null;

			}
			
		} 

		log.error("selectAll 실패");

		return null;

	}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public ProductDTO selectOne(ProductDTO productDTO) {
		
		log.debug("selectOne 진입");
		
		if(productDTO.getSearchCondition().equals("getProductDetail")) {
			
			log.debug("getProductDetail 진입");
			
			Object[] args = { productDTO.getProductID() };

			try {

				return jdbcTemplate.queryForObject(SELECTONE_GET_PRODUCT_DETAIL, args, new getProductDetailRowMapper());

			} catch (Exception e) {
				
				log.error("getProductDetail 예외 발생");

				return null;

			}
			
		}
		
		log.error("selectOne 실패");
		
		return null;

	}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean insert(ProductDTO productDTO) {

		log.debug("insert 진입");

		int result = 0;

		if (productDTO.getSearchCondition().equals("crawlProduct")) {
			
			log.debug("crawlProduct 진입");

			try {
			
				result = jdbcTemplate.update(INSERT, 
										     productDTO.getProductName(), 
										     productDTO.getProductDetail(),
										     productDTO.getCostPrice(), 
										     productDTO.getRetailPrice(), 
										     productDTO.getSalePrice(), 
										 	 productDTO.getStock(), 
										 	 productDTO.getIngredient(), 
										 	 productDTO.getDosage(),
										 	 productDTO.getExpirationDate(), 
										 	 productDTO.getSaleState());

			} catch (Exception e) {
			
				log.error("crawlProduct 예외 발생");

				return false;

			}
			
			if (result <= 0) {
				
				log.error("crawlProduct 실패");

				return false;

			}
			
			log.debug("crawlProduct 성공");

			return true;
			
		} else if(productDTO.getSearchCondition().equals("insertProductByAdmin")) {
			
			log.debug("insertProductByAdmin 진입");

			try {
			
				result = jdbcTemplate.update(INSERT, 
										     productDTO.getProductName(), 
										     productDTO.getProductDetail(),
										     productDTO.getCostPrice(), 
										     productDTO.getRetailPrice(), 
										     productDTO.getSalePrice(), 
										 	 productDTO.getStock(), 
										 	 productDTO.getIngredient(), 
										 	 productDTO.getDosage(),
										 	 productDTO.getExpirationDate(), 
										 	 productDTO.getSaleState());
				
			} catch (Exception e) {
			
				log.error("insertProductByAdmin 예외 발생");

				return false;

			}
			
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

	public boolean update(ProductDTO productDTO) {
		
		log.debug("update 진입");
		
		int result = 0;
		
		if (productDTO.getSearchCondition().equals("updateAdminProductListData")) {
			
			log.debug("updateAdminProductListData 진입");
		
			try {
		
				result = jdbcTemplate.update(UPDATE_PRODUCT_INFO, 
                        					 productDTO.getProductName(), 
                        					 productDTO.getProductDetail(),
                        					 productDTO.getCostPrice(), 
                        					 productDTO.getRetailPrice(), 
                        					 productDTO.getSalePrice(),   
                        					 productDTO.getStock(), 
                        					 productDTO.getIngredient(), 
                        					 productDTO.getDosage(),
                        					 productDTO.getExpirationDate(), 
                        					 productDTO.getProductID());
		
			} catch (Exception e) {
			
				log.error("updateAdminProductListData 예외 발생");

				return false;

			}

			if (result <= 0) {
				
				log.error("updateAdminProductListData 실패");

				return false;

			}
			
			log.error("updateAdminProductListData 성공");
			
			return true;

		} else if(productDTO.getSearchCondition().equals("updateSaleState")) {
			
			log.debug("updateSaleState 진입");
			
			try {
		
				result = jdbcTemplate.update(UPDATE_PRODUCT_SALE_STATE, 
                        					 productDTO.getSaleState(), 
                        					 productDTO.getProductID());
		
			} catch (Exception e) {
			
				log.error("updateSaleState 예외 발생");

				return false;

			}

			if (result <= 0) {
				
				log.error("updateSaleState 실패");

				return false;

			}
			
			log.error("updateSaleState 성공");
			
			return true;
			
			
		}
		
		log.error("update 실패");
		
		return false;
		
	}

}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

@Slf4j
class getLastOneRowMapper implements RowMapper<ProductDTO> {

	@Override
	public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		log.debug("getLastOneRowMapper 진입");

		ProductDTO productDTO = new ProductDTO();

		productDTO.setProductID(rs.getInt("PRODUCT_ID"));

		log.debug("getLastOneRowMapper 완료");

		return productDTO;

	}

}

@Slf4j
class getProductDetailRowMapper implements RowMapper<ProductDTO> {

	@Override
	public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("getProductDetailRowMapper 진입");

		ProductDTO productDTO = new ProductDTO();
		
		productDTO.setProductID(rs.getInt("P.PRODUCT_ID"));
		productDTO.setProductName(rs.getString("P.PRODUCT_NAME"));
		productDTO.setProductDetail(rs.getString("P.PRODUCT_DETAIL"));
		productDTO.setCostPrice(rs.getInt("P.COST_PRICE"));
		productDTO.setRetailPrice(rs.getInt("P.RETAIL_PRICE"));
		productDTO.setSalePrice(rs.getInt("P.SALE_PRICE"));
		productDTO.setStock(rs.getInt("P.STOCK"));
		productDTO.setIngredient(rs.getString("P.INGREDIENT"));
		productDTO.setDosage(rs.getString("P.DOSAGE"));
		productDTO.setExpirationDate(rs.getString("P.EXPIRATION_DATE"));
		productDTO.setRegisterDate(rs.getString("P.REGISTER_DATE"));
		productDTO.setModifyDate(rs.getString("P.MODIFY_DATE"));
		productDTO.setSaleState(rs.getString("P.SALE_STATE"));
		productDTO.setAncCategoryName(rs.getString("CA.CATEGORY_NAME"));
		productDTO.setAncImagePath(rs.getString("I.IMAGE_PATH"));
		
		log.debug("getProductDetailRowMapper 완료");

		return productDTO;

	}

}

@Slf4j
class selectAllCharSearchRowMapper implements RowMapper<ProductDTO> {

	@Override
	public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("selectAllProductRowMapper 진입");

		ProductDTO productDTO = new ProductDTO();
		
		productDTO.setProductID(rs.getInt("P.PRODUCT_ID"));
		productDTO.setProductName(rs.getString("P.PRODUCT_NAME"));
		productDTO.setProductDetail(rs.getString("P.PRODUCT_DETAIL"));
		productDTO.setCostPrice(rs.getInt("P.COST_PRICE"));
		productDTO.setRetailPrice(rs.getInt("P.RETAIL_PRICE"));
		productDTO.setSalePrice(rs.getInt("P.SALE_PRICE"));
		productDTO.setStock(rs.getInt("P.STOCK"));
		productDTO.setIngredient(rs.getString("P.INGREDIENT"));
		productDTO.setDosage(rs.getString("P.DOSAGE"));
		productDTO.setExpirationDate(rs.getString("P.EXPIRATION_DATE"));
		productDTO.setRegisterDate(rs.getString("P.REGISTER_DATE"));
		productDTO.setModifyDate(rs.getString("P.MODIFY_DATE"));
		productDTO.setSaleState(rs.getString("P.SALE_STATE"));
		productDTO.setAncCategoryName(rs.getString("CATEGORIES"));
		productDTO.setAncImagePath(rs.getString("IMAGE_PATHS"));
		
		log.debug("selectAllProductRowMapper 완료");

		return productDTO;

	}

}