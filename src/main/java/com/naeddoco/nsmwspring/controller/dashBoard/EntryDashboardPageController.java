package com.naeddoco.nsmwspring.controller.dashBoard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.naeddoco.nsmwspring.model.dailySalesStatsModel.DailySalesStatsDTO;
import com.naeddoco.nsmwspring.model.dailySalesStatsModel.DailySalesStatsService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EntryDashboardPageController {
	
	@Autowired
	private DailySalesStatsService dailySalesStatsService;
	
	@GetMapping("/dashboard")
	public String dashboardPage(DailySalesStatsDTO dailySalesStatsDTO, Model model, HttpSession session) {
		
		String authority = (String) session.getAttribute("authority");
		
		// 회원이 로그인 상태가 아니라면 false 반환
		if (!authority.equals("ADMIN")) {
			
			log.debug("회원 등급이 관리자(ADMIN)가 아니여서 메인페이지로 이동");
			
			return "redirect:/";
		}	
		
		log.debug("[log] Controller dashboardPage요청");
		
		dailySalesStatsDTO.setSearchCondition("selectDashboardDatas");
		
		List<DailySalesStatsDTO> dailySalesStatsList = dailySalesStatsService.selectAll(dailySalesStatsDTO);
		
		dailySalesStatsList = dailySalesStatsService.selectAll(dailySalesStatsDTO);
		
		model.addAttribute("dashboardDailySalesStats", dailySalesStatsList);
		
		return "admin/dashboard";
	}
}