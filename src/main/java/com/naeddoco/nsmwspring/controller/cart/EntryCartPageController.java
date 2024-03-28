package com.naeddoco.nsmwspring.controller.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naeddoco.nsmwspring.model.cartModel.CartDTO;
import com.naeddoco.nsmwspring.model.cartModel.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EntryCartPageController {

	@Autowired
	private CartService cartService;

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String entryCart(HttpSession session, Model model) {

		String memberID = (String) session.getAttribute("memberID"); // 세션에 있는 유저 아이디 습득

		if (memberID == null) { // 세션에 유저 아이디가 없을 시

			return "redirect:/"; // 메인 페이지로 강제 이동

		}
		
		CartDTO cartDTO = new CartDTO();
		
		cartDTO.setSearchCondition("selectCartDatas"); // 검색 조건 설정
		
		cartDTO.setMemberID(memberID); // 유저 아이디 DTO에 set

		List<CartDTO> cartDTOList = cartService.selectAll(cartDTO); // selectAll 실행 후 반환 값 저장

		model.addAttribute("cartList", cartDTOList); // 모델에 데이터 저장

		return "user/cart"; // 장바구니 페이지로 요청

	}

}
