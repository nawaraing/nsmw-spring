<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>리뷰 상세</title>
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
		<custom:commonHeaderWithLogo/>
	</div>
	<!-- Navbar End -->


	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6">리뷰 상세</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- Single Product Start -->
	<div class="container-fluid py-5 mt-5">
		<div class="container py-5">
			<div class="row g-10 mb-5">

				<form action="#">
					<h4 class="mb-5 fw-bold">리뷰 상세</h4>
					<div class="row g-4">
						<div class="col-lg-4">
							<div class="border-bottom rounded">
								<input type="text" class="form-control border-0 me-4" value="${ReviewDetail.MID}" readonly style="background-color: white;">
							</div>
						</div>
						<div class="col-lg-4">
							<div class="border-bottom rounded">
								<input type="email" class="form-control border-0" value="${ReviewDetail.ancEmail}" readonly style="background-color: white;">
							</div>
						</div>
						<div class="col-lg-4">
							<custom:starRateRD score='${ReviewDetail.score}' index='${loop.index}'/>
						</div> 
						<div class="col-lg-12">
							<div class="border-bottom rounded my-4">
								<textarea name="" id="" class="form-control border-0" cols="30" rows="8" spellcheck="false" readonly style="background-color: white;">${ReviewDetail.contents}</textarea>
							</div>
						</div>
						<div class="col-lg-12">
							<c:if test="${not empty ReviewDetail.imageName}">
								<div class="col-lg-12">
									<img src="img/${ReviewDetail.imageName}" alt="리뷰 이미지" style="max-width: 200px;max-height: 200px;margin: 5px;">
								</div>
							</c:if>
							<div class="d-flex justify-content-between py-3 mb-5">
								<a class="btn border border-secondary text-primary rounded-pill px-4 py-3" href="reviewInfoPage.do"> 돌아가기</a>
							</div>
						</div>
					</div>
				</form>

			</div>
		</div>
	</div>
	<!-- Single Product End -->


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