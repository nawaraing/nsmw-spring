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

    <title>판매통계 - 상품별</title>

    <meta name="description" content="상품별 판매통계 페이지입니다" />

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
    <!-- Layout wrapper -->
    <div class="layout-wrapper layout-content-navbar">
      <div class="layout-container">
        <custom:adminSidebar pageName='statProduct'/>

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
                                    <button type="button" class="btn rounded-pill btn-primary col-2 mb-4" onclick="searchProductDate()">검색</button>
                                  </div>
                                </div>  
                              </div>
                              <div class="tab-pane fade show" id="list-month">
                                <div class="row">
                                  <label for="start-month-input" class="col-md-2 col-form-label">검색 기간</label>
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
                                    <button type="button" class="btn rounded-pill btn-primary col-2 mb-4" onclick="searchProductMonth()">검색</button>
                                  </div>
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
                          <th>상품 ID</th>
                          <th>상품명</th>
                          <th>매출(₩)</th>
                          <th>이익(₩)</th>
                          <th>판매 수량</th>
                        </tr>
                      </thead>
                      <!-- 초기 데이터를 tbody에서 읽어다가 pagination 연산을 진행하기에 아래 <tbody>는 필수 -->
                      <tbody class="table-border-bottom-0" id="table-body">
                      <c:if test="${not empty dailyProductSalesStats}">
						<c:forEach var="dailySalesStat" items="${dailyProductSalesStats}" varStatus="status">
                          <tr>
                            <td id="product-id-${status.index}">${dailySalesStat.productID}</td>
                            <td><i class="fab fa-angular fa-lg me-3"></i> <strong id="product-name-${status.index}">${dailySalesStat.ancProductName}</strong></td>
                            <td id="total-gross-margine-${status.index}">${dailySalesStat.dailyTotalGrossMargine}</td>
                            <td id="total-net-profit-${status.index}">${dailySalesStat.dailyTotalNetProfit}</td>
                            <td id="total-quantity-${status.index}">${dailySalesStat.dailyTotalQuantity}</td>
                          </tr>
                        </c:forEach>
                      </c:if>
                      </tbody>
                        <tfoot class="table-border-bottom-0" id="table-footer">
                          <c:if test="${empty dailyProductSalesStats}">
                            <tr>
                              <td>표시할 데이터가 없습니다..</td>
                            </tr>
                          </c:if>
                        </tfoot>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
              <!--/ Striped Table -->
              
              <custom:adminPagination/>
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

    <!-- Place this tag in your head or just before your close body tag. -->
    <script async defer src="https://buttons.github.io/buttons.js"></script>
   
    <!-- Pagination & Load Page JS -->
    <script src="/resources/admin/js/fill-stat-table.js"></script>
    <script src="/resources/admin/js/page-composed.js"></script>
    <script src="/resources/admin/js/page-variables.js"></script>
    <script src="/resources/admin/js/pagination-action.js"></script>
    <script src="/resources/admin/js/pagination-composed.js"></script>
<!--    <script src="/resources/admin/js/stat-product.js"></script> -->
    
    <!-- Search Stat Product JS -->
    <script src="/resources/admin/js/async-search-stat-product.js"></script>
    
    <!-- Set Default Date JS -->
    <script src="/resources/admin/js/set-default-date.js"></script>
    
    <!-- Utils -->
    <script src="/resources/admin/js/utils.js"></script>
    
  </body>
  
</html>