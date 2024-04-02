package com.naeddoco.nsmwspring.controller.buy;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naeddoco.nsmwspring.model.cartModel.CartDTO;
import com.naeddoco.nsmwspring.model.cartModel.CartService;
import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoDTO;
import com.naeddoco.nsmwspring.model.subscriptionInfoModel.SubscriptionInfoService;
import com.naeddoco.nsmwspring.model.subscriptionInfoProductModel.SubscriptionInfoProductDTO;
import com.naeddoco.nsmwspring.model.subscriptionInfoProductModel.SubscriptionInfoProductService;
import com.naeddoco.nsmwspring.model.subscriptionPolicyModel.SubscriptionPolicyDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class EntrySubscriptionCompPageController {

	@Autowired
	private SubscriptionInfoService subscriptionInfoService;
	@Autowired
	private SubscriptionInfoProductService subscriptionInfoProductService;
	@Autowired
	private CartService cartService;
	
	@RequestMapping(value = "/subscriptionCompPage", method = RequestMethod.POST)
	public String entrySubscriptionCompPageController(SubscriptionInfoDTO subscriptionInfoDTO, SubscriptionInfoProductDTO subscriptionInfoProductDTO, 
													 SubscriptionPolicyDTO subscriptionPolicyDTO , CartDTO cartDTO, HttpSession session, Model model,
													   @RequestParam("PID[]") List<Integer> productID,
													   @RequestParam("qty[]") List<Integer> productQuantity,
													   @RequestParam("ancSalePrice[]") List<Integer> salePrice,
													   @RequestParam("CID[]") List<Integer> cartID) {
		
		
		/*------------------------------------------------------------------ 구독 내역(마이페이지) 추가 ---------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		
		System.out.println("구독 완료 페이지 요청");
		System.out.println("@RequestParam으로 받은 값");
		System.out.println("상품 PK : " + productID.toString());
		System.out.println("상품 수량 : " + productQuantity.toString());
		System.out.println("상품 단가 : " + salePrice.toString());
		System.out.println("카트 번호 : " + cartID.toString());
		
		// 회원 아이디 저장
		String memberID = (String) session.getAttribute("memberID");
		subscriptionInfoDTO.setMemberID(memberID);
		System.out.println("[구독 insert] 회원 ID : " + subscriptionInfoDTO.getMemberID());
		
		// 구독 시작일 저장	
        // 현재 시간을 포함하는 java.util.Date 객체 생성
        Date currentDate = new Date();
        
        // java.util.Date를 java.sql.Timestamp로 변환
        Timestamp beginDate = new Timestamp(currentDate.getTime());   
        subscriptionInfoDTO.setBeginDate(beginDate);
        System.out.println("[구독 insert] 현재 시간: " + subscriptionInfoDTO.getBeginDate());
        
        // 배송지
        System.out.println("[구독 insert] 구독 상품을 배송지 : " + subscriptionInfoDTO.getSubscriptionPostCode() + " // " + subscriptionInfoDTO.getSubscriptionAddress() + " // " + subscriptionInfoDTO.getSubscriptionDetailAddress());
        
        // 구독 기간
        System.out.println("[구독 insert] 구독 기간 : " + subscriptionInfoDTO.getSubscriptionClosingTimes()+"개월");
        
        // DAO 분기 저장
        subscriptionInfoDTO.setSearchCondition("insertSubscriptionData");
        // 구독 정보 저장
        boolean subscriptionInfoResult = subscriptionInfoService.insert(subscriptionInfoDTO);
        
        if(!subscriptionInfoResult) {
        	
        	System.out.println("[구독 insert] 실패");
        	
        	return "redirect:/cart";
        }
        
        System.out.println("[구독 insert] 성공");
        
        /*------------------------------------------------------------------ 구독 상세 내역 추가 ---------------------------------------------------------------------------------------------------------------------------------------------------------------*/
        
        // 방금 추가된 구독 내역의 PK의 가장 큰 값을 가져온다
        subscriptionInfoDTO.setSearchCondition("insertSubscriptionData");
        int maxSubscriptionNum = subscriptionInfoService.selectOne(subscriptionInfoDTO).getMaxPK();
        
        
        // 구독 상품 정보 추가에 사용
        int subscriptionInfoID = subscriptionInfoDTO.getSubscriptionInfoID();        
        subscriptionInfoProductDTO.setSubscriptionInfoID(maxSubscriptionNum);
        
        subscriptionInfoProductDTO.setProductID(subscriptionInfoID);
        
        subscriptionInfoProductDTO.setSearchCondition("insertSubscriptionData");
		
        int i;
        
        for(i = 0; i < productID.size(); i++) {
        	
        	// 상품번호
        	subscriptionInfoProductDTO.setProductID(productID.get(i));
        	// 상품 갯수
        	subscriptionInfoProductDTO.setQuantity(productQuantity.get(i));      	
        	// 상품 가격
        	subscriptionInfoProductDTO.setPurchasePrice(salePrice.get(i));
        	       	
        	subscriptionInfoProductService.insert(subscriptionInfoProductDTO);
        	
        	cartDTO.setCartID(cartID.get(i));
        	
        	cartService.delete(cartDTO);
        	
        }
        
        if(i < 0) {
        	System.out.println("[구독 상품 insert] 실패");
        	
        	return "redirect:/cart";
        }
        
        System.out.println("[구독 성공] 구독 상품 정보 추가 수 : " + i);     					
		
        model.addAttribute("subscriptionID", maxSubscriptionNum);
        
		return "/user/subscriptionBuyComp";
	}
}
