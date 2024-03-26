package com.naeddoco.nsmwspring.controller.subscriptionDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoDAO;
import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoDTO;
import com.naeddoco.nsmwspring.model.subscriptionInfoProductModel.SubscriptionInfoProductDAO;
import com.naeddoco.nsmwspring.model.subscriptionInfoProductModel.SubscriptionInfoProductDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class DeleteSubscriptionInfoController {
	
	@Autowired
	private SubscriptionInfoDAO subscriptionInfoDAO;
	@Autowired
	private SubscriptionInfoProductDAO subscriptionInfoProductDAO;
	
	@RequestMapping(value = "subscriptionDetail/delete", method = RequestMethod.GET)
	public String updateSubscriptionInfo(HttpSession session, Model model, SubscriptionInfoDTO subscriptionInfoDTO,
										 SubscriptionInfoProductDTO subscriptionInfoProductDTO,
										 @RequestParam("subscriptionInfoID") int subscriptionInfoID) {
		
		// -----------------------------------------------세션 확인 ↓-----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에서 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시

			return "redirect:/"; // 메인 페이지로 강제 이동

		}
		
		// -----------------------------------------------구독 정보 데이터 삭제 ↓-----------------------------------------------
		
		subscriptionInfoDTO.setSearchCondition("deleteSubscriptionData");
		subscriptionInfoDTO.setSubscriptionInfoID(subscriptionInfoID);
		
		subscriptionInfoDAO.delete(subscriptionInfoDTO);
		
		// -----------------------------------------------구독 상품 데이터 삭제 ↓-----------------------------------------------
		
		subscriptionInfoProductDTO.setSearchCondition("deleteSubscriptionData");
		subscriptionInfoProductDTO.setSubscriptionInfoID(subscriptionInfoID);
		
		subscriptionInfoProductDAO.delete(subscriptionInfoProductDTO);
		
		return "subscriptionDetail";
		
	}

}
