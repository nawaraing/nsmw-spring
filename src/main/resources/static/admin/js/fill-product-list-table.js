/*
 * Fill Product List Table
 */

function fillProductListTable(datas) {
	console.log('fillProductListTable');
//    console.log("curFile start fillProductListTable: " + curFile);

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
    let td, div, input, aTag, span, ul, li, label;
    $.each(datas, function(index, data) {
//    	console.log("index: " + index);
        if (Math.floor(index / 10) + 1 === curPage) {
            let tr = $('<tr>');
            
            // 체크 박스
            td = $('<td>');
            div = $('<div class="form-check">');
            input = $('<input class="form-check-input" type="checkbox" value="' + data.productID + '" name="productID" id="checkbox-id-' + rowNum + '" onclick="handleListCheckbox(' + rowNum + ', ' + Math.min(datas.length - index, 10) + ')" />');
            tr.append(td.append(div.append(input)));
            
            // 상품명
            td = $('<td>');
            input = $('<input style="width: 150px;" type="text" class="form-control" id="product-name-' + rowNum + '" value="' + data.productName + '" name="productName" />');
            tr.append(td.append(input));
            
            // 상품 설명
            td = $('<td>');
            input = $('<input style="width: 150px;" type="text" class="form-control" id="product-detail-' + rowNum + '" value="' + data.productDetail + '" name="productDetail" />');
            tr.append(td.append(input));
         
			// 원가
            td = $('<td>');
            input = $('<input style="width: 90px;" type="number" class="form-control" id="cost-price-' + rowNum + '" value="' + data.costPrice + '" name="costPrice" />');
            tr.append(td.append(input));
			
			// 정가
            td = $('<td>');
            input = $('<input style="width: 90px;" type="number" class="form-control" id="retail-price-' + rowNum + '" value="' + data.retailPrice + '" name="retailPrice" />');
            tr.append(td.append(input));
			
			// 판매가
            td = $('<td>');
            input = $('<input style="width: 90px;" type="number" class="form-control" id="sale-price-' + rowNum + '" value="' + data.salePrice + '" name="salePrice" />');
            tr.append(td.append(input));
			
			// 재고
            td = $('<td>');
            input = $('<input style="width: 70px;" type="number" class="form-control" id="stock-' + rowNum + '" value="' + data.stock + '" name="stock" />');
            tr.append(td.append(input));
			
			// 성분
            td = $('<td>');
            input = $('<input style="width: 150px;" type="text" class="form-control" id="ingredient-' + rowNum + '" value="' + data.ingredient + '" name="ingredient" />');
            tr.append(td.append(input));
            
			// 용법
            td = $('<td>');
            input = $('<input style="width: 150px;" type="text" class="form-control" id="dosage-' + rowNum + '" value="' + data.dosage + '" name="dosage" />');
            tr.append(td.append(input));
            
			// 소비기한
            td = $('<td>');
            input = $('<input style="width: 150px;" type="text" class="form-control" id="expiration-date-' + rowNum + '" value="' + data.expirationDate + '" name="expirationDate" />');
            tr.append(td.append(input));
            
			// 카테고리
            td = $('<td>');
			// 전체 카테고리 목록 표시
			ul = $('<ul class="dropdown-menu" aria-labelledby="navbarDropdown" id="checkbox-categories-' + rowNum + '">');
            $.each(categoryList, function(allCategoryIdx, allCategory) {
				li = $('<li>');
				div = $('<div class="form-check">');
				label = $('<label class="dropdown-item" for="checkbox-product-' + rowNum + '-category-' + allCategory + '" id="dropdown-item-product-' + rowNum + '-category-' + allCategory + '">');
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
			td = $('<td id="register-date-' + rowNum + '">').text(moment(data.registerDate).format('YYYY-MM-DD'));
			tr.append(td);
			
			// 마지막 수정일
			td = $('<td id="modify-date-' + rowNum + '">').text(moment(data.modifyDate).format('YYYY-MM-DD'));
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
    $('#table-body').replaceWith(tbody);
    
//    console.log("curFile end fillProductListTable: " + curFile);
}