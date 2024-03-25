<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<!DOCTYPE html>


<html>
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"/>

    <title>판매통계 - 기간별</title>

    <meta name="description" content="기간별 판매통계 페이지입니다" />

    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap" rel="stylesheet" />

    <!-- Icons. Uncomment required icon fonts -->
    <link rel="stylesheet" href="/resources/admin/assets/vendor/fonts/boxicons.css" />

    <!-- Core CSS -->
    <link rel="stylesheet" href="/resources/admin/assets/vendor/css/core.css" class="template-customizer-core-css" />
    <link rel="stylesheet" href="/resources/admin/assets/vendor/css/theme-default.css" class="template-customizer-theme-css" />
    <link rel="stylesheet" href="/resources/admin/assets/css/demo.css" />

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="/resources/admin/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />

    <link rel="stylesheet" href="/resources/admin/assets/vendor/libs/apex-charts/apex-charts.css" />

    <!-- Page CSS -->

    <!-- Helpers -->
    <script src="/resources/admin/assets/vendor/js/helpers.js"></script>

    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="/resources/admin/assets/js/config.js"></script>
    
    <%-- 파비콘 --%>
    <custom:favicon/>
    
  </head>

  <body>
    <script>
    /*
    * 검색 기능
    */
    
    let totalPage = 1;
    let curPage = 1;
    let startPage = 1;
    let endPage = 1;
    
    let curDatas = [];
    let curType = "date";
    
    function searchDate() {
        let startDate = $('#start-date-input').val(); // 시작일 입력 필드의 값 가져오기
        let endDate = $('#end-date-input').val(); // 종료일 입력 필드의 값 가져오기
        $.ajax({
            type : "GET",
            dataType : "json",
            url : "/statDate/searchDate",
            data : {
                "startDate" : startDate,
                "endDate" : endDate
            },
            success : function(datas) {
            	curPage = 1;
            	drawPage(datas, "date");
            	// 페이지네이션을 위한 저장
            }
        });
    }
    function searchMonth() {
        let startMonth = $('#start-month-input').val(); // 시작일 입력 필드의 값 가져오기
        let endMonth = $('#end-month-input').val(); // 종료일 입력 필드의 값 가져오기
        $.ajax({
            type : "GET",
            dataType : "json",
            url : "/statDate/searchMonth",
            data : {
                "startMonth" : startMonth,
                "endMonth" : endMonth
            },
            success : function(datas) {
            	curPage = 1;
            	drawPage(datas, "month");
            	// 페이지네이션을 위한 저장
            }
        });
    }
    </script>
  
  
  
    <!-- Layout wrapper -->
    <div class="layout-wrapper layout-content-navbar">
      <div class="layout-container">
        <custom:adminSidebar pageName='statDate'/>

        <!-- Layout container -->
        <div class="layout-page">
          <custom:adminNav/>

          <!-- Content wrapper -->
          <div class="content-wrapper">
            <div class="container-xxl flex-grow-1 container-p-y">
              <div class="card mb-4">
                <div class="card-body">
                  <div class="row">
                    <!-- JavaScript Behavior -->
                    <div class="mb-4 mb-xl-0">
                      <div class="mt-3">
                        <div class="row">
                          <div class="col-md-2 col-6 mb-3 mb-md-0">
                            <div class="list-group">
                              <a
                                class="list-group-item list-group-item-action active"
                                id="list-date-list"
                                data-bs-toggle="list"
                                href="#list-date"
                                >일별 통계</a
                              >
                              <a
                                class="list-group-item list-group-item-action"
                                id="list-month-list"
                                data-bs-toggle="list"
                                href="#list-month"
                                >월별 통계</a
                              >
                            </div>
                          </div>
                          <div class="col-md-10 col-12">
                            <div class="tab-content p-0">
                              <div class="tab-pane fade show active" id="list-date">
                                <div class="row">
                                  <label for="start-date-input" class="col-md-2 col-form-label">검색 기간</label>
                                  <form id="dateForm" action="/statDate/searchDate" method="GET">
                                    <div class="row">
                                      <!-- Start Date -->
                                      <div class="col-md-5 mb-4">
                                        <input class="form-control" type="date" name="startDate" value="2021-06-18" id="start-date-input" />
                                      </div>
                                      <!-- / Start Date -->
                                      <!-- End Date -->
                                      <div class="col-md-5 mb-4">
                                        <input class="form-control" type="date" name="endDate" value="2021-06-18" id="end-date-input" />
                                      </div>
                                      <!-- / End Date -->
                                      <button type="button" class="btn rounded-pill btn-primary col-2 mb-4" onclick="searchDate()">검색</button>
                                    </div>
                                  </form>
                                </div>  
                              </div>
                              <div class="tab-pane fade show" id="list-month">
                                <div class="row">
                                  <label for="start-month-input" class="col-md-2 col-form-label">검색 기간</label>
                                  <form id="dateForm" action="/statDate/searchMonth" method="GET">
                                    <div class="row">
                                      <!-- Start Date -->
                                      <div class="col-md-5 mb-4">
                                        <input class="form-control" type="month" name="startMonth" value="2021-06" id="start-month-input" />
                                      </div>
                                      <!-- / Start Date -->
                                      <!-- End Date -->
                                      <div class="col-md-5 mb-4">
                                        <input class="form-control" type="month" name="endMonth" value="2021-06" id="end-month-input" />
                                      </div>
                                      <!-- / End Date -->
                                      <button type="button" class="btn rounded-pill btn-primary col-2 mb-4" onclick="searchMonth()">검색</button>
                                    </div>
                                  </form>
                                </div>  
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <!-- / JavaScript Behavior -->
                  </div>
                </div>
              </div>
              
              <!-- Striped Table -->
              <div class="mb-4">
                <div class="card">
                  <h5 class="card-header" id="table-card-header">일별 통계</h5>
                  <div class="table-responsive text-nowrap">
                    <table class="table table-striped">
                      <thead>
                        <tr>
                          <th>날짜</th>
                          <th>매출(₩)</th>
                          <th>이익(₩)</th>
                        </tr>
                      </thead>
                      <tbody class="table-border-bottom-0" id="table-body">
                      <c:if test="${not empty dailySalesStats}">
						<c:forEach var="dailySalesStat" items="${dailySalesStats}">
                          <tr>
                            <td><i class="fab fa-angular fa-lg me-3"></i> <strong>${dailySalesStat.dailyTotalCalculateDate}</strong></td>
                            <td>${dailySalesStat.dailyTotalGrossMargine}</td>
                            <td>${dailySalesStat.dailyTotalNetProfit}</td>
                          </tr>
                        </c:forEach>
                      </c:if>
                      </tbody>
                        <tfoot class="table-border-bottom-0" id="table-footer">
                      <c:if test="${empty dailySalesStats}">
                          <tr>
                            <td>표시할 데이터가 없습니다..</td>
                          </tr>
                      </c:if>
                        </tfoot>
                    </table>
                  </div>
                </div>
              </div>
              <!--/ Striped Table -->
              
              <!-- Pagination -->
              <div class="row justify-content-center mb-4">
                <div class="col-md-4">
                  <div class="card">
                    <div class="card-body">
                      <div class="row">
                        <div class="col">
                          <div class="demo-inline-spacing">
                            <nav aria-label="Page navigation">
                              <ul class="pagination justify-content-center" id="pagination">
                                <li class="page-item prev">
                                  <a class="page-link" href="javascript:void(0);"
                                    ><i class="tf-icon bx bx-chevron-left"></i
                                  ></a>
                                </li>
                                <li class="page-item">
                                  <a class="page-link" href="javascript:void(0);">1</a>
                                </li>
                                <li class="page-item">
                                  <a class="page-link" href="javascript:void(0);">2</a>
                                </li>
                                <li class="page-item active">
                                  <a class="page-link" href="javascript:void(0);">3</a>
                                </li>
                                <li class="page-item">
                                  <a class="page-link" href="javascript:void(0);">4</a>
                                </li>
                                <li class="page-item">
                                  <a class="page-link" href="javascript:void(0);">5</a>
                                </li>
                                <li class="page-item next">
                                  <a class="page-link" href="javascript:void(0);"
                                    ><i class="tf-icon bx bx-chevron-right"></i
                                  ></a>
                                </li>
                              </ul>
                            </nav>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!-- Pagination -->
            </div>

            <custom:adminFooter/>
            <div class="content-backdrop fade"></div>
          </div>
          <!-- / Content wrapper -->
        </div>
        <!-- / Layout page -->
      </div>

      <!-- Overlay -->
      <div class="layout-overlay layout-menu-toggle"></div>
    </div>
    <!-- / Layout wrapper -->

    <!-- build:js assets/vendor/js/core.js -->
    <script src="/resources/admin/assets/vendor/libs/jquery/jquery.js"></script>
    <script src="/resources/admin/assets/vendor/libs/popper/popper.js"></script>
    <script src="/resources/admin/assets/vendor/js/bootstrap.js"></script>
    <script src="/resources/admin/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.js"></script>

    <script src="/resources/admin/assets/vendor/js/menu.js"></script>
    <!-- endbuild -->

    <!-- Vendors JS -->
    <script src="/resources/admin/assets/vendor/libs/apex-charts/apexcharts.js"></script>

    <!-- Main JS -->
    <script src="/resources/admin/assets/js/main.js"></script>

    <!-- Page JS -->
    <script src="/resources/admin/assets/js/dashboards-analytics.js"></script>

    <!-- Place this tag in your head or just before your close body tag. -->
    <script async defer src="https://buttons.github.io/buttons.js"></script>
   
    <!-- 화면 첫 로드 시 데이터 정보 -->
    <c:set var="datas" value="${dailySalesStats}" /> 
    <c:if test="${empty datas}">
        <c:set var="datas" value="[]" />
    </c:if>
    
    <c:set var="datasLen" value="${fn:length(dailySalesStats)}" /> 
