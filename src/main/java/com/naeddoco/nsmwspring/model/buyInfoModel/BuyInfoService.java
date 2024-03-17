package com.naeddoco.nsmwspring.model.buyInfoModel;

import java.util.List;

public interface BuyInfoService {
	public List<BuyInfoDTO> selectAll(BuyInfoDTO buyInfoDTO);
	public BuyInfoDTO selectOne(BuyInfoDTO buyInfoDTO);
	public boolean insert(BuyInfoDTO buyInfoDTO);
	public boolean update(BuyInfoDTO buyInfoDTO);
	public boolean delete(BuyInfoDTO buyInfoDTO);

}
