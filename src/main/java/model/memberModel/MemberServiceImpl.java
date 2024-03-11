package model.memberModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDAO mDAO;
	
	@Override
	public List<MemberDTO> selectAll(MemberDTO mDTO) {
		return mDAO.selectAll(mDTO);
	}

	@Override
	public MemberDTO selectOne(MemberDTO mDTO) {
		return mDAO.selectOne(mDTO);
	}

	@Override
	public boolean insert(MemberDTO mDTO) {
		mDAO.insert(mDTO); // 성공
		return mDAO.insert(mDTO); // 실패
	}

	@Override
	public boolean update(MemberDTO mDTO) {
		return mDAO.update(mDTO);
	}

	@Override
	public boolean delete(MemberDTO mDTO) {
		return mDAO.delete(mDTO);
	}

}
