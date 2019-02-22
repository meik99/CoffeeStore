package com.rynkbit.coffeestore.password

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import com.rynkbit.coffeestore.NavbarHider
import com.rynkbit.coffeestore.R
import kotlinx.android.synthetic.main.activity_password.*

class PasswordActivity : AppCompatActivity() {

    lateinit var viewModel: PasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavbarHider(this).hideNavbar()

        setContentView(R.layout.activity_password)
        viewModel = ViewModelProviders.of(this).get(PasswordViewModel::class.java)

        editPassword.addTextChangedListener {
            viewModel.password = it.toString()
        }

        btnSave.setOnClickListener {
            viewModel.savePassword(it.context)
            Toast.makeText(this, R.string.password_saved, Toast.LENGTH_SHORT).show()
        }
    }
}
