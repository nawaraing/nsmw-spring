/*
 * List Checkbox
 */

function handleListCheckbox(idx, rowNum) {
//	console.log('idx : ' + idx);
	let isChecked = $("#checkbox-id-" + idx).prop("checked");
//	console.log('isChecked : ' + isChecked);

	if (isChecked) {
		// 전체가 checked이면 header도 checked
		let allCheck = true;
		for (let i = 0; i < rowNum; i++) {
			if ($("#checkbox-id-" + i).prop("disabled")) {
				continue;
			}
			if (!$("#checkbox-id-" + i).prop("checked")) {
				allCheck = false;
				break;
			}
		}
		if (allCheck) {
			$("#checkbox-id-table-header").prop("checked", true);
		}
	} else {
		$("#checkbox-id-table-header").prop("checked", false);
	}
}

function handleCheckboxHeader(rowNum) {
	let isChecked = $("#checkbox-id-table-header").prop("checked");

	if (isChecked) {
		for (let i = 0; i < rowNum; i++) {
			if ($("#checkbox-id-" + i).prop("disabled")) {
				continue;
			}
			$("#checkbox-id-" + i).prop("checked", true);
		}
	} else {
		for (let i = 0; i < rowNum; i++) {
			if ($("#checkbox-id-" + i).prop("disabled")) {
				continue;
			}
			$("#checkbox-id-" + i).prop("checked", false);
		}
	}
}

/*
 * Category Checkbox
 */

function handleCategoryCheckbox(rowNum, allCategory) {
	console.log("escape: " +"#checkbox-product-" + rowNum + "-category-" + jQuery.escapeSelector(allCategory));
	let checkbox = $("#checkbox-product-" + rowNum + "-category-" + jQuery.escapeSelector(allCategory));
	let aTag = $('#product-categories-' + rowNum);
	
	if (checkbox.prop("checked")) {
	    span = $('<span class="badge bg-label-info me-1" id="product-' + rowNum + '-category-' + allCategory + '">').text(allCategory);
		aTag.append(span);
	} else {
		$('#product-' + rowNum + '-category-' + jQuery.escapeSelector(allCategory)).remove();
	}
}