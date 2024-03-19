package com.naeddoco.nsmwspring.model.dailySalesStatsModel;

import java.util.List;

public interface DailySalesStatsService {
	public List<DailySalesStatsDTO> selectAll(DailySalesStatsDTO dailySalesStatsDTO);
	public DailySalesStatsDTO selectOne(DailySalesStatsDTO dailySalesStatsDTO);
	public boolean insert(DailySalesStatsDTO dailySalesStatsDTO);
	public boolean update(DailySalesStatsDTO dailySalesStatsDTO);
	public boolean delete(DailySalesStatsDTO dailySalesStatsDTO);
}
