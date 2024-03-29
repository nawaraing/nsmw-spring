<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.naeddoco.nsmwspring.model.*" %>
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

    <title>상품 목록</title>

    <meta name="description" content="상품 목록" />

    <!-- Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap"
      rel="stylesheet"
    />

    <!-- Icons. Uncomment required icon fonts -->
    <link rel="stylesheet" href="/resources/admin/assets/vendor/fonts/boxicons.css" />

    <!-- Core CSS -->
    <link rel="stylesheet" href="/resources/admin/assets/vendor/css/core.css" class="template-customizer-core-css" />
    <link rel="stylesheet" href="/resources/admin/assets/vendor/css/theme-default.css" class="template-customizer-theme-css" />
    <link rel="stylesheet" href="/resources/admin/assets/css/demo.css" />

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="/resources/admin/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />

    <!-- Helpers -->
    <script src="/resources/admin/assets/vendor/js/helpers.js"></script>

    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="/resources/admin/assets/js/config.js"></script>
    
    <%-- 파비콘 --%>
    <custom:favicon/>
    
  </head>

  <body>
    <c:set var="pageName" value="memberList" />
  
    <!-- Layout wrapper -->
    <div class="layout-wrapper layout-content-navbar">
      <div class="layout-container">
        <custom:adminSidebar pageName="${pageName}" />

        <!-- Layout container -->
        <div class="layout-page">
          <custom:adminNav/>

          <!-- Content wrapper -->
          <div class="content-wrapper">
            <!-- Content -->
            <div class="container-xxl flex-grow-1 container-p-y">
              <custom:adminSearchNav pageName="${pageName}" />
              
              <!-- Striped Table -->
              <div class="mb-4">
                <div class="card">
                  <h5 class="card-header" id="table-card-header">회원 목록</h5>
                  <div class="table-responsive text-nowrap">
                    <table class="table table-striped">
                      <thead>
                        <tr>
                          <th>회원 이름</th>
                          <th>회원 ID</th>
                          <th>생년월일</th>
                          <th>성별</th>
                          <th>전화번호</th>
                          <th>이메일</th>
                          <th>우편번호</th>
                          <th>도로명주소</th>
                          <th>상세주소</th>
                          <th>회원 등급</th>
                          <th>건강상태</th>
                        </tr>
                      </thead>
                      <!-- 초기 데이터를 tbody에서 읽어다가 pagination 연산을 진행하기에 아래 <tbody>는 필수 -->
                      <tbody class="table-border-bottom-0" id="table-body">
                        <c:if test="${not empty memberList}">
					      <c:forEach var="member" items="${memberList}" varStatus="status">
                            <tr>
                              <!-- 회원 이름 -->
                              <td><i class="fab fa-angular fa-lg me-3"><strong id="member-name-${status.index}">${member.memberName}</strong></i></td>
                              <!-- / 회원 이름 -->
                              <!-- 회원 ID -->
                              <td id="member-id-${status.index}">${member.memberID}</td>
                              <!-- / 회원 ID -->
                              <!-- 생년월일 -->
                              <td id="register-date-${status.index}">${member.dayOfBirth}</td>
                              <!-- / 생년월일 -->
                              <!-- 성별 -->
                              <td id="gender-${status.index}">${member.gender}</td>
                              <!-- / 성별 -->
                              <!-- 전화번호 -->
                              <td id="phone-number-${status.index}">${member.phoneNumber}</td>
                              <!-- / 전화번호 -->
                              <!-- 이메일 -->
                              <td id="email-${status.index}">${member.email}</td>
                              <!-- / 이메일 -->
                              <!-- 우편번호 -->
                              <td id="postcode-${status.index}">${member.ancShippingPostCode}</td>
                              <!-- / 우편번호 -->
                              <!-- 도로명주소 -->
                              <td id="postcode-${status.index}">${member.ancShippingAddress}</td>
                              <!-- / 도로명주소 -->
                              <!-- 상세주소 -->
                              <td id="postcode-${status.index}">${member.ancShippingAddressDetail}</td>
                              <!-- / 상세주소 -->
                              <!-- 회원등급 -->
                              <td id="grade-${status.index}">${member.ancGradeName}</td>
                              <!-- / 회원등급 -->
                              <!-- 건강상태 -->
                              <td id="grade-${status.index}">${member.ancCategoryName}</td>
                              <!-- / 건강상태 -->
                            </tr>
                          </c:forEach>
                        </c:if>
                      </tbody>
                        <tfoot class="table-border-bottom-0" id="table-footer">
                          <c:if test="${empty memberList}">
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
            <!-- / Content -->

            <custom:adminFooter/>
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

    <!-- Place this tag in your head or just before your close body tag. -->
    <script async defer src="https://buttons.github.io/buttons.js"></script>
    
    <!-- Utils -->
    <script src="/resources/admin/js/utils.js"></script>
    <script src="/resources/admin/js/checkbox.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
    
    
    <!-- Pagination & Load Page JS -->
    <!-- <script src="/resources/admin/js/fill-member-list-table.js"></script> -->
    <script src="/resources/admin/js/page-composed.js"></script>
    <script src="/resources/admin/js/page-variables.js"></script>
    <script src="/resources/admin/js/pagination-action.js"></script>
    <script src="/resources/admin/js/pagination-composed.js"></script>
    <!-- <script src="/resources/admin/js/member-list.js"></script> -->
    
  </body>
</html>