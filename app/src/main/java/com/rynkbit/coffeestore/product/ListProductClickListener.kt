package com.rynkbit.coffeestore.product

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.customer.MainActivity
import com.rynkbit.coffeestore.data.AppDatabaseStore
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.data.entity.Product
import com.rynkbit.coffeestore.data.repository.CustomerRepository
import com.rynkbit.coffeestore.data.repository.CustomerTransactionRepository
import com.rynkbit.coffeestore.data.repository.ProductRepository
import java.text.NumberFormat

class ListProductClickListener(private val customer: Customer, private val product: Product): View.OnClickListener{
    override fun onClick(v: View?) {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(v?.context)
        dialog
            .setMessage(
                v?.context?.getString(
                    R.string.dialog_buy_product, product.name,
                    NumberFormat.getCurrencyInstance().format(product.price))
            )
            .setTitle(
                v?.context?.getString(
                    R.string.dialog_tilte_buy_product, product.name)
            )
            .setPositiveButton(R.string.dialog_confirm, DialogInterface.OnClickListener { dialogInterface, _ ->
                CustomerTransactionRepository(AppDatabaseStore.instance.appDatabase)
                    .performTransaction(customer, product, CustomerRepository(AppDatabaseStore.instance.appDatabase), ProductRepository(AppDatabaseStore.instance.appDatabase))
                v?.context?.startActivity(
                    Intent(v.context, MainActivity::class.java)
                )
                dialogInterface.dismiss()
            })
            .setNegativeButton(R.string.dialog_cancel, DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.dismiss()
            })
        dialog.show()
    }

}