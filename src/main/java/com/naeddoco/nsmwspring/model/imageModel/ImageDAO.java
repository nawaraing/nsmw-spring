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

	private static final String SELECTALL = "";

	private static final String SELECTONE = "";
	
	private static final String INSERT = "";
	
	private static final String UPDATE = "";
	
	private static final String DELETE = "";
	
	
	public List<ImageDTO> selectAll(ImageDTO imageDTO) {
		log.debug("selectAll start");
		return (List<ImageDTO>)jdbcTemplate.query(SELECTALL, new ImageRowMapper());
	}

	
	public ImageDTO selectOne(ImageDTO imageDTO) {

//		Object[] args = { gradeDTO.getGradeID() };
//		log.debug("selectOne start");
//	
//		try {
//			return jdbcTemplate.queryForObject(SELECTONE, args, new ImageRowMapper());
//		} catch (Exception e) {
//			log.debug("selectOne 예외처리");
			return null;
//		}
	}

	
	public boolean insert(ImageDTO imageDTO) {
	
//		int result = jdbcTemplate.update(INSERT);
//		if(result <= 0) {
//			log.debug("insert 실패");
			return false;
//		}
//		log.debug("insert 성공");
//		return true;
	}

	
	public boolean update(ImageDTO imageDTO) {

//		int result = jdbcTemplate.update(UPDATE);
//		if(result <= 0) {
//			log.debug("update 실패");
			return false;
//		}
//		log.debug("update 성공");
//		return true;
	}

	
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

//개발자의 편의를 위해 RowMapper 인터페이스를 사용
@Slf4j
class ImageRowMapper implements RowMapper<ImageDTO> {
	@Override
	public ImageDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ImageDTO data = new ImageDTO();
		
		data.setImageID(rs.getInt("IMAGE_ID"));
		data.setImagePath(rs.getString("IMAGE_PATH"));
			
		log.debug(Integer.toString(rs.getInt("IMAGE_ID")));
		log.debug(rs.getString("IMAGE_PATH"));
		
		return data;
	}
	
}
