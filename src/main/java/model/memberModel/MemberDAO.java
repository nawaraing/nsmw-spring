package model.memberModel;

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
	private static final String SELECTALL = "";
	private static final String SELECTONE = "";
	private static final String INSERT = "";
	private static final String UPDATE = "";
	private static final String DELETE = "";

	public List<MemberDTO> selectAll(MemberDTO mDTO) {
		return (List<MemberDTO>) jdbcTemplate.query(SELECTALL, new MemberRowMapper());
	}

	public MemberDTO selectOne(MemberDTO mDTO) {
		Object[] args = { mDTO.getMEMBER_ID(), mDTO.getMEMBER_PASSWORD() };
		try {
			return jdbcTemplate.queryForObject(SELECTONE, args, new MemberRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	public boolean insert(MemberDTO mDTO) {
		int result = jdbcTemplate.update(INSERT, 
										 mDTO.getMEMBER_ID(), 
										 mDTO.getMEMBER_PASSWORD(), 
										 mDTO.getMEMBER_NAME(), 
										 mDTO.getDAY_OF_BIRTH(),
									     mDTO.getGENDER(),
										 mDTO.getPHONE_NUMBER(),
									     mDTO.getEMAIL(),
										 mDTO.getAUTHORITY(),
										 mDTO.getMEMBER_STATE());
		if (result <= 0) {
			return false;
		}
		return true;
	}

	public boolean update(MemberDTO mDTO) {
		int result = jdbcTemplate.update(UPDATE, 
				                         mDTO.getMEMBER_ID(),
										 mDTO.getMEMBER_PASSWORD(), 
				                         mDTO.getMEMBER_NAME(), 
				                         mDTO.getDAY_OF_BIRTH(),
			                             mDTO.getGENDER(),
				                         mDTO.getPHONE_NUMBER(),
			                             mDTO.getEMAIL(),
				                         mDTO.getAUTHORITY(),
				                         mDTO.getMEMBER_STATE());
		if (result <= 0) {
			return false;
		}
		return true;
	}

	public boolean delete(MemberDTO mDTO) {
		int result = jdbcTemplate.update(DELETE, 
				                         mDTO.getMEMBER_ID(),
				                         mDTO.getMEMBER_PASSWORD(), 
                                         mDTO.getMEMBER_NAME(), 
                                         mDTO.getDAY_OF_BIRTH(),
                                         mDTO.getGENDER(),
                                         mDTO.getPHONE_NUMBER(),
                                         mDTO.getEMAIL(),
                                         mDTO.getAUTHORITY(),
                                         mDTO.getMEMBER_STATE());
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
		MemberDTO data = new MemberDTO();
		data.setMEMBER_ID(rs.getString("MEMBER_ID"));
		data.setMEMBER_PASSWORD(rs.getString("MEMBER_PASSWORD"));
		data.setMEMBER_NAME(rs.getString("MEMBER_NAME"));
		data.setDAY_OF_BIRTH(rs.getString("DAY_OF_BIRTH"));
		data.setGENDER(rs.getString("GENDER"));
		data.setPHONE_NUMBER(rs.getString("PHONE_NUMBER"));
		data.setEMAIL(rs.getString("EMAIL"));
		data.setAUTHORITY(rs.getString("AUTHORITY"));
		data.setMEMBER_STATE(rs.getString("MEMBER_STATE"));
		return data;
	}
}