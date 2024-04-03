<%@ tag language="java" pageEncoding="UTF-8"%>

<script src="/resources/admin/assets/vendor/libs/jquery/jquery.js"></script>

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
            <!-- Carousel -->
            <div id="carouselExample" class="carousel carousel-dark slide" data-bs-ride="carousel">
              <ol class="carousel-indicators" id="carousel-indicators">
                <li data-bs-target="#carouselExample" data-bs-slide-to="0" class="active"></li>
              </ol>
              <div class="carousel-inner mb-3" id="image-preview">
                <div class="carousel-item active">
                  <img class="d-block w-100" src="/resources/commonImages/no-image.jpg" alt="First slide" />
<!--                   <div class="carousel-caption d-none d-md-block">
                    <h3>First slide</h3>
                  </div> --> 
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
              <input type="file" name="images" multiple class="btn btn-primary me-2" id="upload-image" onchange="displayImage()" style="display: none;"/>
              <div id="too-many-images"></div>
              <button type="button" class="btn btn-primary me-2" onclick="$('#upload-image').click();">이미지 업로드</button>
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
              />
            </div>
            <div class="mb-3">
              <label for="exampleFormControlTextarea1" class="form-label">상품 설명</label>
              <textarea name="productDetail" class="form-control" id="exampleFormControlTextarea1" rows="3" placeholder="상품 설명"></textarea>
            </div>
            <div class="mb-3">
              <label for="costPrice" class="form-label">원가</label>
              <div class="input-group input-group-merge">
                <span class="input-group-text">₩</span>
                <input type="number" name="costPrice" class="form-control" placeholder="12000" aria-label="Amount (to the nearest dollar)" id="costPrice">
              </div>
            </div>
            <div class="mb-3">
              <label for="costPrice" class="form-label">정가</label>
              <div class="input-group input-group-merge">
                <span class="input-group-text">₩</span>
                <input type="number" name="retailPrice" class="form-control" placeholder="12000" aria-label="Amount (to the nearest dollar)" id="costPrice">
              </div>
            </div>
            <div class="mb-3">
              <label for="costPrice" class="form-label">판매가</label>
              <div class="input-group input-group-merge">
                <span class="input-group-text">₩</span>
                <input type="number" name="salePrice" class="form-control" placeholder="12000" aria-label="Amount (to the nearest dollar)" id="costPrice">
              </div>
            </div>
            <div class="mb-3">
              <label for="costPrice" class="form-label">재고</label>
              <div class="input-group input-group-merge">
                <input type="number" name="stock" class="form-control" placeholder="30" aria-label="Amount (to the nearest dollar)" id="costPrice">
                <span class="input-group-text">개</span>
              </div>
            </div>
            <div class="mb-3">
              <label for="defaultFormControlInput" class="form-label">성분</label>
              <input
                type="text"
                name="ingredient"
                class="form-control"
                id="defaultFormControlInput"
                placeholder="성분"
                aria-describedby="defaultFormControlHelp"
              />
            </div>
            <div class="mb-3">
              <label for="defaultFormControlInput" class="form-label">용법</label>
              <input
                type="text"
                name="dosage"
                class="form-control"
                id="defaultFormControlInput"
                placeholder="1일 2회, 1회 2정 섭취"
                aria-describedby="defaultFormControlHelp"
              />
            </div>
            <div class="mb-3">
              <label for="defaultFormControlInput" class="form-label">소비기한</label>
              <input
                type="text"
                name="expirationDate"
                class="form-control"
                id="defaultFormControlInput"
                placeholder="제조일로부터 24개월"
                aria-describedby="defaultFormControlHelp"
              />
            </div>

            <!-- 카테고리 -->
            <div class="mb-3">
              <label for="defaultFormControlInput" class="form-label">카테고리</label>
              <div>
              <a
                class="btn btn-outline-primary dropdown-toggle"
                href="javascript:void(0)"
                id="product-categories"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                <span class="badge bg-label-info me-1" id="product-0-category-">뇌</span>
                <span class="badge bg-label-info me-1" id="product-0-category-">간</span>
                <span class="badge bg-label-info me-1" id="product-0-category-">눈</span>
              </a>
              <ul class="dropdown-menu" aria-labelledby="navbarDropdown" id="checkbox-categories-${status.index}">
                <li>
                  <div class="form-check">
                    <label class="dropdown-item" for="checkbox-product--category-" id="dropdown-item-coupon--category-">
                      <input class="form-check-input" name="categoryNames" type="checkbox" value="뇌" id="checkbox-product--category-" />뇌
                    </label>
                    <label class="dropdown-item" for="checkbox-product--category-" id="dropdown-item-coupon--category-">
                      <input class="form-check-input" name="categoryNames" type="checkbox" value="간" id="checkbox-product--category-" />간
                    </label>
                    <label class="dropdown-item" for="checkbox-product--category-" id="dropdown-item-coupon--category-">
                      <input class="form-check-input" name="categoryNames" type="checkbox" value="눈" id="checkbox-product--category-" />눈
                    </label>
                  </div>
                </li>
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

