package com.rynkbit.coffeestore.test

import com.rynkbit.coffeestore.data.AppDatabaseStore
import com.rynkbit.coffeestore.data.entity.Customer
import com.rynkbit.coffeestore.data.repository.CustomerRepository

class CustomerGenerator{
    val CUSTOMER_COUNT = 1_000

    fun generateCustomers(){
        for(i in 0..CUSTOMER_COUNT-1) {
            CustomerRepository(AppDatabaseStore.instance.appDatabase)
                .insert(Customer(0, "Customer $i"))
        }
    }
}