package com.rynkbit.coffeestore.management.product.tablet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rynkbit.coffeestore.data.AppDatabaseStore
import com.rynkbit.coffeestore.data.entity.Product
import com.rynkbit.coffeestore.data.repository.ProductRepository

class ManageProductsViewModel: ViewModel() {
    fun saveProduct(product: Product) {
        productsRepository.update(product)
        selectedProduct.value = product
    }

    fun deleteProduct(product: Product) {
        productsRepository.delete(product)
        selectedProduct.value = Product(-1, String(), 0.toDouble(), 0)
    }

    fun insertProduct(product: Product) {
        product.id = 0
        productsRepository.insert(product)
    }

    private var productsRepository = ProductRepository(AppDatabaseStore.instance.appDatabase)

    val products = productsRepository.products
    val selectedProduct: MutableLiveData<Product> = MutableLiveData()

    init {
        selectedProduct.value = Product(-1, "", 0.toDouble(), 0)
    }
}