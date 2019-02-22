package com.rynkbit.coffeestore.management.transaction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rynkbit.coffeestore.NavbarHider
import com.rynkbit.coffeestore.R

class ManageTransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavbarHider(this).hideNavbar()
        setContentView(R.layout.activity_manage_transaction)
    }
}
