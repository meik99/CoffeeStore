package com.rynkbit.coffeestore.management.customer.tablet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.data.entity.Customer
import java.text.NumberFormat

class ManageCustomerAdapter(val viewModel: ManageCustomersViewModel): RecyclerView.Adapter<ManageCustomerViewHolder>() {
    var customers: List<Customer> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageCustomerViewHolder {
        return ManageCustomerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_customer, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    override fun onBindViewHolder(holder: ManageCustomerViewHolder, position: Int) {
        val customer = customers[position]
        holder.txtCustomerName.text = customer.name
        holder.txtCustomerBalance.text = NumberFormat.getCurrencyInstance().format(customer.balance)
        holder.itemView.setOnClickListener {
            viewModel.selectedCustomer.value = customer
        }
    }

}

class ManageCustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txtCustomerName: TextView = itemView.findViewById(R.id.txtTransactionCustomerName)
    val txtCustomerBalance: TextView = itemView.findViewById(R.id.txtCustomerBalance)
}
