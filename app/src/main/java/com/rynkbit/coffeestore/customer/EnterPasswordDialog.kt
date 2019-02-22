package com.rynkbit.coffeestore.customer

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.rynkbit.coffeestore.R
import kotlinx.android.synthetic.main.dialog_enter_password.*

class EnterPasswordDialog(context: Context): AlertDialog(context) {
    val password: MutableLiveData<String> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_enter_password)

        btnCancel.setOnClickListener {
            dismiss()
        }
        btnAccept.setOnClickListener {
            password.value = editPassword.text.toString()
            dismiss()
        }
    }
}