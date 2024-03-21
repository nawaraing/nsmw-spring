package com.naeddoco.nsmwspring.model.productModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.naeddoco.nsmwspring.model.buyInfoModel.BuyInfoDAO;

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
	
	// 하나의 상품의 데이터를 가져오는 쿼리문
	private static final String SELECTONE_GET_PRODUCT_DETAIL = "SELECT p.PRODUCT_ID, p.PRODUCT_NAME, p.PRODUCT_DETAIL, p.COST_PRICE, p.RETAIL_PRICE, p.SALE_PRICE, p.STOCK, p.INGREDIENT, p.DOSAGE, "
													+ "p.EXPIRATION_DATE, p.REGISTER_DATE, p.MODIFY_DATE, p.SALE_STATE, c.CATEGORY_NAME, i.IMAGE_PATH "
													+ "FROM PRODUCT p "
													+ "JOIN PRODUCT_CATEGORY pc ON p.PRODUCT_ID = pc.PRODUCT_ID "
													+ "JOIN CATEGORY c ON pc.CATEGORY_ID = c.CATEGORY_ID "
													+ "JOIN PRODUCT_IMAGE pi ON p.PRODUCT_ID = pi.PRODUCT_ID "
													+ "JOIN IMAGE i ON pi.IMAGE_ID = i.IMAGE_ID "
													+ "WHERE p.PRODUCT_ID = ?";

	// 크롤링한 상품 데이터를 추가하는 쿼리문
	private static final String INSERT = "INSERT INTO PRODUCT ( PRODUCT_NAME, PRODUCT_DETAIL, COST_PRICE, RETAIL_PRICE, SALE_PRICE, STOCK, INGREDIENT, DOSAGE, EXPIRATION_DATE, REGISTER_DATE, MODIFY_DATE, SALE_STATE ) " + 
									     "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ? )";

	private static final String UPDATE = "";

	/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public List<ProductDTO> selectAll(ProductDTO productDTO) {

		log.debug("[로그] product SELECTALL_GET_LAST_ONE 처리 진입");

		if (productDTO.getSearchCondition().equals("getLastOne")) {

			log.debug("[로그] product getLastOne 처리 진입");

			try {

				return (List<ProductDTO>) jdbcTemplate.query(SELECTALL_GET_LAST_ONE, new getLastOneRowMapper());

			} catch (Exception e) {

				log.debug("[로그] product getLastOne 예외 발생");

				return null;

			}

		}

		log.debug("[로그] product getLastOne 처리 실패");

		return null;

	}

	/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public ProductDTO selectOne(ProductDTO productDTO) {
		
		log.debug("[로그] product SELECTONE_GET_PRODUCT_DETAIL 처리 진입");
		
		if(productDTO.getSearchCondition().equals("getProductDetail")) {
			
			log.debug("[로그] product getProductDetail 처리 진입");
			
			Object[] args = { productDTO.getProductID() };

			try {

				return jdbcTemplate.queryForObject(SELECTONE_GET_PRODUCT_DETAIL, args, new getProductDetailRowMapper());

			} catch (Exception e) {
				
				log.debug("[로그] product getProductDetail 예외 발생");

				return null;

			}
			
		}
		
		log.debug("[로그] product SELECTONE_GET_PRODUCT_DETAIL 처리 실패");
		
		return null;

	}

	/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	public boolean insert(ProductDTO productDTO) {

		log.debug("[로그] product INSERT 처리 진입");

		int result = 0;

		if (productDTO.getSearchCondition().equals("crawlProduct")) {
			
			log.debug("[로그] product crawlProduct 처리 진입");

			result = jdbcTemplate.update(INSERT, productDTO.getProductName(), productDTO.getProductDetail(),
												 productDTO.getCostPrice(), productDTO.getRetailPrice(), 
												 productDTO.getSalePrice(), productDTO.getStock(), 
												 productDTO.getIngredient(), productDTO.getDosage(),
												 productDTO.getExpirationDate(), productDTO.getSaleState());

		}

		if (result <= 0) {

			return false;

		}
		
		log.debug("[로그] product INSERT 처리 실패");

		return true;

	}

	/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

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

}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

@Slf4j
class getLastOneRowMapper implements RowMapper<ProductDTO> {

	@Override
	public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

		log.debug("[로그] product getLastOneRowMapper 처리 진입");

		ProductDTO productDTO = new ProductDTO();

		productDTO.setProductID(rs.getInt("PRODUCT_ID"));

		log.debug("[로그] product getLastOneRowMapper 처리 완료");

		return productDTO;

	}

}

@Slf4j
class getProductDetailRowMapper implements RowMapper<ProductDTO> {

	@Override
	public ProductDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		log.debug("[로그] product getProductDetailRowMapper 처리 진입");

		ProductDTO productDTO = new ProductDTO();
		
		productDTO.setProductID(rs.getInt("PRODUCT_ID"));
		productDTO.setProductName(rs.getString("PRODUCT_NAME"));
		productDTO.setProductDetail(rs.getString("PRODUCT_DETAIL"));
		productDTO.setCostPrice(rs.getInt("COST_PRICE"));
		productDTO.setRetailPrice(rs.getInt("RETAIL_PRICE"));
		productDTO.setSalePrice(rs.getInt("SALE_PRICE"));
		productDTO.setStock(rs.getInt("STOCK"));
		productDTO.setIngredient(rs.getString("INGREDIENT"));
		productDTO.setDosage(rs.getString("DOSAGE"));
		productDTO.setExpirationDate(rs.getString("EXPIRATION_DATE"));
		productDTO.setRegisterDate(rs.getString("REGISTER_DATE"));
		productDTO.setModifyDate(rs.getString("MODIFY_DATE"));
		productDTO.setSaleState(rs.getString("SALE_STATE"));
		productDTO.setAncCategory(rs.getString("c.CATEGORY_NAME"));
		productDTO.setAncImagePath(rs.getString("i.IMAGE_PATH"));
		
		log.debug("[로그] product getProductDetailRowMapper 처리 완료");

		return productDTO;

	}

}