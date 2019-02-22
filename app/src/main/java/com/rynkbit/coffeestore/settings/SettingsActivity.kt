package com.rynkbit.coffeestore.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rynkbit.coffeestore.NavbarHider
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.backup.BackupActivity
import com.rynkbit.coffeestore.export.ExportActivity
import com.rynkbit.coffeestore.management.customer.ManageCustomersActivity
import com.rynkbit.coffeestore.management.product.ManageProductsActivity
import com.rynkbit.coffeestore.management.transaction.ManageTransactionActivity
import com.rynkbit.coffeestore.password.PasswordActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavbarHider(this).hideNavbar()
        setContentView(R.layout.activity_settings)

        cardManageCustomers.setOnClickListener {
            it?.context?.startActivity(
                Intent(it.context, ManageCustomersActivity::class.java)
            )
        }
        cardManageProducts.setOnClickListener {
            it?.context?.startActivity(
                Intent(it.context, ManageProductsActivity::class.java)
            )
        }
        cardManageTransactions.setOnClickListener {
            it?.context?.startActivity(
                Intent(it.context, ManageTransactionActivity::class.java)
            )
        }
        cardExport.setOnClickListener {
            it?.context?.startActivity(
                Intent(it.context, ExportActivity::class.java)
            )
        }
        cardPassword.setOnClickListener {
            it?.context?.startActivity(
                Intent(it.context, PasswordActivity::class.java)
            )
        }
        cardBackup.setOnClickListener {
            it?.context?.startActivity(
                Intent(it.context, BackupActivity::class.java)
            )
        }
    }
}
