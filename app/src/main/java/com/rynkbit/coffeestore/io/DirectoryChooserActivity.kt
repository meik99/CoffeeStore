package com.rynkbit.coffeestore.io

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeestore.NavbarHider
import com.rynkbit.coffeestore.R
import kotlinx.android.synthetic.main.activity_directory_chooser.*

class DirectoryChooserActivity : AppCompatActivity() {
    companion object {
        val resultDirectoryChosen = 0
        val extratDirectoryChosen = "directory"
    }

    val directoryReader = DirectoryReader()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavbarHider(this).hideNavbar()
        setContentView(R.layout.activity_directory_chooser)

        listFolders.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        listFolders.adapter = DirectoryAdapter(directoryReader)
        listFolders.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        listFolders.itemAnimator = DefaultItemAnimator()

        btnCancel.setOnClickListener {
            finish()
        }
        btnAccept.setOnClickListener {
            setResult(resultDirectoryChosen,
                Intent().putExtra(
                    extratDirectoryChosen,
                    directoryReader.currentDirectory.absolutePath.substring(
                        DirectoryReader.rootDirectory.absolutePath.length
                    )))
            finish()
        }
    }
}
