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

<!-- jquery -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

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
<link href="/resources/user/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
<link href="/resources/user/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

<!-- Customized Bootstrap Stylesheet -->
<link href="/resources/user/css/bootstrap.min.css" rel="stylesheet">

<!-- Template Stylesheet -->
<link href="/resources/user/css/style.css" rel="stylesheet">
<link href="/resources/user/css/table.css" rel="stylesheet">

<!-- 파비콘 -->
<custom:favicon/>
</head>
<body>

<style>
    .table tbody tr:hover {
        background-color: #f0f0f0; /* 마우스 오버 시 배경색을 변경합니다 */
    }
</style>


	<!-- 구독 상세 모달 비동기 -->
	<script>
		function subscriptionDetail(subscriptionInfoID) {
		    $.ajax({
		        type: "GET",
		        url: "subscriptionDetail/detail",
		        data: { 'subscriptionInfoID': subscriptionInfoID },
		        success: function (subscriptionInfo) {
		            if (subscriptionInfo !== "null") {
		                // 테이블 헤더 생성
		                var tableHtml = '<table class="table">';
		                tableHtml += '<thead>';
		                tableHtml += '<tr>';
		                tableHtml += '<th>상품명</th>';
		                tableHtml += '<th>구독 수량</th>';
		                tableHtml += '<th>상품 단가</th>';
		                tableHtml += '</tr>';
		                tableHtml += '</thead>';
		                tableHtml += '<tbody>';
	
		                // 데이터 추가
		                subscriptionInfo.forEach(function (subscriptionInfo) {
		                    tableHtml += '<tr>';
		                    tableHtml += '<td>' + subscriptionInfo.ancProductName + '</td>';
		                    tableHtml += '<td>' + subscriptionInfo.quantity + '</td>';
		                    tableHtml += '<td>' + subscriptionInfo.purchasePrice.toLocaleString('ko-KR') + '</td>';
		                    tableHtml += '</tr>';
		                });
	
		                tableHtml += '</tbody>';
		                tableHtml += '</table>';
	
		                // 모달 창에 테이블 추가
		                Swal.fire({
		                    title: '구독 상세 정보',
		                    html: tableHtml
		                });
		            } else {
		                Swal.fire({
		                    icon: 'error',
		                    title: '구독 상세 정보',
		                    text: '정보를 불러오는 데 실패했습니다.'
		                });
		            }
		        }
		    });
		}
	</script>
	<!-- 구독 상세 모달 비동기 -->
	
	<!-- 주소 api 자바스크립트 -->
	<script>

    function goPopup(index, subscriptionInfoID) {
    	
    	console.log("선택된 index번호 : " + index);
    	console.log("해당 index번호의 PK값 : " + subscriptionInfoID);
    	
        var url = "subscriptionJusoPopup.jsp?index=" + index + "&subscriptionInfoID=" + subscriptionInfoID;
        var pop = window.open(url, "pop", "width=570,height=420, scrollbars=yes, resizable=yes");
        
    }

		function jusoCallBack(zipNo, roadAddrPart1, addrDetail, subscriptionInfoID, index) {
			
			var subscriptionInfoID = subscriptionInfoID;
			var postCode = zipNo;
			var address = roadAddrPart1;
			var detailAddress = addrDetail;
			
		    $.ajax({
		        type: "POST",
		        url: "subscriptionDetail/updateAddress",
		        data: { 'subscriptionInfoID': subscriptionInfoID,
		        		'subscriptionPostCode': postCode,
		        		'subscriptionAddress': address,
		        		'subscriptionDetailAddress': detailAddress,},
		        success: function (subscriptionInfo) {
		            if (subscriptionInfo === "suc") {
		                Swal.fire({
							icon : 'success',
							title : '정기 배송지 변경',
							text : '성공.',
		                });
		            } else {
		                Swal.fire({
							icon : 'error',
							title : '정기 배송지 변경',
							text : '실패.',
		                });
		            }
		        }
		    });
		
			document.getElementById('updateAddress_' + index).innerText = address + ' ' + detailAddress;
		}
	</script>
	<!-- 주소 api 자바스크립트 -->

	<%-- 세션 확인 후 없으면 메인으로 --%>
	<custom:emthySessionAndGoToMain/>


	<!-- Spinner Start -->
	<div id="spinner" class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
		<div class="spinner-grow text-primary" role="status"></div>
	</div>
	<!-- Spinner End -->


	<!-- 로고가 포홤된 헤더 시작 -->
	<div class="container-fluid fixed-top">
			<custom:commonHeader />		
			<custom:myPageHeaderWithLogo />		
	</div>
	<!-- 로고가 포홤된 헤더 끝 -->


	<!-- Single Page Header start -->
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6">구독내역</h1>
	</div>
	<!-- Single Page Header End -->


	<!-- Cart Page Start -->
	<div class="container-fluid py-5">
		<div class="container py-5">
			<div class="table-responsive">
				<table class="table">
					<thead>
						<tr>
							<th scope="col">구독 시작일</th>
							<th scope="col">결제 예정 금액</th>
							<th scope="col">결제 예정일</th>
							<th scope="col">구독 종료 예정일</th>
							<th scope="col">배송지</th>
							<th scope="col">배송지 변경</th>
							<th scope="col">구독 취소</th>
						</tr>
					</thead>
						<tbody>
							<c:forEach var="subscriptionInfo" items="${subscriptionInfos}" varStatus="status">
							    <tr>
							        <td onclick='subscriptionDetail(${subscriptionInfo.subscriptionInfoID})' style="cursor: pointer;">
							            <p id="subscriptionDetail_${status.index}" class="mb-0 mt-4">${subscriptionInfo.beginDate}</p>
							        </td>
							        <td onclick='subscriptionDetail(${subscriptionInfo.subscriptionInfoID})' style="cursor: pointer;">
							            <p id="subscriptionDetail_${status.index}" class="mb-0 mt-4"><fmt:formatNumber value="${subscriptionInfo.ancTotalPrice}" currencyCode="KRW" />원</p>
							        </td>
							        <td onclick='subscriptionDetail(${subscriptionInfo.subscriptionInfoID})' style="cursor: pointer;">
							            <p id="subscriptionDetail_${status.index}" class="mb-0 mt-4">${subscriptionInfo.nextPaymentDate}</p>
							        </td>
							        <td onclick='subscriptionDetail(${subscriptionInfo.subscriptionInfoID})' style="cursor: pointer;">
							            <p id="subscriptionDetail_${status.index}" class="mb-0 mt-4">${subscriptionInfo.ancLastPaymentDate}</p>
							        </td>
							        <td>
							            <p onclick='subscriptionDetail(${subscriptionInfo.subscriptionInfoID})' style="cursor: pointer;" class="mb-0 mt-4" id="updateAddress_${status.index}">${subscriptionInfo.subscriptionAddress} ${subscriptionInfo.subscriptionDetailAddress}</p>
							        </td>
							        <td onclick='goPopup(${status.index}, ${subscriptionInfo.subscriptionInfoID})' style="cursor: pointer;">
							            <a class="btn border-secondary text-primary rounded-pill mb-0 mt-3" >배송지 변경</a>
							        </td>
							        <td>
							            <a class="btn border-secondary text-primary rounded-pill mb-0 mt-3" onclick='location.href="/subscriptionDetail/delete?subscriptionInfoID=${subscriptionInfo.subscriptionInfoID}";'>구독 취소</a>                                
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
	<script src="/resources/user/lib/easing/easing.min.js"></script>
	<script src="/resources/user/lib/waypoints/waypoints.min.js"></script>
	<script src="/resources/user/lib/lightbox/js/lightbox.min.js"></script>
	<script src="/resources/user/lib/owlcarousel/owl.carousel.min.js"></script>

	<!-- Template Javascript -->
	<script src="/resources/user/js/main.js"></script>
</body>

</html>