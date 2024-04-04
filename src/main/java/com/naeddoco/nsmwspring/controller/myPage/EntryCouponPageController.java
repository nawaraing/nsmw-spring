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

@Controller
public class EntryCouponPageController {
	
	@Autowired
	private MemberCouponService memberCouponService;
	
	@RequestMapping(value = "/user/couponInfo", method = RequestMethod.GET)
	public String entryCouponPageController(MemberCouponDTO memberCouponDTO, Model model, HttpSession session) {
		
		String memberID = (String) session.getAttribute("memberID");
		
		memberCouponDTO.setMemberID(memberID);
		
		memberCouponDTO.setSearchCondition("selectAllMyCoupon");
		
		List<MemberCouponDTO> myCouponList= memberCouponService.selectAll(memberCouponDTO);
		
		System.out.println("내 쿠폰 목록 : " + myCouponList);
		
		model.addAttribute("myCouponList", myCouponList);
		
		model.addAttribute("pageValue", "쿠폰 관리");
		
		return "/user/couponInfo";
	}

}
