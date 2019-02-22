package com.rynkbit.coffeestore.customer

import android.content.Intent
import android.view.View
import com.rynkbit.coffeestore.EXTRA_CUSTOMER_ID
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.product.ProductActivity

class CustomerClickListener(val customer: Customer): View.OnClickListener{
    override fun onClick(v: View?) {
        val intent = Intent(v?.context, ProductActivity::class.java)
        intent.putExtra(EXTRA_CUSTOMER_ID, customer.id)
        v?.context?.startActivity(intent)
    }
}