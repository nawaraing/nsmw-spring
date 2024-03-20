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
public class entryCartPageController {
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping(value = "/entryCart", method = RequestMethod.GET)
	public String entryCart(HttpSession session, Model model, CartDTO cartDTO) {
		
		String memberID = (String) session.getAttribute("memberID"); // 세션에 있는 유저 아이디 습득
		
		cartDTO.setSearchCondition("selectCartDatas");
		cartDTO.setMemberID(memberID);
		
		List<CartDTO> cartDTOList = cartService.selectAll(cartDTO);
		
		model.addAttribute("cartList", cartDTOList);
		
		return "user/cart";
		
	}

}
