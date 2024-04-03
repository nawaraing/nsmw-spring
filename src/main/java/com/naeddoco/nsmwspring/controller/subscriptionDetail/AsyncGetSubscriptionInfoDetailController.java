package com.naeddoco.nsmwspring.controller.subscriptionDetail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naeddoco.nsmwspring.model.subscriptionInfoProductModel.SubscriptionInfoProductDAO;
import com.naeddoco.nsmwspring.model.subscriptionInfoProductModel.SubscriptionInfoProductDTO;

// 구독 리스트 클릭시, 상세 정보를 제공하는 컨트롤러

@Controller
@ResponseBody
public class AsyncGetSubscriptionInfoDetailController {
	
	@Autowired
	SubscriptionInfoProductDAO subscriptionInfoProductDAO = new SubscriptionInfoProductDAO();
	
	@RequestMapping(value = "subscriptionDetail/detail", method = RequestMethod.GET)
	public List<SubscriptionInfoProductDTO> getSubscriptionInfoDetail(Model model,
															   	      @RequestParam("subscriptionInfoID") int subscriptionInfoID) {
		
		System.out.println("구독 내역 상세 비동기 요청");
		System.out.println("구독 내역 상세 비동기의 구독 PK : " + subscriptionInfoID);
		SubscriptionInfoProductDTO subscriptionProductDTO = new SubscriptionInfoProductDTO();
		
		subscriptionProductDTO.setSearchCondition("selectSubscriptionDatas");
		subscriptionProductDTO.setSubscriptionInfoID(subscriptionInfoID);
		
		List<SubscriptionInfoProductDTO> subscriptionInfoProductList = subscriptionInfoProductDAO.selectAll(subscriptionProductDTO);
		
		System.out.println("구독 내역 상세 비동기의 반환값 : " + subscriptionInfoProductList);
		
		return subscriptionInfoProductList;		
	}
}
