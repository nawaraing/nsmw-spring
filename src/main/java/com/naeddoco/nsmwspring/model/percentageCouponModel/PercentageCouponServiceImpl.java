package com.naeddoco.nsmwspring.model.percentageCouponModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("percentageCouponService")
public class PercentageCouponServiceImpl implements PercentageCouponService{
	
	@Autowired
	private PercentageCouponDAO percentageCouponDAO;
	
	@Override
	public List<PercentageCouponDTO> selectAll(PercentageCouponDTO percentageCouponDTO) {
		return percentageCouponDAO.selectAll(percentageCouponDTO);
	}

	@Override
	public PercentageCouponDTO selectOne(PercentageCouponDTO percentageCouponDTO) {
		return percentageCouponDAO.selectOne(percentageCouponDTO);
	}

	@Override
	public boolean insert(PercentageCouponDTO percentageCouponDTO) {
		return percentageCouponDAO.insert(percentageCouponDTO);
	}

	@Override
	public boolean update(PercentageCouponDTO percentageCouponDTO) {
		return percentageCouponDAO.update(percentageCouponDTO);
	}

	@Override
	public boolean delete(PercentageCouponDTO percentageCouponDTO) {
		return percentageCouponDAO.delete(percentageCouponDTO);
	}

}
