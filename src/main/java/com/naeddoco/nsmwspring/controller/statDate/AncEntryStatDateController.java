package com.naeddoco.nsmwspring.controller.statDate;

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

import com.naeddoco.nsmwspring.model.dailySalesStatsModel.DailySalesStatsDTO;
import com.naeddoco.nsmwspring.model.dailySalesStatsModel.DailySalesStatsService;

import lombok.extern.slf4j.Slf4j;

// 일별 매출 통계
@Controller
@Slf4j
public class AncEntryStatDateController {
	
	@Autowired
	private DailySalesStatsService dailySalesStatsService;
	
	@RequestMapping(value = "/statDate/searchDate", method = RequestMethod.GET)
	public @ResponseBody List<DailySalesStatsDTO> ancEntryStatDateController(DailySalesStatsDTO dailySalesStatsDTO,
											@RequestParam("startDate") Date ancStartDate,
											@RequestParam("endDate") Date ancEndDate) {	
		
		log.debug("[log] 일별 매출통계 요청");
		
        log.debug("입력된 검색 시작일 : " + ancStartDate);
        log.debug("입력된 검색 종료일 : " + ancEndDate);
        log.debug("startDate 자료형	:" + ancStartDate.getClass());
        log.debug("endDate 자료형	:" + ancEndDate.getClass());
   
        // 날짜 연산을 위해 자료형 변경
        LocalDate localStartDate = ancStartDate.toLocalDate();
        LocalDate localEndDate = ancEndDate.toLocalDate();
        
        // 일 차이 계간
        long days = ChronoUnit.DAYS.between(localStartDate, localEndDate);
    	
        log.debug("입력된 일수 차이 : " + days);
        
        if(days > 30) {
        	
        	log.debug("[log] 입력된 날짜의 일수 차이가 30일 초과");
        	
        	return null;
        	
        }
        
    	dailySalesStatsDTO.setSearchCondition("selectAdminStatDateDatas");
    	dailySalesStatsDTO.setAncStartDate(ancStartDate);
    	dailySalesStatsDTO.setAncEndDate(ancEndDate);
    	
    	List<DailySalesStatsDTO> dailySalesStats = dailySalesStatsService.selectAll(dailySalesStatsDTO);
    	
    	if(dailySalesStats.size() < 1) {
    		
    		log.debug("[log] 매출 불러오기 실패");
    		log.debug("[log] 리스트에 담긴 index : " + dailySalesStats.size());
    		
    		return null;
    	}
    	
    	log.debug("[log] 매출 불러오기 성공");
    	log.debug("[log] 리스트에 담긴 index : " + dailySalesStats.size());

		return dailySalesStats;
	}
}
