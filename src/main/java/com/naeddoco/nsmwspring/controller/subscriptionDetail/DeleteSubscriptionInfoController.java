package com.naeddoco.nsmwspring.controller.subscriptionDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoService;
import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoDTO;
import com.naeddoco.nsmwspring.model.subscriptionInfoProductModel.SubscriptionInfoProductService;
import com.naeddoco.nsmwspring.model.subscriptionInfoProductModel.SubscriptionInfoProductDTO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

// 사용자가 구독을 삭제하는 컨트롤러

@Controller
@Slf4j
public class DeleteSubscriptionInfoController {
	
	@Autowired
	private SubscriptionInfoService subscriptionInfoService;
	@Autowired
	private SubscriptionInfoProductService subscriptionInfoProductService;
	
	@RequestMapping(value = "subscriptionDetail/delete", method = RequestMethod.GET)
	public String updateSubscriptionInfo(HttpSession session, 
										 Model model,
										 @RequestParam("subscriptionInfoID") int subscriptionInfoID) {
		
		log.debug("구독 취소 Controller");
		log.debug("구독 PK : " + subscriptionInfoID);
		
		// -----------------------------------------------세션 확인 ↓-----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에서 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시
			
			log.debug("세션 만료된 ID");

			return "redirect:/"; // 메인 페이지로 강제 이동

		}
		
		// -----------------------------------------------구독 정보 데이터 삭제 ↓-----------------------------------------------
		
		SubscriptionInfoDTO subscriptionInfoDTO = new SubscriptionInfoDTO();
		
		subscriptionInfoDTO.setSearchCondition("deleteSubscriptionData");
		subscriptionInfoDTO.setSubscriptionInfoID(subscriptionInfoID);
		
		boolean deleteSubscriptionInfoResult = subscriptionInfoService.delete(subscriptionInfoDTO);
		
		if(!deleteSubscriptionInfoResult) {
			
			log.debug("구독 삭제 실패");
			
		}
		
		log.debug("구독 삭제 성공");
		
		// -----------------------------------------------구독 상품 데이터 삭제 ↓-----------------------------------------------
		
		SubscriptionInfoProductDTO subscriptionInfoProductDTO = new SubscriptionInfoProductDTO();
		
		subscriptionInfoProductDTO.setSearchCondition("deleteSubscriptionData");
		subscriptionInfoProductDTO.setSubscriptionInfoID(subscriptionInfoID);
		
		boolean deleteSubscriptionProductResult = subscriptionInfoProductService.delete(subscriptionInfoProductDTO);
		
		if(!deleteSubscriptionProductResult) {
			
			log.debug("구독 상세 삭제 실패");
			
		}
		
		log.debug("구독 상세 삭제 성공");
		
		return "redirect:/subscriptionDetail";
		
	}

}
