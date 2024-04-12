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

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EntryStatDatePageController {
	
	@Autowired
	private DailySalesStatsService dailySalesStatsService;
	
	@RequestMapping(value = "/statDate", method = RequestMethod.GET)
	public String entryStatDatePageController(DailySalesStatsDTO dailySalesStatsDTO, Model model, HttpSession session) {	
		
		String authority = (String) session.getAttribute("authority");
		
		// 회원이 로그인 상태가 아니라면 false 반환
		if (!authority.equals("ADMIN")) {
			
			log.debug("회원 등급이 관리자(ADMIN)가 아니여서 메인페이지로 이동");
			
			return "redirect:/";
		}	
		
		log.debug("[log] 기간별 매출통계 페이지 요청");
		
        // 현재 날짜 가져오기
        LocalDate today = LocalDate.now();
        // 현재 날짜에서 30일 빼기
        LocalDate thirtyDaysBefore = today.minusDays(30);
        
        // LocalDate를 java.sql.Date로 변환
        Date todaySQL = Date.valueOf(today);
        Date thirtyDaysBeforeSQL = Date.valueOf(thirtyDaysBefore);

        log.debug("오늘 날짜 : " + todaySQL);
        log.debug("30일 전 날짜 : " + thirtyDaysBeforeSQL);        
    	
    	dailySalesStatsDTO.setSearchCondition("selectAdminStatDateDatas");
    	dailySalesStatsDTO.setAncStartDate(thirtyDaysBeforeSQL);
    	dailySalesStatsDTO.setAncEndDate(todaySQL);
    	
    	log.debug("todaySQL 자료형 : " + todaySQL.getClass());
    	log.debug("thirtyDaysBeforeSQL 자료형 : " + todaySQL.getClass());
    	
    	List<DailySalesStatsDTO> dailySalesStats = dailySalesStatsService.selectAll(dailySalesStatsDTO);
    	
    	if(dailySalesStats.size() < 1) {
    		
    		log.debug("[log] 30일 매출 불러오기 실패");
    		log.debug("[log] 리스트에 담긴 index : " + dailySalesStats.size());
    		
    		model.addAttribute("msg", "30일 매출 불러오기를 실패했습니다");
    		
    		return "admin/statDate";
    	}
    	
    	log.debug("[log] 30일 매출 불러오기 성공");
    	log.debug("[log] 리스트에 담긴 index : " + dailySalesStats.size());
    	
    	for(int i = 0; i < dailySalesStats.size(); i++) {
    		
    		log.debug(dailySalesStats.get(i).toString());
    		
    	}
    	
    	model.addAttribute("dailySalesStats", dailySalesStats);

		return "admin/statDate"; // 장바구니 페이지로 요청
	}
}
