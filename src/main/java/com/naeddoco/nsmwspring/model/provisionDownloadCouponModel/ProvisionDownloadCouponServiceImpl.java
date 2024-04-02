package com.naeddoco.nsmwspring.model.provisionDownloadCouponModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("provisionDownloadCouponService")
public class ProvisionDownloadCouponServiceImpl implements ProvisionDownloadCouponService{
	
	@Autowired
	private ProvisionDownloadCouponDAO provisionDownloadCouponDAO;
	
	@Override
	public List<ProvisionDownloadCouponDTO> selectAll(ProvisionDownloadCouponDTO provisionDownloadCouponDTO) {
		return provisionDownloadCouponDAO.selectAll(provisionDownloadCouponDTO);
	}

	@Override
	public ProvisionDownloadCouponDTO selectOne(ProvisionDownloadCouponDTO provisionDownloadCouponDTO) {
		return provisionDownloadCouponDAO.selectOne(provisionDownloadCouponDTO);
	}

	@Override
	public boolean insert(ProvisionDownloadCouponDTO provisionDownloadCouponDTO) {
		return provisionDownloadCouponDAO.insert(provisionDownloadCouponDTO);
	}

	@Override
	public boolean update(ProvisionDownloadCouponDTO provisionDownloadCouponDTO) {
		return provisionDownloadCouponDAO.update(provisionDownloadCouponDTO);
	}

	@Override
	public boolean delete(ProvisionDownloadCouponDTO provisionDownloadCouponDTO) {
		return provisionDownloadCouponDAO.delete(provisionDownloadCouponDTO);
	}

}
