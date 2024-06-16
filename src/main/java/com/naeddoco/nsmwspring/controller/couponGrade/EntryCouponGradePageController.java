package com.naeddoco.nsmwspring.controller.couponGrade;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.categoryModel.CategoryDTO;
import com.naeddoco.nsmwspring.model.couponModel.CouponDTO;
import com.naeddoco.nsmwspring.model.gradeModel.GradeDTO;
import com.naeddoco.nsmwspring.model.provisionGradeCouponModel.ProvisionGradeCouponDTO;

@Controller
public class EntryCouponGradePageController {
	
	@RequestMapping(value = "/couponGrade", method = RequestMethod.GET)
	public String entryCart(Model model) {
		
		// 테이블 각 row 정보
		List<CouponDTO> coupons = new ArrayList<CouponDTO>();
		List<ProvisionGradeCouponDTO> gradeCoupons = new ArrayList<ProvisionGradeCouponDTO>();
		
		// 전체 등급, 카테고리 정보
		List<GradeDTO> allGrades = new ArrayList<GradeDTO>();
		List<CategoryDTO> allCategories = new ArrayList<CategoryDTO>();
		
		for (int i = 0; i < 13; i++) {
			CouponDTO coupon = new CouponDTO();
			List<CategoryDTO> categories = new ArrayList<CategoryDTO>();
			ProvisionGradeCouponDTO gradeCoupon = new ProvisionGradeCouponDTO();
			
			coupon.setCouponID(1000 + i);
			coupon.setCouponName("Test Coupon" + i);
			coupon.setExpirationDate(new Timestamp(new Date().getTime()));
			// 쿠폰 카테고리 ID들..
			CategoryDTO category1 = new CategoryDTO();
			CategoryDTO category2 = new CategoryDTO();
			CategoryDTO category3 = new CategoryDTO();
			category1.setCategoryID(i % 8 + 1);
			category2.setCategoryID((i + 1) % 8 + 1);
			category3.setCategoryID((i + 2) % 8 + 1);
			categories.add(category1);
			categories.add(category2);
			categories.add(category3);
			model.addAttribute("categories" + i, categories);
			
			if (i % 2 == 1) {
				coupon.setCouponType("WON");
				coupon.setAncDiscountAmount(2000);
				coupon.setAncMinOrderAmount(40000);
			} else {
				coupon.setCouponType("PERCENTAGE");
				coupon.setAncDiscountRate(5);
				coupon.setAncMaxDiscountAmount(2000);
			}
			// 지급 주기 deployCycle
			// 지급 기준 deployBase
			// 회원 등급 gradeID
			if (i % 2 == 1) {
				gradeCoupon.setDeployCycle("MONTH");
				gradeCoupon.setDeployBase("4");
				gradeCoupon.setGradeID(i % 2 + 1);
			} else {
				gradeCoupon.setDeployCycle("WEEK");
				gradeCoupon.setDeployBase("WED");
				gradeCoupon.setGradeID(i % 2 + 1);
			}
			
			coupons.add(coupon);
			gradeCoupons.add(gradeCoupon);
		}
		
		// 전체 카테고리 IDs names
		// 전체 등급 IDs names
		CategoryDTO c1 = new CategoryDTO();
		CategoryDTO c2 = new CategoryDTO();
		CategoryDTO c3 = new CategoryDTO();
		CategoryDTO c4 = new CategoryDTO();
		CategoryDTO c5 = new CategoryDTO();
		CategoryDTO c6 = new CategoryDTO();
		CategoryDTO c7 = new CategoryDTO();
		CategoryDTO c8 = new CategoryDTO();
		c1.setCategoryID(1);
		c2.setCategoryID(2);
		c3.setCategoryID(3);
		c4.setCategoryID(4);
		c5.setCategoryID(5);
		c6.setCategoryID(6);
		c7.setCategoryID(7);
		c8.setCategoryID(8);
		c1.setCategoryName("뼈/치아");
		c2.setCategoryName("간");
		c3.setCategoryName("눈");
		c4.setCategoryName("활력");
		c5.setCategoryName("면역");
		c6.setCategoryName("뇌");
		c7.setCategoryName("피부");
		c8.setCategoryName("소화");
		allCategories.add(c1);
		allCategories.add(c2);
		allCategories.add(c3);
		allCategories.add(c4);
		allCategories.add(c5);
		allCategories.add(c6);
		allCategories.add(c7);
		allCategories.add(c8);
		
		GradeDTO g1 = new GradeDTO();
		GradeDTO g2 = new GradeDTO();
		GradeDTO g3 = new GradeDTO();
		g1.setGradeID(1);
		g2.setGradeID(2);
		g3.setGradeID(3);
		g1.setGradeName("브론즈");
		g2.setGradeName("실버");
		g3.setGradeName("골드");
		allGrades.add(g1);
		allGrades.add(g2);
		allGrades.add(g3);
		
    	model.addAttribute("coupons", coupons);
    	model.addAttribute("gradeCoupons", gradeCoupons);
    	model.addAttribute("allGrades", allGrades);
    	model.addAttribute("allCategories", allCategories);
		
		return "admin/couponGrade"; // 장바구니 페이지로 요청

	}

}
