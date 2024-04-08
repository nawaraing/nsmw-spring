package com.naeddoco.nsmwspring.controller.couponDownload;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.provisionDownloadCouponModel.ProvisionDownloadCouponDTO;
import com.naeddoco.nsmwspring.model.provisionDownloadCouponModel.ProvisionDownloadCouponService;

@Controller
public class UpdateDownloadCouponController {
	
	@Autowired
	private ProvisionDownloadCouponService provisionDownloadCouponService;
		
	@RequestMapping(value = "/couponDownload/update", method = RequestMethod.GET)
	public String updateDownloadCoupon(@RequestParam("couponDownloadID") List<Integer> couponDownloadID,
										@RequestParam("ancDeployDeadline") List<String> ancDeployDeadline) {
		
		System.out.println("다운로드 쿠폰 배포 만료일 업데이트");
		System.out.println("쿠폰ID : " + couponDownloadID.toString());
		System.out.println("쿠폰 마감일 : " + ancDeployDeadline.toString());
		
		int i = 0;
		
		boolean result = false;
		
		for(i = 0; i < couponDownloadID.size(); i++) {
			
			// 배포 만료일
			Timestamp deployDeadline = null;

			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

				java.util.Date utilexpirationDate = dateFormat.parse(ancDeployDeadline.get(i));

				deployDeadline = new Timestamp(utilexpirationDate.getTime());

			} catch (ParseException e) {
				System.out.println("Timestamp 변환 실패: " + e.getMessage());
			}
			
			ProvisionDownloadCouponDTO provisionDownloadCouponDTO = new ProvisionDownloadCouponDTO();
			
			provisionDownloadCouponDTO.setSearchCondition("updateAdminCouponDownloadData");
			provisionDownloadCouponDTO.setProvisionDownloadCouponID(couponDownloadID.get(i));
			provisionDownloadCouponDTO.setDeployDeadline(deployDeadline);
			
			
			result = provisionDownloadCouponService.update(provisionDownloadCouponDTO);
			
		}
		
		
		if(!result) {
			
			System.out.println("다운로드 쿠폰 배포 만료일 변경 실패");
			
			return "admin/couponDownload";
			
		}
		
		System.out.println("다운로드 쿠폰 배포 만료일 변경 성공");
		
		return "redirect:/couponDownload";		
	}	
}