<script>

/*
 * 검색 기간 기본 값 설정
 * 일별 통계: (30일 전) ~ (오늘 날짜)
 * 월별 통계: (1년 전) ~ (이번 달)
 */
function setTodayDate() {
    var today = new Date();
    var todayY = today.getFullYear();
    var todayM = ('0' + (today.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 +1을 해줍니다.
    var todayD = ('0' + today.getDate()).slice(-2); // 일자를 두 자리로 만들어줍니다.
    var todayMonth = todayY + '-' + todayM;
    var todayDate = todayMonth + '-' + todayD;
    document.getElementById('end-month-input').value = todayMonth;
    document.getElementById('end-date-input').value = todayDate;
  
    var Before30Days = new Date(today.getTime() - 30 * 24 * 60 * 60 * 1000);
    var Before30DaysY = Before30Days.getFullYear();
    var Before30DaysM = ('0' + (Before30Days.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 +1을 해줍니다.
    var Before30DaysD = ('0' + Before30Days.getDate()).slice(-2); // 일자를 두 자리로 만들어줍니다.
    var startDate = Before30DaysY + '-' + Before30DaysM + '-' + Before30DaysD;
    document.getElementById('start-date-input').value = startDate;

    var Before365Days = new Date(today.getTime() - 365 * 24 * 60 * 60 * 1000);
    var Before365DaysY = Before365Days.getFullYear();
    var Before365DaysM = ('0' + (Before365Days.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 +1을 해줍니다.
    var startMonth = Before365DaysY + '-' + Before365DaysM;
    document.getElementById('start-month-input').value = startMonth;
}
setTodayDate();
 
/*
 * 화면 구성 요소 채우기
 * - 테이블
 * - 페이지네이션
 */
function drawPage(datas, type) {
	console.log("[log] start drawPage");
	curDatas = datas;
	curType = type;
	
	fillTable(datas, type);
    setPagination(datas.length);
}
drawPage(${datas}, "date");

/*
 * 테이블 채우기
 */
function fillTable(datas, type) {
    // 엘레먼트 불러오기
    let cardHeader = $('#table-card-header');
    let tbody = $('#table-body');
    let tfooter = $('#table-footer');
    // 테이블 내용 비우기
    cardHeader.empty();
    tbody.empty();
    tfooter.empty();

    // Card header
    if (type === "date") {
        cardHeader.append("일별 통계");
    } else if (type == "month"){
        cardHeader.append("월별 통계");
    } else {
    	// TODO: error
    }

    // Table footer
    if (datas.length <= 0) {
        let row = $('<tr>');
        row.append($('<td>').text("표시할 데이터가 없습니다.."));
        tfooter.append(row);
        return ;
    }

    // Table body
    $.each(datas, function(index, data) {
//    	console.log("index: " + index);
        if (Math.floor(index / 10) + 1 === curPage) {
            let row = $('<tr>');
            if (type === "date") {
                row.append($('<td>').text(data.dailyTotalCalculateDate));
                row.append($('<td>').text(formatPrice(data.dailyTotalGrossMargine)));
                row.append($('<td>').text(formatPrice(data.dailyTotalNetProfit)));
            } else if (type == "month") {
                row.append($('<td>').text(data.monthlyTotalCalculateDate.split("-")[0] + "-" + data.monthlyTotalCalculateDate.split("-")[1]));
                row.append($('<td>').text(formatPrice(data.monthlyTotalGrossMargine)));
                row.append($('<td>').text(formatPrice(data.monthlyTotalNetProfit)));
            } else {
                // TODO: error
            }
            tbody.append(row);
        }
    });
}
function formatPrice(price) {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

/*
 * Pagination
 * < 1 2 3 4 5 >
 * 
 * - 최대 5개의 페이지 수 출력
 *   - 전체 페이지 수가 5 미만인 경우에만 출력되는 페이지 수가 5 미만
 *
 * - 가능하면 현재 페이지가 중앙에 오도록 배치
 *   - 좌측 또는 우측에 2개의 페이지 수를 표시할 수 없는 상황이면 현재 페이지가 중앙이 아닐 수 있음
 *
 * - 화살표를 누르면 현재 보이는 페이지 다음 혹은 이전 페이지로 이동
 *   예시: < 2 3 4 5 6 > 현재 4 페이지
 *       좌측 화살표 -> 1 페이지로 이동
 *       우측 화살표 -> 7 페이지로 이동
 *   - 화살표로 이동할 수 있는 페이지 숫자가 유효하지 않다면 가장 첫 페이지 혹은 마지막 페이지로 이동
 *   예시: < 1 2 3 4 > 현재 3 페이지, 전체 4 페이지
 *       좌측 화살표 -> 1 페이지로 이동
 *       우측 화살표 -> 4 페이지로 이동
 *
 * - 첫 페이지 혹은 마지막 페이지인 경우 일부 화살표 미표시
 *   예시: 1 2 3 4 5 > 현재 1 페이지
 *   예시: < 1 2 3 4 5 현재 5 페이지, 전체 5 페이지
 */
 
// Page 화면 구성
function setPagination(dataLen) {
	console.log("[log] start setPagination");
	calculPages(dataLen);
	
	let pagination = $('#pagination');
	pagination.empty();
	
	// < button
	if (curPage > 1) {
		pagination.append(getPrevBtn());
	}
	
	// page button
	
	// 화면에 보여질 시작 페이지, 끝 페이지 계산
	startPage = curPage - 2;
	endPage = curPage + 2;
	if (startPage < 1) {
		startPage = 1;
		endPage += 5 - (endPage - startPage + 1);
	}
	if (endPage > totalPage) {
		endPage = totalPage;
		startPage -= 5 - (endPage - startPage + 1);
	}
	if (startPage < 1) {
		startPage = 1;
	}
	
	for (let i = startPage; i <= endPage; i++) {
		pagination.append(getPageBtn(i));
	}
	
	// > button
	if (curPage < totalPage) {
		pagination.append(getNextBtn());
	}
	
    console.log("[log] totalPage: " + totalPage);
    console.log("[log] curPage: " + curPage);
    console.log("[log] startPage: " + startPage);
    console.log("[log] endPage: " + endPage);
}

function calculPages(dataLen) {
//    console.log("dataLen : " + dataLen);
    console.log("[log] start calculPages");
    totalPage = Math.floor(dataLen / 10);
    if (dataLen % 10 != 0) {
    	totalPage++;
    }
    if (totalPage == 0) {
    	totalPage = 1;
    }
    
    console.log("totalPage: " + totalPage);
    console.log("curPage: " + curPage);
}
function getPrevBtn() {
	let list = $('<li class="page-item prev">');
	let a = $('<button class="page-link" onclick="movePrevPage()"></button>');
	let i = $('<i class="tf-icon bx bx-chevron-left"></i>');
	
	a.append(i);
	list.append(a);
	return list;
}
function getNextBtn() {
	let list = $('<li class="page-item next">');
	let a = $('<button class="page-link" onclick="moveNextPage()"></button>');
	let i = $('<i class="tf-icon bx bx-chevron-right"></i>');
	
	a.append(i);
	list.append(a);
	return list;
}
function getPageBtn(page) {
	let list = $('<li class="page-item">');
	if (page == curPage) {
		list = $('<li class="page-item active">');
	}
	
	let a = $('<button class="page-link" onclick="movePage(\'' + page + '\')">' + page + '</button>');
	
	list.append(a);
	return list;
}

// Page 동작
function movePage(param) {
//	console.log("param: " + param);
	curPage = parseInt(param);
	drawPage(curDatas, curType);
}
function movePrevPage() {
	if (curPage === 1) {
		return;
	}
	if (startPage - 1 > 0) {
		curPage = startPage - 1;
	} else {
		curPage = 1;
	}
	drawPage(curDatas, curType);
}
function moveNextPage() {
//	console.log("[log] start moveNextPage");
	if (curPage === totalPage) {
//		console.log("[log] curPage === totalPage");
		return;
	}
	if (endPage + 1 <= totalPage) {
		curPage = endPage + 1;
	} else {
		curPage = totalPage;
	}
//	console.log("[log] curPage : " + curPage);
	drawPage(curDatas, curType);
}
 
 
</script>
    
  </body>
  
</html>