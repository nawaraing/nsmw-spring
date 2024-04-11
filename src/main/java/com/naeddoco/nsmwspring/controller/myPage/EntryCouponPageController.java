package com.naeddoco.nsmwspring.controller.myPage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.memberCouponModel.MemberCouponDTO;
import com.naeddoco.nsmwspring.model.memberCouponModel.MemberCouponService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EntryCouponPageController {
	
	@Autowired
	private MemberCouponService memberCouponService;
	
	@RequestMapping(value = "/user/couponInfo", method = RequestMethod.GET)
	public String entryCouponPageController(MemberCouponDTO memberCouponDTO, Model model, HttpSession session) {
		
		String memberID = (String) session.getAttribute("memberID");
		
		// 회원이 로그인 상태가 아니라면 false 반환
		if (memberID == null) {
			
			log.debug("[log] InsertCart 로그아웃상태");
			
			return "redirect:/";
		}
				
		memberCouponDTO.setMemberID(memberID);
		
		memberCouponDTO.setSearchCondition("selectAllMyCoupon");
		
		List<MemberCouponDTO> myCouponList= memberCouponService.selectAll(memberCouponDTO);
		
		log.debug("내 쿠폰 목록 : " + myCouponList);
		
		model.addAttribute("myCouponList", myCouponList);
		
		model.addAttribute("pageValue", "쿠폰 관리");
		
		return "/user/couponInfo";
	}

}
