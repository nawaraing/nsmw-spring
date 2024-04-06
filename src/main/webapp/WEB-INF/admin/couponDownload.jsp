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

    <title>쿠폰 지급-사용자 다운로드 쿠폰</title>

    <meta name="description" content="쿠폰 지급-사용자 다운로드 쿠폰" />

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
    <c:set var="pageName" value="couponDownload" />
  
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
                  <h5 class="card-header" id="table-card-header">쿠폰 지급 - 사용자 다운로드 쿠폰</h5>
                  <div class="table-responsive text-nowrap">
                    <table class="table table-striped">
                      <thead>
                        <tr>
                          <!-- checkbox 디자인 때문에 <td> 태그를 사용 -->
                          <td>
                            <div class="form-check">
                              <input class="form-check-input" type="checkbox" value="" id="checkbox-id-table-header" onclick="handleCheckboxHeader(10)" />
                            </div>
                          </td>
                          <th>쿠폰 이름</th>
                          <th>배포 시작일</th>
                          <th>배포 마감일</th>
                          <th>쿠폰 만료일</th>
                          <th>카테고리</th>
                          <th>할인 방식</th>
                          <th>할인율(액)</th>
                          <th>금액 제한</th>
                          <th>팝업 이미지</th>
                          <th>배포 현황</th>
                        </tr>
                      </thead>
                      <!-- 초기 데이터를 tbody에서 읽어다가 pagination 연산을 진행하기에 아래 <tbody>는 필수 -->
                      <tbody class="table-border-bottom-0" id="table-body">
                        <c:if test="${not empty couponList}">
					      <c:forEach var="coupon" items="${couponList}" varStatus="status">
                            <tr>
                              <!-- 체크 박스 -->
                              <td>
                                <div class="form-check">
                                  <input class="form-check-input" type="checkbox" value="${coupon.couponID}" name="couponID" id="checkbox-id-${status.index}" onclick="handleListCheckbox(${status.index}, ${fn:length(couponList)})" />
                                </div>
                              </td>
                              <!-- / 체크 박스 -->
                              <!-- 쿠폰 이름 -->
                              <td id="coupon-name-${status.index}">${coupon.couponName}</td>
                              <!-- / 쿠폰 이름 -->
                              <!-- 배포 시작일 -->
                              <fmt:parseDate var="strDistributeDate" value="${coupon.distributeDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                              <td id="distribute-date-${status.index}"><fmt:formatDate pattern="yyyy-MM-dd" value="${strDistributeDate}" /></td>
                              <!-- / 배포 시작일 -->
                              <!-- 배포 마감일 -->
                              <td>
                                <input style="width: 230px;" type="datetime-local" class="form-control" id="deploy-deadline-${status.index}" value="${coupon.ancDeployDeadline}" name="ancDeployDeadline" />
                              </td>
                              <!-- / 배포 마감일 -->
                              <!-- 쿠폰 만료일 -->
                              <fmt:parseDate var="strExpirationDate" value="${coupon.expirationDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                              <td id="expiration-date-${status.index}"><fmt:formatDate pattern="yyyy-MM-dd" value="${strExpirationDate}" /></td>
                              <!-- / 쿠폰 만료일 -->
                              <!-- 카테고리 -->
                              <td id="coupon-categories-${status.index}">
                                <c:set var="categories" value="${fn:split(coupon.ancCategoryName, ';')}" />
                                <c:forEach var="category" items="${categories}" varStatus="categoryStatus">
                                  <div class="badge bg-label-info me-1" id="coupon-${status.index}-category-${categoryStatus.index}">${category}</div>
                                </c:forEach>
                              </td>
                              <!-- / 카테고리 -->
                              <!-- 할인 방식 -->
                              <c:if test="${coupon.couponType eq 'WON'}">
                                <td id="coupon-type-${status.index}">할인액</td>
                              </c:if>
                              <c:if test="${coupon.couponType eq 'PERCENTAGE'}">
                                <td id="coupon-type-${status.index}">할인율</td>
                              </c:if>
                              <!-- / 할인 방식 -->
                              <!-- 할인율(액) -->
                              <c:if test="${coupon.couponType eq 'WON'}">
                                <td id="coupon-discount-${status.index}">${coupon.ancDiscount}원</td>
                              </c:if>
                              <c:if test="${coupon.couponType eq 'PERCENTAGE'}">
                                <td id="coupon-discount-${status.index}">${coupon.ancDiscount}%</td>
                              </c:if>
                              <!-- / 할인율(액) -->
                              <!-- 금액 제한 -->
                              <c:if test="${coupon.couponType eq 'WON'}">
                                <td id="coupon-limit-${status.index}">최소 ${coupon.ancAmount}원 구매</td>
                              </c:if>
                              <c:if test="${coupon.couponType eq 'PERCENTAGE'}">
                                <td id="coupon-limit-${status.index}">최대 ${coupon.ancAmount}원 할인</td>
                              </c:if>
                              <!-- / 금액 제한 -->
                              <!-- 팝업 이미지 -->
                              <td>
                                <custom:adminCouponDownloadImageModal rowNum="${status.index}" />
                              </td>
                              <!-- / 팝업 이미지 -->
                              <!-- 배포 현황 -->
                              <td>
                                <c:if test="${coupon.ancDeployStatus eq 'WILL'}">
                                  <div id="deploy-status-${status.index}" class="badge bg-label-primary me-1">배포 예정</div>
                                </c:if>
                                <c:if test="${coupon.ancDeployStatus eq 'DOING'}">
                                  <div id="deploy-status-${status.index}" class="badge bg-label-secondary me-1">배포중</div>
                                </c:if>
                                <c:if test="${coupon.ancDeployStatus eq 'STOP'}">
                                  <div id="deploy-status-${status.index}" class="badge bg-label-danger me-1">배포 중단</div>
                                </c:if>
                                <c:if test="${coupon.ancDeployStatus eq 'DONE'}">
                                  <div id="deploy-status-${status.index}" class="badge bg-label-success me-1">배포 완료</div>
                                </c:if>
                              </td>
                              <!-- / 배포 현황 -->
                            </tr>
                          </c:forEach>
                        </c:if>
                      </tbody>
                        <tfoot class="table-border-bottom-0" id="table-footer">
                          <c:if test="${empty couponList}">
                            <tr>
                              <td>표시할 데이터가 없습니다..</td>
                            </tr>
                          </c:if>
                        </tfoot>
                      </tbody>
                    </table>
                  </div>
                  <div class="card-footer text-muted text-end">
                    <form id="update-coupon-download-stop-form" action="/couponDownload/update/couponDownloadStop" method="GET" class="d-inline">
                      <button type="submit" class="btn btn-danger me-5" onclick="updateCouponDownloadStop(10)">배포 중단</button>
                    </form>
                    <c:forEach var="category" items="${categoryList}">
                      <script>console.log('category out: ${category.categoryName}');</script>
                      <c:set var="categoryJoin" value="${categoryJoin},${category.categoryName}" />
                      <script>console.log('categoryJoin out: ${categoryJoin}');</script>
                    </c:forEach>
                    <script>console.log('categoryJoin: ${categoryJoin}');</script>
                    <custom:adminCouponDownloadAddModal categoryNames="${categoryJoin}" />
                    <form id="update-coupon-download-form" action="/couponDownload/update" method="GET" class="d-inline">
                      <button type="submit" class="btn btn-success me-2" onclick="updateCouponDownload(${fn:length(couponList)})">저장</button>
                    </form>
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
    <script src="/resources/admin/js/image-handle.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
    
    
    <!-- Pagination & Load Page JS -->
    <script src="/resources/admin/js/fill-coupon-download-table.js"></script>
    <script src="/resources/admin/js/page-composed.js"></script>
    <script src="/resources/admin/js/page-variables.js"></script>
    <script src="/resources/admin/js/pagination-action.js"></script>
    <script src="/resources/admin/js/pagination-composed.js"></script>
<!--     <script src="/resources/admin/js/coupon-download.js"></script>
 -->
    <script src="/resources/admin/js/update-coupon-download.js"></script>
    <script src="/resources/admin/js/update-coupon-download-stop.js"></script>


  </body>
</html>