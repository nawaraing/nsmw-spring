/*
 * Fill Product List Table
 */

function fillProductListTable(datas) {
	console.log('fillProductListTable');
//    console.log("curFile start fillProductListTable: " + curFile);

    // 엘레먼트 불러오기
    let tbody = $('#table-body');
    let tfooter = $('#table-footer');
    // 테이블 내용 비우기
    tbody.empty();
    tfooter.empty();

    // Table footer
    if (datas.length <= 0) {
        let row = $('<tr>');
        row.append($('<td>').text("표시할 데이터가 없습니다.."));
        tfooter.append(row);
	    console.log("curFile datas.length <= 0: " + curFile);
        return ;
    }

    // Table body
    let rowNum = 0;
    let td, div, input, iTag, strong, aTag, span, ul, li, label;
    $.each(datas, function(index, data) {
//    	console.log("index: " + index);
        if (Math.floor(index / 10) + 1 === curPage) {
            let tr = $('<tr>');
            
            // 체크 박스
            td = $('<td>');
            div = $('<div class="form-check">');
            input = $('<input class="form-check-input" type="checkbox" value="" id="checkbox-id-' + rowNum + '" onclick="handleListCheckbox(' + rowNum + ', 10)" />');
            tr.append(td.append(div.append(input)));
            
            // 상품명
            td = $('<td>');
            iTag = $('<i class="fab fa-angular fa-lg me-3">');
            strong = $('<strong id="product-name-' + rowNum + '">').text(data.productName);
            tr.append(td.append(iTag.append(strong)));
            
            // 상품 설명
            td = $('<td>');
            div = $('<div style="width: 150px;" class="text-truncate" id="product-detail-' + rowNum + '">').text(data.productDetail);
            tr.append(td.append(div));
         
			// 원가
			td = $('<td id="cost-price-' + rowNum + '">').text(data.costPrice);
			tr.append(td);
			
			// 정가
			td = $('<td id="retail-price-' + rowNum + '">').text(data.retailPrice);
			tr.append(td);
			
			// 판매가
			td = $('<td id="sale-price-' + rowNum + '">').text(data.salePrice);
			tr.append(td);
			
			// 재고
			td = $('<td id="stock-' + rowNum + '">').text(data.stock);
			tr.append(td);
			
			// 성분
            td = $('<td>');
            div = $('<div style="width: 150px;" class="text-truncate" id="ingredient-' + rowNum + '">').text(data.ingredient);
            tr.append(td.append(div));
            
			// 용법
            td = $('<td>');
            div = $('<div style="width: 150px;" class="text-truncate" class="dosage" id="dosage-' + rowNum + '">').text(data.dosage);
            tr.append(td.append(div));
            
			// 소비기한
            td = $('<td>');
            div = $('<div style="width: 150px;" class="text-truncate" id="expiration-date-' + rowNum + '">').text(data.expirationDate);
            tr.append(td.append(div));
            
			// 카테고리
            td = $('<td>');
			// 전체 카테고리 목록 표시
			ul = $('<ul class="dropdown-menu" aria-labelledby="navbarDropdown" id="checkbox-categories-' + rowNum + '">');
            $.each(categoryList, function(allCategoryIdx, allCategory) {
				li = $('<li>');
				div = $('<div class="form-check">');
				label = $('<label class="dropdown-item" for="checkbox-product-' + rowNum + '-category-' + allCategory + '" id="dropdown-item-coupon-' + rowNum + '-category-' + allCategory + '">');
				input = $('<input class="form-check-input" type="checkbox" onclick="handleCategoryCheckbox(' + rowNum + ', \'' + allCategory + '\')" value="' + allCategory + '" id="checkbox-product-' + rowNum + '-category-' + allCategory + '">');
				
//				console.log("allCategory: " + allCategory);
	            $.each(data.categories, function(productCategoryIdx, productCategory) {
//					console.log("productCategory: " + productCategory);
					if (allCategory === productCategory) {
						input.prop("checked", true);
					}
				});
				
				label.append(input);
				label.append(allCategory);
				ul.append(li.append(div.append(label)));
			});
            // 상품별 카테고리 표시
			aTag = $(`<a
                      class="dropdown-toggle"
		              href="javascript:void(0)"
		              id="product-categories-` + rowNum + `"
		              role="button"
		              data-bs-toggle="dropdown"
		              aria-expanded="false"
		            >`);
            $.each(data.categories, function(productCategoryIdx, productCategory) {
//				console.log("productCategory: " + productCategory);
			    span = $('<span class="badge bg-label-info me-1" id="product-' + rowNum + '-category-' + productCategory + '">').text(productCategory);
			    aTag.append(span);
			});
			td.append(aTag);
			td.append(ul);
			tr.append(td);
			
			// 등록일
			td = $('<td id="register-date-' + rowNum + '">').text(data.registerDate);
			tr.append(td);
			
			// 마지막 수정일
			td = $('<td id="modify-date-' + rowNum + '">').text(data.modifyDate);
			tr.append(td);
			
			// 판매 상태
			td = $('<td id="sale-state-' + rowNum + '">').text(data.saleState);
			tr.append(td);
			
			// 상품 이미지
			td = $('<td id="image-' + rowNum + '">').text('이미지 확인');
			tr.append(td);
			
            tbody.append(tr);
            
			rowNum++;
        }
    });
    
//    console.log("curFile end fillProductListTable: " + curFile);
}