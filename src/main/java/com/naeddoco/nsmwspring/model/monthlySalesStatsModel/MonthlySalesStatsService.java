package com.naeddoco.nsmwspring.model.monthlySalesStatsModel;

import java.util.List;

public interface MonthlySalesStatsService {

	public List<MonthlySalesStatsDTO> selectAll(MonthlySalesStatsDTO monthlySalesStatsDTO);
	public boolean insert(MonthlySalesStatsDTO monthlySalesStatsDTO);
	
}
