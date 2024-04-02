<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>장바구니</title>
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
<link href="/resources/user/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
<link href="/resources/user/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="/resources/user/css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="/resources/user/css/style.css" rel="stylesheet">
<link href="/resources/user/css/number.css" rel="stylesheet">
<link href="/resources/user/css/table.css" rel="stylesheet">

<!-- 파비콘 -->
<custom:favicon/>
</head>
<body>
	
	<%-- 세션 확인 후 없으면 메인으로 --%>
	<custom:emthySessionAndGoToMain/>


	<!-- 페이지 진입 시 총금액 계산 -->
	<c:set var="total" value="0" /> <!-- 총금액을 저장하기 위한 jstl변수 -->
	<c:forEach var="cart" items="${cartList}" varStatus="status"> <!-- 장바구니 데이터 반복 -->
		<c:set var="total" value="${total + (cart.ancSalePrice * cart.productQuantity)}" /> <!-- 가격*금액을 총금액에 가산 -->
	</c:forEach>
	<script>
		var total = ${total} <!-- 총금액을 자바스크립트 전역 변수에 저장 -->
	</script>
	<!-- 페이지 진입 시 총금액 계산 -->


	<!-- 상품 금액 계산 -->
	<script>
		function calculPlusPrice(ancSalePrice, index) { // 수량 + 버튼을 눌렸을 때 가격을 가산 하는기능
        	var productQuantity = parseInt($("#productQuantity_" + index).val()) + 1; // 버튼을 눌렀을 시점에는 아직 수량 가산이 안됬기 때문에 +1
        	var productPrice = ancSalePrice * productQuantity; // 가격 * 수량
        	$("#productPrice_" + index).text(productPrice.toLocaleString('ko-KR') + "" + "원"); // 가격 * 수량 금액을 원화 단위를 적용하여 표시
        
        	productQuantity = productQuantity - 1; 
        	productPrice = ancSalePrice;
        	total = total + productPrice;
        	document.getElementById("totalPrice").textContent = total.toLocaleString('ko-KR') + "" + "원";
    	}
    	function calculMinusPrice(ancSalePrice, index) {
    		var productQuantity = parseInt($("#productQuantity_" + index).val());
    		if (productQuantity > 0) {
    			productQuantity -= 1;
	           	var productPrice = ancSalePrice * productQuantity;
	           	$("#productPrice_" + index).text(productPrice.toLocaleString('ko-KR') + "" + "원");
	            
	           	productPrice = ancSalePrice;
	           	total = total - productPrice;
	           	document.getElementById("totalPrice").textContent = total.toLocaleString('ko-KR') + "" + "원"; 
    		}
    	}
	</script>
	<!-- 상품 금액 계산 -->


	<!-- 총금액 갱신 -->
	<script>
    	function updateTotalPrice(checkbox, price, index) { // 체크박스가 변경될 때 호출되는 함수
    		var productPrice = price * $("#productQuantity_" + index).val();    	
        	if (checkbox.checked) { // 체크박스의 체크 상태 확인
        		total = total + productPrice; // 체크되었다면 가격을 배열에 추가
        	} else {
        		total = total - productPrice; // 체크가 해제되었다면 배열에서 제거
        	}
        	document.getElementById("totalPrice").textContent = total.toLocaleString('ko-KR') + "" + "원"; // totalPrice라는 id를 가진 태그에 텍스트 넣기
    	}
	</script>
	<!-- 총금액 계산 -->
	
	<!-- 구독 -->
	<script>
    	function goToSubscription() {
    		var actionName = '/buy/subscription';
        	var rows = document.querySelectorAll('tr[id^="row_"]');
        	rows.forEach(function(row) {
            	var form = document.getElementById('cartForm');
            	if(row.querySelector('#checkbox').checked){
            		form.innerHTML += '<input type="hidden" name="imagePath[]" value="' + row.querySelector('img').src + '">';
                	form.innerHTML += '<input type="hidden" name="productName[]" value="' + row.querySelector('#productName').innerText + '">';
                	form.innerHTML += '<input type="hidden" name="salePrice[]" value="' + row.querySelector('#hiddenSalePrice').value + '">';
                	form.innerHTML += '<input type="hidden" name="productQuantity[]" value="' + row.querySelector('input[id^="productQuantity_"]').value + '">';
                	form.innerHTML += '<input type="hidden" name="productID[]" value="' + row.querySelector('#hiddenProductID').value + '">';
                	form.innerHTML += '<input type="hidden" name="cartID[]" value="' + row.querySelector('#hiddenCartID').value + '">';
                	form.innerHTML += '<input type="hidden" name="category[]" value="' + row.querySelector('#hiddenCategory').value + '">';
        		}
        	});
        	// 폼을 서버로 제출합니다.
        	document.getElementById('cartForm').action = actionName;
        	document.getElementById('cartForm').submit();
    	}
	</script>
	<!-- 구독 -->


	<!-- 구매 진행 -->
	<script>
    	function goToBuy() {
    		var actionName = '/buy/once';
        	var rows = document.querySelectorAll('tr[id^="row_"]');
        	rows.forEach(function(row) {
            	var form = document.getElementById('cartForm');
            	if(row.querySelector('#checkbox').checked){
            		form.innerHTML += '<input type="hidden" name="imagePath[]" value="' + row.querySelector('img').src + '">';
                	form.innerHTML += '<input type="hidden" name="productName[]" value="' + row.querySelector('#productName').innerText + '">';
                	form.innerHTML += '<input type="hidden" name="salePrice[]" value="' + row.querySelector('#hiddenSalePrice').value + '">';
                	form.innerHTML += '<input type="hidden" name="productQuantity[]" value="' + row.querySelector('input[id^="productQuantity_"]').value + '">';
                	form.innerHTML += '<input type="hidden" name="productID[]" value="' + row.querySelector('#hiddenProductID').value + '">';
                	form.innerHTML += '<input type="hidden" name="cartID[]" value="' + row.querySelector('#hiddenCartID').value + '">';
                	form.innerHTML += '<input type="hidden" name="category[]" value="' + row.querySelector('#hiddenCategory').value + '">';
        		}
        	});
        	// 폼을 서버로 제출합니다.
        	document.getElementById('cartForm').action = actionName;
        	document.getElementById('cartForm').submit();
    	}
	</script>
	<!-- 구매 진행 -->

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
		<h1 class="text-center text-white display-6">장바구니</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- Cart Page Start -->
	<div class="container-fluid py-5">
		<div class="container py-5">
			<div class="table-responsive">
				<form id="cartForm" method="POST">
					<table class="table">
						<thead>
							<tr>
								<th scope="col">선택</th>
								<th scope="col">상품</th>
								<th scope="col">상품명</th>
								<th scope="col">가격</th>
								<th scope="col">수량</th>
								<th scope="col">상품금액</th>
								<th scope="col">삭제</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="cart" items="${cartList}" varStatus="status">
								<tr id="row_${status.index}">
									<td scope="row">
										<p class="mb-3 mt-4">
											<input type="checkbox" id="checkbox" onclick="updateTotalPrice(this, ${cart.ancSalePrice}, ${status.index})" checked>
										</p>									
									</td>
									<!-- 이미지 -->
									<td scope="row">
										<div class="row justify-content-center">
											<img src="${cart.ancImagePath}" class="img-fluid rounded-circle" style="width: 80px; height: 80px;">									
										</div>
									</td>
									<!-- 이미지 -->
									<!-- 이름 -->
									<td>
										<p class="btn text-primary mb-0 mt-3 mb-0 mt-4" id="productName" onclick='location.href="productDetail?productID=${cart.productID}"'>${cart.ancProductName}</p>
									</td>
									<!-- 이름 -->
									<!-- 가격 -->
									<td>
										<p class="mb-0 mt-4" id="salePrice"><fmt:formatNumber value="${cart.ancSalePrice}" currencyCode="KRW" />원</p> 
										<input type="hidden" id="hiddenSalePrice" value="${cart.ancSalePrice}" />
									</td>
									<!-- 가격 -->
									<!-- 수량 -->
									<td>
									    <div class="row justify-content-center">
										<div class="input-group quantity mt-4" style="width: 150px;">
											<div class="input-group-btn">
												<button class="btn btn-sm btn-minus rounded-circle bg-light border" type="button" onclick="calculMinusPrice(${cart.ancSalePrice}, ${status.index})">
													<i class="fa fa-minus"></i>
												</button>
											</div>
											<input id="productQuantity_${status.index}" type="number" class="form-control form-control-sm text-center border-0" value="${cart.productQuantity}" onchange="noQtyZero(this, 1)" readonly>
											<div class="input-group-btn">
												<button class="btn btn-sm btn-plus rounded-circle bg-light border" type="button" onclick="calculPlusPrice(${cart.ancSalePrice}, ${status.index})">
													<i class="fa fa-plus"></i>
												</button>
											</div>
										</div>
									    </div>
									</td>
									<!-- 수량 -->
									<!-- 가격*수량 -->
									<td>
										<p class="text-center mb-0 mt-4" id="productPrice_${status.index}">
											<fmt:formatNumber value="${cart.ancSalePrice * cart.productQuantity}" currencyCode="KRW" />
											원
										</p>
									</td>
									<!-- 취소 버튼 -->
									<td>
										<button class="btn btn-md rounded-circle bg-light border mt-4" type="button" onclick='location.href="cartDelete.do?cartID=${cart.cartID}";'>
											<i class="fa fa-times text-danger"></i>
										</button>
										<input type="hidden" id="hiddenCartID" value="${cart.cartID}" />
									</td>
									<!-- 취소 버튼 -->
									<td>
										<input type="hidden" id="hiddenProductID" value="${cart.productID}" />
										<input type="hidden" id="hiddenCategory" value="${cart.ancCategory}" />
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form>
			</div>
			<c:if test="${empty cartList}">
				<c:set var="message" value="장바구니에 상품이 존재하지 않습니다." />
					<p style="text-align:center;"><c:out value="${message}" /></p>
			</c:if>
			<div class="mt-5"></div>
			<div class="row g-4 justify-content-end">
				<div class="col-8"></div>
				<div class="col-sm-8 col-md-7 col-lg-6 col-xl-4">
					<div class="bg-light rounded">
						<div class="p-4">
							<h1 class="display-6 mb-4">총금액</h1>
							<div class="d-flex justify-content-between mb-4">
								<h5 class="mb-0 me-4">합계:</h5>
								<p class="mb-0" id="totalPrice">
									<fmt:formatNumber value="${total}" currencyCode="KRW" />
									원
								</p>
							</div>
							<div class="d-flex justify-content-between"></div>
						</div>
						<button class="btn border-secondary rounded-pill px-5 py-3 text-primary text-uppercase mb-4 ms-4" type="button" onclick="goToSubscription()">정기 구매</button>
						<button class="btn border-secondary rounded-pill px-4 py-3 text-primary text-uppercase mb-4 ms-4" type="button" onclick="goToBuy()">한 번만 구매</button>
					</div>
				</div>
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
	<script src="/resources/user/lib/easing/easing.min.js"></script>
	<script src="/resources/user/lib/waypoints/waypoints.min.js"></script>
	<script src="/resources/user/lib/lightbox/js/lightbox.min.js"></script>
	<script src="/resources/user/lib/owlcarousel/owl.carousel.min.js"></script>

	<!-- Template Javascript -->
	<script src="/resources/user/js/main.js"></script>
</body>

</html>