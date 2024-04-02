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
	public MonthlyProductSalesStatsDTO selectOne(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
		return monthlyProductSalesStatsDAO.selectOne(monthlyProductSalesStatsDTO);
	}

	@Override
	public boolean insert(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
		return monthlyProductSalesStatsDAO.insert(monthlyProductSalesStatsDTO);
	}

	@Override
	public boolean update(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
		return monthlyProductSalesStatsDAO.update(monthlyProductSalesStatsDTO);
	}

	@Override
	public boolean delete(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
		return monthlyProductSalesStatsDAO.delete(monthlyProductSalesStatsDTO);
	}

}
