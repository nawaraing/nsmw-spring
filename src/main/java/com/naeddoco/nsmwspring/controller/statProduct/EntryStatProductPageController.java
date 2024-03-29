package com.naeddoco.nsmwspring.controller.statProduct;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.dailyProductSalesStatsModel.DailyProductSalesStatsDTO;
import com.naeddoco.nsmwspring.model.dailyProductSalesStatsModel.DailyProductSalesStatsService;

@Controller
public class EntryStatProductPageController {
	
	@Autowired
	private DailyProductSalesStatsService dailyProductSalesStatsService;
	
	@RequestMapping(value = "/statProduct", method = RequestMethod.GET)
	public String entryStatProductPageController(DailyProductSalesStatsDTO dailyProductSalesStatsDTO, Model model) {
		
		System.out.println("[log] 일별 상품 매출통계 페이지 요청");
		
        // 현재 날짜 가져오기
        LocalDate today = LocalDate.now();
        // 현재 날짜에서 30일 빼기
        LocalDate thirtyDaysBefore = today.minusDays(30);
        
        // LocalDate를 java.sql.Date로 변환
        Date todaySQL = Date.valueOf(today);
        Date thirtyDaysBeforeSQL = Date.valueOf(thirtyDaysBefore);

        System.out.println("오늘 날짜 : " + todaySQL);
        System.out.println("30일 전 날짜 : " + thirtyDaysBeforeSQL);
        
    	
        dailyProductSalesStatsDTO.setSearchCondition("selectAdminStatProductDatas");
        dailyProductSalesStatsDTO.setAncStartDate(thirtyDaysBeforeSQL);
        dailyProductSalesStatsDTO.setAncEndDate(todaySQL);
    	
    	System.out.println("todaySQL 자료형 : " + todaySQL.getClass());
    	System.out.println("thirtyDaysBeforeSQL 자료형 : " + todaySQL.getClass());
    	
    	List<DailyProductSalesStatsDTO> dailyProductSalesStats = dailyProductSalesStatsService.selectAll(dailyProductSalesStatsDTO);
    	
    	if(dailyProductSalesStats.size() < 1) {
    		
    		System.out.println("[log] 30일 매출 불러오기 실패");
    		System.out.println("[log] 리스트에 담긴 index : " + dailyProductSalesStats.size());
    		model.addAttribute("msg", "30일 매출 불러오기를 실패했습니다");
    		
    		return "admin/statDate";
    	}
    	
    	System.out.println("[log] 30일 매출 불러오기 성공");
    	System.out.println("[log] 리스트에 담긴 index : " + dailyProductSalesStats.size());
    	for(int i = 0; i < dailyProductSalesStats.size(); i++) {
    		System.out.println(dailyProductSalesStats.get(i));
    	}
    	model.addAttribute("dailyProductSalesStats", dailyProductSalesStats);

		return "/admin/statProduct"; // 장바구니 페이지로 요청

	}

}
