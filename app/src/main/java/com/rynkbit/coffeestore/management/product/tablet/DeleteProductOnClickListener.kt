package com.rynkbit.coffeestore.management.product.tablet

import android.app.AlertDialog
import android.view.View
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.data.entity.Product
import java.text.NumberFormat

class DeleteProductOnClickListener(val viewModel: ManageProductsViewModel, val product: Product): View.OnClickListener {
    override fun onClick(v: View?) {
        AlertDialog.Builder(v?.context)
            .setMessage(
                v?.context?.getString(
                    R.string.dialog_delete_product,
                    product.name,
                    NumberFormat.getCurrencyInstance().format(product.price),
                    NumberFormat.getInstance().format(product.stock)
                )
            )
            .setTitle(
                v?.context?.getString(R.string.dialog_delete_title, product.name))
            .setPositiveButton(R.string.dialog_confirm) { dialogInterface, _ ->
                viewModel.deleteProduct(product)
                dialogInterface.dismiss()
            }
            .setNegativeButton(R.string.dialog_cancel) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .show()
    }

}