<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="pageName" %>

<script src="/resources/admin/assets/vendor/libs/jquery/jquery.js"></script>

<!-- 페이지별 변수 설정 -->
<c:if test='${pageName eq "productList"}'>
  <c:set var="sortDisplayList" value='${fn:split("등록일순,판매 가격순,판매 상태순", ",")}' />
  <c:set var="sortCodeList" value='${fn:split("registerDate,salePrice,saleState", ",")}' />
  <c:set var="placeholder" value="상품명 검색" />
  <c:set var="asyncUrl" value="/productList/searchAndSort" />
</c:if>
<c:if test='${pageName eq "memberList"}'>
  <c:set var="sortDisplayList" value='${fn:split("회원 ID 오름차순,회원 ID 내림차순", ",")}' />
  <c:set var="sortCodeList" value='${fn:split("ASC,DESC", ",")}' />
  <c:set var="placeholder" value="회원 ID 검색" />
  <c:set var="asyncUrl" value="/memberList/searchAndSort" />
</c:if>
<c:if test='${pageName eq "couponDownload"}'>
  <c:set var="sortDisplayList" value='${fn:split("배포 상태순,배포 마감일순,쿠폰 만료일순,할인순", ",")}' />
  <c:set var="sortCodeList" value='${fn:split("deployStatus,deployDeadline,expirationDate,discount", ",")}' />
  <c:set var="placeholder" value="쿠폰 이름 검색" />
  <c:set var="asyncUrl" value="/couponDownload/searchAndSort" />
</c:if>
<!-- / 페이지별 변수 설정 -->

<nav class="navbar navbar-expand-lg navbar-light mb-3">
  <div class="container-fluid">
    <form class="collapse navbar-collapse" onsubmit="asyncSubmit('${asyncUrl}'); return false;" action="${asyncUrl}" method="GET" id="search-sort-form">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <div class="d-flex">
            <input class="form-control me-2" type="search" name="searchKeyword" id="search-keyword" value="" placeholder="${placeholder}" aria-label="Search">
            <button class="btn btn-outline-primary" type="submit">Search</button>
          </div>
        </li>
      </ul>
      <div class="btn-group">
        <button
          type="button"
          class="btn btn-outline-primary dropdown-toggle"
          id="dropdown-sort-btn"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          000순
        </button>
        <input type="hidden" name="sortColumnName" value="" id="sort-column-name" />
        <ul class="dropdown-menu">
          <c:forEach var="sortDisplay" items="${sortDisplayList}" varStatus="status">
            <li>
              <button type="submit" class="dropdown-item" onclick="handleSort('${sortDisplay}', '${sortCodeList[status.index]}')" id="dropdown-sort-item-${status.index}">${sortDisplay}</button>
            </li>
          </c:forEach>
        </ul>
      </div>
    </form>
  </div>
</nav>

<script>
function handleSort(sortDisplay, sortCode) {
	// Dropdown btn에 보이는 문자 수정
	let btn = $('#dropdown-sort-btn');
	btn.empty();
	btn.text(sortDisplay);
	
	// Controller에 submit할 value 추가
	let sortColumnName = $("#sort-column-name");
	sortColumnName.val(sortCode);
//	asyncSubmit();
}

function asyncSubmit(asyncUrl) {
	let searchKeyword = $("#search-keyword");
	let sortColumnName = $("#sort-column-name");
	console.log("search-keyword: " + searchKeyword.val());
	console.log("sort-column-name: " + sortColumnName.val());
	
	$.ajax({
		type : "GET",
		dataType : "json",
		url : asyncUrl,
		data : {
			"searchKeyword" : searchKeyword.val(),
			"sortColumnName" : sortColumnName.val()
		},
		success : function(datas) {
			console.log("curFile after ajax: " + curFile);
			
			curDatas = datas;
			totalPage = calculTotalPage(curDatas.length);
			curPage = 1;
			startPage = 1;
			endPage = 1;
			
			// 별도 멤버 변수 추가가 필요한 경우
			if (curFile == "productList.jsp") {
	            $.each(datas, function(dataIdx, data) {
					console.log("pid : " + data.productID);
					console.log("pname : " + data.productName);
					console.log("data.ancCategory: " + data.ancCategory);
					if (data.ancCategory != null) {
						data.categories = data.ancCategory.split(";");
					} else {
						data.categories = [];
					}
	            });
			} else if (curFile == "memberList.jsp") {
	            $.each(datas, function(dataIdx, data) {
					console.log("mid : " + data.memberID);
					console.log("mname : " + data.memberName);
					console.log("data.ancCategoryName: " + data.ancCategoryName);
					if (data.ancCategoryName != null) {
						data.categories = data.ancCategoryName.split(";");
					} else {
						data.categories = [];
					}
	            });
			} else if (curFile == "couponDownload.jsp") {
	            $.each(datas, function(dataIdx, data) {
					console.log("cid : " + data.couponID);
					console.log("cname : " + data.couponName);
					console.log("data.ancCategoryName: " + data.ancCategoryName);
					if (data.ancCategoryName != null) {
						data.categories = data.ancCategoryName.split(";");
					} else {
						data.categories = [];
					}
	            });
	            $.each(curDatas, function(dataIdx, data) {
		            if (data.couponType === 'PERCENTAGE') {
		            	data.couponType = '할인율';
		            	data.ancDiscount = data.ancDiscount + '%';
		            	data.ancAmount = '최대 ' + data.ancAmount + '원 할인';
		            } else if (data.couponType === 'WON') {
		            	data.couponType = '할인액';
		            	data.ancDiscount = data.ancDiscount + '원  ';
		            	data.ancAmount = '최소 ' + data.ancAmount + '원 구매';
		            } else {
		            	// TODO: error handle
		            	console.log('unknown couponType : ' + data.couponType);
		            }
		            if (data.ancDeployStatus === 'WILL') {
		            	data.ancDeployStatus = '배포 예정';
		            } else if (data.ancDeployStatus === 'DOING') {
		            	data.ancDeployStatus = '배포중';
		            } else if (data.ancDeployStatus === 'STOP') {
		            	data.ancDeployStatus = '배포 중단';
		            } else if (data.ancDeployStatus === 'DONE') {
		            	data.ancDeployStatus = '배포 완료';
		            } else {
		            	// TODO: error handle
		            	console.log('unknown ancDeployStatus : ' + data.ancDeployStatus);
		            }
		            
		            
					// 원하는 날짜 포맷&시간대로 포맷팅
		            const dateString = data.ancDeployDeadline;
					const date = new Date(dateString);

					data.ancDeployDeadline = date.getFullYear() + "-" +
					                      ("0" + (date.getMonth() + 1)).slice(-2) + "-" +
					                      ("0" + date.getDate()).slice(-2) + "T" +
					                      "00:00";
		            
	            });
			}
			
			console.log('curFile : ' + curFile);
			composePage(curDatas, curFile, "");
		}
	});
}

// Dropdown 초기화
(function () {
	let btn = $('#dropdown-sort-btn');
	let item0 = $('#dropdown-sort-item-0').html();
	
	btn.empty();
	btn.text(item0);
})();
</script>