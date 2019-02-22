package com.rynkbit.coffeestore.management.transaction.tablet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.data.entity.CustomerTransaction
import com.rynkbit.coffeestore.data.entity.Product
import kotlinx.android.synthetic.main.item_transaction.view.*
import java.text.SimpleDateFormat

class ManageTransactionAdapter(val viewModel: ManageTransactionViewModel): RecyclerView.Adapter<ManageTransactionViewHolder>() {
    var transactions: List<CustomerTransaction> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var products: List<Product> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var customers: List<Customer> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageTransactionViewHolder {
        return ManageTransactionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        )
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: ManageTransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        val product = products.singleOrNull { it.id == transaction.productId }
        val customer = customers.singleOrNull { it.id == transaction.customerId }

        holder.txtTransactionDate.text = SimpleDateFormat.getDateTimeInstance().format(transaction.date)
        holder.txtTransactionStatus.text =
            holder.itemView.context.getString(transaction.transactionState.resourceStringId)

        if(customer != null){
            holder.txtCustomerName.text = customer.name
        }else{
            holder.txtCustomerName.setText(R.string.customer_not_found)
        }
        if(product != null){
            holder.txtProductName.text = product.name
        }else{
            holder.txtProductName.setText(R.string.product_not_found)
        }

        holder.itemView.setOnClickListener { viewModel.selectedTransaction.value = transaction }
    }

}

class ManageTransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val context: Context = itemView.context
    val txtCustomerName = itemView.txtTransactionCustomerName
    val txtProductName = itemView.txtTransactionProductName
    val txtTransactionDate = itemView.txtItemTransactionDate
    val txtTransactionStatus = itemView.txtItemTransactionStatus
}