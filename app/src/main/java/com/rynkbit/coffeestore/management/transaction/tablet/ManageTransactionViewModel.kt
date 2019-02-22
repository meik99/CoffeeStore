package com.rynkbit.coffeestore.management.transaction.tablet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rynkbit.coffeestore.data.AppDatabaseStore
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.data.entity.CustomerTransaction
import com.rynkbit.coffeestore.data.entity.Product
import com.rynkbit.coffeestore.data.entity.TransactionState
import com.rynkbit.coffeestore.data.repository.CustomerRepository
import com.rynkbit.coffeestore.data.repository.CustomerTransactionRepository
import com.rynkbit.coffeestore.data.repository.ProductRepository
import java.util.*

class ManageTransactionViewModel : ViewModel() {


    private val customerTransactionRepository: CustomerTransactionRepository = CustomerTransactionRepository(AppDatabaseStore.instance.appDatabase)
    private val productRepository: ProductRepository = ProductRepository(AppDatabaseStore.instance.appDatabase)
    private val customerRepository: CustomerRepository = CustomerRepository(AppDatabaseStore.instance.appDatabase)

    val customerTransactions = customerTransactionRepository.customerTransactions
    val prodcts = productRepository.products
    val customers = customerRepository.customers

    val selectedTransaction: MutableLiveData<CustomerTransaction> = MutableLiveData()
    var selectedProduct: Product = Product(-1, "", 0.toDouble(), 0)
    var selectedCustomer: Customer = Customer(-1, "")

    init {
        selectedTransaction.value =
            CustomerTransaction(-1, -1, -1, TransactionState.CLOSED, Date())
    }

    fun revokeTransaction(): Boolean {
        val customerTransaction = selectedTransaction.value

        if(customerTransaction != null && selectedCustomer.id > -1 && selectedProduct.id > -1){
            customerTransactionRepository.revokeTransaction(
                customerTransaction,
                selectedCustomer,
                selectedProduct,
                customerRepository,
                productRepository
            )
            return true
        }
        return false
    }

    fun closeTransaction(): Boolean {
        val customerTransaction = selectedTransaction.value

        if(customerTransaction != null && selectedCustomer.id > -1 && selectedProduct.id > -1){
            customerTransactionRepository.closeTransaction(
                customerTransaction,
                selectedCustomer,
                selectedProduct,
                customerRepository
            )
            return true
        }
        return false
    }

    fun openTransaction(): Boolean {
        val customerTransaction = selectedTransaction.value

        if(customerTransaction != null && selectedCustomer.id > -1 && selectedProduct.id > -1){
            customerTransactionRepository.openTransaction(
                customerTransaction,
                selectedCustomer,
                selectedProduct,
                customerRepository
            )
            return true
        }
        return false
    }
}
