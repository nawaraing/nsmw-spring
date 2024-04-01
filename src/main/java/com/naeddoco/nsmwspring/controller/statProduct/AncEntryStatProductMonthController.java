package com.naeddoco.nsmwspring.controller.statProduct;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naeddoco.nsmwspring.model.monthlyProductSalesStatsModel.MonthlyProductSalesStatsDTO;
import com.naeddoco.nsmwspring.model.monthlyProductSalesStatsModel.MonthlyProductSalesStatsService;
import com.naeddoco.nsmwspring.model.monthlySalesStatsModel.MonthlySalesStatsDTO;
import com.naeddoco.nsmwspring.model.monthlySalesStatsModel.MonthlySalesStatsService;

// 월별 매출 통계
@Controller
public class AncEntryStatProductMonthController {
	
	@Autowired
	private MonthlyProductSalesStatsService monthlyProductSalesStatsService;
	
	@RequestMapping(value = "/statProduct/searchMonth", method = RequestMethod.GET)
	public @ResponseBody List<MonthlyProductSalesStatsDTO> ancEntryStatMonthController(MonthlyProductSalesStatsDTO monthlyProductSalesStatsDTO,
											@RequestParam("startMonth") String ancStartMonth,
											@RequestParam("endMonth") String ancEndMonth) {	
		
		System.out.println("[log] 월별 매출통계 요청");
		
		// SQL문 양식에 맞춰서 변경(yyyy-mm-dd)
		ancStartMonth =  ancStartMonth + "-01";
		ancEndMonth = ancEndMonth + "-01";
		
        System.out.println("입력된 검색 시작일 : " + ancStartMonth);
        System.out.println("입력된 검색 종료일 : " + ancEndMonth);
        
        // 월 차이 계산을 위해 자료형 변경
        LocalDate locakStartMonth = LocalDate.parse(ancStartMonth);
        LocalDate locakEndMonth = LocalDate.parse(ancEndMonth);

        // 월차이 계산
        long months = ChronoUnit.MONTHS.between(locakStartMonth, locakEndMonth);
        
        System.out.println("입력된 월 차이 : " + months);
        
        if(months > 12) {
        	
        	System.out.println("[log] 입력된 날짜의 월 차이가 12 초과");
        	
        	return null;
        	
        }
        
        monthlyProductSalesStatsDTO.setSearchCondition("selectAdminStatProductDatas");
        monthlyProductSalesStatsDTO.setAncStartMonth(ancStartMonth);
        monthlyProductSalesStatsDTO.setAncEndMonth(ancEndMonth);
    	
    	List<MonthlyProductSalesStatsDTO> monthlyProductSalesStats = monthlyProductSalesStatsService.selectAll(monthlyProductSalesStatsDTO);
    	
    	if(monthlyProductSalesStats == null) {
    		
    		System.out.println("[log] 매출 불러오기 실패");
    		
    		return null;
    	}
    	
    	System.out.println("[log] 매출 불러오기 성공");
    	System.out.println("[log] 리스트에 담긴 index : " + monthlyProductSalesStats.size());

		return monthlyProductSalesStats;
	}
}
