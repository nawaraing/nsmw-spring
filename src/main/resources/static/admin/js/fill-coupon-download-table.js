/*
 * Fill Coupon Download Table
 */

function fillCouponDownloadTable(datas) {
	console.log('fillCouponDownloadTable');
	
    let tbody = $('<tbody class="table-border-bottom-0" id="table-body">');

    // Table footer
    let tfooter = $('#table-footer');
    tfooter.empty();
    if (datas.length <= 0) {
        let row = $('<tr>');
        row.append($('<td>').text("표시할 데이터가 없습니다.."));
        tfooter.append(row);
	    console.log("curFile datas.length <= 0: " + curFile);
        return ;
    }

    // Table body
    let rowNum = 0;
    let td, div, input, disabled, button, modalContent, modalHeader, modalBody, modalSm, form ,h5, textCenter;
    $.each(datas, function(index, data) {
//    	console.log("index: " + index);
        if (Math.floor(index / 10) + 1 === curPage) {
            let tr = $('<tr>');
            
            disabled = '';
            if (data.ancDeployStatus === '배포 중단' || data.ancDeployStatus === '배포 완료') {
				disabled = 'disabled';
			}
            
            // 체크 박스
            td = $('<td>');
            div = $('<div class="form-check">');
            input = $('<input class="form-check-input" type="checkbox" value="' + data.couponDownloadID + '" name="couponDownloadID" id="checkbox-id-' + rowNum + '" onclick="handleListCheckbox(' + rowNum + ', ' + Math.min(datas.length - index, 10) + ')" ' + disabled + ' />');
            tr.append(td.append(div.append(input)));
            
            // 쿠폰 이름
            td = $('<td id="coupon-name-${status.index}">').text(data.couponName);
            tr.append(td);
            
			// 배포 시작일
			td = $('<td id="distribute-date-' + rowNum + '">').text(moment(data.distributeDate).format('YYYY-MM-DD'));
			tr.append(td);
			
            // 배포 마감일
            td = $('<td>');
            input = $('<input style="width: 230px;" type="datetime-local" class="form-control" id="deploy-deadline-' + rowNum + '" value="' + data.ancDeployDeadline + '" name="ancDeployDeadline" ' + disabled + ' />');
            tr.append(td.append(input));
         
			// 쿠폰 만료일
			td = $('<td id="expiration-date-' + rowNum + '">').text(moment(data.expirationDate).format('YYYY-MM-DD'));
			tr.append(td);
            
			// 카테고리
            td = $('<td id="coupon-categories-' + rowNum + '">');
//            console.log("data.categories : " + data.categories);
            $.each(data.categories, function(couponCategoryIdx, couponCategory) {
				div = $('<div class="badge bg-label-info me-1" id="coupon-' + rowNum + '-category-' + couponCategoryIdx + '">').text(couponCategory);
			    td.append(div);
			});
			tr.append(td);
			
			// 할인 방식
			td = $('<td id="coupon-type-' + rowNum + '">').text(data.couponType);
			tr.append(td);
			
			// 할인율(액)
			td = $('<td id="coupon-discount-' + rowNum + '">').text(data.ancDiscount);
			tr.append(td);
			
			// 금액 제한
			td = $('<td id="coupon-limit-' + rowNum + '">').text(data.ancAmount);
			tr.append(td);
			
			// 팝업 이미지
			td = $('<td>');
			
			button = $('<button type="button" class="btn btn-primary me-2" data-bs-toggle="modal" data-bs-target="#imageModal-' + rowNum + '">').text("이미지 확인");
			td.append(button);
			
			div = $('<div class="modal fade" id="imageModal-' + rowNum + '" data-bs-backdrop="static" tabindex="-1">');
			modalSm = $('<div class="modal-dialog modal-dialog-centered modal-sm">');
			
			modalContent = $('<div class="modal-content">');
			modalHeader = $('<div class="modal-header">');
			h5 = $('<h5 class="modal-title" id="backDropModalTitle">').text('쿠폰 추가 - 사용자 다운로드 쿠폰');
			button = $('<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>');
			modalHeader.append(h5);
			modalHeader.append(button);
			
			form = $('<form method="POST" enctype="multipart/form-data" action="/couponDownload/imageUpdate" id="form-image-modal-' + rowNum + '">');
			form.append($('<input type="hidden" name="couponDownloadID" value="' + data.couponDownloadID + '"/>'))
			modalBody = $('<div class="modal-body">').append(adminImageHandle(rowNum, data.ancImagePath));
			
			textCenter = $('<div class="text-center">');
			button = adminImageUploadButton(rowNum, 1);
			textCenter.append(button.find('#upload-image-' + rowNum));
			textCenter.append(button.find('#too-many-images-' + rowNum));
			textCenter.append(button.find('#upload-image-button-' + rowNum));
			textCenter.append($('<button type="button" class="btn btn-secondary me-2" data-bs-dismiss="modal">').text('취소'));
			textCenter.append($('<button type="submit" id="submit-button" class="btn btn-success me-2">').text('저장'));
			modalBody.append(textCenter);
			
			form.append(modalBody);
			modalContent.append(modalHeader);
			modalContent.append(form);
			console.log('ancImageID: ' + data.ancImageID);
			modalContent.append($('<input type="hidden" id="image-id-' + rowNum + '" name="imageID" value="' + data.ancImageID + '">'));
			
			modalSm.append(modalContent);
			div.append(modalSm);
			td.append(div);
			tr.append(td);
			
			// 배포 현황
			let bgLabel = '';
            if (data.ancDeployStatus === '배포 예정') {
				bgLabel = 'bg-label-primary';
            } else if (data.ancDeployStatus === '배포중') {
				bgLabel = 'bg-label-secondary';
            } else if (data.ancDeployStatus === '배포 중단') {
				bgLabel = 'bg-label-danger';
            } else if (data.ancDeployStatus === '배포 완료') {
				bgLabel = 'bg-label-success';
			}
            td = $('<td>');
			div = $('<div class="badge ' + bgLabel + ' me-1" id="deploy-status-' + rowNum + '">').text(data.ancDeployStatus);
			tr.append(td.append(div));
			
            tbody.append(tr);
            
			rowNum++;
        }
    });
    $('#table-body').replaceWith(tbody);
    
//    console.log("curFile end fillProductListTable: " + curFile);
}
