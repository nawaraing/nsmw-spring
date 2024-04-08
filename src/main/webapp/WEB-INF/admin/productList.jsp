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
    <c:set var="pageName" value="productList" />
  
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
                  <h5 class="card-header" id="table-card-header">상품 목록</h5>
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
                          <th>상품명</th>
                          <th>상품 설명</th>
                          <th>원가(₩)</th>
                          <th>정가(₩)</th>
                          <th>판매가(₩)</th>
                          <th>재고</th>
                          <th>성분</th>
                          <th>용법</th>
                          <th>소비기한</th>
                          <th>카테고리</th>
                          <th>등록일</th>
                          <th>마지막 수정일</th>
                          <th>판매 상태</th>
                          <th>상품 이미지</th>
                        </tr>
                      </thead>
                      <!-- 초기 데이터를 tbody에서 읽어다가 pagination 연산을 진행하기에 아래 <tbody>는 필수 -->
                      <tbody class="table-border-bottom-0" id="table-body">
                        <c:if test="${not empty productList}">
					      <c:forEach var="product" items="${productList}" varStatus="status">
                            <tr>
                              <!-- 체크 박스 -->
                              <td>
                                <div class="form-check">
                                  <input class="form-check-input" type="checkbox" value="${product.productID}" name="productID" id="checkbox-id-${status.index}" onclick="handleListCheckbox(${status.index}, 10)" />
                                </div>
                              </td>
                              <!-- / 체크 박스 -->
                              <!-- 상품명 -->
                              <td>
                                <input style="width: 150px;" type="text" class="form-control" id="product-name-${status.index}" value="${product.productName}" name="productName" />
                              </td>
                              <!-- / 상품명 -->
                              <!-- 상품 설명 -->
                              <td>
                                <input style="width: 150px;" type="text" class="form-control" id="product-detail-${status.index}" value="${product.productDetail}" name="productDetail" />
                              </td>
                              <!-- / 상품 설명 -->
                              <!-- 원가 -->
                              <td>
                                <input style="width: 90px;" type="number" class="form-control" id="cost-price-${status.index}" value="${product.costPrice}" name="costPrice" />
                              </td>
                              <!-- / 원가 -->
                              <!-- 정가 -->
                              <td>
                                <input style="width: 90px;" type="number" class="form-control" id="retail-price-${status.index}" value="${product.retailPrice}" name="retailPrice" />
                              </td>
                              <!-- / 정가 -->
                              <!-- 판매가 -->
                              <td>
                                <input style="width: 90px;" type="number" class="form-control" id="sale-price-${status.index}" value="${product.salePrice}" name="salePrice" />
                              </td>
                              <!-- / 판매가 -->
                              <!-- 재고 -->
                              <td>
                                <input style="width:80px;" type="number" class="form-control" id="stock-${status.index}" value="${product.stock}" name="stock" />
                              </td>
                              <!-- / 재고 -->
                              <!-- 성분 -->
                              <td>
                                <input style="width: 150px;" type="text" class="form-control" id="ingredient-${status.index}" value="${product.ingredient}" name="ingredient" />
                              </td>
                              <!-- / 성분 -->
                              <!-- 용법 -->
                              <td>
                                <input style="width: 150px;" type="text" class="form-control" id="dosage-${status.index}" value="${product.dosage}" name="dosage" />
                              </td>
                              <!-- / 용법 -->
                              <!-- 소비기한 -->
                              <td>
                                <input style="width: 150px;" type="text" class="form-control" id="expiration-date-${status.index}" value="${product.expirationDate}" name="expirationDate" />
                              </td>
                              <!-- / 소비기한 -->
                              <!-- 카테고리 -->
                              <td>
                                <a
                                  class="dropdown-toggle"
                                  href="javascript:void(0)"
                                  id="product-categories-${status.index}"
                                  role="button"
                                  data-bs-toggle="dropdown"
                                  aria-expanded="false"
                                >
                                  <c:set var="categories" value="${fn:split(product.ancCategoryName, ';')}" />
                                  <c:forEach var="category" items="${categories}" varStatus="categoryStatus">
                                    <span class="badge bg-label-info me-1" id="product-${status.index}-category-${categoryStatus.index}">${category}</span>
                                  </c:forEach>
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown" id="checkbox-categories-${status.index}">
                                  <c:forEach var="category" items="${categoryList}" varStatus="categoryStatus">
                                    <li>
                                      <div class="form-check">
                                        <label class="dropdown-item" for="checkbox-product-${status.index}-category-${categoryStatus.index}" id="dropdown-item-product-${status.index}-category-${categoryStatus.index}">
                                          <input class="form-check-input" type="checkbox" value="${category.categoryName}" id="checkbox-product-${status.index}-category-${categoryStatus.index}" />${category.categoryName}
                                        </label>
                                      </div>
                                    </li>
                                  </c:forEach>
                                </ul>
                              </td>
                              <!-- / 카테고리 -->
                              <!-- 등록일 -->
                              <fmt:parseDate var="strRegDate" value="${product.registerDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                              <td id="register-date-${status.index}"><fmt:formatDate pattern="yyyy-MM-dd" value="${strRegDate}" /></td>
                              <!-- / 등록일 -->
                              <!-- 마지막 수정일 -->
                              <fmt:parseDate var="strModDate" value="${product.modifyDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                              <td id="modify-date-${status.index}"><fmt:formatDate pattern="yyyy-MM-dd" value="${strModDate}" /></td>
                              <!-- / 마지막 수정일 -->
                              <!-- 판매 상태 -->
                              <c:if test='${product.saleState eq "SALES"}'>
                                <td id="sale-state-${status.index}">판매중</td>
                              </c:if>
                              <c:if test='${product.saleState eq "DISCONTINUED"}'>
                                <td id="sale-state-${status.index}">단종</td>
                              </c:if>
                              <c:if test='${(product.saleState ne "SALES") && (product.saleState ne "DISCONTINUED")}'>
                                <td id="sale-state-${status.index}"></td>
                              </c:if>
                              <!-- / 판매 상태 -->
                              <!-- 상품 이미지 -->
                              <%-- <td id="image-${status.index}">이미지 확인</td> --%>
                              <td>
                                <custom:adminProductListImageModal rowNum="${status.index}" imagePath="${product.ancImagePath}" productID="${product.productID}" />
                              </td>
                              <!-- / 상품 이미지 -->
                            </tr>
                          </c:forEach>
                        </c:if>
                      </tbody>
                        <tfoot class="table-border-bottom-0" id="table-footer">
                          <c:if test="${empty productList}">
                            <tr>
                              <td>표시할 데이터가 없습니다..</td>
                            </tr>
                          </c:if>
                        </tfoot>
                      </tbody>
                    </table>
                  </div>
                  <div class="card-footer text-muted text-end">
                    <form id="update-product-sale-stop-form" action="/productList/stop" method="POST" class="d-inline">
                      <button type="submit" class="btn btn-danger me-5" onclick="updateProductSaleStop(10)">판매 중단</button>
                    </form>
                    <c:forEach var="category" items="${categoryList}">
                      <script>console.log('category out: ${category.categoryName}');</script>
                      <c:set var="categoryJoin" value="${categoryJoin},${category.categoryName}" />
                      <script>console.log('categoryJoin out: ${categoryJoin}');</script>
                    </c:forEach>
                    <script>console.log('categoryJoin: ${categoryJoin}');</script>
                    <custom:adminProductListAddModal categoryNames="${categoryJoin}" />
                    <form id="update-product-form" action="/productList/update" method="POST" class="d-inline">
                      <button type="submit" class="btn btn-success me-2" onclick="updateProduct(10)">저장</button>
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
    <script src="/resources/admin/js/fill-product-list-table.js"></script>
    <script src="/resources/admin/js/page-composed.js"></script>
    <script src="/resources/admin/js/page-variables.js"></script>
    <script src="/resources/admin/js/pagination-action.js"></script>
    <script src="/resources/admin/js/pagination-composed.js"></script>
<!--     <script src="/resources/admin/js/product-list.js"></script>
 -->    <script src="/resources/admin/js/update-product.js"></script>
    <script src="/resources/admin/js/update-product-sale-stop.js"></script>
  </body>
</html>