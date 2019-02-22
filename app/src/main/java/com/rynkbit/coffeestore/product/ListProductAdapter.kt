package com.rynkbit.coffeestore.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.data.entity.Product
import java.text.NumberFormat

class ListProductAdapter : RecyclerView.Adapter<ListProductViewHolder>() {
    var products = listOf<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var customer: Customer = Customer(0, "Invalid Customer")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListProductViewHolder {
        return ListProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ListProductViewHolder, position: Int) {
        val product = products[position]

        holder.txtProductName.text = product.name
        holder.txtProductPrice.text = NumberFormat.getCurrencyInstance().format(
            product.price
        )
        holder.txtProductStock.text = holder.itemView.context.getString(
            R.string.in_stock, product.stock
        )

        if(product.stock <= 0){
            holder.itemView.setBackgroundColor(
                holder.itemView.resources.getColor(R.color.mtrl_btn_bg_color_disabled)
            )
        } else {
            holder.itemView.setOnClickListener(ListProductClickListener(customer, product))
        }
    }
}

class ListProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txtProductName: TextView = itemView.findViewById(R.id.txtCustomerBalance)
    val txtProductPrice: TextView = itemView.findViewById(R.id.txtProductPrice)
    val txtProductStock: TextView = itemView.findViewById(R.id.txtProductStock)
}