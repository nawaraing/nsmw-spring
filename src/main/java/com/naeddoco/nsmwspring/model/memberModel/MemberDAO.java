package com.naeddoco.nsmwspring.model.memberModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("memberDAO")
public class MemberDAO {

	// 의존관계 ▶ DI(의존주입)
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	// 쿼리문
	private static final String SELECTALL = "SELECT * FROM MEMBER;";
	private static final String SELECTONE = "";
	private static final String INSERT = "";
	private static final String UPDATE = "";
	private static final String DELETE = "";

	public List<MemberDTO> selectAll(MemberDTO memberDTO) {
		
		return (List<MemberDTO>) jdbcTemplate.query(SELECTALL, new MemberRowMapper());
		
	}

	public MemberDTO selectOne(MemberDTO memberDTO) {
		
		Object[] args = { memberDTO.getMemberID(), memberDTO.getMemberPassword() };
		
		try {
			
			return jdbcTemplate.queryForObject(SELECTONE, args, new MemberRowMapper());
			
		} catch (Exception e) {
			
			return null;
			
		}
		
	}

	public boolean insert(MemberDTO memberDTO) {
		
		int result = jdbcTemplate.update(INSERT, 
										 memberDTO.getMemberID(), 
										 memberDTO.getMemberPassword(), 
										 memberDTO.getMemberName(), 
										 memberDTO.getDayOfBirth(),
									     memberDTO.getGender(),
										 memberDTO.getPhoneNumber(),
									     memberDTO.getEmail(),
										 memberDTO.getAuthority(),
										 memberDTO.getMemberState());
		
		if (result <= 0) {
			
			return false;
			
		}
		
		return true;
		
	}

	public boolean update(MemberDTO memberDTO) {
		
		int result = jdbcTemplate.update(UPDATE, 
				 						 memberDTO.getMemberID(), 
				 						 memberDTO.getMemberPassword(), 
				 						 memberDTO.getMemberName(), 
				 						 memberDTO.getDayOfBirth(),
				 						 memberDTO.getGender(),
				 						 memberDTO.getPhoneNumber(),
				 						 memberDTO.getEmail(),
				 						 memberDTO.getAuthority(),
				 						 memberDTO.getMemberState());
		
		if (result <= 0) {
			
			return false;
			
		}
		
		return true;
		
	}

	public boolean delete(MemberDTO memberDTO) {
		
		int result = jdbcTemplate.update(DELETE, 
				 						 memberDTO.getMemberID(), 
				 						 memberDTO.getMemberPassword());
		
		if (result <= 0) {
			
			return false;
			
		}
		
		return true;
		
	}
	
}

// 개발자의 편의를 위해 RowMapper 인터페이스를 사용
class MemberRowMapper implements RowMapper<MemberDTO> {
	
	@Override
	public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		MemberDTO memberDTO = new MemberDTO();
		
		memberDTO.setMemberID(rs.getString("MEMBER_ID"));
		memberDTO.setMemberPassword(rs.getString("MEMBER_PASSWORD"));
		memberDTO.setMemberName(rs.getString("MEMBER_NAME"));
		memberDTO.setDayOfBirth(rs.getString("DAY_OF_BIRTH"));
		memberDTO.setGender(rs.getString("GENDER"));
		memberDTO.setPhoneNumber(rs.getString("PHONE_NUMBER"));
		memberDTO.setEmail(rs.getString("EMAIL"));
		memberDTO.setAuthority(rs.getString("AUTHORITY"));
		memberDTO.setMemberState(rs.getString("MEMBER_STATE"));
		
		System.out.println(rs.getString("MEMBER_ID"));
		System.out.println(rs.getString("MEMBER_PASSWORD"));
		System.out.println(rs.getString("MEMBER_NAME"));
		System.out.println(rs.getString("DAY_OF_BIRTH"));
		System.out.println(rs.getString("GENDER"));
		System.out.println(rs.getString("PHONE_NUMBER"));
		System.out.println(rs.getString("EMAIL"));
		System.out.println(rs.getString("AUTHORITY"));
		System.out.println(rs.getString("MEMBER_STATE"));
		
		return memberDTO;
		
	}
	
}