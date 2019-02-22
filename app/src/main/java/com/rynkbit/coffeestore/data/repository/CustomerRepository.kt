package com.rynkbit.coffeestore.data.repository

import androidx.lifecycle.LiveData
import com.rynkbit.coffeestore.data.AppDatabase
import com.rynkbit.coffeestore.data.AppDatabaseStore
import com.rynkbit.coffeestore.data.dao.CustomerDao
import com.rynkbit.coffeestore.data.dao.GenericDao
import com.rynkbit.coffeestore.data.entity.Customer

class CustomerRepository(db: AppDatabase) : Repository<Customer>(db.customerDao()) {
    val customers: LiveData<List<Customer>> = dao.findAll()


}