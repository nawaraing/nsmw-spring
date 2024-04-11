package com.naeddoco.nsmwspring.model.memberCouponModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memberCouponService")
public class MemberCouponServiceImpl implements MemberCouponService{

	//MyBatis 연결
	@Autowired
	private IMemberCouponDAO memberCouponDAO;

	@Override
	public List<MemberCouponDTO> selectAll(MemberCouponDTO memberCouponDTO) {
		return memberCouponDAO.selectAll(memberCouponDTO);
	}

	@Override
	public boolean insert(MemberCouponDTO memberCouponDTO) {

		Map<String,Object> map = new HashMap<String,Object>();

		map.put("memberID", memberCouponDTO.getMemberID());
		map.put("couponID", memberCouponDTO.getCouponID());

		return memberCouponDAO.insert(map);
	}

}
