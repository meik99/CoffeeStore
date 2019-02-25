package com.rynkbit.coffeestore.backup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import com.rynkbit.coffeestore.NavbarHider
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.io.DirectoryChooserActivity
import kotlinx.android.synthetic.main.activity_backup.*

class BackupActivity : AppCompatActivity() {

    companion object {
        val requestReadStorage = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavbarHider(this).hideNavbar()
        setContentView(R.layout.activity_backup)

        btnSelectReadPath.setOnClickListener {
            startDirectoryChooserActivity()
        }
        btnSelectWritePath.setOnClickListener {
            startDirectoryChooserActivity()
        }


    }

    private fun startDirectoryChooserActivity(){
        if(PermissionChecker.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            == PermissionChecker.PERMISSION_GRANTED){
            startDirectoryChooserIntent()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), requestReadStorage)
            } else {
                startDirectoryChooserIntent()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            requestReadStorage -> {
                if(grantResults[0] == PermissionChecker.PERMISSION_GRANTED){
                    startDirectoryChooserIntent()
                }
            }
        }
    }

    private fun startDirectoryChooserIntent(){
        startActivity(
            Intent(this, DirectoryChooserActivity::class.java)
        )
    }
}
