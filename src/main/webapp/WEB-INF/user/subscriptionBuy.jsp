<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<!DOCTYPE html>
<html>
<head>
<!-- <meta charset="utf-8"> -->
<title>구독</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">

<!-- jquery -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<!-- jquery -->

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

<!-- sweetalert2 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>

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

	<!-- 원래 가격 삭선 스타일 -->
	<style>
    	#total {
        	position: relative; /* 부모 요소를 기준으로 절대 위치 지정 */
    	}

    	#total::after {
        	content: ""; /* 가상 요소를 생성 */
        	position: absolute; /* 절대 위치로 설정 */
        	top: 50%; /* 세로 중앙 정렬을 위해 부모의 50% 지점으로 이동 */
	        left: 0; /* 가로 위치를 왼쪽으로 설정 */
	        width: 100%; /* 가로 전체 너비로 설정 */
	        border-top: 1px solid #000; /* 1픽셀 선 생성 및 색상 설정 */
	    }
	</style>
	<!-- 원래 가격 삭선 스타일 -->

	<%-- 세션 확인 후 없으면 메인으로 --%>
	<custom:emthySessionAndGoToMain/>
	

	<!-- 주소 api 자바스크립트 -->
	<script language="javascript">
		// opener관련 오류가 발생하는 경우 아래 주석을 해지하고, 사용자의 도메인정보를 입력합니다. 
		// (＂팝업 API 호출 소스"도 동일하게 적용시켜야 합니다.)
		//document.domain = "abc.go.kr";
		function goPopup() {
			//경로는 시스템에 맞게 수정하여 사용
			//호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(https://business.juso.go.kr/addrlink/addrLinkUrl.do)를
			//호출하게 됩니다.
			var pop = window.open("/jusoPopup.jsp", "pop",
					"width=570,height=420, scrollbars=yes, resizable=yes");
			//** 2017년 5월 모바일용 팝업 API 기능 추가제공 **/
			// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서
			// 실제 주소검색 URL(https://business.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
			// var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
		}

		function jusoCallBack(zipNo, roadAddrPart1, addrDetail) {

			// 2017년 2월 제공항목이 추가되었습니다. 원하시는 항목을 추가하여 사용하시면 됩니다.
			document.getElementById('subscriptionPostCode').value = zipNo;	
			document.getElementById('subscriptionAddress').value = roadAddrPart1;	
			document.getElementById('subscriptionDetailAddress').value = addrDetail;	
			
			console.log("________________________주소 API_____________________");
			console.log("우편번호 : " + document.getElementById('subscriptionPostCode').value);
			console.log("주   소 : " + document.getElementById('subscriptionAddress').value);
			console.log("상세주소 : " + document.getElementById('subscriptionDetailAddress').value);
			console.log("________________________주소 API 끝_____________________");

		}
	</script>
	<!-- 주소 api 자바스크립트 -->

	
	<!-- 구독 정책에 따른 가격 할인 -->
	<script>
	
	// 페이지 로드 시 함수 실행
	window.onload = function() {
	    calculateTotal();
	};
	
	// 셀렉트 박스 변경 시 함수 실행
	function onChangeSelect() {
	    calculateTotal();
	}
	
	function calculateTotal() {
		
	    // 선택된 ID의 값 전부 저장
	    var selectElement = document.getElementById('subscriptionDuration');
	    
	    // 현재 셀텍트 박스의 선택된 값을 저장
	    var selectedOption = selectElement.options[selectElement.selectedIndex];
	    
	    // 현재 셀렉트 박스의 값에서 text값을 저장 : 구독 개월
	    var selectedText = selectedOption.text;

	    // 현재 셀렉트 박스의 값에서 value값을 저장 : 할인율
	    var discountRate = selectedOption.value;	    
	    console.log("calculateTotal함수의 할인율 : " + discountRate);
	    
	    // text값에서 '개월'앞의 숫자만 저장
	    var selectedText = selectedText.split('개월')[0].trim();
	    console.log("calculateTotal함수의 구독개월 : " + selectedText);
	    
	    // hiddenSubscriptionClosingTimes ID를 가진 태그의 value값에 '개월'을 제외한 text값 저장 : 구독 개월
	    document.getElementById('hiddenSubscriptionClosingTimes').value = selectedText;
	    
	    // hiddendiscount ID를 가진 태그의 value 값에 value값 저장 : 할인율
	    document.getElementById('hiddendiscount').value = discountRate;	    
	    
	    // 할인율 계산해서 지불할 비율 계산
	    var paymentRate = (100 - discountRate) / 100;
	    
	    console.log("calculateTotal함수의 지불% : " + paymentRate);	    
	    
	 	// buyProducts의 각 상품의 hiddenPrice를 가져와서 할인율을 적용하여 업데이트
	    var trElements = document.querySelectorAll('tr[id^="product_"]');
	    console.log("trElements:", trElements); // trElements 출력
	    
	    // 상품별 지불가격에 할인율 적용
	    for (var i = 0; i < trElements.length; i++) {
	        var productIndex = trElements[i].id.split('_')[1]; // 각 상품의 인덱스 얻기
	        console.log("productIndex:", productIndex); // productPriceElement 출력
	        var productPriceElement = trElements[i].querySelector(`#productPrice_`+productIndex);
	        console.log("productPriceElement:", productPriceElement); // productPriceElement 출력
	        
	        var productPrice = parseFloat(productPriceElement.getAttribute('data-price'));
	        console.log("productPrice:", productPrice); // productPrice 출력
	        
	        var newProductPrice = productPrice * paymentRate;
	        console.log("newProductPrice:", newProductPrice); // newProductPrice 출력

	        if (productPriceElement) {
	            productPriceElement.textContent = formatCurrency(newProductPrice) + "원";
	            console.log("productPriceElement.textContent:", productPriceElement.textContent); // productPriceElement.textContent 출력
	        }
	    }
	
	    // total 지불가격에 할인율 적용
	    var total = parseFloat(document.getElementById('total').getAttribute('data-total'));
	
	    // 총 가격 계산
	    var totalPrice = total * paymentRate; // 할인율을 적용
	    
	    // 반올림
	    totalPrice = Math.round(totalPrice);
	    
	    // 정수를 원화로 변경
	    discountPrice = formatCurrency(totalPrice);
	
	    // 결과 출력
	    console.log("총 가격: " + totalPrice + "원");
	
	    // 웹 페이지에 결과 표시
	    document.getElementById('discountTotal').innerText = discountPrice + "원";
	    
	    console.log("___________________________________________구독 기간 변경에 따른 가격 변경____________________________________________________");
	}
	
	// 통화 포맷 함수 정의
	function formatCurrency(amount) {
	    // 숫자를 문자열로 변환하여 3자리마다 쉼표(,) 삽입
	    var formattedAmount = amount.toLocaleString('ko-KR', {
	        style: 'currency',
	        currency: 'KRW'
	    });
	
	    // 통화 기호와 공백 제거
	    formattedAmount = formattedAmount.replace('₩', ''); // ₩ 기호 제거
	
	    // 결과 반환
	    return formattedAmount;
	}
	</script>
	<!-- 구독 정책에 따른 가격 할인 -->
	
	<!-- 구매 확정 -->
	<script>
		function goToPurchase(){
			var form = document.getElementById('subscriptionForm');
			
			<!-- 유저 정보 수집 -->			
			console.log("________________________goToPurchase_____________________");
			console.log("우편번호 : " + document.getElementById('subscriptionPostCode').value);
			console.log("주   소 : " + document.getElementById('subscriptionAddress').value);
			console.log("상세주소 : " + document.getElementById('subscriptionDetailAddress').value);
			console.log("________________________goToPurchase끝_____________________");
			
			var postCode = document.getElementById('subscriptionPostCode').value;
			var address = document.getElementById('subscriptionAddress').value;
			var detailAddress = document.getElementById('subscriptionDetailAddress').value;
			
			console.log("________________________goToPurchase 변수에 저장한 값_____________________");
			console.log("우편번호 : " + postCode);
			console.log("주   소 : " + address);
			console.log("상세주소 : " + detailAddress);
			console.log("________________________goToPurchase 변수에 저장한 값 끝_____________________");
			
			form.innerHTML += '<input type="hidden" name="subscriptionPostCode" value="' + postCode + '">';
			form.innerHTML += '<input type="hidden" name="subscriptionAddress" value="' + address + '">';
			form.innerHTML += '<input type="hidden" name="subscriptionDetailAddress" value="' + detailAddress + '">';	
			
			<!-- 구독기간 -->
			form.innerHTML += '<input type="hidden" name="subscriptionClosingTimes" value="' + document.getElementById('hiddenSubscriptionClosingTimes').value + '">';
			console.log("제출 전 구독기간 : " + document.getElementById('hiddenSubscriptionClosingTimes').value);
			
			var finalDiscount = parseFloat(document.getElementById('hiddendiscount').value);
			var productRows = document.querySelectorAll('tr[id^="product_"]');

			productRows.forEach(function(row) {
			    var originalPrice = parseFloat(row.querySelector('#hiddenPrice2').value);
			    console.log("현재 row의 가격정보 : " + originalPrice);
			    var discountedPrice = Math.round(originalPrice * (100 - finalDiscount) / 100);
			    console.log("할인율이 적용된 가격 정보 : " + discountedPrice);

			    form.innerHTML += '<input type="hidden" name="PID[]" value="' + row.querySelector('#hiddenProductID').value + '">';
			    form.innerHTML += '<input type="hidden" name="CID[]" value="' + row.querySelector('#hiddenCartID').value + '">';
			    form.innerHTML += '<input type="hidden" name="ancSalePrice[]" value="' + discountedPrice + '">';
			    form.innerHTML += '<input type="hidden" name="qty[]" value="' + row.querySelector('#productQuantity').innerText + '">';
			});		

			// 제출
			form.submit();
		}
	</script>
	<!-- 구매 확정 -->

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
		<h1 class="text-center text-white display-6">구독</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- Checkout Page Start -->
	<div class="container-fluid py-5">
		<div class="container py-5">
			<h1 class="mb-4">Billing details</h1>
			<form action="/subscriptionCompPage" name="subscriptionForm" id="subscriptionForm" method="POST">
				<div class="row g-5">
					<div class="col-md-12 col-lg-6 col-xl-5">
						<c:set var="memberInfo" value="${requestScope.memberInfo}" />
						<div class="row">
							<div class="col-md-12 col-lg-12">
								<div class="form-item w-100">
									<label class="form-label my-3"></label> <input class="form-control p-3" type="text" id="name" value="${memberInfo.memberName}">
								</div>
							</div>
						</div>
						<div class="form-item w-100">
							<label class="form-label my-3"></label> <input class="form-control p-3" type="text" id="phoneNum" value="${memberInfo.phoneNumber}">
						</div>
						<div class="form-item w-100">
							<label class="form-label my-3"></label> <input class="form-control p-3" type="text" id="email" value="${memberInfo.email}">
						</div>
						<div class="row">
							<label class="form-label my-3"></label>
							<div class="col-lg-8">
								<input class="form-control p-3 border-secondary" type="number" id="subscriptionPostCode" value="${memberInfo.ancShippingPostCode}" readonly required />
							</div>
							<div class="col-lg-4">
								<input class="btn border border-secondary text-primary rounded-pill px-4 py-3" type="button" onClick="goPopup()" value="우편번호 찾기" />
							</div>
						</div>
						<div class="row">
							<label class="form-label my-3"></label>
							<div class="col-lg-6">
								<input class="form-control p-3" type="text" id="subscriptionAddress" value="${memberInfo.ancShippingAddress}" readonly required />
							</div>
							<div class="col-lg-6">
								<input class="form-control p-3" type="text" id="subscriptionDetailAddress" value="${memberInfo.ancShippingAddressDetail}" readonly required />
							</div>
						</div>
					</div>
					<div class="col-md-12 col-lg-6 col-xl-7">
						<div class="table-responsive">
							<table class="productTable">
								<thead>
									<tr class="border-bottom">
										<th class="col-md-1" scope="col">상품</th>
										<th scope="col">이름</th>
										<th scope="col">수량</th>
										<th scope="col">판매 금액</th>
										<th scope="col">결제 금액</th>
									</tr>
								</thead>
								<tbody>
									<c:set var="total" value="0" />
									<c:forEach var="product" items="${buyProducts}" varStatus="status">
										<tr class="border-bottom" id="product_${status.index}">
											<th scope="row">
												<div class="d-flex align-items-center mt-2">
													<img src="${product.ancImagePath}" class="img-fluid rounded-circle" style="width: 90px; height: 90px;" alt="">
												</div>
											</th>
											<td class="py-5">${product.ancProductName}</td>
											<td class="py-5" id="productQuantity">${product.productQuantity}</td>
											<td class="py-5" style="position: relative;">
											    <div style="position: absolute; top: 50%; left: 0; right: 0; border-top: 1px solid black;"></div>
											    <div style="position: relative; z-index: 1;"> <!-- 데이터 부분 -->
											        <fmt:formatNumber value="${product.ancSalePrice*product.productQuantity}" currencyCode="KRW" />원
											    </div>
											</td>
											<td class="py-5" id="productPrice_${status.index}" data-price="${product.ancSalePrice*product.productQuantity}"><fmt:formatNumber value="${product.ancSalePrice*product.productQuantity}" currencyCode="KRW" />원</td>
											
											<td><input type="hidden" id="hiddenPrice" value="${product.ancSalePrice}"></td>
											<td><input type="hidden" id="hiddenPrice2" value="${product.ancSalePrice}"></td>
											<td><input type="hidden" id="hiddendiscount" value= 0 ></td>
											<td><input type="hidden" id="hiddenProductID" value="${product.productID}"></td>
											<td><input type="hidden" id="hiddenCartID" value="${product.cartID}"></td>
											<td><input type="hidden" id="hiddenSubscriptionClosingTimes" value="${product.cartID}"></td>
											<c:set var="total" value="${total +(product.ancSalePrice*product.productQuantity)}" />
										</tr>
									</c:forEach>
									<tr>
										<td style="white-space: nowrap;">구독 기간</td>
										<td>										
										<select id="subscriptionDuration" onchange="onChangeSelect()" style="border-radius: 10px; border: 2px solid lime;">
										    <c:forEach var="policy" items="${subscriptionPolicy}" varStatus="status">
										        <option value="${policy.discountRate}" data-index="${status.index}">${policy.upperLimitMonth}개월(${policy.discountRate}%할인))</option>
										    </c:forEach>
										</select>
										</td>
										<td></td>
										<td class="py-5">
											<p class="mb-0 text-dark text-uppercase py-3">할인 전</p>
											<p class="mb-0 text-dark text-uppercase py-3">할인 후</p>
										</td>
										<td class="py-5">
											<div class="py-2">
												<p class="mb-0 text-dark text-uppercase py-3" id="total" data-total="${total}"><fmt:formatNumber value="${total}" currencyCode="KRW" />원</p>
												<p class="mb-0 text-dark text-uppercase py-3" id="discountTotal" data-total="${total}"><fmt:formatNumber value="${total}" currencyCode="KRW" />원</p>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="table-responsive my-3">
							<table class="table" id="usedCoponList">
							</table>
						</div>
						<div class="row g-4 text-center align-items-center justify-content-center pt-4">
							<button class="btn border-secondary py-3 px-4 text-uppercase w-50 text-primary" type="button" onclick="goToPurchase()">구독시작</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- Checkout Page End -->

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