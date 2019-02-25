package com.rynkbit.coffeestore.password

import android.content.Context
import androidx.lifecycle.ViewModel
import com.rynkbit.coffeestore.R

class PasswordViewModel : ViewModel() {
    var password: String = ""

    fun savePassword(context: Context) {

        val sharedPreferences = context.getSharedPreferences(
            context.getString(R.string.preference_password_key), Context.MODE_PRIVATE
        )

        with(sharedPreferences.edit()) {
            if(password.isNotEmpty()) {
                putString(context.getString(R.string.preference_password_key), password)
            }else{
                remove(context.getString(R.string.preference_password_key))
            }
            apply()
        }
    }
}