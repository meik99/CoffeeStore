package com.rynkbit.coffeestore.data.repository

import androidx.lifecycle.LiveData
import com.rynkbit.coffeestore.data.AppDatabase
import com.rynkbit.coffeestore.data.entity.Product

class ProductRepository(db: AppDatabase): Repository<Product>(db.productDao()) {
    val products: LiveData<List<Product>> = dao.findAll()
}