<script>
const maxImageCnt = 3;
function displayImage() {
	console.log('Call displayImage()');
	
	// 3개 이상 등록 시 경고 문구 비우기
	let tooManyImages = $('#too-many-images');
	tooManyImages.empty();

    // 하단 이미지 목록 비우기
    let imageList = $('#image-list');
    imageList.empty();
	
    // 상단 이미지 미리보기 비우기
    var imagePreview = $('#image-preview');
    imagePreview.empty();
    
    // 캐러셀 눈금 비우기
    let indicators = $('#carousel-indicators');
    indicators.empty();
    
    let files = $('#upload-image')[0].files;
    console.log('files: ' + files);
    // TODO: files가 null인 경우 처리 
	let modifiedFiles = [];
    
    $.each(files, function(index, file) {
    	console.log('$.each(' + index + ')');
    	// 3개 초과 시 경고 문구 & 초과하는 이미지 파일 무시
    	if (index >= maxImageCnt) {
    		tooManyImages.empty();
    		tooManyImages.append($('<h5 class="text-danger">').text('이미지는 최대 3개까지 등록할 수 있습니다'));
    		return false;
    	}
    	
    	// 하단 이미지 목록에 이미지 이름 추가
    	let imageName = generateUUID() + '.jpg';
    	
		let div = $('<div class="d-flex mb-3" id="image-list-' + index + '">').append($('<div class="flex-grow-1 row">'));
		let h4 = $('<div class="col-10 col-sm-10 mb-sm-0 mb-2">').append($('<h4 class="mb-0">').text(imageName));
		let hidden = $('<input name="imagePaths" value="' + imageName + '" type="hidden">');
		let trashBtn = $('<div class="col-2 col-sm-2 text-end">').append($('<button type="button" class="btn btn-icon btn-outline-danger" onclick="deleteImage(' + index + ')">').append('<i class="bx bx-trash-alt">'));
		
		div.append(h4);
		div.append(hidden);
		div.append(trashBtn);
		
		imageList.append(div);
		modifiedFiles.push(file);
		
		// 상단 이미지 미리보기
	    var reader = new FileReader();
	    let idx = index;
	
	    reader.onload = function(event) {
	     
			var imageUrl = event.target.result;
			// console.log('imageUrl: ' + imageUrl);
			let active = '';
			if (idx === 0) {
				active = 'active';
			}
			console.log('idx: ' + idx);
			console.log('active: ' + active);
			
			var imageHtml = `
			    <div class="carousel-item ` + active + `" id="image-preview-` + idx + `">
			        <img class="d-block w-100" src="` + imageUrl + `" />
			    </div>`;
			// console.log('imageHtml: ' + imageHtml);
			imagePreview.append(imageHtml);
		};
		reader.readAsDataURL(file);
		
		// 캐러셀 눈금
    	let active = '';
    	if (index === 0) active = 'class="active"';
    	let li = $('<li data-bs-target="#carouselExample" id="carousel-indicator-' + index + '" data-bs-slide-to="' + index + '" ' + active + '>');
    	indicators.append(li);
    });
    $(this)[0].files = modifiedFiles;
}
    
    
/*     <ol class="carousel-indicators" id="carousel-indicators">
    <li data-bs-target="#carouselExample" data-bs-slide-to="0" class="active"></li>
    <li data-bs-target="#carouselExample" data-bs-slide-to="1"></li>
    <li data-bs-target="#carouselExample" data-bs-slide-to="2"></li>
  </ol>
 */

let deleteImageList = [];
function deleteImage(index) {
	console.log('deleteImage index: ' + index);
	
	// 하단 이미지 목록 삭제
	$('#image-list-' + index).remove();
	
	// 상단 이미지 미리보기 삭제
	let imagePreview = $('#image-preview-' + index);
//	console.log('imagePreview: ' + imagePreview);
	if (imagePreview.hasClass('active')) {
		console.log('hasClass == true');
		// active 옮기기
		for (let i = 0; i < maxImageCnt; i++) {
			if (index === i || $('#image-preview-' + i).length <= 0) continue;
			
			$('#image-preview-' + i).addClass('active');
			console.log('image preview addClass(active) on ' + i);
			break;
		}
	}
	deleteImageList.push(index);
	imagePreview.remove();
	
	// 캐러셀 눈금 삭제
	let imageIndicator = $('#carousel-indicator-' + index);
	if (imageIndicator.hasClass('active')) {
		// active 옮기기
		for (let i = 0; i < maxImageCnt; i++) {
			if (index === i || $('#carousel-indicator-' + i).length <= 0) continue;
			
			$('#carousel-indicator-' + i).addClass('active');
			console.log('image indicator addClass(active) on ' + i);
			break;
		}
	}
	imageIndicator.remove();

	
	// multipart 이미지 삭제
    var files = $('#upload-image')[0].files;
	console.log('delete files: ' + files);
	
    var modifiedFiles = Array.from(files).filter(function(_, idx) {
        return idx !== index;
    });
    $('#upload-image')[0].files = modifiedFiles;
}

function generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0,
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

// 저장 버튼 클릭 시
// 이미지 파일 input에 추가
$(document).ready(function() {
    $('#submit-button').click(function() {
        var files = $('#upload-image')[0].files;
        var formData = new FormData();
        
        $.each(files, function (index, file) {
        	if (index >= maxImageCnt) {
        		return false;
        	}
        	if (deleteImageList.indexOf(index) === -1) {
        		return ;
        	}
        	console.log('index : ' + index);
            formData.append('images[]', file);
        });
    });
});


</script>
