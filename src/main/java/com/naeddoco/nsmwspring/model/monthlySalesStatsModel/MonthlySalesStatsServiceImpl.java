package com.naeddoco.nsmwspring.model.monthlySalesStatsModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("monthlySalesStatsService")
public class MonthlySalesStatsServiceImpl implements MonthlySalesStatsService{
	
	@Autowired
	private MonthlySalesStatsDAO monthlySalesStatsDAO;
	
	@Override
	public List<MonthlySalesStatsDTO> selectAll(MonthlySalesStatsDTO monthlySalesStatsDTO) {
		return monthlySalesStatsDAO.selectAll(monthlySalesStatsDTO);
	}

	@Override
	public boolean insert(MonthlySalesStatsDTO monthlySalesStatsDTO) {
		return monthlySalesStatsDAO.insert(monthlySalesStatsDTO);
	}

}
