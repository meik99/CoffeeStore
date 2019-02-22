package com.rynkbit.coffeestore.management.customer.tablet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.rynkbit.coffeestore.data.AppDatabaseStore
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.data.repository.CustomerRepository
import com.rynkbit.coffeestore.data.repository.CustomerTransactionRepository
import com.rynkbit.coffeestore.data.repository.ProductRepository

class ManageCustomersViewModel : ViewModel() {
    private val customerRepository: CustomerRepository = CustomerRepository(AppDatabaseStore.instance.appDatabase)
    private val productRepository: ProductRepository = ProductRepository(AppDatabaseStore.instance.appDatabase)
    private val customerTransactionRepository: CustomerTransactionRepository = CustomerTransactionRepository(AppDatabaseStore.instance.appDatabase)

    val customers = customerRepository.customers

    var selectedCustomer: MutableLiveData<Customer> = MutableLiveData()

    init {
        selectedCustomer.value = Customer(-1, "")
    }

    fun insertCustomer(customer: Customer) {
        customerRepository.insert(customer)
    }

    fun updateCustomer(customer: Customer) {
        customerRepository.update(customer)
    }

    fun deleteCustomer(customer: Customer) {
        customerRepository.delete(customer)
    }

    fun clearBalance() {
        val customer = selectedCustomer.value

        if(customer != null) {
            customerTransactionRepository.clearBalance(
                customer,
                customerRepository)
        }
    }
}
