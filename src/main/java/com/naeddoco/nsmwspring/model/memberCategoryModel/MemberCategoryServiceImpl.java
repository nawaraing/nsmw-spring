package com.naeddoco.nsmwspring.model.memberCategoryModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memberCategoryService")
public class MemberCategoryServiceImpl implements MemberCategoryService {
	
	@Autowired
	private MemberCategoryDAO memberCategoryDAO;
	
	@Override
	public List<MemberCategoryDTO> selectAll(MemberCategoryDTO memberCategoryDTO) {
		return memberCategoryDAO.selectAll(memberCategoryDTO);
	}

	@Override
	public MemberCategoryDTO selectOne(MemberCategoryDTO memberCategoryDTO) {
		return memberCategoryDAO.selectOne(memberCategoryDTO);
	}

	@Override
	public boolean insert(MemberCategoryDTO memberCategoryDTO) {
		return memberCategoryDAO.insert(memberCategoryDTO);
	}

	@Override
	public boolean update(MemberCategoryDTO memberCategoryDTO) {
		return memberCategoryDAO.update(memberCategoryDTO);
	}

	@Override
	public boolean delete(MemberCategoryDTO memberCategoryDTO) {
		return memberCategoryDAO.delete(memberCategoryDTO);
	}

}
