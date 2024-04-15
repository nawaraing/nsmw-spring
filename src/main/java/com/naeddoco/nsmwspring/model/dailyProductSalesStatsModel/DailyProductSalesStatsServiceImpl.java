package com.naeddoco.nsmwspring.model.dailyProductSalesStatsModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dailyProductSalesStatsService")
public class DailyProductSalesStatsServiceImpl implements DailyProductSalesStatsService{
	
	@Autowired
	private DailyProductSalesStatsDAO dailyProductSalesStatsDAO;
	
	@Override
	public List<DailyProductSalesStatsDTO> selectAll(DailyProductSalesStatsDTO dailyProductSalesStatsDTO) {
		return dailyProductSalesStatsDAO.selectAll(dailyProductSalesStatsDTO);
	}


}
