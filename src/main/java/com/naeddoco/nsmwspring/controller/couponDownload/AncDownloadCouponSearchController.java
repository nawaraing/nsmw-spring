package com.naeddoco.nsmwspring.controller.couponDownload;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naeddoco.nsmwspring.model.couponModel.CouponDTO;
import com.naeddoco.nsmwspring.model.couponModel.CouponService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AncDownloadCouponSearchController {
	
	@Autowired
	private CouponService couponService;
	
	@RequestMapping(value = "/couponDownload/searchAndSort", method = RequestMethod.GET)
	public @ResponseBody List<CouponDTO> ancDownloadCouponSearch(@RequestParam("searchKeyword") String searchKeyword,
																@RequestParam("sortColumnName") String sortColumnName) {
		
		log.debug("다운로드 쿠폰 비동기 검색");
		log.debug("다운로드 쿠폰 입력된 키워드 : " + searchKeyword);
		log.debug("다운로드 쿠폰 입력된 정렬기준 : " + sortColumnName);
		
		CouponDTO couponDTO = new CouponDTO();
		
		couponDTO.setSearchCondition("selectAllSearchAndSortDownload");
		couponDTO.setAncSearchKeyword(searchKeyword);
		couponDTO.setAncSortColumnName(sortColumnName);
		
		List<CouponDTO> couponList = couponService.selectAll(couponDTO);
		
		if(couponList == null) {
			
			log.debug("비동기 검색 실패");
			
			return couponList;			
		}
		
		for(CouponDTO datas : couponList ) {
			
			log.debug("비동기 검색으로 인한 결과 : " + datas.toString());
			
		}
		
		return couponList;
	}
}
