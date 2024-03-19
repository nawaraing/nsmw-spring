package com.naeddoco.nsmwspring.model.provisionBatchCouponModel;

import java.util.List;

import org.springframework.stereotype.Service;

@Service("provisionBatchCouponService")
public class ProvisionBatchCouponServiceImpl implements ProvisionBatchCouponService{

	private ProvisionBatchCouponDAO provisionBatchCouponDAO;
	
	@Override
	public List<ProvisionBatchCouponDTO> selectAll(ProvisionBatchCouponDTO provisionBatchCouponDTO) {
		return provisionBatchCouponDAO.selectAll(provisionBatchCouponDTO);
	}

	@Override
	public ProvisionBatchCouponDTO selectOne(ProvisionBatchCouponDTO provisionBatchCouponDTO) {
		return provisionBatchCouponDAO.selectOne(provisionBatchCouponDTO);
	}

	@Override
	public boolean insert(ProvisionBatchCouponDTO provisionBatchCouponDTO) {
		return provisionBatchCouponDAO.insert(provisionBatchCouponDTO);
	}

	@Override
	public boolean update(ProvisionBatchCouponDTO provisionBatchCouponDTO) {
		return provisionBatchCouponDAO.update(provisionBatchCouponDTO);
	}

	@Override
	public boolean delete(ProvisionBatchCouponDTO provisionBatchCouponDTO) {
		return provisionBatchCouponDAO.delete(provisionBatchCouponDTO);
	}

}
