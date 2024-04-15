package com.naeddoco.nsmwspring.model.buyInfoModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("buyInfoService")
public class BuyInfoServiceImpl implements BuyInfoService{
	
	@Autowired
	private BuyInfoDAO buyInfoDAO;
	
	@Override
	public List<BuyInfoDTO> selectAll(BuyInfoDTO buyInfoDTO) {
		return buyInfoDAO.selectAll(buyInfoDTO);
	}
	
	@Override
	public BuyInfoDTO selectOne(BuyInfoDTO buyInfoDTO) {
		return buyInfoDAO.selectOne(buyInfoDTO);
	}

	@Override
	public boolean insert(BuyInfoDTO buyInfoDTO) {
		return buyInfoDAO.insert(buyInfoDTO);
	}

}