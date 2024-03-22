<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<!DOCTYPE html>


<html>
  <head>
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"
    />

    <title>대시보드</title>

    <meta name="description" content="대시보드는 관리자 메인페이지입니다" />

    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap"
      rel="stylesheet"
    />

    <!-- Icons. Uncomment required icon fonts -->
    <link rel="stylesheet" href="admin/assets/vendor/fonts/boxicons.css" />

    <!-- Core CSS -->
    <link rel="stylesheet" href="admin/assets/vendor/css/core.css" class="template-customizer-core-css" />
    <link rel="stylesheet" href="admin/assets/vendor/css/theme-default.css" class="template-customizer-theme-css" />
    <link rel="stylesheet" href="admin/assets/css/demo.css" />

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="admin/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />

    <link rel="stylesheet" href="admin/assets/vendor/libs/apex-charts/apex-charts.css" />

    <!-- Page CSS -->

    <!-- Helpers -->
    <script src="admin/assets/vendor/js/helpers.js"></script>

    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="admin/assets/js/config.js"></script>
    
    <%-- 파비콘 --%>
    <custom:favicon/>
    
  </head>

  <body>
    <!-- Layout wrapper -->
    <div class="layout-wrapper layout-content-navbar">
      <div class="layout-container">
        <custom:adminSidebar pageName='dashboard' />

        <!-- Layout container -->
        <div class="layout-page">
          <custom:adminNav/>

          <!-- Content wrapper -->
          <div class="content-wrapper">
            <!-- Content -->
            <div class="container-xxl flex-grow-1 container-p-y">
              <div class="row">
                <!-- Expense Overview -->
                <div class="col-md-6 col-lg-6 order-0 mb-4">
                  <div class="card">
                    <div class="card-header">
                      <h5>일자별 그래프</h5>
                    </div>
                    <div class="card-body px-0">
                      <div id="incomeChart"></div>
                    </div>
                  </div>
                </div>
                <!--/ Expense Overview -->
                
                <!-- Striped Table -->
                <div class="col-md-6 col-lg-6 order-1 mb-4">
                  <div class="card">
                    <h5 class="card-header">일자별 요약</h5>
                    <div class="table-responsive text-nowrap">
                      <table class="table table-striped">
                        <thead>
                          <tr>
                            <th>날짜</th>
                            <th>매출(₩)</th>
                            <th>이익(₩)</th>
                          </tr>
                        </thead>
                        <tbody class="table-border-bottom-0">
                          <c:if test="${fn:length(dashboardDailySalesStats) > 0}">
                            <c:forEach var="dailySalesStat" items="${dashboardDailySalesStats}">
                              <tr>
                                <td><i class="fab fa-angular fa-lg me-3"></i> <strong>${dailySalesStat.dailyTotalCalculateDate}</strong></td>
                                <td>${dailySalesStat.dailyTotalGrossMargine}</td>
                                <td>${dailySalesStat.dailyTotalNetProfit}</td>
                              </tr>
                            </c:forEach>
                          </c:if>
                        </tbody>
                        <c:if test="${fn:length(dashboardDailySalesStats) <= 0}">
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
              </div>
            </div>
            <!-- / Content -->

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
    <script src="admin/assets/vendor/libs/jquery/jquery.js"></script>
    <script src="admin/assets/vendor/libs/popper/popper.js"></script>
    <script src="admin/assets/vendor/js/bootstrap.js"></script>
    <script src="admin/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.js"></script>

    <script src="admin/assets/vendor/js/menu.js"></script>
    <!-- endbuild -->

    <!-- Vendors JS -->
    <script src="admin/assets/vendor/libs/apex-charts/apexcharts.js"></script>

    <!-- Main JS -->
    <script src="admin/assets/js/main.js"></script>

    <!-- Render dashboard charts -->
    <script src="admin/assets/js/dashboards-analytics.js"></script>
    <script>
      let values = [];
      let dates = [];
    </script>
    <c:if test="${fn:length(dashboardDailySalesStats) > 0}">
      <c:forEach var="dailySalesStat" items="${dashboardDailySalesStats}">
        <script>
          values.push(${dailySalesStat.dailyTotalGrossMargine});
          dates.push(`${dailySalesStat.dailyTotalCalculateDate}`);
        </script>
      </c:forEach>
    </c:if>
    <script>
      values.reverse();
      dates.reverse();
      renderCharts(values, dates);
    </script>
    
    <!-- Place this tag in your head or just before your close body tag. -->
    <script async defer src="https://buttons.github.io/buttons.js"></script>
  </body>
</html>