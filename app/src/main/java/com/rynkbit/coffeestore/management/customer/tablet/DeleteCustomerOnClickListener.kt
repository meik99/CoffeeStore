package com.rynkbit.coffeestore.management.customer.tablet

import android.app.AlertDialog
import android.view.View
import com.rynkbit.coffeestore.R
import java.text.NumberFormat

class DeleteCustomerOnClickListener( val viewModel: ManageCustomersViewModel): View.OnClickListener{
    override fun onClick(v: View?) {
        val customer = viewModel.selectedCustomer.value
        if (customer != null) {
            AlertDialog.Builder(v?.context)
                .setMessage(
                    v?.context?.getString(
                        R.string.dialog_delete_customer,
                        customer.name,
                        NumberFormat.getCurrencyInstance().format(customer.balance)
                    )
                )
                .setTitle(
                    v?.context?.getString(R.string.dialog_delete_title, customer.name))
                .setPositiveButton(R.string.dialog_confirm) { dialogInterface, _ ->
                    viewModel.deleteCustomer(customer)
                    dialogInterface.dismiss()
                }
                .setNegativeButton(R.string.dialog_cancel) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .show()
        }

    }

}