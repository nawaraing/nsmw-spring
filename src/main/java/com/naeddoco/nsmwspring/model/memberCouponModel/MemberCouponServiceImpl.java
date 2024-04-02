package com.naeddoco.nsmwspring.model.memberCouponModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memberCouponService")
public class MemberCouponServiceImpl implements MemberCouponService{

	@Autowired
	private MemberCouponDAO memberCouponDAO;
	
	@Override
	public List<MemberCouponDTO> selectAll(MemberCouponDTO memberCouponDTO) {
		return memberCouponDAO.selectAll(memberCouponDTO);
	}

	@Override
	public MemberCouponDTO selectOne(MemberCouponDTO memberCouponDTO) {
		return memberCouponDAO.selectOne(memberCouponDTO);
	}

	@Override
	public boolean insert(MemberCouponDTO memberCouponDTO) {
		return memberCouponDAO.insert(memberCouponDTO);
	}

	@Override
	public boolean update(MemberCouponDTO memberCouponDTO) {
		return memberCouponDAO.update(memberCouponDTO);
	}

	@Override
	public boolean delete(MemberCouponDTO memberCouponDTO) {
		return memberCouponDAO.delete(memberCouponDTO);
	}

}
