/*
 * Fill Member List Table
 */

function fillMemberListTable(datas) {
	console.log('fillMemberListTable');
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
            
            // 회원 이름
            td = $('<td>');
            iTag = $('<i class="fab fa-angular fa-lg me-3">');
            strong = $('<strong id="member-name-' + rowNum + '">').text(data.memberName);
            tr.append(td.append(iTag.append(strong)));
            
			// 회원 ID
			td = $('<td id="member-id-' + rowNum + '">').text(data.memberID);
			tr.append(td);
			
			// 생년월일
			td = $('<td id="day-of-birth-' + rowNum + '">').text(data.dayOfBirth);
			tr.append(td);
			
			// 회원 ID
			td = $('<td id="gender-' + rowNum + '">').text(data.gender);
			tr.append(td);
			
			// 회원 ID
			td = $('<td id="phone-number-' + rowNum + '">').text(data.phoneNumber);
			tr.append(td);
			
			// 회원 ID
			td = $('<td id="email' + rowNum + '">').text(data.email);
			tr.append(td);
			
			// 회원 ID
			td = $('<td id="postcode-' + rowNum + '">').text(data.ancShippingPostCode);
			tr.append(td);
			
			// 회원 ID
			td = $('<td id="address-' + rowNum + '">').text(data.ancShippingAddress);
			tr.append(td);
			
			// 회원 ID
			td = $('<td id="adress-detail-' + rowNum + '">').text(data.ancShippingAddressDetail);
			tr.append(td);
			
			// 회원 ID
			td = $('<td id="grade-' + rowNum + '">').text(data.ancGradeName);
			tr.append(td);
			
			// 카테고리
            td = $('<td id="member-categories-' + rowNum + '">');
            console.log("data.categories : " + data.categories);
            $.each(data.categories, function(productCategoryIdx, productCategory) {
				div = $('<div class="badge bg-label-info me-1" id="member-' + rowNum + '-category-' + productCategoryIdx + '">').text(productCategory);
			    td.append(div);
			});
			tr.append(td);
			
            tbody.append(tr);
            
			rowNum++;
        }
    });
    
//    console.log("curFile end fillProductListTable: " + curFile);
}