package com.naeddoco.nsmwspring.controller.statDate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.dailySalesStatsModel.DailySalesStatsDTO;
import com.naeddoco.nsmwspring.model.dailySalesStatsModel.DailySalesStatsService;

@Controller
public class EntryStatDatePageController {
	
	@Autowired
	private DailySalesStatsService dailySalesStatsService;
	
	@RequestMapping(value = "/statDate", method = RequestMethod.GET)
	public String entryCart(DailySalesStatsDTO dailySalesStatsDTO, Model model) {	
		
		System.out.println("[log] 기간별 매출통계 페이지 요청");
		
        // 현재 날짜 가져오기
        LocalDate today = LocalDate.now();
        // 현재 날짜에서 30일 빼기
        LocalDate thirtyDaysBefore = today.minusDays(30);
        
        // LocalDate를 java.sql.Date로 변환
        Date todaySQL = Date.valueOf(today);
        Date thirtyDaysBeforeSQL = Date.valueOf(thirtyDaysBefore);

        System.out.println("오늘 날짜 : " + todaySQL);
        System.out.println("30일 전 날짜 : " + thirtyDaysBeforeSQL);
        
    	
    	dailySalesStatsDTO.setSearchCondition("selectAdminStatDateDatas");
    	dailySalesStatsDTO.setAncStartDate(thirtyDaysBeforeSQL);
    	dailySalesStatsDTO.setAncEndDate(todaySQL);
    	dailySalesStatsDTO.setAncStartRow(0);
    	dailySalesStatsDTO.setAncSelectMax(10);
    	
    	System.out.println("todaySQL 자료형 : " + todaySQL.getClass());
    	System.out.println("thirtyDaysBeforeSQL 자료형 : " + todaySQL.getClass());
    	
    	List<DailySalesStatsDTO> dailySalesStats = dailySalesStatsService.selectAll(dailySalesStatsDTO);
    	
    	if(dailySalesStats.size() < 1) {
    		
    		System.out.println("[log] 30일 매출 불러오기 실패");
    		System.out.println("[log] 리스트에 담긴 index : " + dailySalesStats.size());
    		model.addAttribute("msg", "30일 매출 불러오기를 실패했습니다");
    		
    		return "admin/statDate";
    	}
    	
    	System.out.println("[log] 30일 매출 불러오기 성공");
    	System.out.println("[log] 리스트에 담긴 index : " + dailySalesStats.size());
    	for(int i = 0; i < dailySalesStats.size(); i++) {
    		System.out.println(dailySalesStats.get(i));
    	}
    	model.addAttribute("dailySalesStats", dailySalesStats);

		return "admin/statDate"; // 장바구니 페이지로 요청
	}
}
