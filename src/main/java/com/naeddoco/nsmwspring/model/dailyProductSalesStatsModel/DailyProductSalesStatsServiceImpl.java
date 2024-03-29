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

	@Override
	public DailyProductSalesStatsDTO selectOne(DailyProductSalesStatsDTO dailyProductSalesStatsDTO) {
		return dailyProductSalesStatsDAO.selectOne(dailyProductSalesStatsDTO);
	}

	@Override
	public boolean insert(DailyProductSalesStatsDTO dailyProductSalesStatsDTO) {
		return dailyProductSalesStatsDAO.insert(dailyProductSalesStatsDTO);
	}

	@Override
	public boolean update(DailyProductSalesStatsDTO dailyProductSalesStatsDTO) {
		return dailyProductSalesStatsDAO.update(dailyProductSalesStatsDTO);
	}

	@Override
	public boolean delete(DailyProductSalesStatsDTO dailyProductSalesStatsDTO) {
		return dailyProductSalesStatsDAO.delete(dailyProductSalesStatsDTO);
	}

}
