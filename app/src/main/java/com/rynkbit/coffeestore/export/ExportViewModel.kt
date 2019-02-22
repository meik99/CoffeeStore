package com.rynkbit.coffeestore.export

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rynkbit.coffeestore.data.AppDatabaseStore
import com.rynkbit.coffeestore.data.entity.CustomerTransaction
import com.rynkbit.coffeestore.data.repository.CustomerRepository
import com.rynkbit.coffeestore.data.repository.CustomerTransactionRepository
import com.rynkbit.coffeestore.data.repository.ProductRepository
import java.util.*

class ExportViewModel: ViewModel(){
    fun findByDateSpan(): LiveData<List<CustomerTransaction>> {
        if(fromDate != null && toDate != null) {
            return customerTransactionRepository.findByDateSpan(fromDate!!, toDate!!)
        }else{
            return MutableLiveData<List<CustomerTransaction>>()
        }
    }

    private val customerTransactionRepository: CustomerTransactionRepository =
            CustomerTransactionRepository(AppDatabaseStore.instance.appDatabase)
    private val customerRepository: CustomerRepository =
            CustomerRepository(AppDatabaseStore.instance.appDatabase)
    private val productRepository: ProductRepository =
            ProductRepository(AppDatabaseStore.instance.appDatabase)

    val customerTransactions = customerTransactionRepository.customerTransactions
    val customers = customerRepository.customers
    val products = productRepository.products

    var fromDate: Date? = null
        set(value) {
            oldFromDate = field
            field = value
        }
    var toDate: Date? = null
        set(value) {
            oldToDate = field
            field = value
        }

    var oldFromDate: Date? = null
    var oldToDate: Date? = null
}