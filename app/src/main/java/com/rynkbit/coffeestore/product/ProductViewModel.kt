package com.rynkbit.coffeestore.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rynkbit.coffeestore.data.AppDatabaseStore
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.data.repository.CustomerRepository
import com.rynkbit.coffeestore.data.repository.ProductRepository

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepository = ProductRepository(AppDatabaseStore.instance.appDatabase)
    private val customerRepository = CustomerRepository(AppDatabaseStore.instance.appDatabase)

    val products = productRepository.products

    var customerId: Int = -1
    val customer: LiveData<Customer>
        get() {
            return if(customerId > 0){
                customerRepository.findById(customerId)
            } else {
                val livedata = MutableLiveData<Customer>()
                livedata.value = Customer(0, "Invalid Customer")
                livedata
            }
        }
}