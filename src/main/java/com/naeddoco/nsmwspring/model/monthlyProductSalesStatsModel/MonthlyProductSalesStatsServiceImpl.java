package com.naeddoco.nsmwspring.model.monthlyProductSalesStatsModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("monthlyProductSalesStatsService")
public class MonthlyProductSalesStatsServiceImpl implements MonthlyProductSalesStatsService{
	
	@Autowired
	private MonthlyProductSalesStatsDAO monthlyProductSalesStatsDAO;
	
	@Override
	public List<MonthlyProductSalesStatsDTO> selectAll(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
		return monthlyProductSalesStatsDAO.selectAll(monthlyProductSalesStatsDTO);
	}

	@Override
	public boolean insert(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
		return monthlyProductSalesStatsDAO.insert(monthlyProductSalesStatsDTO);
	}

}
