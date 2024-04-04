package com.naeddoco.nsmwspring.controller.subscriptionDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoService;
import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoDTO;

import jakarta.servlet.http.HttpSession;

// 구독 정보를 업데이트 컨트롤러

@Controller
public class UpdateSubscriptionInfoController {
	
	@Autowired
	private SubscriptionInfoService subscriptionInfoService;
	
	@RequestMapping(value = "subscriptionDetail/updateAddress", method = RequestMethod.POST)
	public @ResponseBody String updateSubscriptionInfo(HttpSession session, SubscriptionInfoDTO subscriptionInfoDTO) {
		
		System.out.println("구독 배송지 변경 요청 진입");
		
		// -----------------------------------------------세션 확인 ↓-----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에서 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시

			return "redirect:/"; // 메인 페이지로 강제 이동

		}
		
		System.out.println("구독 배송지 변경 컨트롤러의 구독 PK : " + subscriptionInfoDTO.getSubscriptionInfoID());
		System.out.println("구독 배송지 변경 컨트롤러의 우편변호 : " + subscriptionInfoDTO.getSubscriptionPostCode());
		System.out.println("구독 배송지 변경 컨트롤러의 주   소 : " + subscriptionInfoDTO.getSubscriptionAddress());
		System.out.println("구독 배송지 변경 컨트롤러의 상세주소 : " + subscriptionInfoDTO.getSubscriptionDetailAddress());
		
		subscriptionInfoDTO.setSearchCondition("updateSubscriptionShippingData");
		
		boolean result = subscriptionInfoService.update(subscriptionInfoDTO);
		
		if(!result) {
			System.out.println("구독 배송지 변경 실패");
			
			return "fail";
		}
		
		System.out.println("구독 배송지 변경 성공");
		return "suc";
		
	}

}
