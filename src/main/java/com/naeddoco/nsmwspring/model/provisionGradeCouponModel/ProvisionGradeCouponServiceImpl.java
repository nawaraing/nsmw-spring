package com.naeddoco.nsmwspring.model.provisionGradeCouponModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("provisionGradeCouponService")
public class ProvisionGradeCouponServiceImpl implements ProvisionGradeCouponService{

	@Autowired
	private ProvisionGradeCouponDAO provisionGradeCouponDAO;

	@Override
	public List<ProvisionGradeCouponDTO> selectAll(ProvisionGradeCouponDTO provisionGradeCouponDTO) {
		return provisionGradeCouponDAO.selectAll(provisionGradeCouponDTO);
	}

	@Override
	public ProvisionGradeCouponDTO selectOne(ProvisionGradeCouponDTO provisionGradeCouponDTO) {
		return provisionGradeCouponDAO.selectOne(provisionGradeCouponDTO);
	}

	@Override
	public boolean insert(ProvisionGradeCouponDTO provisionGradeCouponDTO) {
		return provisionGradeCouponDAO.insert(provisionGradeCouponDTO);
	}

	@Override
	public boolean update(ProvisionGradeCouponDTO provisionGradeCouponDTO) {
		return provisionGradeCouponDAO.update(provisionGradeCouponDTO);
	}

	@Override
	public boolean delete(ProvisionGradeCouponDTO provisionGradeCouponDTO) {
		return provisionGradeCouponDAO.delete(provisionGradeCouponDTO);


	}
}
