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
  data-bs-target="#backDropModal"
>추가</button>

<!-- Modal -->
<div class="modal fade" id="backDropModal" data-bs-backdrop="static" tabindex="-1">
  <div class="modal-dialog modal-dialog-centered modal-xl">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="backDropModalTitle">쿠폰 추가 - 사용자 다운로드 쿠폰</h5>
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
            <!-- Carousel -->
            <div id="carouselExample" class="carousel carousel-dark slide" data-bs-ride="carousel">
              <ol class="carousel-indicators" id="carousel-indicators">
                <li data-bs-target="#carouselExample" data-bs-slide-to="0" class="active"></li>
              </ol>
              <div class="carousel-inner mb-3" id="image-preview">
                <div class="carousel-item active">
                  <img class="d-block w-100" src="/resources/commonImages/no-image.jpg" alt="First slide" />
                </div>
              </div>
              <a class="carousel-control-prev" href="#carouselExample" role="button" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
              </a>
              <a class="carousel-control-next" href="#carouselExample" role="button" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
              </a>
            </div>
            <!-- / Carousel -->
            <!-- Image List -->
            <div id="image-list"></div>
            <!-- / Image List -->
            
            <div class="text-center">
              <input type="file" name="images" multiple class="btn btn-primary me-2" id="upload-image" onchange="displayImage(1)" style="display: none;"/>
              <div id="too-many-images"></div>
              <button type="button" class="btn btn-primary me-2" onclick="$('#upload-image').click();">이미지 업로드</button>
            </div>
          </div>
          
          <div class="col-md-1"></div>
          
          <div class="col-md-5 col-12 text-start">
            <div class="mb-3">
              <label for="defaultFormControlInput" class="form-label">쿠폰 이름</label>
              <input
                type="text"
                name="productName"
                class="form-control"
                id="defaultFormControlInput"
                placeholder="쿠폰 이름"
                aria-describedby="defaultFormControlHelp"
                required
              />
            </div>
            <div class="mb-3">
              <label for="distribute-date" class="form-label">쿠폰 배포 시작일</label>
              <input type="datetime-local" name="distributeDate" class="form-control" aria-label="Amount (to the nearest dollar)" id="distribute-date" required>
            </div>
            <div class="mb-3">
              <label for="deploy-deadline" class="form-label">쿠폰 배포 마감일</label>
              <input type="datetime-local" name="ancDeployDeadline" class="form-control" aria-label="Amount (to the nearest dollar)" id="deploy-deadline" required>
            </div>
            <div class="mb-3">
              <label for="expiration-date" class="form-label">쿠폰 만료일</label>
              <input type="datetime-local" name="expirationDate" class="form-control" aria-label="Amount (to the nearest dollar)" id="expiration-date" required>
            </div>
            <!-- 카테고리 -->
            <div class="mb-3">
              <label for="defaultFormControlInput" class="form-label">카테고리</label>
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
                        <label class="dropdown-item" for="checkbox-product-add-category-${category}" id="dropdown-item-product-add-category-${categoryStatus.index}">
                          <input class="form-check-input" type="checkbox" onclick="handleCategoryCheckbox('add', '${category}')" value="${category}" name="categoryNames" id="checkbox-product-add-category-${category}" />${category}
                        </label>
                      </div>
                    </li>
                  </c:forEach>
                </ul>
              </div>
            </div>
            <!-- / 카테고리 -->
            <!-- 할인 방식 -->
            <div class="mb-3">
              <label for="costPrice" class="form-label">할인 방식</label>
              <div class="form-check">
                <input name="coupon-type" class="form-check-input" type="radio" value="PERCENTAGE" id="defaultRadio2" onclick="clickRadioPercentageCoupon()" checked />
                <label class="form-check-label" for="defaultRadio2">할인율</label>
              </div>
              <div class="form-check">
                <input name="coupon-type" class="form-check-input" type="radio" value="WON" id="defaultRadio1" onclick="clickRadioWonCoupon()" />
                <label class="form-check-label" for="defaultRadio1">할인액</label>
              </div>
            </div>
            <!-- / 할인 방식 -->
            <!-- 할인율(액) -->
            <div class="mb-3" id="coupon-discount">
              <label for="coupon-discount-rate" class="form-label">할인율</label>
              <div class="input-group input-group-merge">
                <span class="input-group-text">%</span>
                <input type="number" name="ancDiscount" class="form-control" placeholder="10" aria-label="Amount (to the nearest dollar)" id="coupon-discount-rate" required />
              </div>
            </div>
            <!-- / 할인율(액) -->
            <!-- 금액 제한 -->
            <div class="mb-3" id="coupon-limit">
              <label for="coupon-limit-amount" class="form-label" id="coupon-limit-amount-label">최대 할인 금액</label>
              <div class="input-group input-group-merge">
                <span class="input-group-text">₩</span>
                <input type="number" name="ancAmount" class="form-control" placeholder="5000" aria-label="Amount (to the nearest dollar)" id="coupon-limit-amount" required />
              </div>
            </div>
            <!-- / 금액 제한 -->
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

