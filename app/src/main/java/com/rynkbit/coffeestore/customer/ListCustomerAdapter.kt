package com.rynkbit.coffeestore.customer

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.data.entity.Customer
import java.text.NumberFormat

class ListCustomerAdapter : RecyclerView.Adapter<ListCustomerViewHolder>() {
    var customers = listOf<Customer>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCustomerViewHolder {
        return ListCustomerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_customer, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return customers.size + 1
    }

    override fun onBindViewHolder(holder: ListCustomerViewHolder, position: Int) {
        if(position == 0){
            holder.itemView.setBackgroundColor(ResourcesCompat.getColor(
                holder.itemView.resources,
                R.color.colorPrimary,
                null))
            holder.txtCustomerName.setTextColor(Color.WHITE)
            holder.txtCustomerName.text = holder.itemView.context.getString(R.string.settings)
            holder.txtCustomerBalance.text = String()
            holder.itemView.setOnClickListener(SettingsClickListener())
        } else {
            holder.itemView.setBackgroundColor(ResourcesCompat.getColor(
                holder.itemView.resources,
                R.color.cardview_light_background,
                null))
            holder.txtCustomerName.setTextColor(ResourcesCompat.getColor(
                holder.itemView.resources,
                R.color.material_grey_600,
                null))
            val customer = customers[position - 1]
            holder.txtCustomerName.text = customer.name
            holder.txtCustomerBalance.text = NumberFormat.getCurrencyInstance().format(customer.balance)
            holder.itemView.setOnClickListener(CustomerClickListener(customer))
        }
    }

}

class ListCustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txtCustomerName: TextView = itemView.findViewById(R.id.txtTransactionCustomerName)
    val txtCustomerBalance: TextView = itemView.findViewById(R.id.txtCustomerBalance)
}