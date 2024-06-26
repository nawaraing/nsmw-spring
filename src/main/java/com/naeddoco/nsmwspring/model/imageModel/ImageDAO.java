package com.naeddoco.nsmwspring.model.imageModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository("imageDAO")
@Slf4j
public class ImageDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// 최근에 추가된 하나의 데이터를 가져오는 쿼리문
	private static final String SELECTALL_GET_LAST_ONE = "SELECT IMAGE_ID FROM IMAGE ORDER BY IMAGE_ID DESC LIMIT 1";

	// 이미지 데이터를 가져오는 쿼리문
	private static final String SELECTONE_IMAGE = "SELECT IMAGE_ID, IMAGE_PATH FROM IMAGE WHERE IMAGE_ID = ?";
	
	// 이미지 경로 데이터를 추가하는 쿼리문
	private static final String INSERT = "INSERT INTO IMAGE (IMAGE_PATH) VALUES (?)";
	
	// 이미지ID로 데이터 삭제
	private static final String DELETE = "DELETE FROM IMAGE WHERE IMAGE_ID = ?";
	
	
/*-----------------------------------[ selectAll ] ---------------------------------------------------------------------------------------------------------*/		
	
	public List<ImageDTO> selectAll(ImageDTO imageDTO) {
		
		log.trace("selectAll 진입");
		
		if(imageDTO.getSearchCondition().equals("getLastOne")) {
			
			log.trace("getLastOne 진입");
		
			try {
			
				return jdbcTemplate.query(SELECTALL_GET_LAST_ONE, new getLastOneRowMapper());
		
			} catch (Exception e) {
				
				log.error("getLastOne 예외 발생");
				return null;

			} 	
		} 
			
		log.error("selectAll 실패");
		return null;
		
	}

/*-----------------------------------[ selectOne ] ---------------------------------------------------------------------------------------------------------*/	

	public ImageDTO selectOne(ImageDTO imageDTO) {
		
		log.debug("selectOne 진입");

		if(imageDTO.getSearchCondition().equals("deleteAdminProductImageDatas")) {
			
			log.debug("deleteAdminProductImageDatas 진입");
			
			Object[] args = { imageDTO.getImageID() };
			
			try {
			
				return jdbcTemplate.queryForObject(SELECTONE_IMAGE, args, new imageRowMapper());
		
			} catch (Exception e) {
				
				log.error("deleteAdminProductImageDatas 예외 발생");
				return null;

			} 	
			
		} 
		
		log.error("selectOne 실패");
		return null;
			
	}
	
/*-----------------------------------[ insert ] ---------------------------------------------------------------------------------------------------------*/	

	public boolean insert(ImageDTO imageDTO) {
		
		log.trace("insert 진입");
		
		int result = 0;
		
		if(imageDTO.getSearchCondition().equals("insertProductByAdmin")) {
			
			log.trace("insertProductByAdmin 진입");
			
			try {
				
				result = jdbcTemplate.update(INSERT, imageDTO.getImagePath());
				
				if(result <= 0) {
					
					log.error("insertProductByAdmin 실패");
					return false;
				
				}
			
			} catch (Exception e) {
				
				log.error("insertProductByAdmin 예외발생");
				return false;

			} 	
			
			log.trace("insertProductByAdmin 성공");
			return true;
			
		}
		
		log.error("insert 실패");
		return false;
		
	}

/*-----------------------------------[ delete ] ---------------------------------------------------------------------------------------------------------*/	

	public boolean delete(ImageDTO imageDTO) {
		
		log.trace("delete 진입");
		
		int result = 0;
		
		if(imageDTO.getSearchCondition().equals("deleteAdminProductImageDatas")) {
			
			log.trace("deleteAdminProductImageDatas 진입");
			
			try {
				
				result = jdbcTemplate.update(DELETE, imageDTO.getImageID());
				
				if(result <= 0) {
					
					log.error("deleteAdminProductImageDatas 실패");
					return false;
				
				}
			
			} catch (Exception e) {
				
				log.error("deleteAdminProductImageDatas 예외 발생");
				return false;
				
			}
			
		}	
			
		log.trace("delete 성공");
		return true;
			
	}	
	
}

/*-----------------------------------[ RowMapper ] ---------------------------------------------------------------------------------------------------------*/	

@Slf4j
class getLastOneRowMapper implements RowMapper<ImageDTO> {
	
	@Override
	public ImageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ImageDTO imageDTO = new ImageDTO();
		
		log.trace("getLastOneRowMapper 진입");
		
		imageDTO.setImageID(rs.getInt("IMAGE_ID"));
		
		log.trace("getLastOneRowMapper 완료");
		
		return imageDTO;
		
	}
	
}

@Slf4j
class imageRowMapper implements RowMapper<ImageDTO> {
	
	@Override
	public ImageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ImageDTO imageDTO = new ImageDTO();
		
		log.trace("imageRowMapper 진입");
		
		imageDTO.setImageID(rs.getInt("IMAGE_ID"));
		imageDTO.setImagePath(rs.getString("IMAGE_PATH"));
		
		log.trace("imageRowMapper 완료");
		
		return imageDTO;
		
	}
	
}
