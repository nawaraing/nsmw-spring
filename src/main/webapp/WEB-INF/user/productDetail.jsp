<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>상품 상세</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<%-- jquery --%>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

<!-- sweetalert2 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>

<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet">

<!-- Icon Font Stylesheet -->
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

<!-- Libraries Stylesheet -->
<link href="/resources/user/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
<link href="/resources/user/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


<!-- Customized Bootstrap Stylesheet -->
<link href="/resources/user/css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="/resources/user/css/style.css" rel="stylesheet">
<link href="/resources/user/css/div.css" rel="stylesheet">


<!-- 파비콘 -->
<custom:favicon />
</head>

<body>

<!-- 배너 -->
<custom:banner/>

	<!-- 장바구니 추가 비동기처리 -->
	<script>
		function addItemToCart(productID) {
			var productQuantity = document.getElementById('productQuantity').value;
			//console.log("[로그] 수량:"+productQuantity);
			
			$.ajax({
				type : "POST", // 또는 "GET"
				url : "insertCart", // 서버에서 아이디 중복 확인을 처리할 PHP 파일 경로
				data : {'productID' : productID,
						'stock' : productQuantity},
				success : function(data) {
					if (data === "true") {
						Swal.fire({
							icon : 'success',
							title : '장바구니 추가',
							text : '추가되었습니다.',
						})
					} else {
						Swal.fire({
							icon : 'error',
							title : '장바구니 추가',
							text : '실패하였습니다.',
						})
					}
				}
			});
		}
	</script>
	<!-- 장바구니 추가 비동기처리 -->


	<!-- Spinner Start -->
	<div id="spinner" class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
		<div class="spinner-grow text-primary" role="status"></div>
	</div>
	<!-- Spinner End -->


	<!-- Navbar start -->
	<div class="container-fluid fixed-top">
		<custom:commonHeader/>
		<custom:commonHeaderWithLogo/>
	</div>
	<!-- Navbar End -->


	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6">상품 상세</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- Single Product Start -->
	<div class="container-fluid py-5 mt-5">
		<div class="container py-5">
			<div class="row g-4 mb-5">
				<div class="col-lg-8 col-xl-9">
					<c:if test="${productDetail != null}">
						<div class="row g-4">
							<div class="col-lg-6">
								<div class="border rounded">
									<a href="#"> <img src="${productDetail.ancImagePath}" class="img-fluid rounded" alt="Image">
									</a>
								</div>
							</div>
							<!-- 제품 상세 -->

							<div class="col-lg-6">
								<h4 class="fw-bold mb-3">${productDetail.productName}</h4>
								<p class="mb-3">카테고리: ${productDetail.ancCategoryName}</p>
								<h5 class="fw-bold mb-3">
									<fmt:formatNumber value="${productDetail.salePrice}" currencyCode="KRW" />
									원
								</h5>
								<div class="d-flex mb-4">
									<custom:starRatePD1 score='3' index='0' />
								</div>
								<p class="mb-4">${productDetail.productDetail}</p>
								<div class="input-group quantity mb-5" style="width: 100px;">
									<div class="input-group-btn">
										<button class="btn btn-sm btn-minus rounded-circle bg-light border">
											<i class="fa fa-minus"></i>
										</button>
									</div>
									<input type="text" id="productQuantity" class="form-control form-control-sm text-center border-0" value="1" readonly>
									<div class="input-group-btn">
										<button class="btn btn-sm btn-plus rounded-circle bg-light border">
											<i class="fa fa-plus"></i>
										</button>
									</div>
								</div>
								<c:if test="${memberID != null}">
									<button class="btn border border-secondary rounded-pill px-3 text-primary" onclick="addItemToCart(${productDetail.productID})">장바구니 추가</button>
								</c:if>
								<c:if test="${memberID == null}">
									<a href="/login" class="btn border border-secondary rounded-pill px-4 py-2 mb-4 text-primary"><i class="fa fa-shopping-bag me-2 text-primary"></i> 장바구니 추가</a>
								</c:if>
							</div>

							<!-- 제품 추천 시작 -->


							<!-- 제품 상세 -->
							<div class="col-lg-12">
								<!-- 하단 메뉴 -->
								<nav>
									<div class="nav nav-tabs mb-3">
										<button class="nav-link active border-white border-bottom-0" type="button" role="tab" id="nav-about-tab" data-bs-toggle="tab" data-bs-target="#nav-about" aria-controls="nav-about" aria-selected="true">설명</button>
										<button class="nav-link border-white border-bottom-0" type="button" role="tab" id="nav-mission-tab" data-bs-toggle="tab" data-bs-target="#nav-mission" aria-controls="nav-mission" aria-selected="false">리뷰</button>
									</div>
								</nav>
								<!-- 하단 메뉴 -->
								<div class="tab-content mb-5">
									<div class="tab-pane active" id="nav-about" role="tabpanel" aria-labelledby="nav-about-tab">

										<div class="row bg-light align-items-center justify-content-center py-2">
											<div class="col-6 text-center">
												<p class="mb-0">성분</p>
											</div>
											<div class="col-6 text-left">
												<p class="mb-0">${productDetail.ingredient}</p>
											</div>
										</div>
										<div class="row align-items-center justify-content-center py-2">
											<div class="col-6 text-center text-center">
												<p class="mb-0 text-left">용법</p>
											</div>
											<div class="col-6 text-left">
												<p class="mb-0">${productDetail.dosage}</p>
											</div>
										</div>
										<div class="row bg-light align-items-center justify-content-center py-2">
											<div class="col-6 text-center">
												<p class="mb-0">소비기한</p>
											</div>
											<div class="col-6 text-left">
												<p class="mb-0">${productDetail.expirationDate}</p>
											</div>
										</div>

									</div>
									
									<!-- 제품상세 하단 리뷰 -->
									<div class="tab-pane" id="nav-mission" role="tabpanel" aria-labelledby="nav-mission-tab">
										<c:if test="${empty reviewList}">
											<c:set var="message" value="리뷰가 존재하지 않습니다." />
											<p style="text-align: center;">
												<c:out value="${message}" />
											</p>
										</c:if>
										<c:forEach var="review" items="${reviewList}" varStatus="loop">
											<div class="d-flex">
												<img src="img/avatar.jpg" class="img-fluid rounded-circle p-3" style="width: 100px; height: 100px;" alt="">
												<div class="">
													<p class="mb-2" style="font-size: 14px;">${review.createTime}</p>
													<div class="d-flex justify-content-between">
														<h5 class="mt-2">${review.MID}</h5>
														<custom:starRatePD2 score='${review.score}' index='${loop.index}' />
													</div>
													<p>${review.contents}</p>
													<c:if test="${not empty review.imageName}">
														<div class="col-lg-12">
															<img src="img/${review.imageName}" alt="리뷰 이미지" style="max-width: 200px; max-height: 200px; margin: 5px;">
														</div>
													</c:if>
												</div>
											</div>
										</c:forEach>
									</div>
									<!-- 제품상세 하단 리뷰 종료 -->
									
																<!-- 케러셀의 제목 -->
							<div class="container py-5">
								<h1 class="mb-0">추천 상품</h1>
								<!-- 케러셀의 내용 -->
								<div class="owl-carousel vegetable-carousel owl-theme">
					<!-- ProductCategoryDTO // recommandProductsByPC -->
					<c:if test="${not empty recommandProductsByPC}">
						<c:forEach var="data" items="${recommandProductsByPC}" varStatus="loop">					
								<div class="border border-primary rounded position-relative vesitable-item">
									<div class="vesitable-img" onclick='location.href="/productDetail?productID=${data.productID}";'>
										<img src="${data.ancImagePath}" class="img-fluid w-100 rounded-top">
									</div>
									<div class="text-white bg-primary px-3 py-1 rounded position-absolute" style="top: 10px; right: 10px;">${data.ancCategoryName}</div>
									<div class="p-4 rounded-bottom">
										<h4 style="text-align: center;">${data.ancProductName}</h4>
										<custom:starRateMain1 score='3' index='${loop.index}'/>
										<div class="line-clamp my-2">
											<p>${data.ancProductDetail}</p>
										</div>
										<div class="d-flex justify-content-between flex-lg-wrap">
											<div class="row">
												<div class="col">
													<p class="text-dark fs-5 fw-bold mb-0 my-2"><fmt:formatNumber value="${data.ancSalePrice}" currencyCode="KRW" />원</p>
												</div>
											</div>
											<c:if test="${not empty memberID}">
												<div class="row">
													<button class="btn border border-secondary rounded-pill px-3 text-primary" onclick="addItemToCart(${data.productID})">장바구니 추가</button>
												</div>
											</c:if>
											<c:if test="${empty memberID}">
												<a href="/login" class="btn border border-secondary rounded-pill px-3 text-primary"> 
													<i class="fa fa-shopping-bag me-2 text-primary"></i> 장바구니 추가
												</a>
											</c:if>
										</div>
									</div>
								</div>
						</c:forEach>
					</c:if>
					
					<!-- BuyInfoDTO // recommandProductsByBI -->
					<c:if test="${not empty recommandProductsByBI}">
						<c:forEach var="data" items="${recommandProductsByBI}" varStatus="loop">					
								<div class="border border-primary rounded position-relative vesitable-item">
									<div class="vesitable-img" onclick='location.href="/productDetail?productID=${data.ancProductID}";'>
										<img src="${data.ancImagePath}" class="img-fluid w-100 rounded-top">
									</div>
									<div class="text-white bg-primary px-3 py-1 rounded position-absolute" style="top: 10px; right: 10px;">${data.ancCategoryName}</div>
									<div class="p-4 rounded-bottom">
										<h4 style="text-align: center;">${data.ancProductName}</h4>
										<custom:starRateMain1 score='3' index='${loop.index}'/>
										<div class="line-clamp my-2">
											<p>${data.ancProductDetail}</p>
										</div>
										<div class="d-flex justify-content-between flex-lg-wrap">
											<div class="row">
												<div class="col">
													<p class="text-dark fs-5 fw-bold mb-0 my-2"><fmt:formatNumber value="${data.ancSalePrice}" currencyCode="KRW" />원</p>
												</div>
											</div>
											<c:if test="${not empty memberID}">
												<div class="row">
													<button class="btn border border-secondary rounded-pill px-3 text-primary" onclick="addItemToCart(${data.ancProductID})">장바구니 추가</button>
												</div>
											</c:if>
											<c:if test="${empty memberID}">
												<a href="/login" class="btn border border-secondary rounded-pill px-3 text-primary"> 
													<i class="fa fa-shopping-bag me-2 text-primary"></i> 장바구니 추가
												</a>
											</c:if>
										</div>
									</div>
								</div>
						</c:forEach>
					</c:if>
								</div>
							</div>
							<!-- 케러셀 끝 -->
									
									<div class="tab-pane" id="nav-vision" role="tabpanel">
										<p class="text-dark">Tempor erat elitr rebum at clita. Diam dolor diam ipsum et tempor sit. Aliqu diam amet diam et eos labore. 3</p>
										<p class="mb-0">Diam dolor diam ipsum et tempor sit. Aliqu diam amet diam et eos labore. Clita erat ipsum et lorem et sit</p>
									</div>
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<!-- Single Product End -->


	<!-- 풋터 시작 -->
	<custom:commonFooter />
	<!-- 풋터 끝 -->


	<!-- 카피라이트 시작 -->
	<custom:commonCopyright />
	<!-- 카피라이트 끝 -->


	<!-- Back to Top -->
	<a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i class="fa fa-arrow-up"></i></a>


	<!-- JavaScript Libraries -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="/resources/user/lib/easing/easing.min.js"></script>
	<script src="/resources/user/lib/waypoints/waypoints.min.js"></script>
	<script src="/resources/user/lib/lightbox/js/lightbox.min.js"></script>
	<script src="/resources/user/lib/owlcarousel/owl.carousel.min.js"></script>

	<!-- Template Javascript -->
	<script src="/resources/user/js/main.js"></script>

</body>

</html>