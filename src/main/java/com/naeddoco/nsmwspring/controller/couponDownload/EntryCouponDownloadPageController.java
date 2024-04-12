package com.naeddoco.nsmwspring.controller.couponDownload;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.categoryModel.CategoryDTO;
import com.naeddoco.nsmwspring.model.categoryModel.CategoryService;
import com.naeddoco.nsmwspring.model.couponModel.CouponDTO;
import com.naeddoco.nsmwspring.model.couponModel.CouponService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EntryCouponDownloadPageController {
	
	@Autowired
	private CouponService couponService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/couponDownload", method = RequestMethod.GET)
	public String entryCouponDownloadPage(CouponDTO couponDTO, CategoryDTO categoryDTO, Model model, HttpSession session) {
		
		String authority = (String) session.getAttribute("authority");
		
		// 회원이 로그인 상태가 아니라면 false 반환
		if (!authority.equals("ADMIN")) {
			
			log.debug("회원 등급이 관리자(ADMIN)가 아니여서 메인페이지로 이동");
			
			return "redirect:/";
		}	

		couponDTO.setSearchCondition("selectAllDownloadCouponInfo");
		
		List<CouponDTO> downloadCouponList = couponService.selectAll(couponDTO);

		model.addAttribute("couponList", downloadCouponList);
		
		List<CategoryDTO> categoryList = categoryService.selectAll(categoryDTO);
		
		model.addAttribute("categoryList", categoryList);
		
		// 다운로드 쿠폰 관리 페이지 요청
		return "admin/couponDownload";

	}

}
