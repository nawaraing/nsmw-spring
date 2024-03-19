package com.naeddoco.nsmwspring.model.monthlySalesStatsModel;

import java.util.List;

public interface MonthlySalesStatsService {

	public List<MonthlySalesStatsDTO> selectAll(MonthlySalesStatsDTO monthlySalesStatsDTO);
	public MonthlySalesStatsDTO selectOne(MonthlySalesStatsDTO monthlySalesStatsDTO);
	public boolean insert(MonthlySalesStatsDTO monthlySalesStatsDTO);
	public boolean update(MonthlySalesStatsDTO monthlySalesStatsDTO);
	public boolean delete(MonthlySalesStatsDTO monthlySalesStatsDTO);
	
}