<script>

/*
 * radio click 동작
 */

/*
   <div class="mb-3" id="coupon-discount">
     <label for="coupon-discount-rate" class="form-label">할인율</label>
     <div class="input-group input-group-merge">
       <span class="input-group-text">%</span>
       <input type="number" name="ancAmount" class="form-control" placeholder="10" aria-label="Amount (to the nearest dollar)" id="coupon-discount-rate">
     </div>
   </div>
*/
 
function clickRadioPercentageCoupon() {
	let couponDiscount = $('#coupon-discount');
	couponDiscount.empty();
	
	let label = $('<label for="coupon-discount-rate" class="form-label">').text('할인율');
	let div = $('<div class="input-group input-group-merge">');
	let span = $('<span class="input-group-text">').text('%');
	let input = $('<input type="number" name="ancAmount" class="form-control" placeholder="10" aria-label="Amount (to the nearest dollar)" id="coupon-discount-rate" required />');
	
	div.append(span);
	div.append(input);
	
	couponDiscount.append(label);
	couponDiscount.append(div);
	
	let couponLimitLabel = $('#coupon-limit-amount-label');
	couponLimitLabel.empty();
	couponLimitLabel.append('최대 할인 금액');
}
function clickRadioWonCoupon() {
	let couponDiscount = $('#coupon-discount');
	couponDiscount.empty();
	
	let label = $('<label for="coupon-discount-rate" class="form-label">').text('할인액');
	let div = $('<div class="input-group input-group-merge">');
	let span = $('<span class="input-group-text">').text('₩');
	let input = $('<input type="number" name="ancAmount" class="form-control" placeholder="1000" aria-label="Amount (to the nearest dollar)" id="coupon-discount-amount" required />');
	
	div.append(span);
	div.append(input);
	
	couponDiscount.append(label);
	couponDiscount.append(div);
	
	let couponLimitLabel = $('#coupon-limit-amount-label');
	couponLimitLabel.empty();
	couponLimitLabel.append('최소 구매 금액');
}

/*
 * 쿠폰 추가 모달 날짜 초기화
 */
(function () {
	let today = new Date();
	let after7days = new Date(today.getTime() + 7 * 24 * 60 * 60 * 1000);
	let after30days = new Date(today.getTime() + 30 * 24 * 60 * 60 * 1000);

	$('#distribute-date').val(parseDate(today));
	console.log('today: ' + parseDate(today));
	$('#deploy-deadline').val(parseDate(after7days));
	$('#expiration-date').val(parseDate(after30days));
})();

function parseDate(date) {
	return date.toLocaleString('sv-SE', {timeZone: 'Asia/Seoul'}).slice(0, 16);
}
 


</script>
