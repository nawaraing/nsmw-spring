package com.naeddoco.nsmwspring.controller.couponDownload;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.provisionDownloadCouponModel.ProvisionDownloadCouponDTO;
import com.naeddoco.nsmwspring.model.provisionDownloadCouponModel.ProvisionDownloadCouponService;

@Controller
public class UpdateDownloadCouponStopController {
	
	@Autowired
	private ProvisionDownloadCouponService provisionDownloadCouponService;
		
	@RequestMapping(value = "/couponDownload/update/couponDownloadStop", method = RequestMethod.GET)
	public String updateDownloadCouponStop(@RequestParam("couponDownloadID") List<Integer> couponDownloadID) {
		
		System.out.println("다운로드 쿠폰 배포 상태 업데이트");
		System.out.println("쿠폰ID : " + couponDownloadID.toString());
		
		int i = 0;
		
		boolean result = false;
		
		for(i = 0; i < couponDownloadID.size(); i++) {
			
			
			ProvisionDownloadCouponDTO provisionDownloadCouponDTO = new ProvisionDownloadCouponDTO();
			
			provisionDownloadCouponDTO.setSearchCondition("stopAdminCouponDownloadData");
			provisionDownloadCouponDTO.setProvisionDownloadCouponID(couponDownloadID.get(i));		
			
			result = provisionDownloadCouponService.update(provisionDownloadCouponDTO);
			
		}
		
		
		if(!result) {
			
			System.out.println("다운로드 쿠폰 배포 상태 변경 실패");
			
			return "admin/couponDownload";
			
		}
		
		System.out.println("다운로드 쿠폰 배포 상태 변경 성공");
		
		return "redirect:/couponDownload";		
	}	
}
