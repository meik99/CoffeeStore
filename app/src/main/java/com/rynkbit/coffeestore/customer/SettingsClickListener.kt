package com.rynkbit.coffeestore.customer

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.settings.SettingsActivity

class SettingsClickListener: View.OnClickListener{
    override fun onClick(v: View?) {
        val context = v?.context

        if(context != null) {
            val sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_password_key),
                Context.MODE_PRIVATE
            )
            val savedPassword = sharedPreferences.getString(
                context.getString(R.string.preference_password_key),
                null
            )

            if(savedPassword == null || savedPassword.isEmpty()){
                openSettings(v)
            } else {
                val passwordDialog = EnterPasswordDialog(context)
                passwordDialog.password.observeForever(object : Observer<String> {
                    override fun onChanged(it: String?) {
                        passwordDialog.password.removeObserver(this)
                        if(it != null){
                            if(it == savedPassword){
                                openSettings(v)
                            }
                        }
                    }

                })
                passwordDialog.show()
            }
        }
    }

    private fun openSettings(v: View) {
        v.context?.startActivity(
            Intent(v.context, SettingsActivity::class.java)
        )
    }
}