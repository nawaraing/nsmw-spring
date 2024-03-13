<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<!DOCTYPE html>
<html>
<head>
<!-- <meta charset="utf-8"> -->
<title>구매</title>
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
<link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
<link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

<!-- sweetalert2 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.10/dist/sweetalert2.min.js"></script>

<!-- Customized Bootstrap Stylesheet -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="css/style.css" rel="stylesheet">
<link href="css/number.css" rel="stylesheet">
<link href="css/table.css" rel="stylesheet">

<!-- 파비콘 -->
<custom:favicon/>
</head>

<body>

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
			var pop = window.open("jusoPopup.jsp", "pop",
					"width=570,height=420, scrollbars=yes, resizable=yes");
			//** 2017년 5월 모바일용 팝업 API 기능 추가제공 **/
			// 모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서
			// 실제 주소검색 URL(https://business.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
			// var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
		}

		function jusoCallBack(roadAddrPart1, addrDetail, zipNo) {
			// 2017년 2월 제공항목이 추가되었습니다. 원하시는 항목을 추가하여 사용하시면 됩니다.
			document.buyForm.roadAddrPart1.value = roadAddrPart1;
			document.buyForm.addrDetail.value = addrDetail;
			document.buyForm.zipNo.value = zipNo;
		}
	</script>
	<!-- 주소 api 자바스크립트 -->


	<!--  쿠폰 모달  -->
	<script>
		$(document).ready(function() {
			// 모달 열기
			$("#openModalButton").click(function() {
				$("#couponModal").fadeIn();
			});
			// 모달 닫기
			$("#closeModalButton").click(function() {
				$("#couponModal").fadeOut();
			});
		});
	</script>
	<!--  쿠폰 모달  -->


	<!-- 할인 금액 계산 -->
	<script>
		function applyCoupon() {
			$('#couponModal').modal('hide');
			// 체크된 쿠폰들의 정보 수집
			var selectedCoupons = [];
			var products = [];
			$("input[type=checkbox]:checked").each(
				function() {
					var couponName = $(this).closest("tr").find("td:eq(1)").text();
					var discount = $(this).closest("tr").find("td:eq(2)").text();
					var period = $(this).closest("tr").find("td:eq(3)").text();
					var category = $(this).closest("tr").find("td:eq(4)").text();
					var CPID = $(this).closest("tr").find("#hiddenCPID").val();
					console.log("[로그] CPID:" + CPID);
					selectedCoupons.push({
						couponName : couponName, 
						discount : discount, 
						period : period,
						category : category,
						CPID : CPID
					});
				});
			
            var usedCouponList = $("#usedCoponList");
            usedCouponList.empty();
            usedCouponList.prepend('<thead>' +
            							'<tr>' +
            								'<th>' +
            									'쿠폰명' +
            								'</th>' +
            								'<th>' +
            									'할인율' +
            								'</th>' +
            								'<th>' +
            									'카테고리' +
            								'</th>' +
            							'</tr>' +
            						'</thead>');
            selectedCoupons.forEach(function(coupon) {
			    var couponHTML = 
			    				"<td>" + 
			    					coupon.couponName + 
			    				"</td>" +
			  					"<td id='couponDiscount'>" + 
			  						coupon.discount + 
			  					"</td>" +
			   					"<td id='couponCategory'>" + 
			   						coupon.category + 
			   					"</td>";
			   	couponHTML += "<td>" +
			   					"<input type='hidden' id='selectedHiddenCPID' value='" + coupon.CPID + "'>" + 
			   				  "</td>";
			   	usedCouponList.append('<tbody>' + 
			   							'<tr id="coupon_">' + 
			   								couponHTML + 
			   							'</tr>' + 
			   						  '</tbody>');
			});
			
			// 각 상품 행을 순회하면서 상품 정보 수집
			$(".productTable tbody tr").each(function() {
				// 상품 이름과 가격을 각각 변수에 저장
				var productPrice = $(this).find("#hiddenPrice").val();
				var productCategory = $(this).find("#hiddenCategory").val();
				// 수집된 상품 정보를 객체로 생성하여 배열에 추가
				var product = {
			 		price: productPrice,
			 		category : productCategory
				};	
				products.push(product);
			});
			
			var index = 0;
	        products.forEach(function(product) {
	            selectedCoupons.forEach(function(coupon) {
	                if (product.category == coupon.category.trim()) {
	                    // 해당 상품의 카테고리와 일치하는 쿠폰이 있으면 할인 적용
	                    product.price -= (product.price * parseFloat(coupon.discount)) / 100;
	                   	$("#productPrice_" + index).text(product.price.toLocaleString('ko-KR') + "" + "원");
	                }
	            });
	            index = index + 1;
	        });
	        
	        var total = 0;
	        products.forEach(function(product) {
	        	if (!isNaN(parseFloat(product.price))) {
		        	/* console.log('product.price : ' + product.price); */
		        	total += parseFloat(product.price);
	        	}
	        });
           	$("#total").text(total.toLocaleString('ko-KR') + "" + "원");
	        /* console.log('total: ' + total); */
 		}		
	</script>
	<!-- 할인 금액 계산 -->


	<!-- 구매 버튼 -->
	<script>
		function checkQty() {
			var PID = [];
			var qty = [];
			
			var productRows = document.querySelectorAll('tr[id^="product_"]');
			productRows.forEach(function(row) {
				<!-- 상품 정보 수집 -->
				PID.push(row.querySelector('#hiddenPID').value);
            	qty.push(row.querySelector('#pQty').innerText);
			});
			console.log('PID[] : ' + PID);
			console.log('qty[] : ' + qty);
			
			$.ajax({
				type : "POST", // 또는 "GET"
				url : "checkQty",
				data : {
					'PID[]' : PID,
					'qty[]' : qty,
				},
				success : function(data) {
					MIDResult = data
					if (data === "suc") {
						goToPurchase();
					} else {
						Swal.fire({
							icon : 'error',
							title : '재고 부족',
							text : '상품의 재고가 부족합니다',
						}).then(result => {
						   if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
							   location.replace("cartPage.do");
						   }
						});
					}
				}
			});
		}
	</script>
	<!-- 구매 버튼 -->

	<!-- 구매 확정 -->
	<script>
		function goToPurchase(){
			var form = document.getElementById('buyForm');
			
			<!-- 유저 정보 수집 -->
			form.innerHTML += '<input type="hidden" name="bPostcode" value="' + document.getElementById('zipNo').value + '">';
			form.innerHTML += '<input type="hidden" name="bAddress" value="' + document.getElementById('roadAddrPart1').value + '">';
			form.innerHTML += '<input type="hidden" name="bDetailedAddress" value="' + document.getElementById('addrDetail').value + '">';
			
			var productRows = document.querySelectorAll('tr[id^="product_"]');
			productRows.forEach(function(row) {
				<!-- 상품 정보 수집 -->
				form.innerHTML += '<input type="hidden" name="PID[]" value="' + row.querySelector('#hiddenPID').value + '">';
				form.innerHTML += '<input type="hidden" name="CID[]" value="' + row.querySelector('#hiddenCID').value + '">';
				form.innerHTML += '<input type="hidden" name="sellingPrice[]" value="' + row.querySelector('#hiddenPrice').value + '">';
            	form.innerHTML += '<input type="hidden" name="productCategory[]" value="' + row.querySelector('#hiddenCategory').value + '">';
            	form.innerHTML += '<input type="hidden" name="qty[]" value="' + row.querySelector('#pQty').innerText + '">';
			});
			var couponRows = document.querySelectorAll('tr[id^="coupon_"]');
			couponRows.forEach(function(row) {
			    <!-- 쿠폰 정보 수집 -->
			    form.innerHTML += '<input type="hidden" name="CPID[]" value="' + row.querySelector('#selectedHiddenCPID').value + '">';
			    form.innerHTML += '<input type="hidden" name="discount[]" value="' + row.querySelector('#couponDiscount').innerText.replace('%', '') + '">';
			    form.innerHTML += '<input type="hidden" name="couponCategory[]" value="' + row.querySelector('#couponCategory').innerText + '">';
			});
			
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


	<!-- 쿠폰 모달 -->
	<div class="modal fade" id="couponModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-fullscreen">
			<div class="modal-content rounded-0">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">쿠폰 적용</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body d-flex align-items-center">
					<div class="input-group w-75 mx-auto d-flex">
						<table class="table">
							<thead>
								<tr>
									<th scope="col"></th>
									<th scope="col">쿠폰이름</th>
									<th scope="col">할인율</th>
									<th scope="col">만료기간</th>
									<th scope="col">카테고리</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="coupon" items="${couponList}">
									<c:forEach var="product" items="${selectedProductsList}">
										<c:if test="${coupon.category eq product.category}">
											<tr>
												<td>
													<p class="mb-3 mt-4"><input type="checkbox"></p>
												</td>
												<td>
													<p class="mb-0 mt-4">${coupon.cpName}</p>
												</td>
												<td>
													<p class="mb-0 mt-4">${coupon.discount}%</p>
												</td>
												<td>
													<p class="mb-0 mt-4">${coupon.period}</p>
												</td>
												<td>
													<p class="mb-0 mt-4">${coupon.category}</p>
												</td>
												<td><input type="hidden" id="hiddenCPID" value="${coupon.CPID}"></td>
											</tr>
										</c:if>
									</c:forEach>
								</c:forEach>
							</tbody>
						</table>
						<div class="mx-auto d-flex">
							<button class="btn border border-secondary text-primary rounded-pill px-4 py-3" type="button" onclick="applyCoupon()">적용하기</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 쿠폰 모달 -->


	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6">구매</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- Checkout Page Start -->
	<div class="container-fluid py-5">
		<div class="container py-5">
			<h1 class="mb-4">Billing details</h1>
			<form action="buyCompPage.do" name="buyForm" id="buyForm" method="POST">
				<div class="row g-5">
					<div class="col-md-12 col-lg-6 col-xl-5">
						<c:set var="memberInfo" value="${requestScope.memberInfo}" />
						<div class="row">
							<div class="col-md-12 col-lg-12">
								<div class="form-item w-100">
									<label class="form-label my-3"></label> <input class="form-control p-3" type="text" id="name" value="${memberInfo.mName}">
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
								<input class="form-control p-3 border-secondary" type="number" id="zipNo" name="zipNo" value="${memberInfo.mPostCode}" readonly required />
							</div>
							<div class="col-lg-4">
								<input class="btn border border-secondary text-primary rounded-pill px-4 py-3" type="button" onClick="goPopup()" value="우편번호 찾기" />
							</div>
						</div>
						<div class="row">
							<label class="form-label my-3"></label>
							<div class="col-lg-6">
								<input class="form-control p-3" type="text" id="roadAddrPart1" name="roadAddrPart1" value="${memberInfo.mAddress}" readonly required />
							</div>
							<div class="col-lg-6">
								<input class="form-control p-3" type="text" id="addrDetail" name="addrDetail" value="${memberInfo.mDetailedAddress}" readonly required />
							</div>
						</div>
						<div class="col-lg-4 my-3">
							<button class="btn border border-secondary text-primary rounded-pill px-4 py-3" type="button" data-bs-toggle="modal" data-bs-target="#couponModal" value="쿠폰 적용">쿠폰적용</button>
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
									<c:forEach var="product" items="${selectedProductsList}" varStatus="status">
										<tr class="border-bottom" id="product_${status.index}">
											<th scope="row">
												<div class="d-flex align-items-center mt-2">
													<img src="${product.imagePath}" class="img-fluid rounded-circle" style="width: 90px; height: 90px;" alt="">
												</div>
											</th>
											<td class="py-5">${product.pName}</td>
											<td class="py-5" id="pQty">${product.pQty}</td>
											<td class="py-5"><fmt:formatNumber value="${product.sellingPrice*product.pQty}" currencyCode="KRW" />원</td>
											<td class="py-5" id="productPrice_${status.index}"><fmt:formatNumber value="${product.sellingPrice*product.pQty}" currencyCode="KRW" />원</td>
											<td><input type="hidden" id="hiddenPrice" value="${product.sellingPrice*product.pQty}"></td>
											<td><input type="hidden" id="hiddenCategory" value="${product.category}"></td>
											<td><input type="hidden" id="hiddenPID" value="${product.PID}"></td>
											<td><input type="hidden" id="hiddenCID" value="${product.ancCID}"></td>
											<c:set var="total" value="${total +(product.sellingPrice*product.pQty)}" />
										</tr>
									</c:forEach>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td class="py-5">
											<p class="mb-0 text-dark text-uppercase py-3">TOTAL</p>
										</td>
										<td class="py-5">
											<div class="py-2">
												<p class="mb-0 text-dark" id="total"><fmt:formatNumber value="${total}" currencyCode="KRW" />원</p>
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
							<button class="btn border-secondary py-3 px-4 text-uppercase w-50 text-primary" type="button" onclick="checkQty()">구매</button>
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
	<script src="lib/easing/easing.min.js"></script>
	<script src="lib/waypoints/waypoints.min.js"></script>
	<script src="lib/lightbox/js/lightbox.min.js"></script>
	<script src="lib/owlcarousel/owl.carousel.min.js"></script>

	<!-- Template Javascript -->
	<script src="js/main.js"></script>
</body>

</html>