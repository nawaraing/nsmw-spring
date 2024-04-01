package com.naeddoco.nsmwspring.controller.buy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.cartModel.CartDTO;
import com.naeddoco.nsmwspring.model.memberModel.MemberDTO;
import com.naeddoco.nsmwspring.model.memberModel.MemberService;
import com.naeddoco.nsmwspring.model.subscriptionPolicyModel.SubscriptionPolicyDTO;
import com.naeddoco.nsmwspring.model.subscriptionPolicyModel.SubscriptionPolicyService;

import jakarta.servlet.http.HttpSession;

// 정기구독 버튼을 누를 시, 구매 페이지로 넘어가는 컨트롤러

@Controller
public class EntrySubscriptionBuyPageController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private SubscriptionPolicyService subscriptionPolicyService;

	@RequestMapping(value = "buy/subscription", method = RequestMethod.POST)
	public String entryBuy(HttpSession session, 
			               Model model,
						   @RequestParam(name = "imagePath[]") List<String> imagePath,
						   @RequestParam(name = "productName[]") List<String> productName,
						   @RequestParam(name = "salePrice[]") List<Integer> salePrice,
						   @RequestParam(name = "productQuantity[]") List<Integer> productQuantity,
						   @RequestParam(name = "productID[]") List<Integer> productID,
						   @RequestParam(name = "cartID[]") List<Integer> cartID,
						   @RequestParam(name = "category[]") List<String> category) {

		// -----------------------------------------------세션 확인 ↓-----------------------------------------------

		String memberID = (String) session.getAttribute("memberID"); // 세션에서 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시

			return "redirect:/"; // 메인 페이지로 강제 이동

		}

		//-----------------------------------------------멤버 정보 습득 ↓-----------------------------------------------
		
		MemberDTO memberDTO = new MemberDTO();
		
		memberDTO.setSearchCondition("selectMemberInfo"); // 검색 조건 set
		
		memberDTO.setMemberID(memberID); // DTO에 아이디 데이터 set
		
		memberDTO = memberService.selectOne(memberDTO); // selectOne 실행 후 반환 값을 변수에 저장
		
		model.addAttribute("memberInfo", memberDTO); // 모델에 DTO 데이터 저장
		
		//-----------------------------------------------상품 정보 습득 ↓-----------------------------------------------
		
		List<CartDTO> buyProducts = new ArrayList<>(); // 구매 상품 정보들을 넣을 리스트 선언

		for (int i = 0; i < imagePath.size(); i++) { // 뷰에서 온 데이터 리스트의 길이만큼 순회

			CartDTO cartDTO = new CartDTO(); // 카트 DTO 객체 생성
			
			cartDTO.setAncImagePath(imagePath.get(i)); // DTO에 이미지 경로 데이터를 set
			cartDTO.setAncProductName(productName.get(i)); // DTO에 상품 이름 데이터 set
			cartDTO.setAncSalePrice(salePrice.get(i)); // DTO에 상품 가격 데이터 set
			cartDTO.setProductQuantity(productQuantity.get(i)); // DTO에 상품 수량 데이터 set
			cartDTO.setProductID(productID.get(i)); // DTO에 상품 PK 데이터 set
			cartDTO.setAncCategory(category.get(i)); // DTO 카테고리 데이터 set
			
			buyProducts.add(cartDTO); // 리스트에 DTO 추가
			
		}
		
		model.addAttribute("buyProducts", buyProducts); // 모델에 리스트 데이터 저장
		
		//----------------------------------------------- 구독 정책 정보 습득 ↓-----------------------------------------------
		
		SubscriptionPolicyDTO subscriptionPolicyDTO = new SubscriptionPolicyDTO();
		
		List<SubscriptionPolicyDTO> subscriptionPolicyDTOList = subscriptionPolicyService.selectAll(subscriptionPolicyDTO);
		
		model.addAttribute("subscriptionPolicy", subscriptionPolicyDTOList);
		

		return "user/subscriptionBuy";

	}

}