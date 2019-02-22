package com.rynkbit.coffeestore.management.product.tablet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rynkbit.coffeestore.R
import com.rynkbit.coffeestore.toInternationalDouble
import kotlinx.android.synthetic.main.fragment_manage_products_tablet.*
import java.text.NumberFormat

class ManageProductsFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() =
            ManageProductsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_manage_products_tablet, container, false)
    }

    lateinit var viewModel: ManageProductsViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ManageProductsViewModel::class.java)

        val productAdapter = ManageProductsAdapter(viewModel)

        listProducts.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        listProducts.adapter = productAdapter

        viewModel.products.observe(
            this,
            Observer {
                productAdapter.products = it

                if(it.isNotEmpty()){
                    listProducts.visibility = View.VISIBLE
                    txtNoProductsFound.visibility = View.GONE
                }else{
                    listProducts.visibility = View.GONE
                    txtNoProductsFound.visibility = View.VISIBLE
                }
            }
        )

        viewModel.selectedProduct.observe(
            this,
            Observer {product ->
                btnSave.isEnabled = product.id > -1
                btnDelete.isEnabled = product.id > -1

                if(product.id > -1){
                    editProductName.setText(product.name, TextView.BufferType.EDITABLE)
                    editProductStock.setText(product.stock.toString(), TextView.BufferType.EDITABLE)
                    editProductPrice.setText(NumberFormat.getInstance().format(product.price), TextView.BufferType.EDITABLE)
                }else{
                    editProductName.setText(String(), TextView.BufferType.EDITABLE)
                    editProductStock.setText(String(), TextView.BufferType.EDITABLE)
                    editProductPrice.setText(String(), TextView.BufferType.EDITABLE)
                }

                btnSave.setOnClickListener {
                    viewModel.saveProduct(product)
                }

                btnDelete.setOnClickListener(DeleteProductOnClickListener(viewModel, product))

                fabAddProduct.setOnClickListener {
                    viewModel.insertProduct(product)
                }
            }
        )

        editProductName.addTextChangedListener { viewModel.selectedProduct.value?.name = it.toString() }
        editProductStock.addTextChangedListener { 
            if(it != null && it.isNotBlank()) { viewModel.selectedProduct.value?.stock = it.toString().toInt() }
            else { viewModel.selectedProduct.value?.stock = 0 }}
        editProductPrice.addTextChangedListener {
            if(it != null && it.isNotBlank()) {
                if(it.startsWith(".") || it.startsWith(",")){
                    it.insert(0, "0")

                    if(it.endsWith(".") || it.endsWith(",")){
                        it.append("0")
                    }
                }
                viewModel.selectedProduct.value?.price = it.toString().toInternationalDouble()
            }
            else { viewModel.selectedProduct.value?.price = 0.toDouble() }
        }
    }
}
