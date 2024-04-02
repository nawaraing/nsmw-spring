package com.naeddoco.nsmwspring.model.memberCouponModel;

import java.util.List;

public interface MemberCouponService {

	public List<MemberCouponDTO> selectAll(MemberCouponDTO memberCouponDTO);
	public MemberCouponDTO selectOne(MemberCouponDTO memberCouponDTO);
	public boolean insert(MemberCouponDTO memberCouponDTO);
	public boolean update(MemberCouponDTO memberCouponDTO);
	public boolean delete(MemberCouponDTO memberCouponDTO);
}
