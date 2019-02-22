package com.rynkbit.coffeestore.data.repository.tasks

import android.os.AsyncTask
import com.rynkbit.coffeestore.data.dao.CustomerTransactionDao
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.data.entity.TransactionState

class SetTransactionStateAsyncTask(
    private val customerTransactionDao: CustomerTransactionDao,
    private val newTransactionState: TransactionState): AsyncTask<Customer, Void, Boolean>() {

    override fun doInBackground(vararg params: Customer): Boolean {
        customerTransactionDao.setTransactionStateByCustomerId(params[0].id, newTransactionState)
        return true
    }

}