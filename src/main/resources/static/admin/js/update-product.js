/*
 * Update Product
 */

function updateProduct(maxIdx) {
	
	let isChecked;
	for (let i = 0; i < maxIdx; i++) {
		isChecked = $('#checkbox-id-' + i).prop("checked");
        if (!isChecked) {
			continue;
        }

		let updateProductForm = $('#update-product-form');
		
		let productID = $('#checkbox-id-' + i);
		updateProductForm.append(productID);
		let productName = $('#product-name-' + i);
		updateProductForm.append(productName);
		let productDetail = $('#product-detail-' + i);
		updateProductForm.append(productDetail);
		let costPrice = $('#cost-price-' + i);
		updateProductForm.append(costPrice);
		let retailPrice = $('#retail-price-' + i);
		updateProductForm.append(retailPrice);
		let salePrice = $('#sale-price-' + i);
		updateProductForm.append(salePrice);
		let stock = $('#stock-' + i);
		updateProductForm.append(stock);
		let ingredient = $('#ingredient-' + i);
		updateProductForm.append(ingredient);
		let dosage = $('#dosage-' + i);
		updateProductForm.append(dosage);
		let expirationDate = $('#expiration-date-' + i);
		updateProductForm.append(expirationDate);
		
		let categories = '';
		let labelInput = $('#checkbox-categories-' + i + ' input');
		labelInput.each(function(index) {
			if (!($(this).prop("checked"))) {
				return true;
			}
			if (categories !== '') {
				categories += ';';
			}
			categories += $(this).attr('value');
		});
		console.log('productName: ' + productName.attr('value') + 'categories: ' + categories);
		let categoryIDs = $('<input type="text" value="' + categories + '" name="categoryNames">');
		updateProductForm.append(categoryIDs);
	}
	
}