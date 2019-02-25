package com.rynkbit.coffeestore.io

import android.os.Environment
import java.io.File
import java.util.*

class DirectoryReader() {
    private val directory: File =
        Environment.getExternalStorageDirectory()
    private val directoryStack: Stack<File> = Stack()

    var currentDirectory = directory
    val directories: List<File>
        get() {
            val tmpDirectories = mutableListOf<File>()
            val directoryStrings = currentDirectory.list { dir, name -> dir != null && dir.isDirectory }

            if(directoryStrings != null) {
                for (dirString in directoryStrings) {
                    tmpDirectories.add(File(dirString))
                }
            }

            return tmpDirectories
        }

    fun setDirectory(directory: File){
        directoryStack.push(currentDirectory)
        currentDirectory = directory
    }

    fun back(){
        if(currentDirectory.path != directory.path) {
            currentDirectory = directoryStack.pop()
        }
    }
}