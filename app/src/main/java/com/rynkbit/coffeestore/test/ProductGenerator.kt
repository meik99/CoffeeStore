package com.rynkbit.coffeestore.test

import com.rynkbit.coffeestore.data.AppDatabaseStore
import com.rynkbit.coffeestore.data.entity.Product
import com.rynkbit.coffeestore.data.repository.ProductRepository

class ProductGenerator{
    val PRODUCT_COUNT = 1_000

    fun generateProducts(){
        for(i in 0..PRODUCT_COUNT-1) {
            ProductRepository(AppDatabaseStore.instance.appDatabase)
                .insert(Product(0, "Product $i", 1.toDouble() * i, (i+1) * 10))
        }
    }
}