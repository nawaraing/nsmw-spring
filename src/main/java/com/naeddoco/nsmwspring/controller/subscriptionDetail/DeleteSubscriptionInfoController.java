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
public class DeleteSubscriptionInfoController {
	
	@Autowired
	private SubscriptionInfoDAO subscriptionInfoDAO;
	
	@RequestMapping(value = "subscriptionDetail/delete", method = RequestMethod.GET)
	public String updateSubscriptionInfo(HttpSession session, Model model, SubscriptionInfoDTO subscriptionInfoDTO,
										 @RequestParam("subscriptionInfoID") int subscriptionInfoID) {
		
		subscriptionInfoDTO.setSearchCondition("deleteSubscriptionData");
		subscriptionInfoDTO.setSubscriptionInfoID(subscriptionInfoID);
		
		subscriptionInfoDAO.delete(subscriptionInfoDTO);
		
		return "subscriptionDetail";
		
	}

}
