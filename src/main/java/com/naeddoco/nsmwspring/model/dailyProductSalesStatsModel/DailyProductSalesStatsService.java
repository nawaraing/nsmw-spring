package com.naeddoco.nsmwspring.model.dailyProductSalesStatsModel;

import java.util.List;

public interface DailyProductSalesStatsService {
	
	public List<DailyProductSalesStatsDTO> selectAll(DailyProductSalesStatsDTO dailyProductSalesStatsDTO);
}
