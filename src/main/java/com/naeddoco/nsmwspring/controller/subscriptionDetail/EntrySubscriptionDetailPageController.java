package com.naeddoco.nsmwspring.controller.subscriptionDetail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;
import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoService;
import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoDTO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

// 사용자의 구독 정보 페이지에 이동하는 컨트롤러

@Controller
@Slf4j
public class EntrySubscriptionDetailPageController {

	@Autowired
	private SubscriptionInfoService subscriptionService;

	@RequestMapping(value = "subscriptionDetail", method = RequestMethod.GET)
	public String entrySubscriptionDetail(HttpSession session, Model model) {
		
		log.debug("[페이지 이동] 마이페이지 → 구독내역");

		// -----------------------------------------------세션 확인 ↓-----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에서 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시
			
			log.debug("로그아웃 상태");

			return "redirect:/"; // 메인 페이지로 강제 이동

		}
		
		// -----------------------------------------------구독 정보 습득-----------------------------------------------
		
		SubscriptionInfoDTO subscriptionDTO = new SubscriptionInfoDTO();
		
		subscriptionDTO.setSearchCondition("selectSubscriptionDatas");
		subscriptionDTO.setMemberID(memberID);
		
		List<SubscriptionInfoDTO> subscriptionDTOList = subscriptionService.selectAll(subscriptionDTO);
		
		if(subscriptionDTOList == null) {
			
			log.debug("구독 상품 없음");
			
		}
		
		log.debug("구독 상품 selectAll 성공 : " + subscriptionDTOList);
		
		model.addAttribute("subscriptionInfos", subscriptionDTOList);
		
		model.addAttribute("pageValue", "구독내역");
		
		return "user/subscriptionInfo";

	}

}