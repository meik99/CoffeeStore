package com.rynkbit.coffeestore.data.repository

import androidx.lifecycle.LiveData
import com.rynkbit.coffeestore.data.AppDatabase
import com.rynkbit.coffeestore.data.dao.CustomerTransactionDao
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.data.entity.CustomerTransaction
import com.rynkbit.coffeestore.data.entity.Product
import com.rynkbit.coffeestore.data.entity.TransactionState
import com.rynkbit.coffeestore.data.repository.tasks.InsertAsyncTask
import com.rynkbit.coffeestore.data.repository.tasks.SetTransactionStateAsyncTask
import com.rynkbit.coffeestore.data.repository.tasks.UpdateAsyncTask
import java.util.*

class CustomerTransactionRepository(db: AppDatabase) : Repository<CustomerTransaction>(db.customerTransactionDao()) {
    val customerTransactionDao: CustomerTransactionDao = dao as CustomerTransactionDao
    val customerTransactions: LiveData<List<CustomerTransaction>> = dao.findAll()

    fun findByDateSpan(fromDate: Date, toDate: Date): LiveData<List<CustomerTransaction>> {
        return customerTransactionDao.findByDateSpan(fromDate.time, toDate.time)
    }

    fun performTransaction(
        customer: Customer,
        product: Product,
        customerRepository: Repository<Customer>,
        productRepository: Repository<Product>
    ) {
        InsertAsyncTask(dao).execute(
            CustomerTransaction(
                0, customer.id, product.id, TransactionState.OPEN, Date()
            )
        ).get()
        customer.balance += product.price
        product.stock--
        customerRepository.update(customer)
        productRepository.update(product)
    }

    fun clearBalance(
        customer: Customer,
        customerRepository: Repository<Customer>
    ) {
        customer.balance = 0.toDouble()
        SetTransactionStateAsyncTask(customerTransactionDao, TransactionState.CLOSED).execute(customer)
        UpdateAsyncTask<Customer>(customerRepository.dao).execute(customer)
    }

    fun revokeTransaction(
        customerTransaction: CustomerTransaction,
        customer: Customer,
        product: Product,
        customerRepository: Repository<Customer>,
        productRepository: Repository<Product>
    ) {
        product.stock++
        customer.balance -= product.price

        UpdateAsyncTask(customerRepository.dao).execute(customer)
        UpdateAsyncTask(productRepository.dao).execute(product)

        setTransactionState(customerTransaction, TransactionState.REVOKED)
    }

    fun closeTransaction(
        customerTransaction: CustomerTransaction,
        customer: Customer,
        product: Product,
        customerRepository: Repository<Customer>
    ) {
        customer.balance -= product.price

        UpdateAsyncTask(customerRepository.dao).execute(customer)
        setTransactionState(customerTransaction, TransactionState.CLOSED)
    }

    fun openTransaction(
        customerTransaction: CustomerTransaction,
        customer: Customer,
        product: Product,
        customerRepository: Repository<Customer>
    ) {
        customer.balance += product.price
        customerTransaction.transactionState = TransactionState.OPEN

        UpdateAsyncTask(customerRepository.dao).execute(customer)
        setTransactionState(customerTransaction, TransactionState.OPEN)
    }

    private fun setTransactionState(
        customerTransaction: CustomerTransaction,
        transactionState: TransactionState
    ) {
        customerTransaction.transactionState = transactionState
        UpdateAsyncTask(customerTransactionDao).execute(customerTransaction)
    }

}