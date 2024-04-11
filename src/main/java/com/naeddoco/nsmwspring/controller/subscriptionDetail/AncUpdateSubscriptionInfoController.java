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
import lombok.extern.slf4j.Slf4j;

// 구독 정보를 업데이트 컨트롤러

@Controller
@Slf4j
public class AncUpdateSubscriptionInfoController {
	
	@Autowired
	private SubscriptionInfoService subscriptionInfoService;
	
	@RequestMapping(value = "subscriptionDetail/updateAddress", method = RequestMethod.POST)
	public @ResponseBody String ancUpdateSubscriptionInfo (HttpSession session, SubscriptionInfoDTO subscriptionInfoDTO) {
		
		log.debug("[비동기] 구독 배송지 변경 요청 진입");
		
		// -----------------------------------------------세션 확인 ↓-----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에서 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시
			
			log.debug("로그아웃 상태");

			return "redirect:/"; // 메인 페이지로 강제 이동

		}
		
		log.debug("구독 배송지 변경 컨트롤러의 구독 PK : " + subscriptionInfoDTO.getSubscriptionInfoID());
		log.debug("구독 배송지 변경 컨트롤러의 우편변호 : " + subscriptionInfoDTO.getSubscriptionPostCode());
		log.debug("구독 배송지 변경 컨트롤러의 주   소 : " + subscriptionInfoDTO.getSubscriptionAddress());
		log.debug("구독 배송지 변경 컨트롤러의 상세주소 : " + subscriptionInfoDTO.getSubscriptionDetailAddress());
		
		subscriptionInfoDTO.setSearchCondition("updateSubscriptionShippingData");
		
		boolean result = subscriptionInfoService.update(subscriptionInfoDTO);
		
		if(!result) {
			
			log.debug("구독 배송지 변경 실패");
			
			return "fail";
		}
		
		log.debug("구독 배송지 변경 성공");
		
		return "suc";		
	}

}
