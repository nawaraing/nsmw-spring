package com.naeddoco.nsmwspring.model.dailySalesStatsModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dailySalesStatsService")
public class DailySalesStatsServiceImpl implements DailySalesStatsService{
	
	@Autowired
	private DailySalesStatsDAO dailySalesStatsDAO;
	
	@Override
	public List<DailySalesStatsDTO> selectAll(DailySalesStatsDTO dailySalesStatsDTO) {
		return dailySalesStatsDAO.selectAll(dailySalesStatsDTO);
	}

	@Override
	public boolean insert(DailySalesStatsDTO dailySalesStatsDTO) {
		return dailySalesStatsDAO.insert(dailySalesStatsDTO);
	}

}
