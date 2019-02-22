package com.rynkbit.coffeestore.customer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rynkbit.coffeestore.data.AppDatabaseStore
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.data.entity.CustomerTransaction
import com.rynkbit.coffeestore.data.entity.Product
import com.rynkbit.coffeestore.data.repository.CustomerRepository
import com.rynkbit.coffeestore.data.repository.CustomerTransactionRepository
import com.rynkbit.coffeestore.data.repository.ProductRepository

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val customerRepository: CustomerRepository = CustomerRepository(AppDatabaseStore.instance.appDatabase)
    private val customerTransactionRepository: CustomerTransactionRepository = CustomerTransactionRepository(AppDatabaseStore.instance.appDatabase)
    private val productRepository: ProductRepository = ProductRepository(AppDatabaseStore.instance.appDatabase)

    val customers: LiveData<List<Customer>> = customerRepository.customers
    val customerTransaction: LiveData<List<CustomerTransaction>> = customerTransactionRepository.customerTransactions
    val products: LiveData<List<Product>> = productRepository.products
}