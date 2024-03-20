<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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

    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="commonImages/favicon.png" />

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
  </head>

  <body>
    <!-- Layout wrapper -->
    <div class="layout-wrapper layout-content-navbar">
      <div class="layout-container">
        <!-- Menu -->

        <aside id="layout-menu" class="layout-menu menu-vertical menu bg-menu-theme">
          <div class="app-brand demo">
            <img src="commonImages/favicon.png" height="35" />
            <a href="dashboard" class="app-brand-link">
              <span class="app-brand-text demo menu-text fw-bolder ms-2">NAEDDOCO</span>
            </a>

            <a href="javascript:void(0);" class="layout-menu-toggle menu-link text-large ms-auto d-block d-xl-none">
              <i class="bx bx-chevron-left bx-sm align-middle"></i>
            </a>
          </div>

          <div class="menu-inner-shadow"></div>

          <ul class="menu-inner py-1">
            <li class="menu-item active">
              <a href="dashboard" class="menu-link">
                <i class="menu-icon tf-icons bx bx-home-circle"></i>
                <div>대시보드</div>
              </a>
            </li>
            <li class="menu-item">
              <a href="productList" class="menu-link">
                <i class="menu-icon tf-icons bx bx-layout"></i>
                <div>상품 목록</div>
              </a>
            </li>
            <li class="menu-item">
              <a href="memberList" class="menu-link">
                <i class="menu-icon tf-icons bx bx-layout"></i>
                <div>회원 목록</div>
              </a>
            </li>

            <li class="menu-header small">
              <span class="menu-header-text">판매 통계</span>
            </li>
            <li class="menu-item">
              <a href="statDate" class="menu-link">
                <i class="menu-icon tf-icons bx bx-layout"></i>
                <div>기간별</div>
              </a>
            </li>
            <li class="menu-item">
              <a href="statProduct" class="menu-link">
                <i class="menu-icon tf-icons bx bx-layout"></i>
                <div>상품별</div>
              </a>
            </li>
            
            <li class="menu-header small">
              <span class="menu-header-text">쿠폰 지급</span>
            </li>
            <li class="menu-item">
              <a href="couponGrade" class="menu-link">
                <i class="menu-icon tf-icons bx bx-layout"></i>
                <div>등급별 자동 발송</div>
              </a>
            </li>
            <li class="menu-item">
              <a href="couponBatch" class="menu-link">
                <i class="menu-icon tf-icons bx bx-layout"></i>
                <div>전체 발송</div>
              </a>
            </li>
            <li class="menu-item">
              <a href="couponDownload" class="menu-link">
                <i class="menu-icon tf-icons bx bx-layout"></i>
                <div>사용자 다운로드</div>
              </a>
            </li>
            
            <li class="menu-header small">
              <span class="menu-header-text">기타</span>
            </li>
            <li class="menu-item">
              <a href="/" class="menu-link">
                <i class="menu-icon tf-icons bx bx-layout"></i>
                <div>내 쇼핑몰</div>
              </a>
            </li>
            
          </ul>
        </aside>
        <!-- / Menu -->

        <!-- Layout container -->
        <div class="layout-page">
          <!-- Navbar -->

          <nav
            class="layout-navbar container-xxl navbar navbar-expand-xl navbar-detached align-items-center bg-navbar-theme"
            id="layout-navbar"
          >
            <div class="layout-menu-toggle navbar-nav align-items-xl-center me-3 me-xl-0 d-xl-none">
              <a class="nav-item nav-link px-0 me-xl-4" href="javascript:void(0)">
                <i class="bx bx-menu bx-sm"></i>
              </a>
            </div>

            <div class="navbar-nav-left d-flex align-items-center" id="navbar-collapse">
              <ul class="navbar-nav flex-row align-items-center ms-auto">
                <!-- Place this tag where you want the button to render. -->
                <li class="nav-item lh-1 me-3">
                  <a href="dashboard" class="fw-semibold d-block mb-1">Dashboard</a>
                </li>
                <li class="nav-item lh-1 me-3">
                  <a href="/" class="fw-semibold d-block mb-1">Shop</a>
                </li>
              </ul>
            </div>
            <div class="navbar-nav-right d-flex align-items-center" id="navbar-collapse">
              <ul class="navbar-nav flex-row align-items-center ms-auto">
                <!-- Place this tag where you want the button to render. -->
                <li class="nav-item lh-1 me-3">
                  <a href="logout" class="fw-semibold d-block mb-1">Logout</a>
                </li>
              </ul>
            </div>
          </nav>

          <!-- / Navbar -->

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
                
                <!-- Striped Rows -->
                <div class="col-md-6 col-lg-6 order-1 mb-4">
                  <div class="card">
                    <h5 class="card-header">일자별 요약</h5>
                    <div class="table-responsive text-nowrap">
                      <table class="table table-striped">
                        <thead>
                          <tr>
                            <th>일자</th>
                            <th>매출액(₩)</th>
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
                <!--/ Striped Rows -->
              </div>
            </div>
            <!-- / Content -->

            <!-- Footer -->
            <footer class="content-footer footer bg-footer-theme">
              <div class="container-xxl d-flex flex-wrap justify-content-between py-2 flex-md-row flex-column">
                <div class="mb-2 mb-md-0">
                  © 2024, made with 
                  <a href="https://github.com/NaeDdoCo/nsmw-spring" target="_blank" class="footer-link fw-bolder">NaeDdoCo</a>
                  ❤️ 
                </div>
              </div>
            </footer>
            <!-- / Footer -->

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

    <!-- Page JS -->
    <script src="admin/assets/js/dashboards-analytics.js"></script>

    <!-- Place this tag in your head or just before your close body tag. -->
    <script async defer src="https://buttons.github.io/buttons.js"></script>
  </body>
</html>