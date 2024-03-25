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
                // 테이블 바디 채우기
                let tbody = $('#table-body'); // 테이블 바디 요소 가져오기
                tbody.empty(); // 테이블 바디 내용 비우기

                $.each(datas, function(index, data) {
                    // 새로운 행 생성
                    let row = $('<tr>');
                    // 각 데이터를 셀로 만들어 행에 추가
                    row.append($('<td>').text(data.dailyTotalCalculateDate));
                    row.append($('<td>').text(data.dailyTotalGrossMargine));
                    row.append($('<td>').text(data.dailyTotalNetProfit));
                    // 생성한 행을 테이블 바디에 추가
                    tbody.append(row);
                });
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
                                      <button type="submit" class="btn rounded-pill btn-primary col-2 mb-4" onclick="searchMonth()">검색</button>
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
                  <c:if test="${(empty dailySalesStats && empty monthlySalesStats) || not empty dailySalesStats}"> <!-- daily is default -->
                    <h5 class="card-header">일별 통계</h5>
                  </c:if>
                  <c:if test="${empty dailySalesStats && not empty monthlySalesStats}">
                    <h5 class="card-header">월별 통계</h5>
                  </c:if>
                  <div class="table-responsive text-nowrap">
                    <table class="table table-striped">
                      <thead id="table-header">
                        <tr>
                          <th>날짜</th>
                          <th>매출(₩)</th>
                          <th>이익(₩)</th>
                        </tr>
                      </thead>
                      <tbody class="table-border-bottom-0" id="table-body">
                        <tr>
                          <td><i class="fab fa-angular fa-lg me-3"></i> <strong>${dailySalesStat.dailyTotalCalculateDate}</strong></td>
                          <td>${dailySalesStat.dailyTotalGrossMargine}</td>
                          <td>${dailySalesStat.dailyTotalNetProfit}</td>
                        </tr>
                      </tbody>
                      <c:if test="${empty dailySalesStats && empty monthlySalesStats}">
                        <tfoot class="table-border-bottom-0">
                          <tr>
                            <td>표시할 데이터가 없습니다..</td>
                          </tr>
                        </tfoot>
                      </c:if>
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
                          <ul class="pagination justify-content-center">
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
          <!-- Content wrapper -->
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
    
<script>
// 오늘 날짜를 가져와서 input 요소의 value로 설정하는 함수
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

// 페이지가 로드되면 오늘 날짜를 input 요소에 설정합니다.
setTodayDate();
</script>
    
    
  </body>
  
</html>