package com.naeddoco.nsmwspring.model.monthlyProductSalesStatsModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("monthlyProductSalesStatsService")
public class MonthlyProductSalesStatsServiceImpl implements MonthlyProductSalesStatsService{
	
	@Autowired
	private MonthlyProductSalesStatsDAO monthlyProductSalesStatsServiceDAO;
	
	@Override
	public List<MonthlyProductSalesStatsDTO> selectAll(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
		return monthlyProductSalesStatsServiceDAO.selectAll(monthlyProductSalesStatsDTO);
	}

	@Override
	public MonthlyProductSalesStatsDTO selectOne(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
		return monthlyProductSalesStatsServiceDAO.selectOne(monthlyProductSalesStatsDTO);
	}

	@Override
	public boolean insert(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
		return monthlyProductSalesStatsServiceDAO.insert(monthlyProductSalesStatsDTO);
	}

	@Override
	public boolean update(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
		return monthlyProductSalesStatsServiceDAO.update(monthlyProductSalesStatsDTO);
	}

	@Override
	public boolean delete(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO) {
		return monthlyProductSalesStatsServiceDAO.delete(monthlyProductSalesStatsDTO);
	}

}
