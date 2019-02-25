package com.rynkbit.coffeestore.io

import android.os.Environment
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeestore.R
import java.io.File

class DirectoryAdapter(val directoryReader: DirectoryReader): RecyclerView.Adapter<DirectoryViewHolder>() {
    private val animationSleepTime: Long = 200

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryViewHolder {
        return DirectoryViewHolder(
            LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        ).apply {
            val typedValue: TypedValue = TypedValue()

            itemView.isFocusable = true
            itemView.isClickable = true
            itemView.context.theme.resolveAttribute(
                android.R.attr.selectableItemBackground, typedValue, true)
            itemView.setBackgroundResource(typedValue.resourceId)
        }
    }

    override fun getItemCount(): Int {
        return directoryReader.directories.size + 1
    }

    override fun onBindViewHolder(holder: DirectoryViewHolder, position: Int) {
        if(position == 0){
            bindBackButton(holder, position)
        }else{
            bindFolder(holder, position-1)
        }
    }

    private fun bindBackButton(holder: DirectoryViewHolder, position: Int) {
        holder.txtText1.text = holder.itemView.context.getString(R.string.previous_directory)

        holder.itemView.setOnClickListener {
            Thread.sleep(animationSleepTime)
            directoryReader.back()
            notifyDataSetChanged()
        }
    }

    private fun bindFolder(holder: DirectoryViewHolder, position: Int) {
        val directory = directoryReader.directories[position]
        holder.txtText1.text = directory.name
        holder.directory = directory

        holder.itemView.setOnClickListener {
            if(holder.directory != null) {
                Thread.sleep(animationSleepTime)
                directoryReader.setDirectory(Environment.getExternalStorageDirectory().resolve(holder.directory!!))
                notifyDataSetChanged()
            }
        }
    }

}
class DirectoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txtText1 = itemView.findViewById<TextView>(android.R.id.text1)
    var directory: File? = null
}