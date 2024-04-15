package com.naeddoco.nsmwspring.model.dailySalesStatsModel;

import java.util.List;

public interface DailySalesStatsService {
	
	public List<DailySalesStatsDTO> selectAll(DailySalesStatsDTO dailySalesStatsDTO);
}
