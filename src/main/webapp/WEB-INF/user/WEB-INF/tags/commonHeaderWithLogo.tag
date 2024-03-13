<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="member" %>
<div class="container px-0">
	<nav class="navbar navbar-light bg-white navbar-expand-xl">
	
		<!-- 로고 버튼 -->
		<a href="mainPage.do" class="navbar-brand">
  			<img src="img/favicon.png" width="70" alt="대체 텍스트">
		</a>
		<a href="mainPage.do" class="navbar-brand">
  			<img src="img/logo.png" width="250" alt="대체 텍스트">
		</a>
		<!-- 로고 버튼 -->
		
		
		<button class="navbar-toggler py-2 px-3" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
			<span class="fa fa-bars text-primary"></span>
		</button>
		<div class="collapse navbar-collapse bg-white" id="navbarCollapse">
			<div class="navbar-nav mx-auto"></div>
			<div class="d-flex m-3 me-0">
			
			
				<!-- 로그아웃 버튼 -->
				<c:if test="${not empty member}">
					<a class="btn border border-secondary text-primary rounded-pill position-relative my-auto me-4" href="logout.do">로그아웃</a>
				</c:if>
				<!-- 로그아웃 버튼 -->
				
				
				<!-- 장바구니 버튼 -->
				<c:if test="${not empty member}">
					<a href="cartPage.do" class="position-relative me-4 my-auto"> <i class="fa fa-shopping-bag fa-2x"></i>
					</a>
				</c:if>
				<c:if test="${empty member}">
					<a href="loginPage.do" class="position-relative me-4 my-auto"> <i class="fa fa-shopping-bag fa-2x"></i>
					</a>
				</c:if>
				<!-- 장바구니 버튼 -->
				
				
				<!-- 프로필 버튼 -->
				<c:if test="${not empty member}">
					<a href="mypage.do" class="my-auto"> <i class="fas fa-user fa-2x"></i>
					</a>
				</c:if>
				<!-- 프로필 버튼 -->
				
				
				<!-- 로그인 버튼 -->
				<c:if test="${empty member}">
					<a class="btn border border-secondary text-primary rounded-pill position-relative my-auto me-4" href="loginPage.do">로그인</a>
				</c:if>
				<!-- 로그인 버튼 -->
				
			</div>
		</div>
	</nav>
</div>