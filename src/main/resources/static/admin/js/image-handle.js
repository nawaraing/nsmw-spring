/*
 * Image Handle
 */

function displayImage(rowNum, maxImageCnt) {
	console.log('Call displayImage()');
	
	// 3개 이상 등록 시 경고 문구 비우기
	let tooManyImages = $('#too-many-images-' + rowNum);
	tooManyImages.empty();

    // 하단 이미지 목록 비우기
    let imageList = $('#image-list-' + rowNum);
    imageList.empty();
	
    // 상단 이미지 미리보기 비우기
    var imagePreview = $('#image-preview-' + rowNum);
    imagePreview.empty();
    
    // 캐러셀 눈금 비우기
    let indicators = $('#carousel-indicators-' + rowNum);
    indicators.empty();
    
    let files = $('#upload-image-' + rowNum)[0].files;
    console.log('files: ' + files);
    // TODO: files가 null인 경우 처리 
	let modifiedFiles = [];
    
    $.each(files, function(index, file) {
    	console.log('$.each(' + index + ')');
    	// 3개 초과 시 경고 문구 & 초과하는 이미지 파일 무시
    	if (index >= maxImageCnt) {
    		tooManyImages.empty();
    		tooManyImages.append($('<h5 class="text-danger">').text('이미지는 최대 ' + maxImageCnt + '개까지 등록할 수 있습니다'));
    		return false;
    	}
    	
    	// 하단 이미지 목록에 이미지 이름 추가
    	let imageName = generateUUID() + '.jpg';
    	
		let div = $('<div id="image-list-' + rowNum + '-image-' + index + '" class="d-flex mb-3" >').append($('<div class="flex-grow-1 row">'));
		let h4 = $('<div class="col-10 col-sm-10 mb-sm-0 mb-2">').append($('<h4 class="mb-0 text-truncate">').text(imageName));
		let hidden = $('<input name="imagePaths" value="' + imageName + '" type="hidden">');
		let trashBtn = $('<div class="col-2 col-sm-2 text-end">').append($('<button type="button" class="btn btn-icon btn-outline-danger" onclick="deleteImage(\'' + rowNum + '\', ' + index + ', ' + maxImageCnt + ')">').append('<i class="bx bx-trash-alt">'));
		
		div.append(h4);
		div.append(hidden);
		div.append(trashBtn);
		
		imageList.append(div);
//		modifiedFiles.push(file);
		
		// 상단 이미지 미리보기
	    var reader = new FileReader();
	
	    reader.onload = function(event) {
		    let idx = index;
	     
			var imageUrl = event.target.result;
			// console.log('imageUrl: ' + imageUrl);
			let active = '';
			if (idx === 0) {
				active = 'active';
			}
			console.log('idx: ' + idx);
			console.log('active: ' + active);
			
			var imageHtml = `
			    <div class="carousel-item ` + active + `" id="image-preview-` + rowNum + `-image-` + idx + `">
			        <img class="d-block w-100" src="` + imageUrl + `" />
			    </div>`;
			// console.log('imageHtml: ' + imageHtml);
			imagePreview.append(imageHtml);
		};
		reader.readAsDataURL(file);
		
		// 캐러셀 눈금
    	let active = '';
    	if (index === 0) active = 'class="active"';
    	let li = $('<li data-bs-target="#carousel-' + rowNum + '" id="carousel-indicator-' + rowNum + '-image-' + index + '" data-bs-slide-to="' + index + '" ' + active + '>');
    	indicators.append(li);
    });
//    미동작 코드 주석, 연구 필요
//    $(this)[0].files = modifiedFiles;
}
    
    
/*     <ol class="carousel-indicators" id="carousel-indicators">
    <li data-bs-target="#carouselExample" data-bs-slide-to="0" class="active"></li>
    <li data-bs-target="#carouselExample" data-bs-slide-to="1"></li>
    <li data-bs-target="#carouselExample" data-bs-slide-to="2"></li>
  </ol>
 */

let deleteImageList = [];
function deleteImage(rowNum, index, maxImageCnt) {
	console.log('deleteImage index: ' + index);
	
	// 하단 이미지 목록 삭제
	$('#image-list-' + rowNum + '-image-' + index).remove();
	
	// 상단 이미지 미리보기 삭제
	let imagePreview = $('#image-preview-' + rowNum + '-image-' + index);
//	console.log('imagePreview: ' + imagePreview);
	if (imagePreview.hasClass('active')) {
		console.log('hasClass == true');
		// active 옮기기
		for (let i = 0; i < maxImageCnt; i++) {
			if (index === i || $('#image-preview-' + rowNum + '-image-' + i).length <= 0) continue;
			
			$('#image-preview-' + rowNum + '-image-' + i).addClass('active');
			console.log('image preview addClass(active) on ' + i);
			break;
		}
	}
	deleteImageList.push(index);
	imagePreview.remove();
	
	// 캐러셀 눈금 삭제
	let imageIndicator = $('#carousel-indicator-' + rowNum + '-image-' + index);
	if (imageIndicator.hasClass('active')) {
		// active 옮기기
		for (let i = 0; i < maxImageCnt; i++) {
			if (index === i || $('#carousel-indicator-' + rowNum + '-image-' + i).length <= 0) continue;
			
			$('#carousel-indicator-' + rowNum + '-image-' + i).addClass('active');
			console.log('image indicator addClass(active) on ' + i);
			break;
		}
	}
	imageIndicator.remove();

	// multipart 이미지 삭제 -> gpt 피셜 불가능하다고 함, 좀 더 알아볼 필요가 있음
//    var files = $('#upload-image-' + rowNum)[0].files;
//	console.log('delete files: ' + files);
//	
//    var modifiedFiles = Array.from(files).filter(function(_, idx) {
//        return idx !== index;
//    });
//    $('#upload-image-' + rowNum)[0].files = modifiedFiles;
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

