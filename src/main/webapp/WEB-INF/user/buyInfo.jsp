<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>구매내역</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
<link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css/style.css" rel="stylesheet">
<link href="css/table.css" rel="stylesheet">

<!-- 파비콘 -->
<custom:favicon/>
</head>
<body>

	<%-- 세션 확인 후 없으면 메인으로 --%>
	<custom:emthySessionAndGoToMain/>


	<!-- Spinner Start -->
	<div id="spinner" class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
		<div class="spinner-grow text-primary" role="status"></div>
	</div>
	<!-- Spinner End -->


	<!-- Navbar start -->
	<div class="container-fluid fixed-top">
		<custom:commonHeader/>
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
					<div class="navbar-nav mx-auto">
						<a href="checkUserPasswordPage.do?where=modifyUserInfo" class="nav-item nav-link">개인정보수정</a>
						<a href="checkUserPasswordPage.do?where=modifyUserPassword" class="nav-item nav-link">비밀번호변경</a> 
						<a class="btn text-primary mb-0 mt-1" href="buyInfoPage.do" class="nav-item nav-link">구매내역</a> 
						<a href="reviewInfoPage.do" class="nav-item nav-link">리뷰내역</a> 
						<a href="couponInfoPage.do" class="nav-item nav-link">쿠폰관리</a></div>
					<div class="d-flex m-3 me-0">
						<a class="btn border border-secondary text-primary rounded-pill position-relative my-auto me-4" href="logout.do">로그아웃</a>
						<a href="cartPage.do" class="position-relative me-4 my-auto"> 
							<i class="fa fa-shopping-bag fa-2x"></i> 
						</a> 
						<a href="mypage.do" class="my-auto"> 
							<i class="fas fa-user fa-2x"></i>
						</a>
					</div>
				</div>
			</nav>
		</div>
	</div>
	<!-- Navbar End -->


	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6">구매내역</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- Cart Page Start -->
	<div class="container-fluid py-5">
		<div class="container py-5">
			<div class="table-responsive">
				<table class="table">
					<thead>
						<tr>
							<th scope="col">상품</th>
							<th scope="col">주문번호</th>
							<th scope="col">상품명</th>
							<th scope="col">구매일시</th>
							<th scope="col">구매수량</th>
							<th scope="col">구매가격</th>
							<th scope="col">배송상태</th>
							<th scope="col">리뷰작성</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="buyInfo" items="${bDTOs}">
						<tr>
							<th scope="row">
								<img src="${buyInfo.ancImagePath}" class="img-fluid me-5 rounded-circle" style="width: 80px; height: 80px;" alt="">
							</th>
							<td>
								<p class="mb-0 mt-4">${buyInfo.orderNum}</p>
							</td>
							<td>
								<p class="btn text-primary mb-0 mt-3 mb-0 mt-4" onclick='location.href="productDetailPage.do?PID=${buyInfo.PID}"'>${buyInfo.ancPName}</p>
							</td>
							<td>
								<p class="mb-0 mt-4">${buyInfo.ancBuyTime}</p>
							</td>
							<td>
								<p class="mb-0 mt-4">${buyInfo.bQty}</p>
							</td>
							<td>
								<p class="mb-0 mt-4"><fmt:formatNumber value="${buyInfo.paymentPrice}" currencyCode="KRW" />원</p>
							</td>
							<td>
								<p class="mb-0 mt-4">${buyInfo.deliState}</p>
							</td>
							<td>
								<c:if test="${buyInfo.hasReview eq 0}">
									<a class="btn border-secondary text-primary rounded-pill mb-0 mt-3" onclick='location.href="writeReviewPage.do?BID=${buyInfo.BID}";'>리뷰 작성</a>
								</c:if>
								<c:if test="${buyInfo.hasReview eq 1}">
									<p class="text-primary mb-0 mt-3 mb-0 mt-4">작성 완료</p>
								</c:if>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- Cart Page End -->


	<!-- 풋터 시작 -->
	<custom:commonFooter/>
	<!-- 풋터 끝 -->


	<!-- 카피라이트 시작 -->
	<custom:commonCopyright/>
	<!-- 카피라이트 끝 -->


	<!-- Back to Top -->
	<a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i class="fa fa-arrow-up"></i></a>


	<!-- JavaScript Libraries -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="lib/easing/easing.min.js"></script>
	<script src="lib/waypoints/waypoints.min.js"></script>
	<script src="lib/lightbox/js/lightbox.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>

	<!-- Template Javascript -->
	<script src="js/main.js"></script>
</body>

</html>