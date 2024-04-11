package com.naeddoco.nsmwspring.controller.couponDownload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.memberCouponModel.MemberCouponDTO;
import com.naeddoco.nsmwspring.model.memberCouponModel.MemberCouponService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class GiveDownCouponController {
	
	@Autowired
	private MemberCouponService memberCouponService;
	
	@RequestMapping(value = "/couponDownload/give", method = RequestMethod.GET)
	public String giveDownCoupon (MemberCouponDTO memberCouponDTO, HttpSession session) {
	
		log.debug("다운로드 쿠폰 지급");
		
		String memberID = (String) session.getAttribute("memberID");
		
		log.debug("memberID : " + memberID);
		log.debug("couponID : " + memberCouponDTO.getCouponID());
		
		if(memberID == null) {
			
			log.debug("비로그인 상태");
			
			return "redirect:/login";
		}
		
		//memberCouponDTO.setSearchCondition();
		memberCouponDTO.setMemberID(memberID);
		
		boolean memberGiveCouponResult = memberCouponService.insert(memberCouponDTO);
		
		if(!memberGiveCouponResult) {
			
			log.debug("다운로드 쿠폰 Give 실패");
			
			return "redirect:/";
			
		}
		
		log.debug("다운로드 쿠폰 Give 성공");
		
		return "redirect:/user/couponInfo";
	}
}
