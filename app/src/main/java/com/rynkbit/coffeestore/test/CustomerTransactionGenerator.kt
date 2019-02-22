package com.rynkbit.coffeestore.test

import com.rynkbit.coffeestore.data.AppDatabaseStore
import com.rynkbit.coffeestore.data.entity.CustomerTransaction
import com.rynkbit.coffeestore.data.entity.TransactionState
import com.rynkbit.coffeestore.data.repository.CustomerTransactionRepository
import java.util.*

class CustomerTransactionGenerator {
    val transactionCount = 100_000

    fun generate() {
        for (i in 0..transactionCount - 1) {

            CustomerTransactionRepository(AppDatabaseStore.instance.appDatabase)
                .insert(
                    CustomerTransaction(
                        0,
                        (i + 1) % (CustomerGenerator().CUSTOMER_COUNT-1) + 1,
                        (i + 1) % (ProductGenerator().PRODUCT_COUNT-1) + 1,
                        TransactionState.values()[(i + 1) % 3],
                        Date()
                    )
                )
        }
    }
}
