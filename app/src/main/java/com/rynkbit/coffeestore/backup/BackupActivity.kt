package com.rynkbit.coffeestore.backup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModelProviders
import com.rynkbit.coffeestore.NavbarHider
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.io.DirectoryChooserActivity
import com.rynkbit.coffeestore.io.DirectoryReader
import kotlinx.android.synthetic.main.activity_backup.*
import java.io.File

class BackupActivity : AppCompatActivity() {

    companion object {
        val requestReadStorage = 5
    }

    lateinit var viewmodel: BackupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavbarHider(this).hideNavbar()
        setContentView(R.layout.activity_backup)

        viewmodel = ViewModelProviders.of(this).get(BackupViewModel::class.java)

        btnSelectReadPath.setOnClickListener {
            viewmodel.writeBackupFlag = false
            startDirectoryChooserActivity()
        }
        btnSelectWritePath.setOnClickListener {
            viewmodel.writeBackupFlag = true
            startDirectoryChooserActivity()
        }
        updateReadPath()
        updateWritePath()
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
        startActivityForResult(
            Intent(this, DirectoryChooserActivity::class.java),
            DirectoryChooserActivity.resultDirectoryChosen
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(resultCode){
            DirectoryChooserActivity.resultDirectoryChosen -> {
                viewmodel.backupPath = File(
                    DirectoryReader.rootDirectory.absolutePath +
                            data?.getStringExtra(DirectoryChooserActivity.extratDirectoryChosen))
                updatePathLabel()
            }
        }
    }

    private fun updatePathLabel() {
        if(!viewmodel.writeBackupFlag){
            updateReadPath()
        }else{
            updateWritePath()
        }
    }

    private fun updateWritePath() {
        txtWritePath.text = viewmodel.backupPath.absolutePath
    }

    private fun updateReadPath() {
        txtReadPath.text = viewmodel.backupPath.absolutePath
    }
}
