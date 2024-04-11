package com.naeddoco.nsmwspring.model.memberCouponModel;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberCouponDAO {
	
	public List<MemberCouponDTO> selectAll(MemberCouponDTO memberCouponDTO);
	public boolean insert(Map<String,Object> map);// 낮은 결합도를 유지하게 해줌
}
