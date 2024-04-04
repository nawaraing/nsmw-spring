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
    let td, div, input, disabled;
    $.each(datas, function(index, data) {
//    	console.log("index: " + index);
        if (Math.floor(index / 10) + 1 === curPage) {
            let tr = $('<tr>');
            
            // 체크 박스
            td = $('<td>');
            div = $('<div class="form-check">');
            disabled = '';
            if (data.ancDeployStatus === '배포 중단' || data.ancDeployStatus === '배포 완료') {
				disabled = 'disabled';
			}
            input = $('<input class="form-check-input" type="checkbox" value="' + data.couponID + '" name="couponID" id="checkbox-id-' + rowNum + '" onclick="handleListCheckbox(' + rowNum + ', ' + Math.min(datas.length - index, 10) + ')" ' + disabled + ' />');
            tr.append(td.append(div.append(input)));
            
            // 쿠폰 이름
            td = $('<td id="coupon-name-${status.index}">').text(data.couponName);
            tr.append(td);
            
			// 배포 시작일
			td = $('<td id="distribute-date-' + rowNum + '">').text(moment(data.distributeDate).format('YYYY-MM-DD'));
			tr.append(td);
			
            // 배포 마감일
            td = $('<td>');
            input = $('<input style="width: 230px;" type="datetime-local" class="form-control" id="deploy-deadline-' + rowNum + '" value="' + data.ancDeployDeadline + '" name="ancDeployDeadline" />');
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
			td = $('<td id="image-' + rowNum + '">').text('이미지 확인');
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