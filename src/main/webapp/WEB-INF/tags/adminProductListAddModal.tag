<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%@ attribute name="categoryNames" %>

<script src="/resources/admin/assets/vendor/libs/jquery/jquery.js"></script>
<script src="/resources/admin/js/checkbox.js"></script>

<!-- Button trigger modal -->
<button
  type="button"
  class="btn btn-primary me-2"
  data-bs-toggle="modal"
  data-bs-target="#image-modal-add"
>추가</button>

<!-- Modal -->
<div class="modal fade" id="image-modal-add" data-bs-backdrop="static" tabindex="-1">
  <div class="modal-dialog modal-dialog-centered modal-xl">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="backDropModalTitle">상품 추가</h5>
        <button
          type="button"
          class="btn-close"
          data-bs-dismiss="modal"
          aria-label="Close"
        ></button>
      </div>

      <form method="POST" enctype="multipart/form-data" action="/productList/insert">
        <div class="modal-body">
          <div class="row">
            <div class="col-md-6 col-12 mb-md-0 mb-4">
 			  <custom:adminImageHandle rowNum="add" />
 			  <div class="text-center">
 			    <custom:adminImageUploadButton rowNum="add"/>
              </div>
 			  
			</div> 			
            <div class="col-md-1"></div>
          
            <div class="col-md-5 col-12 text-start">
              <div class="mb-3">
                <label for="defaultFormControlInput" class="form-label">상품명</label>
                <input
                  type="text"
                  name="productName"
                  class="form-control"
                  id="defaultFormControlInput"
                  placeholder="상품명"
                  aria-describedby="defaultFormControlHelp"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="exampleFormControlTextarea1" class="form-label">상품 설명</label>
                <textarea name="productDetail" class="form-control" id="exampleFormControlTextarea1" rows="3" placeholder="상품 설명" required></textarea>
              </div>
              <div class="mb-3">
                <label for="costPrice" class="form-label">원가</label>
                <div class="input-group input-group-merge">
                  <span class="input-group-text">₩</span>
                  <input type="number" name="costPrice" class="form-control" placeholder="12000" aria-label="Amount (to the nearest dollar)" id="costPrice" required>
                </div>
              </div>
              <div class="mb-3">
                <label for="retailPrice" class="form-label">정가</label>
                <div class="input-group input-group-merge">
                  <span class="input-group-text">₩</span>
                  <input type="number" name="retailPrice" class="form-control" placeholder="12000" aria-label="Amount (to the nearest dollar)" id="retailPrice" required>
                </div>
              </div>
              <div class="mb-3">
                <label for="salePrice" class="form-label">판매가</label>
                <div class="input-group input-group-merge">
                  <span class="input-group-text">₩</span>
                  <input type="number" name="salePrice" class="form-control" placeholder="12000" aria-label="Amount (to the nearest dollar)" id="salePrice" required>
                </div>
              </div>
              <div class="mb-3">
                <label for="stock" class="form-label">재고</label>
                <div class="input-group input-group-merge">
                  <input type="number" name="stock" class="form-control" placeholder="30" aria-label="Amount (to the nearest dollar)" id="stock" required>
                  <span class="input-group-text">개</span>
                </div>
              </div>
              <div class="mb-3">
                <label for="ingredient" class="form-label">성분</label>
                <input
                  type="text"
                  name="ingredient"
                  class="form-control"
                  id="ingredient"
                  placeholder="성분"
                  aria-describedby="defaultFormControlHelp"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="dosage" class="form-label">용법</label>
                <input
                  type="text"
                  name="dosage"
                  class="form-control"
                  id="dosage"
                  placeholder="1일 2회, 1회 2정 섭취"
                  aria-describedby="defaultFormControlHelp"
                  required
                />
              </div>
              <div class="mb-3">
                <label for="expirationDate" class="form-label">소비기한</label>
                <input
                  type="text"
                  name="expirationDate"
                  class="form-control"
                  id="expirationDate"
                  placeholder="제조일로부터 24개월"
                  aria-describedby="defaultFormControlHelp"
                  required
                />
              </div>
            
              <!-- 카테고리 -->
              <div class="mb-3">
                <label for="product-categories-add" class="form-label">카테고리</label>
                <div>
                  <a
                    class="dropdown-toggle"
                    href="javascript:void(0)"
                    id="product-categories-add"
                    role="button"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                  </a>
                  <script>console.log('categoryNames: ${categoryNames}');</script>
                  <ul class="dropdown-menu" aria-labelledby="navbarDropdown" id="checkbox-categories-${status.index}">
                    <c:forEach var="category" items="${fn:split(categoryNames, ',')}" varStatus="categoryStatus">
	                  <script>console.log('category: ${category}');</script>
                      <li>
                        <div class="form-check">
                          <label class="dropdown-item" for="checkbox-product-add-category-${category}" id="dropdown-item-coupon-add-category-${categoryStatus.index}">
                            <input class="form-check-input" type="checkbox" onclick="handleCategoryCheckbox('add', '${category}')" value="${category}" name="categoryNames" id="checkbox-product-add-category-${category}" />${category}
                          </label>
                        </div>
                      </li>
                    </c:forEach>
                  </ul>
                </div>
              </div>
              <!-- / 카테고리 -->
              <div class="text-end mb-3">
                <button
                  type="button"
                  class="btn btn-secondary me-2"
                  data-bs-dismiss="modal"
                >취소</button>
                <button
                  type="submit"
                  id="submit-button"
                  class="btn btn-success me-2"
                >저장</button>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
  