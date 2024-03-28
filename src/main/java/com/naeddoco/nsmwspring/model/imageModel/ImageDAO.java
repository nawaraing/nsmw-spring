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

	private static final String SELECTONE = "";
	
	// 이미지 경로 데이터를 추가하는 쿼리문
	private static final String INSERT = "INSERT INTO IMAGE (IMAGE_PATH) VALUES (?)";
	
	private static final String UPDATE = "";
	
	private static final String DELETE = "";
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/		
	
	public List<ImageDTO> selectAll(ImageDTO imageDTO) {
		
		log.debug("selectAll 진입");
		
		if(imageDTO.getSearchCondition().equals("getLastOne")) {
		
			try {
				
				log.debug("selectAll 성공");
			
				return (List<ImageDTO>)jdbcTemplate.query(SELECTALL_GET_LAST_ONE, new getLastOneRowMapper());
		
			} catch (Exception e) {
				
				log.debug("selectAll 발생");

				return null;

			}
		
		}
		
		log.debug("selectAll 실패");
		
		return null;
		
	}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public ImageDTO selectOne(ImageDTO imageDTO) {

//		try {
		
//			return jdbcTemplate.queryForObject(SELECTONE, args, new ImageRowMapper());
		
//		} catch (Exception e) {
		
//			log.debug("selectOne 예외처리");
		
			return null;
			
//		}
			
	}
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public boolean insert(ImageDTO imageDTO) {
		
		log.debug("insert 진입");
	
		int result = jdbcTemplate.update(INSERT, imageDTO.getImagePath());
		
		if(result <= 0) {
			
			log.debug("insert 실패");
			
			return false;
			
		}
		
		log.debug("insert 성공");
		
		return true;
		
	}
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public boolean update(ImageDTO imageDTO) {

//		int result = jdbcTemplate.update(UPDATE);
		
//		if(result <= 0) {
		
			return false;
			
//		}
			
//		log.debug("update 성공");
			
//		return true;
			
	}
	
/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

	public boolean delete(ImageDTO gradeDTO) {
		
//		int result = jdbcTemplate.update(DELETE);
		
//		if(result <= 0) {
		
//			log.debug("delete 성공");
		
			return false;
			
//		}
			
//		log.debug("delete 성공");
			
//		return true;
			
	}	
	
}

/*-------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/	

//개발자의 편의를 위해 RowMapper 인터페이스를 사용
class getLastOneRowMapper implements RowMapper<ImageDTO> {
	
	@Override
	public ImageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ImageDTO imageDTO = new ImageDTO();
		
		imageDTO.setImageID(rs.getInt("IMAGE_ID"));
		
		return imageDTO;
		
	}
	
}
