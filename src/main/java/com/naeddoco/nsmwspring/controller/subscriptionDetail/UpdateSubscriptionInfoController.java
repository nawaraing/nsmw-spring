package com.naeddoco.nsmwspring.controller.subscriptionDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoDAO;
import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class UpdateSubscriptionInfoController {
	
	@Autowired
	private SubscriptionInfoDAO subscriptionInfoDAO;
	
	@RequestMapping(value = "subscriptionDetail/updateAddress", method = RequestMethod.GET)
	public String updateSubscriptionInfo(HttpSession session, 
										 Model model,
										 @RequestParam("subscriptionInfoID") int subscriptionInfoID) {
		
		// -----------------------------------------------세션 확인 ↓-----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에서 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시

			return "redirect:/"; // 메인 페이지로 강제 이동

		}
		
		SubscriptionInfoDTO subscriptionInfoDTO = new SubscriptionInfoDTO();
		
		subscriptionInfoDTO.setSearchCondition("updateSubscriptionShippingData");
		subscriptionInfoDTO.setSubscriptionInfoID(subscriptionInfoID);
		
		subscriptionInfoDAO.update(subscriptionInfoDTO);
		
		return "subscriptionDetail";
		
	}

}
