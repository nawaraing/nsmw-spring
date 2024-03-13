<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="model.dto.*,java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>메인</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<%-- jquery --%>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

<%-- sweetalert2 --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>

<%-- Google Web Fonts --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet">

<%-- Icon Font Stylesheet --%>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

<%-- Libraries Stylesheet --%>
<link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
<link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

<%-- Customized Bootstrap Stylesheet --%>
<link href="css/bootstrap.min.css" rel="stylesheet">

<%-- Template Stylesheet --%>
<link href="css/style.css" rel="stylesheet">
<link href="css/div.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/star-rating+.css" />

<%-- 파비콘 --%>
<custom:favicon/>
</head>
<body>

	<%--로그아웃 확인 후 모달을 띄우는 커스텀 태그 --%>
	<custom:loginResult logoutResult='${logoutResult}' />
	
	
	<%-- 팝업 쿠키 관련  --%>
	<script>
        // 페이지 로드 시 실행할 함수
        window.onload = function () {
        	
        	// 쿠키의 Value값을 저장
            var cookieValue = getCookieValue("popupShown");
            
        	// 저장된 값이 false가 아니라면(null or true)
            if (cookieValue != "false") {
            	// 팝업창 실챙
                openPopup();
            }
            
        };

        // 인자로 쿠키이름을 받아 쿠키값을 반환하는 함수
        function getCookieValue(cookieName) {
        	// 쿠키 이름과 =을 합친 변수 정의
            var nameEQ = cookieName + "=";
            // 현재 페이지의 모든 쿠키를 가져온다
            // 쿠키는 이름1 = 값1; 이름2 = 값2;... 로 저장되기 때문에 ;를 기준으로 잘라서 변수(배열)에 저장한다 
            var cookies = document.cookie.split(';');
            // 쿠키배열의 사이즈만큼 반복
            for (var i = 0; i < cookies.length; i++) {
            	// 해당 인덱스의 쿠키값을 꺼내 앞, 뒤 공백을 제거한 후 저장
                var cookie = cookies[i].trim();

                // cookie가 주어진 쿠키 이름과 같은지 확인
                // cookie에 nameEQ가 처음 나오는 위치를 반환
                // 공백을 제외했기 때문에 같다면0
                if (cookie.indexOf(nameEQ) === 0) {
                	// 주어진 쿠키 이름과 일치하는 쿠키를 찾으면 해당 쿠키의 값을 반환합니다.
                    return cookie.substring(nameEQ.length, cookie.length);
                }
            }
         // 해당하는 쿠키가 없을 경우 null을 반환합니다.
            return null;
        }

        function openPopup() {
        	// 팝업창이 작성되어 있는 JSP파일 주소
            var popupURL = "popupTest.jsp"; 
        	// 팝업창 이름
            var popupName = "popupPage";
        	// 팝업창 너비
            var popupWidth = 458;
        	// 팝업창 높이
            var popupHeight = 678;	
       		// 왼쪽 여백
            var leftPosition = 150; 
   		    // 상단 여백
			var topPosition = 150; 
			
            // 팝업창 열기
            // 팝업창을 닫을 때 동작을 위해 변수에 저장해두기
            var popupWindow = window.open(popupURL, popupName, "width=" + popupWidth + ",height=" + popupHeight + ",left=" + leftPosition + ",top=" + topPosition + ",resizable=no");
        }
	</script>
	<%-- 팝업 쿠키 관련  --%>


	<%-- 장바구니 추가 비동기처리 --%>
	<script>
		function addItemToCart(PID) {
			$.ajax({
				type : "POST", // 또는 "GET"
				url : "insertCart", // 서버에서 아이디 중복 확인을 처리할 PHP 파일 경로
				data : {'PID' : PID},
				success : function(data) {
					console.log(data);
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
	<%-- 장바구니 추가 비동기처리 --%>


	<%-- Spinner Start --%>
	<div id="spinner" class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
		<div class="spinner-grow text-primary" role="status"></div>
	</div>
	<%-- Spinner End --%>


	<%-- 로고가 포홤된 헤더 시작 --%>
	<div class="container-fluid fixed-top">
		<%-- 지도 헤더 --%>
		<custom:commonHeader/>
		<%-- 로고 헤더 --%>
		<custom:commonHeaderWithLogo/>
	</div>
	<%-- 로고가 포홤된 헤더 끝 --%>


	<!-- 검색 버튼 시작 -->
	<div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-fullscreen">
			<div class="modal-content rounded-0">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Search by keyword</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body d-flex align-items-center">
					<div class="input-group w-75 mx-auto d-flex">
						<input type="search" class="form-control p-3" placeholder="keywords" aria-describedby="search-icon-1"> <span id="search-icon-1" class="input-group-text p-3"> <i class="fa fa-search"></i>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 검색 버튼 끝 -->


	<!-- 제품 추천 시작 -->
	<div class="container-fluid py-5 mb-5">
		<div class="container-fluid vesitable py-5">
			<!-- 제품 추천 시작 -->
			<div class="container py-5">
				<h1 class="mb-0">Today's Recommendation</h1>
				<div class="owl-carousel vegetable-carousel owl-theme">
					<c:if test="${fn:length(rcmDTOs) > 0}">
						<c:forEach var="data" items="${rcmDTOs}" varStatus="loop">
							<div class="border border-primary rounded position-relative vesitable-item">
								<div class="vesitable-img" onclick='location.href="productDetailPage.do?PID=${data.PID}";'>
									<img src="${data.imagePath}" class="img-fluid w-100 rounded-top">
								</div>
								<div class="text-white bg-primary px-3 py-1 rounded position-absolute" style="top: 10px; right: 10px;">${data.category}</div>
								<div class="p-4 rounded-bottom">
									<h4 style="text-align: center;">${data.pName}</h4>
									<custom:starRateMain1 score='${data.ancAvgRating}' index='${loop.index}'/>
									<div class="line-clamp my-2">
										<p>${data.pDetail}</p>
									</div>
									<div class="d-flex justify-content-between flex-lg-wrap">
										<div class="row">
											<div class="col">
												<p class="text-dark fs-5 fw-bold mb-0 my-2"><fmt:formatNumber value="${data.sellingPrice}" currencyCode="KRW" />원</p>
											</div>
										</div>
										<c:if test="${member != null}">
											<div class="row">
												<button class="btn border border-secondary rounded-pill px-3 text-primary" onclick="addItemToCart(${data.PID})">장바구니 추가</button>
											</div>
										</c:if>
										<c:if test="${member == null}">
											<a href="loginPage.do" class="btn border border-secondary rounded-pill px-3 text-primary"> 
												<i class="fa fa-shopping-bag me-2 text-primary"></i> Add to cart
											</a>
										</c:if>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:if>
				</div>
			</div>
			<!-- 제품 추천 끝 -->


			<!-- 일반 리스트 시작 -->
			<div class="container-fluid fruite py-5">
				<div class="container py-5">
					<div class="tab-class text-center">
						<div class="row g-4">
							<div class="col-lg-4 text-start">
								<h1>Our Products</h1>
							</div>
							<div class="col-lg-8 text-end">
								<ul class="nav nav-pills d-inline-flex text-center mb-5">
									<li class="nav-item">
										<a class="d-flex m-2 py-2 bg-light rounded-pill active" href="productAllPage.do"> 
											<span class="text-dark" style="width: 130px;">All Products</span>
										</a>
									</li>
								</ul>
							</div>
						</div>
						<div class="tab-content">
							<div id="tab-1" class="tab-pane fade show p-0 active">
								<div class="row g-4">
									<div class="col-lg-12">
										<div class="row g-4">
											<c:if test="${fn:length(pDTOs) > 0}">
												<c:forEach var="data" items="${pDTOs}" varStatus="loop">
													<div class="col-md-6 col-lg-4 col-xl-3">
														<div class="p-4 border border-secondary rounded position-relative fruite-item">
															<div class="fruite-img" onclick='location.href="productDetailPage.do?PID=${data.PID}";'>
																<img src=${data.imagePath } class="img-fluid w-100 rounded-top" alt="">
															</div>
															<div class="text-white bg-secondary px-3 py-1 rounded position-absolute" style="top: 10px; left: 10px;" onclick='location.href="productDetailPage.do?PID=${data.PID}";'>${data.category}</div>
															<div>
																<h4>${data.pName}</h4>
																<div>
																	<custom:starRateMain2 score='${data.ancAvgRating}' index='${loop.index}'/>
																</div>
																<div class="line-clamp my-2">
																	<p>${data.pDetail}</p>
																</div>
																<div class="d-flex justify-content-between flex-lg-wrap">
																	<p class="text-dark fs-5 fw-bold mb-0"><fmt:formatNumber value="${data.sellingPrice}" currencyCode="KRW" />원</p>
																	<c:if test="${member != null}">
																		<button class="btn border border-secondary rounded-pill px-3 text-primary" onclick="addItemToCart(${data.PID})">장바구니 추가</button>
																	</c:if>
																	<c:if test="${member == null}">
																		<a href="loginPage.do" class="btn border border-secondary rounded-pill px-3 text-primary"> <i class="fa fa-shopping-bag me-2 text-primary"></i> Add to cart
																		</a>
																	</c:if>
																</div>
															</div>
														</div>
													</div>
												</c:forEach>
											</c:if>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- 일반 리스트 끝 -->
		</div>
	</div>
	<!-- 제품 추천 끝 -->


	<!-- 풋터 시작 -->
	<custom:commonFooter/>
	<!-- 풋터 끝 -->


	<!-- 카피라이트 시작 -->
	<custom:commonCopyright/>
	<!-- 카피라이트 끝 -->


	<!-- Back to Top -->
	<a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"> <i class="fa fa-arrow-up"></i>
	</a>


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