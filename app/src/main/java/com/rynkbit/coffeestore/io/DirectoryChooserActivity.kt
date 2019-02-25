package com.rynkbit.coffeestore.io

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavbarHider(this).hideNavbar()
        setContentView(R.layout.activity_directory_chooser)

        listFolders.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        listFolders.adapter = DirectoryAdapter(DirectoryReader())
        listFolders.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        listFolders.itemAnimator = DefaultItemAnimator()
    }
}
