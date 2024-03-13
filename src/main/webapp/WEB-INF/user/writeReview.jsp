<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>리뷰 작성</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- jquery -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<!-- jquery -->

<%-- sweetalert2 --%>
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
<link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
<link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css/style.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/starRating.css" />

<!-- 파비콘 -->
<custom:favicon/>
</head>

<body>

	<%-- 세션 확인 후 없으면 메인으로 --%>
	<custom:emthySessionAndGoToMain />
	<%-- 세션 확인 후 없으면 메인으로 --%>


	<%-- 뒤로가기 --%>
	<script>
		function goBack() {
  			window.history.back();
		}
	</script>
	<%-- 뒤로가기 --%>


	<c:set var="BID" value="${param.BID}" />
	<%-- 리뷰 작성 --%>
	<script>
			
		function writeReview(){
			
			var form = document.getElementById('writeReviewForm');
			var radioButtons = document.querySelectorAll('input[type=radio][name="rating"]');
			var checked = false;
			radioButtons.forEach(function(radioButton) {
				if (radioButton.checked) {
					var scoreInput = document.createElement('input');
		            scoreInput.type = 'hidden';
		           	scoreInput.name = 'score';
		           	scoreInput.value = radioButton.value;
		           	form.appendChild(scoreInput);
		           	checked = true;
				}
			});
			
			var contentsInput = document.createElement('input');
			contentsInput.type = 'hidden';
		   	contentsInput.name = 'contents';
		   	contentsInput.value = $("#contents").val();
		   	form.appendChild(contentsInput);

		   	var BIDInput = document.createElement('input');
		 	BIDInput.type = 'hidden';
		 	BIDInput.name = 'BID';
		 	BIDInput.value = ${BID};
		  	form.appendChild(BIDInput);
		  	
		  	if(checked == false){
		  		Swal.fire({
					icon : 'error',
					title : '양식 확인',
					text : '별점을 매겨주세요.',
				})
		  	} else if(contentsInput.value == ""){
		  		Swal.fire({
					icon : 'error',
					title : '양식 확인',
					text : '내용을 기재해주세요.',
				})
		  	} else{
		  		form.submit();
		  	}
		}
	</script>
	<%-- 리뷰 작성 --%>


	<!-- Spinner Start -->
	<div id="spinner" class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
		<div class="spinner-grow text-primary" role="status"></div>
	</div>
	<!-- Spinner End -->


	<!-- Navbar start -->
	<div class="container-fluid fixed-top">
		<custom:commonHeader />
		<custom:commonHeaderWithLogo/>
	</div>
	<!-- Navbar End -->


	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6">리뷰 작성</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- Single Product Start -->
	<div class="container-fluid py-5 mt-5">
		<div class="container py-5">
			<div class="row g-10 mb-5">
				<form method="post" action="writeReview.do" id="writeReviewForm" enctype="multipart/form-data">
					<h4 class="mb-5 fw-bold">리뷰 작성</h4>
					<div class="row g-4">
						<div class="col-lg-4">
							<div class="border-bottom rounded">
								<input type="text" class="form-control border-0 me-4" value="${reviewInfo.MID}" readonly style="background-color: white;">
							</div>
						</div>
						<div class="col-lg-4">
							<div class="border-bottom rounded">
								<input type="email" class="form-control border-0" value="${reviewInfo.email}" readonly style="background-color: white;">
							</div>
						</div>
						<div class="col-lg-4">
							<div class="row">
								<div class="star-rating space-x-4 mx-auto" required>
									<input type="radio" id="5-stars" name="rating" value="5"/>
									<label for="5-stars" class="star pr-4">★</label>
									<input type="radio" id="4-stars" name="rating" value="4"/>
									<label for="4-stars" class="star">★</label>
									<input type="radio" id="3-stars" name="rating" value="3"/>
									<label for="3-stars" class="star">★</label>
									<input type="radio" id="2-stars" name="rating" value="2"/>
									<label for="2-stars" class="star">★</label>
									<input type="radio" id="1-star" name="rating" value="1"/>
									<label for="1-star" class="star">★</label>
								</div>
							</div>
						</div>
						<div class="col-lg-12">
							<div class="border-bottom rounded my-4">
								<textarea class="form-control border-0" id="contents" cols="30" rows="8" placeholder="내용" spellcheck="false" required></textarea>
							</div>
						</div>
						<div class="col-lg-2">
							<custom:uploadFile/>
							<div class="d-flex justify-content-between py-3 mb-5">
								<button class="btn border border-secondary text-primary rounded-pill px-4 py-3" type="button" onclick="writeReview()">작성</button>
								<button class="btn border border-secondary text-primary rounded-pill px-4 py-3" type="button" onclick="goBack()">취소</button>
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