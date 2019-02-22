package com.rynkbit.coffeestore.management.product.tablet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.data.entity.Product
import java.text.NumberFormat

class ManageProductsAdapter(val viewModel: ManageProductsViewModel): RecyclerView.Adapter<ManageProductsViewHolder>() {
    var products = listOf<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageProductsViewHolder {
        return ManageProductsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ManageProductsViewHolder, position: Int) {
        val product = products[position]
        holder.txtProductName.text = product.name
        holder.txtProductStock.text = holder.itemView.context.getString(R.string.in_stock, product.stock)
        holder.txtProductPrice.text = NumberFormat.getCurrencyInstance().format(product.price)
        holder.itemView.setOnClickListener {
            viewModel.selectedProduct.value = product
        }
    }

}

class ManageProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txtProductName: TextView = itemView.findViewById(R.id.txtCustomerBalance)
    val txtProductStock: TextView = itemView.findViewById(R.id.txtProductStock)
    val txtProductPrice: TextView = itemView.findViewById(R.id.txtProductPrice)
}