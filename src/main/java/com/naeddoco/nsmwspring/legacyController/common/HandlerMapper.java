package com.naeddoco.nsmwspring.legacyController.common;

import java.util.HashMap;
import java.util.Map;

import com.naeddoco.nsmwspring.legacyController.action.BuyCompPageAction;
import com.naeddoco.nsmwspring.legacyController.action.BuyInfoPageAction;
import com.naeddoco.nsmwspring.legacyController.action.BuyPageAction;
import com.naeddoco.nsmwspring.legacyController.action.CartDeleteAction;
import com.naeddoco.nsmwspring.legacyController.action.CartPageAction;
import com.naeddoco.nsmwspring.legacyController.action.CheckUserPasswordAction;
import com.naeddoco.nsmwspring.legacyController.action.CheckUserPasswordPageAction;
import com.naeddoco.nsmwspring.legacyController.action.CouponInfoPageAction;
import com.naeddoco.nsmwspring.legacyController.action.DeleteReviewAction;
import com.naeddoco.nsmwspring.legacyController.action.ErrorAction;
import com.naeddoco.nsmwspring.legacyController.action.JoinAction;
import com.naeddoco.nsmwspring.legacyController.action.JoinPageAction;
import com.naeddoco.nsmwspring.legacyController.action.LoginAction;
import com.naeddoco.nsmwspring.legacyController.action.LoginPageAction;
import com.naeddoco.nsmwspring.legacyController.action.LogoutAction;
import com.naeddoco.nsmwspring.legacyController.action.MainPageAction;
import com.naeddoco.nsmwspring.legacyController.action.ModifyUserInfoAction;
import com.naeddoco.nsmwspring.legacyController.action.ModifyUserInfoPageAction;
import com.naeddoco.nsmwspring.legacyController.action.ModifyUserPasswordAction;
import com.naeddoco.nsmwspring.legacyController.action.ModifyUserPasswordPageAction;
import com.naeddoco.nsmwspring.legacyController.action.MypageAction;
import com.naeddoco.nsmwspring.legacyController.action.ProductAllPageAction;
import com.naeddoco.nsmwspring.legacyController.action.ProductDetailPageAction;
import com.naeddoco.nsmwspring.legacyController.action.ReviewDetailPageAction;
import com.naeddoco.nsmwspring.legacyController.action.ReviewInfoPageAction;
import com.naeddoco.nsmwspring.legacyController.action.TermsPageAction;
import com.naeddoco.nsmwspring.legacyController.action.WriteReviewPageAction;
import com.naeddoco.nsmwspring.legacyKakao.KakaoLoginAction;

public class HandlerMapper {
	private Map<String,Action> mappings;
	
	public HandlerMapper() {
		this.mappings = new HashMap<String,Action>();
//		System.out.println("[log] HandlerMapper ");
		this.mappings.put("/mainPage.do", new MainPageAction()); // 메인페이지
		this.mappings.put("/joinPage.do", new JoinPageAction());// 회원가입페이지
		this.mappings.put("/termsPage.do", new TermsPageAction());// 약관페이지
		this.mappings.put("/join.do", new JoinAction());// 회원가입
		this.mappings.put("/loginPage.do", new LoginPageAction());// 로그인페이지
		this.mappings.put("/login.do", new LoginAction()); // 로그인
		this.mappings.put("/logout.do", new LogoutAction());// 로그아웃
		this.mappings.put("/mypage.do", new MypageAction());// 마이페이지
		this.mappings.put("/checkUserPasswordPage.do", new CheckUserPasswordPageAction());// 마이페이지_비밀번호확인페이지
		this.mappings.put("/checkUserPassword.do", new CheckUserPasswordAction());// 마이페이지_비밀번호확인
		this.mappings.put("/modifyUserInfoPage.do", new ModifyUserInfoPageAction());// 마이페이지_개인정보수정페이지
		this.mappings.put("/modifyUserInfo.do", new ModifyUserInfoAction());// 마이페이지_개인정보수정
		this.mappings.put("/modifyUserPasswordPage.do", new ModifyUserPasswordPageAction());// 마이페이지_비밀번호변경페이지
		this.mappings.put("/modifyUserPassword.do", new ModifyUserPasswordAction());// 마이페이지_비밀번호변경
		this.mappings.put("/buyInfoPage.do", new BuyInfoPageAction());// 마이페이지_구매내역
		this.mappings.put("/couponInfoPage.do", new CouponInfoPageAction());// 마이페이지_쿠폰목록(쿠폰관리)
		this.mappings.put("/writeReviewPage.do", new WriteReviewPageAction());// 리뷰작성페이지
		//this.mappings.put("/writeReview.do", new WriteReviewAction());// 리뷰작성
		this.mappings.put("/reviewInfoPage.do", new ReviewInfoPageAction());// 리뷰목록페이지
		this.mappings.put("/reviewDetailPage.do", new ReviewDetailPageAction());// 리뷰상세페이지
		this.mappings.put("/deleteReview.do", new DeleteReviewAction());// 리뷰삭제
		this.mappings.put("/productAllPage.do", new ProductAllPageAction());// 상품전체목록페이지
		this.mappings.put("/productDetailPage.do", new ProductDetailPageAction());// 상품상세페이지
		this.mappings.put("/cartPage.do", new CartPageAction());// 장바구니페이지
		this.mappings.put("/cartDelete.do", new CartDeleteAction());// 장바구니상품삭제
		this.mappings.put("/buyPage.do", new BuyPageAction());// 결제페이지
		this.mappings.put("/buyCompPage.do", new BuyCompPageAction());// 결제완료페이지(구매로직)
		this.mappings.put("/kakaoLogin.do", new KakaoLoginAction());// 카카오계정로그인
		this.mappings.put("/error.do", new ErrorAction());//에러페이지
		
	}
	public Action getAction(String commend) {
		return mappings.get(commend);

	}
	
	
}
