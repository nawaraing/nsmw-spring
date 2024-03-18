package com.naeddoco.nsmwspring.model.dailyProductSalesStatsModel;

import java.util.List;

public interface DailyProductSalesStatsService {
	public List<DailyProductSalesStatsDTO> selectAll(DailyProductSalesStatsDTO dailyProductSalesStatsDTO);
	public DailyProductSalesStatsDTO selectOne(DailyProductSalesStatsDTO dailyProductSalesStatsDTO);
	public boolean insert(DailyProductSalesStatsDTO dailyProductSalesStatsDTO);
	public boolean update(DailyProductSalesStatsDTO dailyProductSalesStatsDTO);
	public boolean delete(DailyProductSalesStatsDTO dailyProductSalesStatsDTO);
}
