package com.rynkbit.coffeestore

import android.app.Activity
import android.view.Window
import android.view.WindowManager

class NavbarHider(val activity: Activity) {
    fun hideNavbar(){
        //Remove title bar
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //Remove notification bar
        activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}