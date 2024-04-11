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

import com.naeddoco.nsmwspring.model.monthlySalesStatsModel.MonthlySalesStatsDTO;
import com.naeddoco.nsmwspring.model.monthlySalesStatsModel.MonthlySalesStatsService;

import lombok.extern.slf4j.Slf4j;

// 월별 매출 통계
@Controller
@Slf4j
public class AncEntryStatMonthController {
	
	@Autowired
	private MonthlySalesStatsService monthlySalesStatsService;
	
	@RequestMapping(value = "/statDate/searchMonth", method = RequestMethod.GET)
	public @ResponseBody List<MonthlySalesStatsDTO> ancEntryStatMonthController(MonthlySalesStatsDTO monthlySalesStatsDTO,
											@RequestParam("startMonth") String ancStartMonth,
											@RequestParam("endMonth") String ancEndMonth) {	
		
		log.debug("[log] 월별 매출통계 요청");
		
        log.debug("입력된 검색 시작일 : " + ancStartMonth);
        log.debug("입력된 검색 종료일 : " + ancEndMonth);
        log.debug("startMonth 자료형	:" + ancStartMonth.getClass());
        log.debug("endMonth 자료형	:" + ancEndMonth.getClass());
        
        // 월 차이 계산을 위해 자료형 변경
        LocalDate locakStartMonth = LocalDate.parse(ancStartMonth + "-01");
        LocalDate locakEndMonth = LocalDate.parse(ancEndMonth + "-01");
        
        log.debug("변경된 검색 시작일 : " + locakStartMonth);
        log.debug("변경된 검색 종료일 : " + locakEndMonth);

        // 월차이 계산
        long months = ChronoUnit.MONTHS.between(locakStartMonth, locakEndMonth);
        
        log.debug("입력된 월 차이 : " + months);
        
        if(months > 12) {
        	
        	log.debug("[log] 입력된 날짜의 월 차이가 12 초과");
        	
        	return null;
        	
        }
        
        // DAO 쿼리문 실행을 위해 sql.Date로 자료형 변경
        Date sqlStartDate = Date.valueOf(locakStartMonth);
        Date sqlEndDate = Date.valueOf(locakEndMonth);
        
        monthlySalesStatsDTO.setSearchCondition("selectAdminStatDateDatas");
        monthlySalesStatsDTO.setAncStartMonth(sqlStartDate);
        monthlySalesStatsDTO.setAncEndMonth(sqlEndDate);
    	
    	List<MonthlySalesStatsDTO> monthlySalesStats = monthlySalesStatsService.selectAll(monthlySalesStatsDTO);
    	
    	if(monthlySalesStats.size() < 1) {
    		
    		log.debug("[log] 매출 불러오기 실패");
    		log.debug("[log] 리스트에 담긴 index : " + monthlySalesStats.size());
    		
    		return null;
    	}
    	
    	log.debug("[log] 매출 불러오기 성공");
    	log.debug("[log] 리스트에 담긴 index : " + monthlySalesStats.size());

		return monthlySalesStats;
	}
}
