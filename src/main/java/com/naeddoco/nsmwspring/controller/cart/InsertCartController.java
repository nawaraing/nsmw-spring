package com.naeddoco.nsmwspring.controller.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naeddoco.nsmwspring.model.cartModel.CartDTO;
import com.naeddoco.nsmwspring.model.cartModel.CartService;
import com.naeddoco.nsmwspring.model.productModel.ProductDTO;
import com.naeddoco.nsmwspring.model.productModel.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class InsertCartController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/insertCart", method = RequestMethod.POST)
	public @ResponseBody boolean insertCart(CartDTO cartDTO, ProductDTO productDTO, Model model, HttpSession session, 
			 								@RequestParam String productID,
			 								@RequestParam String productQuantity) {	
		
		
		// 세션에 있는 사용자 아이디 저장
		String memberID = (String) session.getAttribute("memberID");
		
		// 로그인한 사용자 받아오기 (필수)
		System.out.println("[log] InsertCart 회원아이디 : " + memberID);
		
		// 회원이 로그인 상태가 아니라면 false 반환
		if (memberID == null) {
			
			System.out.println("[log] InsertCart 로그아웃상태");
			
			return false;
		}			
		
		// 상품 ID를 받지못하면 실패
		if(productID == null) {
			
			System.out.println("[log] InsertCart 상품아이디없음");
			
			return false;
		}
		
		// View에서 전달받은 상품PK를 정수로 변환
		int pid = Integer.parseInt(productID);
	
		// 장바구니에 담기는 상품의 기본 수량
		int pQty = 1;
		
		// 만약 Vire에서 수량을 받아온다면 기본 수량을 받아온 수량으로 변경
		if (productQuantity != null) {		
			pQty = Integer.parseInt(productQuantity);			
		} else {
			System.out.println("[log] InsertCart 수량 입력 없음");
		}
		
		System.out.println("[log] InsertCart 수량 : " + pQty);
		
		// 상품의 재고와 수량을 비교
		productDTO.setSearchCondition("상품상세정보");
		productDTO.setProductID(pid);
		productDTO = productService.selectOne(productDTO);
		
		// 상품의 재고가 수량보다 적다면 실패
		if (pQty > productDTO.getStock()) {			
			System.out.println("[log] InsertCart 상품 재고 : " + productDTO.getStock());
			System.out.println("[log] InsertCart 선택 수량 : " + pQty);
			return false;
		}
	

		// 로그인한 회원의 장바구니에 해당 상품이 담겨있는지 확인
		cartDTO.setSearchCondition("상품확인");
		cartDTO.setMemberID(memberID);
		cartDTO.setProductID(pid);
		
		// 해당 상품의 cartID를 반환
		cartDTO = cartService.selectOne(cartDTO);
		
		System.out.println("[log] InsertCart 장바구니 확인 : " + cartDTO);

		
		// 장바구니에 없는 상품이라면 추가
		if (cartDTO == null) {
			cartDTO = new CartDTO();
			cartDTO.setSearchCondition("장바구니추가");
			cartDTO.setMemberID(memberID);
			cartDTO.setProductID(pid);
			cartDTO.setProductQuantity(pQty);

			// 장바구니에 상품추가 결과에 따른 값 반환
			if (cartService.insert(cartDTO)) {
				return true;
			} else {
				return false;
			}
			// 장바구니에 있는 상품이라면 수량 없데이트
		} else {
			cartDTO.setSearchCondition("동일상품추가");
			cartDTO.setProductQuantity(pQty);

			System.out.println("[log] InsertCart 변경될 장바구니 정보 : " + cartDTO);
			
			// 
			if (cartService.update(cartDTO)) {
				return true;
			} else {
				return false;
			}
		}

	}

}
