package com.rynkbit.coffeestore.management.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rynkbit.coffeestore.NavbarHider
import com.rynkbit.coffeestore.R

class ManageProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavbarHider(this).hideNavbar()

        setContentView(R.layout.activity_manage_products)
    }
}
