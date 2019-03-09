package com.rynkbit.coffeestore.backup

import androidx.lifecycle.ViewModel
import com.rynkbit.coffeestore.io.DirectoryReader

class BackupViewModel: ViewModel(){
    var writeBackupFlag = false
    var backupPath = DirectoryReader.rootDirectory
}