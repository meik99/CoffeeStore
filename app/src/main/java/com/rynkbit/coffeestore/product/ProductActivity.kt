package com.rynkbit.coffeestore.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.rynkbit.coffeestore.EXTRA_CUSTOMER_ID
import com.rynkbit.coffeestore.NavbarHider
import com.rynkbit.coffeestore.R
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavbarHider(this).hideNavbar()
        setContentView(R.layout.activity_product)

        val adapter = ListProductAdapter()
        val model = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        listProducts.layoutManager = GridLayoutManager(this, 6)
        listProducts.adapter = adapter

        model.customerId = intent.getIntExtra(EXTRA_CUSTOMER_ID, -1)
        model.products.observe(
            this,
            Observer {
                adapter.products = it
            }
        )
        model.customer.observe(
            this,
            Observer {
                txtTransactionCustomerName.text = it.name
                adapter.customer = it
            }
        )
    }
}
