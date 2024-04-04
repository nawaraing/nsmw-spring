/*
 * Update Product Sale Stop
 */

function updateProductSaleStop(maxIdx) {
	
	let updateProductSaleStopForm = $('#update-product-sale-stop-form');
	let isChecked;
	for (let i = 0; i < maxIdx; i++) {
		isChecked = $('#checkbox-id-' + i).prop("checked");
        if (!isChecked) {
			continue;
        }

		let productID = $('#checkbox-id-' + i);
		console.log('productID: ' + productID.val());
		updateProductSaleStopForm.append(productID);
	}
	
}