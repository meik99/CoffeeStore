package com.rynkbit.coffeestore.password

import android.content.Context
import androidx.lifecycle.ViewModel
import com.rynkbit.coffeestore.R

class PasswordViewModel: ViewModel(){
    var password: String = ""

    fun savePassword(context: Context){
        if(password.isNotEmpty()) {
            val sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_password_key), Context.MODE_PRIVATE
            )

            with(sharedPreferences.edit()) {
                putString(context.getString(R.string.preference_password_key), password)
                apply()
            }
        }
    }
}