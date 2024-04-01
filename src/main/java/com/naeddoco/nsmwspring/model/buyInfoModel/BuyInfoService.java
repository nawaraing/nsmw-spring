package com.naeddoco.nsmwspring.model.buyInfoModel;

import java.util.List;

public interface BuyInfoService {
	
	public List<BuyInfoDTO> selectAll(BuyInfoDTO buyInfoDTO);
	public boolean insert(BuyInfoDTO buyInfoDTO);

}
