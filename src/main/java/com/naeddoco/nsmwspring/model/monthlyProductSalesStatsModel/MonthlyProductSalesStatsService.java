package com.naeddoco.nsmwspring.model.monthlyProductSalesStatsModel;

import java.util.List;

public interface MonthlyProductSalesStatsService {
	public List<MonthlyProductSalesStatsDTO> selectAll(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO);
	public MonthlyProductSalesStatsDTO selectOne(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO);
	public boolean insert(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO);
	public boolean update(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO);
	public boolean delete(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO);
}
